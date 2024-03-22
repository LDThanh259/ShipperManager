/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Dao.OrderServiceDao;
import Dao.OrderServiceDaoImpl;
import Dao.ShipperServiceDao;
import Dao.ShipperServiceDaoImpl;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Order;

/**
 *
 * @author ADMIN
 */
public class OrderController {

    private JButton btnSubmit;
    private JDateChooser jdcDT;
    private JDateChooser jdcRT;
    private JRadioButton jrbPending;
    private JRadioButton jrbProcessing;
    private JRadioButton jrbCompleted;
    private JTextArea jtaDL;
    private JTextArea jtaRL;
    private JTextArea jtaFeedback;
    private JLabel jlbID;
    private JTextField jtfName;
    private JTextField jtfDistance;
    private JTextField jtfPrice;
    private JLabel jlbMsg;
    private JButton btnDelete;

    private JComboBox<Integer> jcbList_id;  
    private JButton btnRandom;    
    private ShipperServiceDao shipperServiceDao = null; 

    private Order order = null;
    private OrderServiceDao orderServiceDao = null;

    // JFrame insert
    public OrderController(JButton btnSubmit, JDateChooser jdcDT, JDateChooser jdcRT, JRadioButton jrbPending, JRadioButton jrbProcessing, JRadioButton jrbCompleted, JTextArea jtaDL, JTextArea jtaRL, JTextArea jtaFeedback, JTextField jtfName, JTextField jtfDistance, JTextField jtfPrice, JTextField jtfShipper_id, JLabel jlbMsg, JComboBox<Integer> jcbList_id, JButton btnRandom) {
        this.btnSubmit = btnSubmit;
        this.jdcDT = jdcDT;
        this.jdcRT = jdcRT;
        this.jrbPending = jrbPending;
        this.jrbProcessing = jrbProcessing;
        this.jrbCompleted = jrbCompleted;
        this.jtaDL = jtaDL;
        this.jtaRL = jtaRL;
        this.jtaFeedback = jtaFeedback;
        this.jtfName = jtfName;
        this.jtfDistance = jtfDistance;
        this.jtfPrice = jtfPrice;
        this.jlbMsg = jlbMsg;
        this.jcbList_id = jcbList_id;
        this.btnRandom = btnRandom;

        this.order = new Order();
        this.orderServiceDao = new OrderServiceDaoImpl();
        this.shipperServiceDao = new ShipperServiceDaoImpl();

    }

    // JFrame update and delete
    public OrderController(JButton btnSubmit, JDateChooser jdcDT, JDateChooser jdcRT, JRadioButton jrbPending, JRadioButton jrbProcessing, JRadioButton jrbCompleted, JTextArea jtaDL, JTextArea jtaRL, JTextArea jtaFeedback, JLabel jlbID, JTextField jtfName, JTextField jtfDistance, JTextField jtfPrice, JLabel jlbMsg, JButton btnDelete, JComboBox<Integer> jcbList_id, JButton btnRandom) {
        this.btnSubmit = btnSubmit;
        this.jdcDT = jdcDT;
        this.jdcRT = jdcRT;
        this.jrbPending = jrbPending;
        this.jrbProcessing = jrbProcessing;
        this.jrbCompleted = jrbCompleted;
        this.jtaDL = jtaDL;
        this.jtaRL = jtaRL;
        this.jtaFeedback = jtaFeedback;
        this.jlbID = jlbID;
        this.jtfName = jtfName;
        this.jtfDistance = jtfDistance;
        this.jtfPrice = jtfPrice;
        this.jlbMsg = jlbMsg;
        this.btnDelete = btnDelete;
        this.jcbList_id = jcbList_id;
        this.btnRandom = btnRandom;

        this.orderServiceDao = new OrderServiceDaoImpl();
    }

