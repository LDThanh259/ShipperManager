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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.poi.hssf.record.PasswordRecord;

/**
 *
 * @author ADMIN
 */
public class OrderController {

	public boolean isValidPhoneNumber(String phoneNumber) {
		// Kiểm tra số điện thoại có đúng 10 chữ số không
		return phoneNumber.matches("^\\+(?:[0-9] ?){6,14}[0-9]$");

	}

	private JButton btnSubmit;
	private JRadioButton jrbPending;
	private JRadioButton jrbProcessing;
	private JRadioButton jrbCompleted;
	private JRadioButton jrbTrue;
	private JRadioButton jrbFalse;
	private JTextArea jtaDescription;
	private JTextField jtfName;
	private JTextField jtfDistance;
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

	private JDateChooser jdcOrder;
	private JDateChooser jdcExpectedDilivery;
	private JDateChooser jdcActualDilevery;
	private JTextField jtfPrice;

	private JButton btnRandomShipper;
	private JButton btnDelete;
	private JButton btnAddCustomer;
	private JButton btnRefreshService;

	private ShipperServiceDao shipperServiceDao = null;

	private Order order = null;
	private Customer customer = new Customer();
	private Service service = new Service();
	ServiceSERVICEDao serviceDao = new ServiceSERVICEDaoImpl();
	List<Integer> optionservice = serviceDao.getListID();

	private OrderServiceDao orderServiceDao = null;
	private CustomerServiceDao customerServiceDao = null;
	int serviceid;
	String phonenumberString;
	InsertOrderJFrame insertOrderJFrame = null;

	// JFrame insert
	public OrderController(JButton btnSubmit, JTextArea jtaDescription, JTextField jtfName, JTextField jtfDistance,
			JLabel jlbMsg, JComboBox<Integer> jcbListShipper, JComboBox<String> jcbListService, JDateChooser jdcOrder,
			JDateChooser jdcExpectedDilivery, JButton btnRandomShipper, JTextField jtfWeight, JTextField jtfPrice,
			JComboBox jcbDistinct, JComboBox jcbProvince, JComboBox jcbWard, JTextField jtfCusPhone, JLabel jlbShipFee,
			JButton btnAddCustomer, JLabel jlbPhone, JButton btnRefreshService) {
		this.btnSubmit = btnSubmit;
		this.jtaDescription = jtaDescription;
		this.jtfName = jtfName;
		this.jtfDistance = jtfDistance;
		this.jlbMsg = jlbMsg;
		this.jcbListShipper = jcbListShipper;
		this.jcbListService = jcbListService;
		this.jdcOrder = jdcOrder;
		this.jdcExpectedDilivery = jdcExpectedDilivery;
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
		this.btnRefreshService = btnRefreshService;
		this.order = new Order();
		this.orderServiceDao = new OrderServiceDaoImpl(); // Initialize OrderServiceDao
		this.shipperServiceDao = new ShipperServiceDaoImpl();
	}

//  JFrame update and delete
//	btnSubmit, jrbPending, jrbProcessing, 
//	jrbCompleted, jtfName, jtfDistance, 
//	jtfWeight, jlbMsg, jcbListShipper, 
//	jcbListService, jtfName, jdcExpectedDilivery,
//	jdcOrderDate,jtaDescription,btnRandom
	public OrderController(JButton btnSubmit, JTextField jtfName, JTextField jtfDistance, JTextField jtfWeight,
			JLabel jlbMsg, JComboBox<Integer> jcbListShipper, JComboBox<String> jcbListService, JTextField jtfPrice,
			JDateChooser jdcExpectedDilivery, JDateChooser jdcOrder, JTextArea jtaDescription, JButton btnRandomShipper,
			JButton btnDelete, JComboBox jcbDistinct, JComboBox jcbProvince, JComboBox jcbWard, JTextField jtfCusPhone,
			JLabel jlbShipFee) {
		this.btnSubmit = btnSubmit;
		this.jtfName = jtfName;
		this.jtfDistance = jtfDistance;
		this.jlbMsg = jlbMsg;
		this.jcbListShipper = jcbListShipper;
		this.jcbListService = jcbListService;
		this.jdcExpectedDilivery = jdcExpectedDilivery;
		this.jtfPrice = jtfPrice;

		this.jtfWeight = jtfWeight;
		this.jdcOrder = jdcOrder;
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
		jtfDistance.setText(String.valueOf(order.getDistance()));

		// Ví dụ về LocalDateTime trả về từ order.getOrderDate()
		LocalDateTime orderDate = order.getOrderDate(); // Thay thế bằng order.getOrderDate()

		// Chuyển đổi LocalDateTime thành java.util.Date
		Date orderUtilDate = Date.from(orderDate.atZone(ZoneId.systemDefault()).toInstant());

		// Đặt ngày cho JDateChooser
		jdcOrder.setDate(orderUtilDate);

		// Đặt giá trị LocalDate trực tiếp cho JDateChooser
		LocalDate expectedDeliveryDate = order.getExpectedDeliveryDate();
		jdcExpectedDilivery.setDate(Date.from(expectedDeliveryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		jcbProvince.setSelectedItem(order.getProvince());
		jcbDistinct.setSelectedItem(order.getDistinct());
		jcbWard.setSelectedItem(order.getWard());
		jtaDescription.setText(order.getDescription());
		jtfPrice.setText(String.valueOf(order.getPrice()));
		jlbShipFee.setText(String.valueOf(order.getShipFee()));
		customerServiceDao.getDataFromID(customer);
		System.out.println(customer.getName() + customer.getId());
		jtfCusPhone.setText(customer.getPhoneNumber());

		jcbListShipper.setSelectedIndex(order.getShipperId());
	}
	
	public void selectService() {
		System.out.println( " "+jcbWard.getSelectedItem() + jcbDistinct.getSelectedItem() + jcbProvince.getSelectedItem());
		if (jtfWeight.getText() != null && !jtfWeight.getText().isEmpty() && jtfDistance.getText() != null
			&& !jtfDistance.getText().isEmpty() && jcbWard.getSelectedItem() != null 
			&& jcbDistinct.getSelectedItem() != null && jcbProvince.getSelectedItem() != null) 
		{
			Service selectedService = (Service) jcbListService.getSelectedItem();
	        if (selectedService != null) {
	            // Tính toán và hiển thị phí vận chuyển
	        	System.out.println(selectedService.getName()+"nae");
	            double shipFee = selectedService.getPrice() * Double.parseDouble(jtfDistance.getText());
	            jlbShipFee.setText(String.valueOf(shipFee));
	        }
			 
		}
	}

	public void setEvent(String s) {
		

//		
//
//		// bắt đầu sự kiện cho việc tự động hiển thị shipfee
//		jtfWeight.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//            	System.out.println(jcbDistinct.getSelectedItem());
//                selectService();
//            }
//        });
//		
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
//		
//
//		jtfDistance.getDocument().addDocumentListener(new DocumentListener() {
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				selectService();
//			}
//
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				selectService();
//			}
//
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//				selectService();
//			}
//		});
//
//		// ket thuc
//
//		btnAddCustomer.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Customer customer = new Customer();
//				InsertCustomerJFrame insertCustomerJFrame = new InsertCustomerJFrame(customer);
//				insertCustomerJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//				insertCustomerJFrame.setphone(jtfCusPhone.getText());
//			}
//		});
//
//		jtfCusPhone.getDocument().addDocumentListener(new DocumentListener() {
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				handleCustomerPhoneInput();
//			}
//
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				handleCustomerPhoneInput();
//			}
//
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//				handleCustomerPhoneInput();
//			}
//
//			private void handleCustomerPhoneInput() {
//				CustomerServiceDao customerServiceDao = new CustomerServiceDaoImpl();
//				List<String> optionscusphone = customerServiceDao.getListPhone();
//				btnAddCustomer.setVisible(false);
//				Customer customer = new Customer();
//				String input = jtfCusPhone.getText().trim();
//				if (input.length() < 10) {
//					jlbPhone.setText("Số điện thoại phải có ít nhất 10 số");
//				} else if (input.length() == 10) {
//					boolean found = false;
//					String phoneNumber = input;
//					for (String option : optionscusphone) {
//						if (phoneNumber.equals(option)) {
//							customer.setPhoneNumber(phoneNumber);
//							customerServiceDao.getDataFromCusPhone(phoneNumber, customer);
//							found = true;
//							btnAddCustomer.setVisible(false);
//							jlbPhone.setText("Khách hàng " + customer.getName() + " đã có trong hệ thống");
//							break;
//						}
//					}
//					updateDistance();
//					if (!found) {
//						jlbPhone.setText("Thêm khách hàng mới?");
//						btnAddCustomer.setVisible(true);
//					}
//				} else {
//					jlbPhone.setText("Yêu cầu nhập số điện thoại");
//				}
//			}
//		});

		btnRandomShipper.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int randomIndex = new Random().nextInt(jcbListShipper.getItemCount());
				jcbListShipper.setSelectedIndex(randomIndex);
			}

		});
		
