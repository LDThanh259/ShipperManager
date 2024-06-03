/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Dao.OrderServiceDao;
import model.Address;
import Dao.OrderServiceDaoImpl;
import Dao.ServiceSERVICEDao;
import Dao.ServiceSERVICEDaoImpl;
import Dao.ShipperServiceDao;
import Dao.ShipperServiceDaoImpl;
import Dao.CustomerServiceDao;
import Dao.CustomerServiceDaoImpl;

import model.Order;
import model.Service;
import view.InsertCustomerJFrame;
import view.InsertOrderJFrame;
import model.Customer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.poi.hssf.record.PasswordRecord;

/**
 *
 * @author ADMIN
 */
public class OrderController {

    public static String formatCurrency(String amount) {
        // Chuyển đổi chuỗi số thành số nguyên
        double number = Double.parseDouble(amount);

        // Sử dụng DecimalFormat để định dạng số với dấu chấm phân cách
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###");
        return decimalFormat.format(number);

    }

    ;
        
	public int WaitingDay(String serviceName) {
        if (serviceName.equals("Free Ship 1")) {
            return 7;
        } else if (serviceName.equals("Tiet kiem 1") || serviceName.equals("Tiet kiem 2")
                || serviceName.equals("Tiet kiem 3")) {
            return 5;
        } else if (serviceName.equals("Nhanh 1") || serviceName.equals("Nhanh 2") || serviceName.equals("Nhanh 3")) {
            return 3;
        } else if (serviceName.equals("Hoa Toc 1") || serviceName.equals("Hoa Toc 2")
                || serviceName.equals("Hoa Toc 3")) {
            return 1;
        } else {
            return 14;
        }
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra số điện thoại có đúng 10 chữ số không
        return phoneNumber.matches("^(0|\\+84)(3[2-9]|5[689]|7[06-9]|8[1-689]|9[0-46-9])[0-9]{7}$");

    }

    private JButton btnSubmit;
    private JRadioButton jrbPending;
    private JRadioButton jrbProcessing;
    private JRadioButton jrbCompleted;
    private JRadioButton jrbTrue;
    private JRadioButton jrbFalse;
    private JTextArea jtaDescription;
    private JTextField jtfName;
    private JLabel jlbDistance;
    private JLabel jlbMsg;
    private JTextField jtfWeight;
    private JComboBox<Integer> jcbListShipper;
    private JComboBox<String> jcbListService;
    private JComboBox<Integer> jcbListCustomer;
    private JComboBox jcbDistinct;
    private JComboBox jcbProvince;
    private JComboBox jcbWard;
    private JTextField jtfCusPhone;
    private JLabel jlbShipFee;
    private JLabel jlbPhone;

    private JDateChooser jdcOrderDate;
    private JDateChooser jdcExpectedDelivery;
    private JDateChooser jdcActualDilevery;
    private JTextField jtfPrice;

    private JButton btnRandomShipper;
    private JButton btnDelete;
    private JButton btnAddCustomer;

    private ShipperServiceDao shipperServiceDao = null;

    private Order order = null;
    private Customer customer = new Customer();
    private Service service = new Service();
    ServiceSERVICEDao serviceDao = new ServiceSERVICEDaoImpl();
    List<Service> optionservice = serviceDao.getList();
    List<Integer> optionserviceid = serviceDao.getListID();

    private OrderServiceDao orderServiceDao = null;
    private CustomerServiceDao customerServiceDao = null;
    List<String> optionscusphone;
    int serviceid;
    String phonenumberString;
    InsertOrderJFrame insertOrderJFrame = null;
    double price;

    public OrderController() {

    }

