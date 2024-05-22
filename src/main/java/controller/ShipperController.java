package controller;

import AddressAPI.AddressController;
import Dao.ShipperServiceDao;
import Dao.ShipperServiceDaoImpl;
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
import model.Shipper;

public class ShipperController {

    private final String PHONE_PATTERN = "^(0|\\+84)(3[2-9]|5[689]|7[06-9]|8[1-689]|9[0-46-9])[0-9]{7}$";
    private final Pattern pattern_phone = Pattern.compile(PHONE_PATTERN);
    private final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final Pattern pattern_email = Pattern.compile(EMAIL_PATTERN);

    private JButton btnSubmit;
    private JButton btnDelete;
    private JComboBox jcbDistinct;
    private JComboBox jcbProvince;
    private JComboBox jcbWard;
    private JLabel jlbID;
    private JLabel jlbMsg;
    private JRadioButton jrbBusy;
    private JRadioButton jrbFalse;
    private JRadioButton jrbIdle;
    private JRadioButton jrbNam;
    private JRadioButton jrbNu;
    private JRadioButton jrbTrue;
    private JTextField jtfLiscensePlate;
    private JTextField jtfName;
    private JTextField jtfPhone;

    private Shipper shipper = null;
    private ShipperServiceDao shipperServiceDao = null;

    // JFrame insert
    public ShipperController(JButton btnSubmit, JComboBox jcbDistinct, JComboBox jcbProvince, JComboBox jcbWard, JLabel jlbMsg, JRadioButton jrbBusy, JRadioButton jrbIdle, JRadioButton jrbNam, JRadioButton jrbNu, JTextField jtfLiscensePlate, JTextField jtfName, JTextField jtfPhone) {
        this.btnSubmit = btnSubmit;
        //this.btnDelete = btnDelete;
        this.jcbDistinct = jcbDistinct;
        this.jcbProvince = jcbProvince;
        this.jcbWard = jcbWard;
        this.jlbMsg = jlbMsg;
        this.jrbBusy = jrbBusy;
        this.jrbIdle = jrbIdle;
        this.jrbNam = jrbNam;
        this.jrbNu = jrbNu;
        this.jtfLiscensePlate = jtfLiscensePlate;
        this.jtfName = jtfName;
        this.jtfPhone = jtfPhone;

        this.shipperServiceDao = new ShipperServiceDaoImpl();
        this.shipper = new Shipper();
    }

    // JFrame update and delete
    public ShipperController(JButton btnSubmit, JButton btnDelete, JComboBox<String> jcbDistinct, JComboBox<String> jcbProvince, JComboBox<String> jcbWard,  JLabel jlbID, JLabel jlbMsg, JRadioButton jrbBusy, JRadioButton jrbFalse, JRadioButton jrbIdle, JRadioButton jrbNam, JRadioButton jrbNu, JRadioButton jrbTrue, JTextField jtfLiscensePlate, JTextField jtfName, JTextField jtfPhone) {
        this.btnSubmit = btnSubmit;
        this.btnDelete = btnDelete;
        this.jcbDistinct = jcbDistinct;
        this.jcbProvince = jcbProvince;
        this.jcbWard = jcbWard;
        this.jlbID = jlbID;
        this.jlbMsg = jlbMsg;
        this.jrbBusy = jrbBusy;
        this.jrbFalse = jrbFalse;
        this.jrbIdle = jrbIdle;
        this.jrbNam = jrbNam;
        this.jrbNu = jrbNu;
        this.jrbTrue = jrbTrue;
        this.jtfLiscensePlate = jtfLiscensePlate;
        this.jtfName = jtfName;
        this.jtfPhone = jtfPhone;

        this.shipperServiceDao = new ShipperServiceDaoImpl();
    }

    public void setView(Shipper shipper) {
        this.shipper = shipper;
        jlbID.setText("" + shipper.getId());
        jtfName.setText(shipper.getName());
        if (shipper.isGender() == "Male") {
            jrbNam.setSelected(false);
            jrbNu.setSelected(true);
        } else {
            jrbNam.setSelected(true);
            jrbNu.setSelected(false);
        } 
        
        if (shipper.getStatus() == "Active") {
            jrbBusy.setSelected(false);
            jrbIdle.setSelected(true);
        } else  {
            jrbBusy.setSelected(true);
            jrbIdle.setSelected(false);
        } 
        
        jtfPhone.setText(shipper.getPhoneNumber() + "");
        jtfLiscensePlate.setText(shipper.getLicensePlate());

        jcbProvince.setSelectedItem(shipper.getProvince());
        jcbDistinct.setSelectedItem(shipper.getDistinct());
        jcbWard.setSelectedItem(shipper.getWard());
        if (shipper.isIsDeleted()) {
            jrbFalse.setSelected(false);
            jrbTrue.setSelected(true);

        } else {
            jrbFalse.setSelected(true);
            jrbTrue.setSelected(false);
        }
    }