		if (s.equalsIgnoreCase("Insert")) {
			btnAddCustomer.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					Customer customer = new Customer();
					customer.setPhoneNumber(jtfCusPhone.getText().trim());
					InsertCustomerJFrame insertCustomerJFrame = new InsertCustomerJFrame(customer);
					insertCustomerJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					insertCustomerJFrame.setVisible(true);
					insertCustomerJFrame.setphone(customer.getPhoneNumber());

					System.out.println("Hoan thanh reload");

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Check if any required fields are empty
				if (jtfName.getText().isEmpty() || jtfDistance.getText().isEmpty() || jtfWeight.getText().isEmpty()
						|| jtfPrice.getText().isEmpty() || jcbListShipper.getSelectedItem() == null
						|| jtfCusPhone.getText().isEmpty()) {
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
								order.setShipFee(12);
								order.setPrice(Double.parseDouble(jtfPrice.getText()));
								order.setProvince((String) jcbProvince.getSelectedItem());
								order.setDistinct((String) jcbDistinct.getSelectedItem());
								order.setWard((String) jcbWard.getSelectedItem());

								// Set distance
								double distance = Double.parseDouble(jtfDistance.getText());
								order.setDistance(distance);

								order.setDescription(jtaDescription.getText());

								// Set order date
								Date orderDateUtil = jdcOrder.getDate();
								if (orderDateUtil != null) {
									LocalDateTime orderDate = LocalDateTime.ofInstant(orderDateUtil.toInstant(),
											ZoneId.systemDefault());
									order.setOrderDate(orderDate);
								}

								// Calculate and set shipping fee based on distance
								double shipFee = distance * 1;
								order.setShipFee(shipFee);

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

			Address address2 = new Address(lastcusprovince, lastcusward, lastcusdistinct);
			double distance = DistanceAddress.distance(address1, address2);

			System.out.println(jtfDistance.getText());
			jtfDistance.setText(distance + "");
			order.setDistance(distance);
		} else {
			System.out.println("Chuỗi nhập vào không hợp lệ.");
		}
	}
}
