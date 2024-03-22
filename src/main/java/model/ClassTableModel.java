package model;

import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClassTableModel {
    
    public DefaultTableModel setTableShipper(List<Shipper> listItem, String[] listColumn ) {
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
        if(rows > 0) {
            for(int i =0; i< rows; i++) {
                Shipper shipper = listItem.get(i);
                obj = new Object[columns];
                obj[0] = shipper.getShipper_Id();
                obj[1] = shipper.getName();
                obj[2] = shipper.getBirthDay();
                obj[3] = shipper.getGender();
                obj[4] = shipper.getStartWork();
                obj[5] = shipper.getPhone();
                obj[6] = shipper.getEmail();
                obj[7] = shipper.getAddress();
                obj[8] = shipper.getDescription();
                
                dtm.addRow(obj);
            }
        }
        return dtm;
        
    }
    
}