    public void setEvent(String s) {

        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jtfName.getText().isEmpty() || jtfPhone.getText().isEmpty()
                        || (!jrbNam.isSelected() && !jrbNu.isSelected())
                        || jcbDistinct.getSelectedItem() == null
                        || jcbProvince.getSelectedItem() == null || jcbWard.getSelectedItem() == null
                        || jtfLiscensePlate.getText().isEmpty()) {
                    jlbMsg.setText("Vui lòng điền đầy đủ thông tin!");
                } else if( !validate_phone(jtfPhone.getText())){
                    jlbMsg.setText("Số điện thoại không đúng định dạng VD: +84383901544");
                }
                else {
                    shipper.setName(jtfName.getText());
                    shipper.setGender(jrbNam.isSelected() ? "Male" : "Female");
                    shipper.setStatus(jrbIdle.isSelected() ? "Active" : "Busy");
                    shipper.setPhoneNumber(jtfPhone.getText());
                    //LocalDateTime now = LocalDateTime.now();
                    //shipper.setUpdated(jtaUpdate.getText());
                    shipper.setLicensePlate(jtfLiscensePlate.getText());
                    //AddressController addressController = new AddressController(jcbDistinct, jcbProvince, jcbWard);
                    shipper.setProvince((String) jcbProvince.getSelectedItem());
                    shipper.setDistinct((String) jcbDistinct.getSelectedItem());
                    shipper.setWard((String) jcbWard.getSelectedItem());
                    if (s.equalsIgnoreCase("UpdateOrDelete") && showDialog("cập nhật")) {
                        shipper.setIsDeleted(jrbTrue.isSelected());
                        int result = shipperServiceDao.update(shipper);
                        if (result > 0) {
                            jlbMsg.setText("Cập nhật dữ liệu thành công!");
                        } else {
                            jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                        }
                    } else if (s.equalsIgnoreCase("Insert") && showDialog("thêm")) {
                        shipper.setRating(5);
                        shipper.setIsDeleted(false);

                        int result = shipperServiceDao.insert(shipper);
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
                        shipper.setIsDeleted(true);
                        int result = shipperServiceDao.update(shipper);
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

    public boolean validate_phone(String phoneNumber) {
        Matcher matcher = pattern_phone.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean validate_email(String email) {
        Matcher matcher = pattern_email.matcher(email);
        return matcher.matches();
    }
}

//package controller;
//
//import AddressAPI.AddressController;
//import Dao.ShipperServiceDao;
//import com.toedter.calendar.JDateChooser;
//import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JRadioButton;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import model.Shipper;
//
//public class ShipperController {
//
//    private final String PHONE_PATTERN = "^\\+(?:[0-9] ?){6,14}[0-9]$";
//    private final Pattern pattern_phone = Pattern.compile(PHONE_PATTERN);
//    private final String EMAIL_PATTERN
//            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//    private final Pattern pattern_email = Pattern.compile(EMAIL_PATTERN);
//
//    private JButton btnSubmit;
//    private JButton btnDelete;
//    private JComboBox<String> jcbDistinct;
//    private JComboBox<String> jcbProvince;
//    private JComboBox<String> jcbWard;
//    private JDateChooser jdcDob;
//    private JLabel jlbID;
//    private JLabel jlbMsg;
//    private JTextArea jtaUpdate;
//    private JRadioButton jrbBusy;
//    private JRadioButton jrbFalse;
//    private JRadioButton jrbIdle;
//    private JRadioButton jrbNam;
//    private JRadioButton jrbNu;
//    private JRadioButton jrbTrue;
//    private JTextField jtfLiscensePlate;
//    private JTextField jtfName;
//    private JTextField jtfPhone;
//
//    private Shipper shipper = null;
//    private ShipperServiceDao shipperServiceDao = null;
//
//    // JFrame insert
//    public ShipperController(JButton btnSubmit, JComboBox<String> jcbDistinct, JComboBox<String> jcbProvince, JComboBox<String> jcbWard, JDateChooser jdcDob, JLabel jlbMsg, JRadioButton jrbBusy, JRadioButton jrbIdle, JRadioButton jrbNam, JRadioButton jrbNu, JTextField jtfLiscensePlate, JTextField jtfName, JTextField jtfPhone) {
//        this.btnSubmit = btnSubmit;
//        //this.btnDelete = btnDelete;
//        this.jcbDistinct = jcbDistinct;
//        this.jcbProvince = jcbProvince;
//        this.jcbWard = jcbWard;
//        this.jdcDob = jdcDob;
//        this.jlbMsg = jlbMsg;
//        this.jrbBusy = jrbBusy;
//        this.jrbIdle = jrbIdle;
//        this.jrbNam = jrbNam;
//        this.jrbNu = jrbNu;
//        this.jtfLiscensePlate = jtfLiscensePlate;
//        this.jtfName = jtfName;
//        this.jtfPhone = jtfPhone;
//
//        this.btnSubmit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                handleSubmitButtonClick();
//            }
//        });
//    }
//
//    // JFrame update and delete
//    public ShipperController(JButton btnSubmit, JButton btnDelete, JComboBox<String> jcbDistinct, JComboBox<String> jcbProvince, JComboBox<String> jcbWard, JDateChooser jdcDob, JLabel jlbID, JLabel jlbMsg, JTextArea jtaUpdate, JRadioButton jrbBusy, JRadioButton jrbFalse, JRadioButton jrbIdle, JRadioButton jrbNam, JRadioButton jrbNu, JRadioButton jrbTrue, JTextField jtfLiscensePlate, JTextField jtfName, JTextField jtfPhone) {
//        this(btnSubmit, jcbDistinct, jcbProvince, jcbWard, jdcDob, jlbMsg, jrbBusy, jrbIdle, jrbNam, jrbNu, jtfLiscensePlate, jtfName, jtfPhone);
//        this.btnDelete = btnDelete;
//        this.jlbID = jlbID;
//        this.jtaUpdate = jtaUpdate;
//        this.jrbFalse = jrbFalse;
//        this.jrbTrue = jrbTrue;
//
//        this.btnSubmit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                handleSubmitButtonClick();
//            }
//        });
//    }
//
//    private void handleSubmitButtonClick() {
//        if (jtfName.getText().isEmpty() || jtfPhone.getText().isEmpty()
//                || jdcDob.getDate() == null
//                || (!jrbNam.isSelected() && !jrbNu.isSelected())
//                || jcbDistinct.getSelectedItem() == null
//                || jcbProvince.getSelectedItem() == null || jcbWard.getSelectedItem() == null
//                || jtfLiscensePlate.getText().isEmpty()) {
//            jlbMsg.setText("Vui lòng điền đầy đủ thông tin!");
//        } else {
//            shipper.setName(jtfName.getText());
//            shipper.setDob(jdcDob.getDate());
//            shipper.setGender(jrbNam.isSelected() ? "Male" : "Female");
//            shipper.setStatus(jrbIdle.isSelected() ? "Active" : "Busy");
//            shipper.setUpdated(jtaUpdate.getText());
//            shipper.setLicensePlate(jtfLiscensePlate.getText());
//            shipper.setProvince((String) jcbProvince.getSelectedItem());
//            shipper.setDistinct((String) jcbDistinct.getSelectedItem());
//            shipper.setWard((String) jcbWard.getSelectedItem());
//            shipper.setIsDeleted(jrbTrue.isSelected());
//            shipper.setPhoneNumber(Integer.parseInt(jtfPhone.getText()));
//        }
//    }
//
//    public void setView(Shipper shipper) {
//        this.shipper = shipper;
//        jlbID.setText(String.valueOf(shipper.getId()));
//        jtfName.setText(shipper.getName());
//        jdcDob.setDate(shipper.getDob());
//        jrbNam.setSelected("Male".equals(shipper.getGender()));
//        jrbNu.setSelected("Female".equals(shipper.getGender()));
//        jrbBusy.setSelected("Busy".equals(shipper.getStatus()));
//        jrbIdle.setSelected("Active".equals(shipper.getStatus()));
//        jtaUpdate.setText(shipper.getUpdated());
//        jtfPhone.setText(String.valueOf(shipper.getPhoneNumber()));
//        jtfLiscensePlate.setText(shipper.getLicensePlate());
//        //AddressController addressController = new AddressController(jcbDistinct, jcbProvince, jcbWard);
//        jrbFalse.setSelected(shipper.isIsDeleted());
//        jrbTrue.setSelected(!shipper.isIsDeleted());
//    }
//
//    public boolean showDialog(String msg) {
//        int dialogResult = JOptionPane.showConfirmDialog(null,
//                "Bạn muốn " + msg + " dữ liệu hay không?", "Thông báo", JOptionPane.YES_NO_OPTION);
//        return dialogResult == JOptionPane.YES_OPTION;
//    }
//
//    public boolean validatePhone(String phoneNumber) {
//        Matcher matcher = pattern_phone.matcher(phoneNumber);
//        return matcher.matches();
//    }
//
//    public boolean validateEmail(String email) {
//        Matcher matcher = pattern_email.matcher(email);
//        return matcher.matches();
//    }
//}