    // JFrame insert
    public OrderController(JButton btnSubmit, JTextArea jtaDescription, JTextField jtfName, JLabel jlbDistance,
            JLabel jlbMsg, JComboBox<Integer> jcbListShipper, JComboBox<String> jcbListService,
            JDateChooser jdcOrderDate, JDateChooser jdcExpectedDelivery, JButton btnRandomShipper, JTextField jtfWeight,
            JTextField jtfPrice, JComboBox jcbDistinct, JComboBox jcbProvince, JComboBox jcbWard,
            JTextField jtfCusPhone, JLabel jlbShipFee, JButton btnAddCustomer, JLabel jlbPhone
    ) {
        this.btnSubmit = btnSubmit;
        this.jtaDescription = jtaDescription;
        this.jtfName = jtfName;
        this.jlbDistance = jlbDistance;
        this.jlbMsg = jlbMsg;
        this.jcbListShipper = jcbListShipper;
        this.jcbListService = jcbListService;
        this.jdcOrderDate = jdcOrderDate;
        this.jdcExpectedDelivery = jdcExpectedDelivery;
        this.btnRandomShipper = btnRandomShipper;
        this.jtfWeight = jtfWeight;
        this.jtfPrice = jtfPrice;
        this.jtfCusPhone = jtfCusPhone;
        this.jlbShipFee = jlbShipFee;
        this.insertOrderJFrame = insertOrderJFrame;
        this.btnAddCustomer = btnAddCustomer;
        this.jcbDistinct = jcbDistinct;
        this.jcbProvince = jcbProvince;
        this.jcbWard = jcbWard;
        this.jlbPhone = jlbPhone;
        this.order = new Order();
        this.orderServiceDao = new OrderServiceDaoImpl(); // Initialize OrderServiceDao
        this.shipperServiceDao = new ShipperServiceDaoImpl();
    }

//  JFrame update and delete
    public OrderController(JButton btnSubmit, JTextField jtfName, JLabel jlbDistance, JTextField jtfWeight,
            JLabel jlbMsg, JComboBox<Integer> jcbListShipper, JComboBox<String> jcbListService, JTextField jtfPrice,
            JDateChooser jdcExpectedDelivery, JDateChooser jdcOrderDate, JTextArea jtaDescription,
            JButton btnRandomShipper, JButton btnDelete, JComboBox jcbDistinct, JComboBox jcbProvince,
            JComboBox jcbWard, JTextField jtfCusPhone, JLabel jlbShipFee, JButton btnAddCustomer, JLabel jlbPhone) {
        this.btnSubmit = btnSubmit;
        this.jtfName = jtfName;
        this.jlbDistance = jlbDistance;
        this.jlbMsg = jlbMsg;
        this.jcbListShipper = jcbListShipper;
        this.jcbListService = jcbListService;
        this.jdcExpectedDelivery = jdcExpectedDelivery;
        this.jtfPrice = jtfPrice;

        this.jlbPhone = jlbPhone;
        this.btnAddCustomer = btnAddCustomer;
        this.jtfWeight = jtfWeight;
        this.jdcOrderDate = jdcOrderDate;
        this.jtaDescription = jtaDescription;
        this.btnDelete = btnDelete;
        this.btnRandomShipper = btnRandomShipper;
        this.jlbShipFee = jlbShipFee;

        this.jcbDistinct = jcbDistinct;
        this.jcbProvince = jcbProvince;
        this.jcbWard = jcbWard;
        this.jtfCusPhone = jtfCusPhone;

        this.order = new Order();
        this.orderServiceDao = new OrderServiceDaoImpl(); // Initialize OrderServiceDao
        this.shipperServiceDao = new ShipperServiceDaoImpl();
        this.customerServiceDao = new CustomerServiceDaoImpl();
    }

