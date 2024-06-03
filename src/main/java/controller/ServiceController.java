package controller;
import Dao.*;
import model.Service;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//import org.apache.commons.lang3.StringUtils;

public class ServiceController{
     private final String MaxDistance_PATTERN = "^[0-9]$";
    private final Pattern pattern_MaxDistance = Pattern.compile(MaxDistance_PATTERN);
    private final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final Pattern pattern_email = Pattern.compile(EMAIL_PATTERN);

    private JButton btnSubmit;
    private JButton btnDelete;
    private JComboBox jcbDistinct;
    private JComboBox jcbProvince;
    private JComboBox jcbWard;
    private JDateChooser jdcDob;
    private JLabel jlbID;
    private JLabel jlbMsg;
    private JTextArea jtaUpdate;
    private JRadioButton jrbBusy;
    private JRadioButton jrbFalse;
    private JRadioButton jrbIdle;
    private JRadioButton jrbNam;
    private JRadioButton jrbNu;
    private JRadioButton jrbTrue;
    private JTextField jtfLiscensePlate;
    private JTextField jtfName;
    private JTextField jtfMaxDistance;
    private JTextField jtfMaxWeight;
    private JTextField jtfPrice;

    private Service service = null;
    private ServiceSERVICEDao serviceSERVICEDao = null;
    // JFrame insert
    public ServiceController(JButton btnSubmit,  JLabel jlbMsg, JTextField jtfName, JTextField jtfMaxDistance,JTextField jtfMaxWeight,JTextField jtfPrice) {
        this.btnSubmit = btnSubmit;
        //this.btnDelete = btnDelete;
        this.jlbMsg = jlbMsg;
       
        this.jtfName = jtfName;
        this.jtfMaxDistance = jtfMaxDistance;
        this.jtfMaxWeight = jtfMaxWeight;
        this.jtfPrice = jtfPrice;

        this.serviceSERVICEDao = new ServiceSERVICEDaoImpl();
        this.service = new Service();
    }
    
    
    // JFrame update and delete
    public ServiceController(JButton btnSubmit, JButton btnDelete,JLabel jlbID, JLabel jlbMsg, JTextField jtfName, JTextField jtfMaxDistance,JTextField jtfMaxWeight,JTextField jtfPrice) {
        this.btnSubmit = btnSubmit;
        this.btnDelete = btnDelete;
        this.jlbID = jlbID;
        this.jlbMsg = jlbMsg;
        this.jtfName = jtfName;
        this.jtfMaxDistance = jtfMaxDistance;
        this.jtfMaxWeight = jtfMaxWeight;
        this.jtfPrice = jtfPrice;
        this.serviceSERVICEDao = new ServiceSERVICEDaoImpl();
    }

    public void setView(Service service) {
        this.service = service;
        jlbID.setText("" + service.getId());
        jtfName.setText(service.getName());
        jtfMaxDistance.setText(service.getMaxDistance() + "");
        jtfMaxWeight.setText(service.getMaxWeight() + "");
        jtfPrice.setText(service.getPrice() + "");
        //AddressController addressController = new AddressController(jcbDistinct, jcbProvince, jcbWard);

    }
    
     public void setEvent(String s) {

        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jtfName.getText().isEmpty() || jtfMaxDistance.getText().isEmpty()
                        || jtfMaxWeight.getText().isEmpty()
                        || jtfPrice.getText().isEmpty()) {
                    jlbMsg.setText("Vui lòng điền đầy đủ thông tin!");
                }     
                else if(!jtfMaxDistance.getText().matches("\\d+")||
                        !jtfMaxWeight.getText().matches("\\d+")||
                        !jtfPrice.getText().matches("\\d+")){
                    jlbMsg.setText("Vui lòng nhập đúng định dạng dữ liệu");
                }
                else {
                    
                    service.setName(jtfName.getText());
                    service.setMaxDistance(Integer.parseInt(jtfMaxDistance.getText()));
                     service.setMaxWeight(Integer.parseInt(jtfMaxWeight.getText()));
                      service.setPrice(Integer.parseInt(jtfPrice.getText()));
                    
                    //LocalDateTime now = LocalDateTime.now();
                    //shipper.setUpdated(jtaUpdate.getText());
//                    shipper.setLicensePlate(jtfLiscensePlate.getText());
//                    //AddressController addressController = new AddressController(jcbDistinct, jcbProvince, jcbWard);
//                    shipper.setProvince((String) jcbProvince.getSelectedItem());
//                    shipper.setDistinct((String) jcbDistinct.getSelectedItem());
//                    shipper.setWard((String) jcbWard.getSelectedItem());
                    if (s.equalsIgnoreCase("UpdateOrDelete") && showDialog("cập nhật")) {
//                        shipper.setUpdated(jtaUpdate.getText());
//                        shipper.setIsDeleted(jrbTrue.isSelected());
                        int result = serviceSERVICEDao.Update(service);
                        if (result > 0) {
                            jlbMsg.setText("Cập nhật dữ liệu thành công!");
                        } else {
                            jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                        }
                    } else if (s.equalsIgnoreCase("Insert") && showDialog("thêm")) {
//                        shipper.setRating(5);
//                        shipper.setIsDeleted(false);
//                        shipper.setWarningCount(0);
//                        //LocalDateTime now = LocalDateTime.now();
//                        shipper.setUpdated("create");
//                        LocalDateTime now = LocalDateTime.now();
//                        shipper.setCreated(now);
//
//                        shipper.setLastAssignedTime(null);
                        int result = serviceSERVICEDao.Insert(service);
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
                //int result = shipperServiceDao.delete(shipper);
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (showDialog("xóa")) {
                        int result = serviceSERVICEDao.Delete(service);
                        if (result > 0) {
                            jlbMsg.setText("Xóa dữ liệu thành công!");
                        } else {
                            jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e
                ) {
                    btnDelete.setBackground(new Color(205, 0, 0));
                }

                @Override
                public void mouseExited(MouseEvent e
                ) {
                    btnDelete.setBackground(new Color(255, 0, 0));
                }

            }
            );
        }
    }
    
    public boolean showDialog(String msg) {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Bạn muốn " + msg + " dữ liệu hay không?", "Thông báo", JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }
}
