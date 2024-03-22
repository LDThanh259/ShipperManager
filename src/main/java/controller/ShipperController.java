package controller;

import Dao.ShipperServiceDao;
import Dao.ShipperServiceDaoImpl;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Shipper;

public class ShipperController {

    private final String PHONE_PATTERN = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    private final Pattern pattern_phone = Pattern.compile(PHONE_PATTERN);
    private final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final Pattern pattern_email = Pattern.compile(EMAIL_PATTERN);

    private JButton btnSubmit;
    private JDateChooser jdcDob;
    private JDateChooser jdcStartwork;
    private JRadioButton jrbNam;
    private JRadioButton jrbNu;
    private JTextArea jtaAdress;
    private JTextArea jtaDescription;
    private JLabel jlbID;
    private JTextField jtfName;
    private JTextField jtfPhone;
    private JTextField jtfEmail;
    private JLabel jlbMsg;
    private JButton btnDelete;

    private Shipper shipper = null;
    private ShipperServiceDao shipperServiceDao = null;

    // JFrame insert
    public ShipperController(JButton btnSubmit, JDateChooser jdcDob, JDateChooser jdcStartwork, JRadioButton jrbNam, JRadioButton jrbNu, JTextArea jtaAdress, JTextArea jtaDescription, JTextField jtfName, JTextField jtfPhone, JTextField jtfEmail, JLabel jlbMsg) {
        this.btnSubmit = btnSubmit;
        this.jdcDob = jdcDob;
        this.jdcStartwork = jdcStartwork;
        this.jrbNam = jrbNam;
        this.jrbNu = jrbNu;
        this.jtaAdress = jtaAdress;
        this.jtaDescription = jtaDescription;
        this.jtfName = jtfName;
        this.jtfPhone = jtfPhone;
        this.jtfEmail = jtfEmail;
        this.jlbMsg = jlbMsg;
        this.shipperServiceDao = new ShipperServiceDaoImpl();
        this.shipper = new Shipper();
    }

    // JFrame update and delete
    public ShipperController(JButton btnSubmit, JDateChooser jdcDob, JDateChooser jdcStartwork, JRadioButton jrbNam, JRadioButton jrbNu, JTextArea jtaAdress, JTextArea jtaDescription, JLabel jlbID, JTextField jtfName, JTextField jtfPhone, JTextField jtfEmail, JLabel jlbMsg, JButton btnDelete) {
        this.btnSubmit = btnSubmit;
        this.jdcDob = jdcDob;
        this.jdcStartwork = jdcStartwork;
        this.jrbNam = jrbNam;
        this.jrbNu = jrbNu;
        this.jtaAdress = jtaAdress;
        this.jtaDescription = jtaDescription;
        this.jlbID = jlbID;
        this.jtfName = jtfName;
        this.jtfPhone = jtfPhone;
        this.jtfEmail = jtfEmail;
        this.jlbMsg = jlbMsg;
        this.btnDelete = btnDelete;
        this.shipperServiceDao = new ShipperServiceDaoImpl();
    }

    public void setView(Shipper shipper) {
        this.shipper = shipper;
        jlbID.setText("" + shipper.getShipper_Id());
        jtfName.setText(shipper.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            //System.out.println("ngay sinh"+shipper.getBirthDay());
            date = dateFormat.parse(shipper.getBirthDay());
        } catch (ParseException ex) {
            //Logger.getLogger(ShipperController.class.getName()).log(Level.SEVERE, null, ex);

        }
        jdcDob.setDate(date);
        //xu lys truong hop gender null
        if (shipper.getGender() == "Female") {
            jrbNam.setSelected(false);
            jrbNu.setSelected(true);

        } else if (shipper.getGender() == "Male") {
            jrbNam.setSelected(true);
            jrbNu.setSelected(false);
        } else {
            jrbNam.setSelected(true);
            jrbNu.setSelected(false);
        }
        try {
            date = dateFormat.parse(shipper.getStartWork());
        } catch (ParseException ex) {
            //Logger.getLogger(ShipperController.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("ngay dau lam  trong");
        }
        jdcStartwork.setDate(date);
        jtfPhone.setText(shipper.getPhone());
        jtfEmail.setText(shipper.getEmail());
        jtaAdress.setText(shipper.getAddress());
        jtaDescription.setText(shipper.getDescription());
    }

    public void setEvent(String s) {

        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Kiểm tra xem các thành phần có giá trị null hay không
                if (jtfName.getText().isEmpty() || jtfPhone.getText().isEmpty()
                        || jtfEmail.getText().isEmpty() || jdcDob.getDate() == null || jdcStartwork.getDate() == null
                        || (jrbNam.isSelected() == false && jrbNu.isSelected() == false) || jtaAdress.getText().isEmpty()
                        || jtaDescription.getText().isEmpty()) {
                    jlbMsg.setText("Vui lòng điền đầy đủ thông tin!");
                } else {

                    shipper.setName(jtfName.getText());
                    Date selectedDate = jdcDob.getDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    shipper.setBirthDay(sdf.format(selectedDate));
                    shipper.setGender(jrbNam.isSelected() ? "Male" : "Female");
                    shipper.setStartWork(sdf.format(jdcStartwork.getDate()));
                    // kieemr tra xem phonr hop le k

                    if (validate_phone(jtfPhone.getText()) && validate_email(jtfEmail.getText())) {
                        shipper.setPhone(jtfPhone.getText());
                        shipper.setEmail(jtfEmail.getText());
                        shipper.setAddress(jtaAdress.getText());
                        shipper.setDescription(jtaDescription.getText());

                        if (s.equalsIgnoreCase("UpdateOrDelete") && showDialog("cập nhật")) {
                            int result = shipperServiceDao.Update(shipper);
                            if (result > 0) {
                                jlbMsg.setText("Cập nhật dữ liệu thành công!");
                            } else {
                                jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                            }
                        } else if (s.equalsIgnoreCase("Insert") && showDialog("thêm")) {
                            int result = shipperServiceDao.Insert(shipper);
                            if (result > 0) {
                                jlbMsg.setText("Thêm dữ liệu thành công!");
                            } else {
                                jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                            }
                        }

                    } else {
                        if (!validate_phone(jtfPhone.getText())) {
                            jlbMsg.setText("Số điện thoại không hợp lệ VD: +8412345678.");
                        } else if (!validate_email(jtfEmail.getText())) {
                            jlbMsg.setText("Email không hợp lệ VD: user123@example.com.");
                        } else jlbMsg.setText("Số điện thoại và email không hợp lệ.");
                    }

                    //kiem tra xem email hop le k
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
                        int result = shipperServiceDao.Delete(shipper);
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

    public boolean validate_phone(String phoneNumber) {
        Matcher matcher = pattern_phone.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean validate_email(String email) {
        Matcher matcher = pattern_email.matcher(email);
        return matcher.matches();
    }
}