    public void setView(Order order) {
        this.order = order;
        order.getOrderDate();
        this.customer = customer;
        customer.setId(order.getCusId());
        jtfName.setText(order.getName());
        jtfWeight.setText(String.valueOf(order.getWeight()));
        jlbDistance.setText(String.valueOf(order.getDistance()));

        // Ví dụ về LocalDateTime trả về từ order.getOrderDate()
        LocalDateTime orderDate = order.getOrderDate(); // Thay thế bằng order.getOrderDate()

        // Chuyển đổi LocalDateTime thành java.util.Date
        Date orderUtilDate = Date.from(orderDate.atZone(ZoneId.systemDefault()).toInstant());

        // Đặt ngày cho JDateChooser
        jdcOrderDate.setDate(orderUtilDate);

        // Đặt giá trị LocalDate trực tiếp cho JDateChooser
        LocalDate expectedDeliveryDate = order.getExpectedDeliveryDate();
        System.out.println(expectedDeliveryDate + " sds");
        if (expectedDeliveryDate != null) {
            jdcExpectedDelivery.setDate(Date.from(expectedDeliveryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        jcbProvince.setSelectedItem(order.getProvince());
        jcbDistinct.setSelectedItem(order.getDistinct());
        jcbWard.setSelectedItem(order.getWard());
        jtaDescription.setText(order.getDescription());
        int giatien = (int) order.getShipFee();
        jtfPrice.setText(order.getPrice() + "");
        jlbShipFee.setText(formatCurrency(giatien + ""));
        customerServiceDao.getDataFromID(customer);
        jtfCusPhone.setText(customer.getPhoneNumber());
        System.out.println(order.getShipperId());
        jcbListShipper.setSelectedIndex(order.getShipperId() - 1);
    }

    public void DeleteOrder(Order order) {
        orderServiceDao = new OrderServiceDaoImpl();
        LocalDateTime currentDate = LocalDateTime.now();
        if (order.getShipCount() > 3 || order.getShipTime() == null) {
            JOptionPane.showMessageDialog(null, "Xóa");
        } else if (ChronoUnit.DAYS.between(order.getOrderDate(), currentDate) > 7) {
            System.out.println("Có thể xóa do đơn hàng đá quá số ngày quy định");
            orderServiceDao.Delete(order);
        } else if (ChronoUnit.DAYS.between(order.getShipTime(), currentDate) > 7) {
            System.out.println("Có thể xóa do đơn hàng đá quá số ngày quy định");
            orderServiceDao.Delete(order);
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa");
        }
    }

    public void selectService() {

        if (jtfWeight.getText() != null && !jtfWeight.getText().isEmpty() && jlbDistance.getText() != null
                && jcbWard.getSelectedItem() != null && jcbDistinct.getSelectedItem() != null
                && jcbProvince.getSelectedItem() != null) {

            optionserviceid.clear();
            optionservice.clear();
            jcbListService.removeAllItems();
            optionservice = serviceDao.selectShipFeeforOrd(Double.parseDouble(jtfWeight.getText()),
                    Double.parseDouble(jlbDistance.getText()));

            // Tạo một Map để lưu trữ dịch vụ theo loại (vd: Hoa Toc, Nhanh, Tiết Kiệm, ...)
            Map<String, Service> serviceMap = new HashMap<>();

            // Duyệt qua danh sách dịch vụ và chỉ lưu dịch vụ có ID thấp nhất của mỗi loại
            // vào Map
            for (Service service : optionservice) {
                String[] serviceNameParts = service.getName().split(" ");
                String serviceType = serviceNameParts[0]; // Lấy phần đầu tiên của tên dịch vụ làm loại

                if (!serviceMap.containsKey(serviceType)) {
                    serviceMap.put(serviceType, service); // Nếu loại dịch vụ chưa được thêm vào Map, thêm vào
                } else {
                    // Nếu loại dịch vụ đã tồn tại trong Map, so sánh ID để ưu tiên lưu loại có ID
                    // thấp hơn
                    Service existingService = serviceMap.get(serviceType);
                    if (service.getId() < existingService.getId()) {
                        serviceMap.put(serviceType, service); // Thay thế bằng dịch vụ có ID thấp hơn
                    }
                }
            }

            // Thêm tất cả các dịch vụ có ID thấp nhất của mỗi loại vào ComboBox
            for (Service service : serviceMap.values()) {
                jcbListService.addItem(service.getName());
                optionserviceid.add(service.getId());
            }

        }
    }

    public void setEvent(String s) {

        // bắt đầu sự kiện cho việc tự động hiển thị shipfee
        jtfWeight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println(jcbDistinct.getSelectedItem());
                selectService();
            }
        });

        jcbListService.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    if (optionservice != null && jtfWeight.getText() != null && !jtfWeight.getText().isEmpty()
                            && jlbDistance.getText() != null && jcbWard.getSelectedItem() != null
                            && jcbDistinct.getSelectedItem() != null && jcbProvince.getSelectedItem() != null
                            && jdcOrderDate != null) {

                        String serviceString = jcbListService.getSelectedItem().toString();

                        Service servicecheck = new Service();

                        for (Service service : optionservice) {
                            if (serviceString == service.getName()) {
                                servicecheck = service;
                            }
                        }

                        if (jdcOrderDate.getDate() != null) {
                            Date orderDate = jdcOrderDate.getDate();
                            // Chuyển đổi từ java.util.Date sang java.time.LocalDate
                            LocalDate localOrderDate = orderDate.toInstant().atZone(ZoneId.systemDefault())
                                    .toLocalDate();
                            // Thêm 3 ngày vào ngày đặt hàng
                            LocalDate expectedDeliveryDate = localOrderDate
                                    .plusDays(WaitingDay(servicecheck.getName()));
                            // Chuyển đổi từ java.time.LocalDate sang java.util.Date
                            Date utilExpectedDeliveryDate = Date
                                    .from(expectedDeliveryDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                            // Đặt giá trị cho JDateChooser jdcExpectedDelivery
                            jdcExpectedDelivery.setDate(utilExpectedDeliveryDate);
                        }
                        serviceid = servicecheck.getId();
                        price = servicecheck.getPrice() * Double.parseDouble(jlbDistance.getText());
                        jlbShipFee.setText(formatCurrency("" + servicecheck.getPrice() * Double.parseDouble(jlbDistance.getText())));
                        servicecheck = null;
                    }
                }
            }
        });

        jcbProvince.setEnabled(false);

        jcbDistinct.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateDistance();
                    selectService();
                }
            }
        });

        jcbWard.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateDistance();
                    selectService();
                }
            }
        });

        jlbDistance.addPropertyChangeListener("text", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                selectService();
            }
        });

        // ket thuc
        jtfCusPhone.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleCustomerPhoneInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleCustomerPhoneInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleCustomerPhoneInput();
            }

            private void handleCustomerPhoneInput() {
                CustomerServiceDao customerServiceDao = new CustomerServiceDaoImpl();
                optionscusphone = customerServiceDao.getListPhone();
                btnAddCustomer.setVisible(false);
                Customer customer = new Customer();
                String input = jtfCusPhone.getText().trim();
                if (input.length() < 10) {
                    jlbPhone.setText("Số điện thoại phải có ít nhất 10 số");
                } else if (isValidPhoneNumber(input.toString()) == true) {
                    boolean found = false;
                    String phoneNumber = input;
                    for (String option : optionscusphone) {
                        if (phoneNumber.equals(option)) {
                            customer.setPhoneNumber(phoneNumber);
                            customerServiceDao.getDataFromCusPhone(phoneNumber, customer);
                            found = true;
                            btnAddCustomer.setVisible(false);
                            jlbPhone.setText("Khách hàng " + customer.getName() + " đã có trong hệ thống");
                            updateDistance();
                            break;
                        }
                    }
                    if (!found) {
                        jlbPhone.setText("Thêm khách hàng mới?");
                        btnAddCustomer.setVisible(true);
                    }
                } else {
                    jlbPhone.setText("Yêu cầu nhập số điện thoại");
                }
            }
        });

        btnRandomShipper.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int randomIndex = new Random().nextInt(jcbListShipper.getItemCount());
                jcbListShipper.setSelectedIndex(randomIndex);
            }

        });

        if (s.equalsIgnoreCase("Insert") || s.equalsIgnoreCase("UpdateOrDelete")) {

            jdcExpectedDelivery.setEnabled(false);
            btnAddCustomer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Customer customer = new Customer();
                    InsertCustomerJFrame insertCustomerJFrame = new InsertCustomerJFrame(customer);
                    insertCustomerJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    insertCustomerJFrame.setphone(jtfCusPhone.getText());
                    insertCustomerJFrame.setVisible(true);
                }
            });
        }

        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if any required fields are empty
                if (jtfName.getText().isEmpty() || jlbDistance.getText().isEmpty() || jtfWeight.getText().isEmpty()
                        || jtfPrice.getText().isEmpty() || jcbListShipper.getSelectedItem() == null
                        || jtfCusPhone.getText().isEmpty() || jdcOrderDate.getDate() == null) {
                    jlbMsg.setText("Vui lòng điền đầy đủ thông tin!");
                } else {
                    try {
                        String phoneNumber = jtfCusPhone.getText().trim();
                        Customer customer = new Customer();
                        if (isValidPhoneNumber(phoneNumber)) {
                            int id = orderServiceDao.checkPhoneNumberExists(phoneNumber, customer);
                            System.out.println(id);
                            if (id > 0) {
                                phonenumberString = phoneNumber;
                                order.setCusId(id);
                                order.setName(jtfName.getText());
                                double weight = Double.parseDouble(jtfWeight.getText());
                                order.setWeight(weight);
                                order.setPrice(Double.parseDouble(jtfPrice.getText()));
                                order.setProvince((String) jcbProvince.getSelectedItem());
                                order.setDistinct((String) jcbDistinct.getSelectedItem());
                                order.setWard((String) jcbWard.getSelectedItem());
                                // Set distance
                                double distance = Double.parseDouble(jlbDistance.getText());
                                order.setDistance(distance);
                                Date date = jdcExpectedDelivery.getDate();
                                LocalDate expectedDeliveryDate = date.toInstant().atZone(ZoneId.systemDefault())
                                        .toLocalDate();
                                order.setExpectedDeliveryDate(expectedDeliveryDate);
                                order.setDescription(jtaDescription.getText());
                                order.setServiceId(serviceid);
                                // Set order date
                                Date orderDateUtil = jdcOrderDate.getDate();
                                if (orderDateUtil != null) {
                                    LocalDateTime orderDate = LocalDateTime.ofInstant(orderDateUtil.toInstant(),
                                            ZoneId.systemDefault());
                                    order.setOrderDate(orderDate);
                                }

                                // Calculate and set shipping fee based on distance
                                order.setShipFee(price);

                                // Set shipper ID
                                Integer selectedOption = (Integer) jcbListShipper.getSelectedItem();
                                order.setShipperId(selectedOption);

                                if (s.equalsIgnoreCase("UpdateOrDelete") && showDialog("cập nhật")) {
                                    order.setRespond(false);
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
                            } else {
                                jlbMsg.setText("Khách hàng mới. Vui lòng tạo dữ liệu mới cho khách hàng");
                            }
                        } else {
                            jlbMsg.setText("Số điện thoại không hợp lệ");
                        }
                    } catch (Exception ex) {
                        // Handle exceptions and rollback if necessary
                        DeleteOrder(order);
                        int result = orderServiceDao.Delete(order);
                        if (result > 0) {
                            jlbMsg.setText("Đã xóa dữ liệu order mới tạo do có lỗi xảy ra!");
                        } else {
                            jlbMsg.setText("Có lỗi xảy ra khi xóa dữ liệu order mới tạo!");
                        }
                        ex.printStackTrace();
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
        });

        if (s.equalsIgnoreCase("UpdateOrDelete")) {

            btnAddCustomer.setVisible(false);
            jcbDistinct.setEditable(false);
            jcbWard.setEditable(false);
            btnDelete.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (showDialog("xóa")) {
                        order.setDeleted(true);
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
        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn muốn " + msg + " dữ liệu hay không?", "Thông báo",
                JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }

    // Phương thức chuyển đổi từ java.util.Date sang java.sql.Date
    public java.sql.Date covertDateToDateSql(Date d) {
        return new java.sql.Date(d.getTime());
    }

    public boolean isStatusNull() {
        return !(jrbPending.isSelected() || jrbProcessing.isSelected() || jrbCompleted.isSelected());
    }

    private void updateDistance() {
        String[] partsProvince = jcbProvince.getSelectedItem().toString().split("/");
        String[] partsDistinct = jcbDistinct.getSelectedItem().toString().split("/");
        String[] partsWard = jcbWard.getSelectedItem().toString().split("/");

        Customer customer = new Customer();
        orderServiceDao = new OrderServiceDaoImpl();
        orderServiceDao.checkPhoneNumberExists(jtfCusPhone.getText().trim(), customer);
        String[] cusprovince = customer.getProvince().split("/");
        String[] cusdistinct = customer.getDistinct().split("/");
        String[] cusward = customer.getWard().split("/");
        System.out.println("sd");
        System.out.println(customer.toString());
        if (partsProvince.length > 0) {
            String lastPartProvince = partsProvince[partsProvince.length - 1];
            String lastPartDistinct = partsDistinct[partsDistinct.length - 1];
            String lastPartWard = partsWard[partsWard.length - 1];

            String lastcusprovince = cusprovince[cusprovince.length - 1];
            String lastcusdistinct = cusdistinct[cusdistinct.length - 1];
            String lastcusward = cusward[cusward.length - 1];
            System.out.println(lastcusprovince);
            System.out.println(lastcusdistinct);
            System.out.println(lastcusward);

            Address address1 = new Address(lastPartProvince, lastPartDistinct, lastPartWard);

            Address address2 = new Address(lastcusprovince, lastcusdistinct, lastcusward);
            double distance = DistanceAddress.distance(address1, address2);

            System.out.println(jlbDistance.getText());
            jlbDistance.setText(distance + "");
            order.setDistance(distance);
        } else {
            System.out.println("Chuỗi nhập vào không hợp lệ.");
        }
    }
}