    public void setView(Order order) {
        this.order = order;
        jlbID.setText("" + order.getId());
        jtfName.setText(order.getName());
        jtaDL.setText(order.getDelivery_Location());
        jdcDT.setDate(order.getDelivery_Time());
        jtaRL.setText(order.getReceive_Location());
        jdcRT.setDate(order.getReceive_Time());

        if (order.getStatus() == "Pending") {
            jrbPending.setSelected(true);
            jrbProcessing.setSelected(false);
            jrbCompleted.setSelected(false);
        } else if (order.getStatus() == "Processing") {
            jrbPending.setSelected(false);
            jrbProcessing.setSelected(true);
            jrbCompleted.setSelected(false);
        } else {
            jrbPending.setSelected(false);
            jrbProcessing.setSelected(false);
            jrbCompleted.setSelected(true);
        }
        jtaFeedback.setText(order.getFeedback());
        jtfDistance.setText("" + order.getDistance());
        jtfPrice.setText("" + order.getPrice());
        jcbList_id.setSelectedIndex(order.getShipper_ID());
    }

    public void setEvent(String s) {

        btnRandom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int randomIndex = new Random().nextInt(jcbList_id.getItemCount());
                jcbList_id.setSelectedIndex(randomIndex);
            }

        });

        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Kiểm tra xem các thành phần có giá trị null hay không
                if (jdcDT.getDate() == null || isStatusNull()
                        || jtaDL.getText().isEmpty() || jtaRL.getText().isEmpty()
                        || jtfName.getText().isEmpty() || jtfDistance.getText().isEmpty()
                        || jtfPrice.getText().isEmpty() || (Integer) jcbList_id.getSelectedItem() == null) {
                    jlbMsg.setText("Vui lòng điền đầy đủ thông tin!");
                } else {

                    order.setName(jtfName.getText());
                    order.setDelivery_Location(jtaDL.getText());
                    order.setDelivery_Time(covertDateToDateSql(jdcDT.getDate()));

                    order.setReceive_Location(jtaRL.getText());

                    // Kiểm tra giá trị null trước khi gán
                    Object receiveTimeValue = jdcRT.getDate();
                    if (receiveTimeValue instanceof Date) {
                        order.setReceive_Time(covertDateToDateSql(jdcRT.getDate()));
                    } else {
                        order.setReceive_Time(null);
                    }

                    if (jrbPending.isSelected()) {
                        order.setStatus("Pending");
                    } else if (jrbProcessing.isSelected()) {
                        order.setStatus("Processing");
                    } else if (jrbCompleted.isSelected()) {
                        order.setStatus("Completed");
                    }

                    order.setFeedback(jtaFeedback.getText());
                    order.setDistance(Double.parseDouble(jtfDistance.getText()));
                    order.setPrice(Double.parseDouble(jtfPrice.getText()));
                    Integer selectedOption = (Integer) jcbList_id.getSelectedItem();
                    order.setShipper_ID(selectedOption);

                    if (s.equalsIgnoreCase("UpdateOrDelete") && showDialog("cập nhật")) {
                        int result = orderServiceDao.Update(order);
                        if (result > 0) {
                            jlbMsg.setText("Cập nhật dữ liệu thành công!");
                        } else {
                            jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                        }
                    } else if (s.equalsIgnoreCase("Insert") && showDialog("thêm")) {
                        int result = orderServiceDao.Insert(order);
                        if (result > 0) {
                            jlbMsg.setText("Thêm dữ liệu thành công!");
                        } else {
                            jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                        }
                    }
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnSubmit.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSubmit.setBackground(new Color(100, 221, 83));
            }

        }
        );
        if (s.equalsIgnoreCase("UpdateOrDelete")) {
            btnDelete.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (showDialog("xóa")) {
                        int result = orderServiceDao.Delete(order);
                        if (result > 0) {
                            jlbMsg.setText("Xóa dữ liệu thành công!");
                        } else {
                            jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    btnDelete.setBackground(new Color(205, 0, 0));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btnDelete.setBackground(new Color(255, 0, 0));
                }

            });
        }

    }

    public boolean showDialog(String msg) {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Bạn muốn " + msg + " dữ liệu hay không?", "Thông báo", JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }

    // Phương thức chuyển đổi từ java.util.Date sang java.sql.Date
    public java.sql.Date covertDateToDateSql(Date d) {
        return new java.sql.Date(d.getTime());
    }

    public boolean isStatusNull() {
        return !(jrbPending.isSelected() || jrbProcessing.isSelected() || jrbCompleted.isSelected());
    }

}
