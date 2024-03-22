/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class TableOrder {

    public DefaultTableModel setTableOrder(List<Order> listItem, String[] listColumn) {
        //https://docs.oracle.com/javase/8/docs/api/javax/swing/table/DefaultTableModel.html
        DefaultTableModel dtm = new DefaultTableModel() {

            // không cho phép thay dỗi dữ liệu trong ô
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        //DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(listColumn);  // set ten col
        int columns = listColumn.length;
        Object[] obj = null;

        int rows = listItem.size();
        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                Order order = listItem.get(i);
                obj = new Object[columns];
                obj[0] = order.getId();
                obj[1] = order.getName();
                obj[2] = order.getDelivery_Location();
                obj[3] = order.getDelivery_Time();
                obj[4] = order.getReceive_Location();
                obj[5] = order.getReceive_Time() != null ? order.getReceive_Time() : "";
                obj[6] = order.getStatus();
                obj[7] = order.getFeedback();
                obj[8] = order.getDistance();
                obj[9] = order.getPrice();
                obj[10] = order.getShipper_ID();

                dtm.addRow(obj);
            }
        }
        return dtm;

    }
}
