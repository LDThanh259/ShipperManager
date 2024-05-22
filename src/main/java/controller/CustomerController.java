package controller;

import AddressAPI.AddressController;
import Dao.CustomerServiceDao;
import Dao.CustomerServiceDaoImpl;
import model.Customer;

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

public class CustomerController {

    private final String PHONE_PATTERN = "^\\+(?:[0-9] ?){6,14}[0-9]$";
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
    private JTextArea jtaUpdate;
    private JRadioButton jrbNam;
    private JRadioButton jrbNu;
    private JTextField jtfName;
    private JTextField jtfPhone;
    private JTextField jtfEmail;
    private JRadioButton jrbTrue;
    private JRadioButton jrbFalse;

    private Customer customer = null;
    private CustomerServiceDao customerServiceDao = null;

    // JFrame insert
    public CustomerController(JButton btnSubmit, JComboBox jcbDistinct, JComboBox jcbProvince, JComboBox jcbWard, JLabel jlbMsg, JRadioButton jrbNam, JRadioButton jrbNu, JTextField jtfName, JTextField jtfPhone, JTextField jtfEmail) {
        this.btnSubmit = btnSubmit;
        this.jcbDistinct = jcbDistinct;
        this.jcbProvince = jcbProvince;
        this.jcbWard = jcbWard;
        this.jlbMsg = jlbMsg;
        this.jrbNam = jrbNam;
        this.jrbNu = jrbNu;
        this.jtfName = jtfName;
        this.jtfPhone = jtfPhone;
        this.jtfEmail = jtfEmail;

        // Các thuộc tính không được sử dụng trong constructor này đã được loại bỏ.
        this.customerServiceDao = new CustomerServiceDaoImpl();
        this.customer = new Customer();
    }

    // JFrame update and delete
    public CustomerController(JButton btnSubmit, JButton btnDelete, JComboBox jcbDistinct, JComboBox jcbProvince, JComboBox jcbWard, JLabel jlbID, JLabel jlbMsg, JRadioButton jrbNam, JRadioButton jrbNu, JTextField jtfName, JTextField jtfPhone, JTextField jtfEmail) {
        this.btnSubmit = btnSubmit;
        this.btnDelete = btnDelete;
        this.jcbDistinct = jcbDistinct;
        this.jcbProvince = jcbProvince;
        this.jcbWard = jcbWard;
        this.jlbID = jlbID;
        this.jlbMsg = jlbMsg;
        this.jrbNam = jrbNam;
        this.jrbNu = jrbNu;
        this.jtfName = jtfName;
        this.jtfPhone = jtfPhone;
        this.jtfEmail = jtfEmail;

        this.customerServiceDao = new CustomerServiceDaoImpl();
        this.customer=customer;
    }

    public void setView(Customer customer) {
        this.customer = customer;
        jlbID.setText(String.valueOf(customer.getId()));
        jtfName.setText(customer.getName());
        jtfPhone.setText(customer.getPhoneNumber());
        if (customer.getGender() == false) {
            jrbNam.setSelected(false);
            jrbNu.setSelected(true);
        } else if (customer.getGender() == true) {
            jrbNam.setSelected(true);
            jrbNu.setSelected(false);
        } else {
            jrbNam.setSelected(false);
            jrbNu.setSelected(false);
        }
        // Lấy giá trị từ các thuộc tính tương ứng
        jtfEmail.setText(customer.getEmail());
        //AddressController addressController = new AddressController(jcbDistinct, jcbProvince, jcbWard);
        jcbProvince.setSelectedItem(customer.getProvince());
        jcbDistinct.setSelectedItem(customer.getDistinct());
        jcbWard.setSelectedItem(customer.getWard());
    }

    public void setEvent(String s) {
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jtfName.getText().isEmpty() || jtfPhone.getText().isEmpty()
                        || (!jrbNam.isSelected() && !jrbNu.isSelected())
                        || jcbDistinct.getSelectedItem() == null
                        || jcbProvince.getSelectedItem() == null
                        || jcbWard.getSelectedItem() == null
                        || jtfEmail.getText().isEmpty()) {
                    jlbMsg.setText("Vui lòng điền đầy đủ thông tin!");
                } else {

                    customer.setName(jtfName.getText());
                    customer.setGender(jrbNam.isSelected() ? false : true);
                    customer.setPhoneNumber(jtfPhone.getText());
                    customer.setEmail(jtfEmail.getText());
                    //LocalDateTime now = LocalDateTime.now();
                    //shipper.setUpdated(jtaUpdate.getText());
                    //AddressController addressController = new AddressController(jcbDistinct, jcbProvince, jcbWard);
                    customer.setProvince((String) jcbProvince.getSelectedItem());
                    customer.setDistinct((String) jcbDistinct.getSelectedItem());
                    customer.setWard((String) jcbWard.getSelectedItem());
                    if (s.equalsIgnoreCase("UpdateOrDelete") && showDialog("cập nhật")) {
//                        customer.setUpdated(jtaUpdate.getText());
//                        customer.setIsDeleted(jrbTrue.isSelected());
                        int result = customerServiceDao.Update(customer);
                        if (result > 0) {
                            jlbMsg.setText("Cập nhật dữ liệu thành công!");
                        } else {
                            jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                        }
                    } else if (s.equalsIgnoreCase("Insert") && showDialog("thêm")) {
                        //LocalDateTime now = LocalDateTime.now();
                        //customer.setUpdated("create");
                        int result = customerServiceDao.Insert(customer);
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
                        int result = customerServiceDao.Delete(customer);
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
