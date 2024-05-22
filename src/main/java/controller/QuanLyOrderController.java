package controller;

import Dao.OrderServiceDao;
import Dao.OrderServiceDaoImpl;
import model.ClassTableModel;
import model.Customer;
import model.Order;
import model.Shipper;
import model.ButtonColumn;

import com.toedter.calendar.JMonthChooser;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import view.UpdateOrDeleteOrder;
import view.InsertOrderJFrame;

public class QuanLyOrderController {

    private JLabel jlbID;
    private JLabel jlbName;
    private JMonthChooser jmcMonth;
    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
    private JButton btnPrint;
    private JComboBox<String> jcbFillter;

    private OrderServiceDao orderServiceDao = null;

    private String[] listColumnPending = {
        "ORD_ID",
        "ORD_NAME",
        "ORD_WEIGHT",
        "ORD_PRICE",
        "ORD_WARD",
        "ORD_ORDER_DATE",
        "SHIPPER_ID",
        "CUS_ID",
        "ORD_IS_DELETED",
        "SV_ID",
        "ORD_SHIPFEE",
        "Vận chuyển"

    };

    private String[] listColumnProcessing = {
        "ORD_ID",
        "ORD_NAME",
        "ORD_SHIP_FEE",
        "ORD_PRICE",
        "ORD_WARD",
        "ORD_DISTANCE",
        "ORD_ORDER_DATE",
        "SHIPPER_ID",
        "CUS_ID",
        "CUS_RESPOND",
        "ORD_TIME",
        "ORD_SHIP_COUNT",
        "SV_ID",
        "Vận chuyển",
        "Xóa",
        "Hoàn Thành"
    };

    private String[] listColumnDeleted = {
        "ORD_ID",
        "ORD_NAME",
        "ORD_PRICE",
        "ORD_ORDER_DATE",
        "SHIPPER_ID",
        "CUS_ID",
        "CUS_RESPOND",
        "ORD_TIME",
        "ORD_SHIP_COUNT",
        "ORD_COMPLETED_TIME",
        "SV_ID",
        "ORD_SHIPFEE"
    };

    private String[] listColumnCompleted = {
        "ORD_ID",
        "ORD_NAME",
        "ORD_PRICE",
        "ORD_ORDER_DATE",
        "SHIPPER_ID",
        "CUS_ID",
        "CUS_RESPOND",
        "ORD_SHIP_COUNT",
        "ORD_COMPLETED_TIME",
        "SV_ID",
        "ORD_SHIPFEE"
    };

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyOrderController(JLabel jlbID, JLabel jlbName, JMonthChooser jmcMonth, JPanel jpnView, JComboBox<String> jcbFillter, JTextField jtfSearch) {
        this.jlbID = jlbID;
        this.jlbName = jlbName;
        this.jmcMonth = jmcMonth;
        this.jpnView = jpnView;
        this.jcbFillter = jcbFillter;
        this.jtfSearch = jtfSearch;

        this.orderServiceDao = new OrderServiceDaoImpl();
    }

    public QuanLyOrderController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch, JButton btnPrint, JComboBox<String> jcbFillter) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnPrint = btnPrint;
        this.jcbFillter = jcbFillter;

        this.orderServiceDao = new OrderServiceDaoImpl();
    }

    private void setupTable(JTable table) {

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);

        table.validate();
        table.repaint();
        refreshTable(table);
    }

    public void initTable() {
        setDataToTable("Deleted"); // Mặc định hiển thị danh sách đơn hàng chưa vận chuyển
    }

    public void setDataToTable(String s) {
        List<Order> listOrders = null;
        String[] listColumn = null;
        JTable table = null;
        if (s == "Pending") {
            listColumn = listColumnPending;
            System.out.println("Pending1 " + listColumn.length);
            listOrders = orderServiceDao.getUnDeliveryOrders();
            table = new JTable();
            DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumn, table, s);
        } else if (s == "Processing") {
            listColumn = listColumnProcessing;
            listOrders = orderServiceDao.getDeletedOrders(false);
            table = new JTable();
            DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumn, table, s);
        } else if (s == "Deleted") {
            listColumn = listColumnDeleted;
            listOrders = orderServiceDao.getDeletedOrders(true);
            DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumn, null, s);
            table = new JTable(model);
        } else if (s == "Completed") {
            table = new JTable();
            listColumn = listColumnCompleted;
            listOrders = orderServiceDao.getCompletedOrders();
            DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumn, table, s);
        }
        setupTable(table);
        initSearchListener();
        initMouseListener(table);
    }

    private void initMouseListener(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRowIndex = table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                if (selectedRowIndex != -1) {
                    selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);
                    if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem editOrderMenuItem = new JMenuItem("Chỉnh sửa thông tin đơn hàng");
                        JMenuItem processingOrders = new JMenuItem("Danh sách đơn hàng đang vận chuyển");
                        JMenuItem pendingOrders = new JMenuItem("Danh sách đơn hàng chưa vận chuyển");
                        JMenuItem listDeleteOrderMenuItem = new JMenuItem("Danh sách đơn hàng đã xóa");

                        popupMenu.add(editOrderMenuItem);
                        popupMenu.add(processingOrders);
                        popupMenu.add(pendingOrders);
                        popupMenu.add(listDeleteOrderMenuItem);

                        // Lay du lieu tu order co id dc cap nhat
                        Order order = new Order();
                        int orderId = (int) model.getValueAt(selectedRowIndex, 0);
                        order.setId(orderId);
                        System.out.println(orderId);
                        orderServiceDao.getDataFromID(order);

                        editOrderMenuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                UpdateOrDeleteOrder orderJFrame = new UpdateOrDeleteOrder(order);
                                orderJFrame.setTitle("Thông tin đơn hàng");
                                orderJFrame.setResizable(false);
                                orderJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                orderJFrame.setLocationRelativeTo(null);
                                orderJFrame.setVisible(true);
                            }
                        });

                        processingOrders.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                setDataToTable("Processing");
                            }
                        });

                        pendingOrders.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                setDataToTable("Pending");
                            }
                        });

                        listDeleteOrderMenuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                setDataToTable("Deleted");
                            }
                        });

                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    private void initSearchListener() {
        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    public void initFilter(final Shipper shipper) {
        
        jlbID.setText(shipper.getId()+"");
        jlbName.setText(shipper.getName());

        final int[] selectedMonthIndex = {LocalDate.now().getMonthValue() - 1};  // Sử dụng mảng để làm biến mutable
        jmcMonth.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("month")) {
                    selectedMonthIndex[0] = jmcMonth.getMonth();
//                List<Order> listOrders = orderServiceDao.getOrderListForShipper(shipper, selectedMonthIndex[0] + 1,"");
//                DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumnPending, null, "Pending");
//                JTable table = new JTable(model);
//                setupTable(table);
                }
            }
        });

        jcbFillter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Order> listOrders = null;
                String[] listColumn = null;
                JTable table = new JTable();

                String selectedValue = (String) jcbFillter.getSelectedItem();
                String status = "Processing";  // Default status
                if (selectedValue.equals("Chưa xử lý")) {
                    status = "Pending";
                    listColumn = listColumnPending;
                } else if (selectedValue.equals("Thành công")) {
                    status = "Completed";
                    listColumn = listColumnCompleted;
                } else if (selectedValue.equals("Đã xóa")) {
                    status = "Deleted";
                    listColumn = listColumnDeleted;
                } else {
                    listColumn = listColumnProcessing;
                }

                listOrders = orderServiceDao.getOrderListForShipper(shipper, selectedMonthIndex[0] + 1, status);
                DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumn, table, status);

                setupTable(table);
                initSearchListener();
                initMouseListener(table);
            }
        });
    }

    private void refreshTable(JTable table) {
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }

    public void initEvents() {
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Order order = new Order();

                InsertOrderJFrame insertOrderJFrame = new InsertOrderJFrame();
                insertOrderJFrame.setTitle("Thông tin Order");
                insertOrderJFrame.setLocationRelativeTo(null);
                insertOrderJFrame.setResizable(false);
                insertOrderJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                insertOrderJFrame.setVisible(true);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnAdd.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAdd.setBackground(new Color(100, 221, 83));
            }
        });

        // Xuat excel
        btnPrint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String filePath = "F:\\JAVA\\NETBEAN\\ShipperMaven\\src\\main\\java\\export\\Danh_sach_order.xlsx";

                try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                    XSSFSheet spreadsheet = workbook.createSheet("Order");

                    XSSFRow row = spreadsheet.createRow(2);
                    row.setHeight((short) 500);
                    Cell cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue("DANH SÁCH Order");

                    row = spreadsheet.createRow(3);
                    row.setHeight((short) 500);
                    String[] headers = {"ID", "Họ và tên", "Cân nặng", "Phí vận chuyển", "Giá", "Phường/Xã", "Quận/Huyện", "Tỉnh/Thành phố", "Khoảng cách", "Mô tả", "Ngày đặt hàng", "Ngày giao hàng dự kiến", "Đã xóa", "Tình trạng", "Shipper ID", "Khách hàng ID", "Số điện thoại", "Thời gian", "Số lượng vận chuyển", "Thời gian hoàn thành", "Xác nhận", "ID dịch vụ"};
                    for (int i = 0; i < headers.length; i++) {
                        cell = row.createCell(i, CellType.STRING);
                        cell.setCellValue(headers[i]);
                    }

                    int rowNum = 4;

                    OrderServiceDao orderServiceDao = new OrderServiceDaoImpl();

                    List<Order> orderList = orderServiceDao.getList();

                    for (Order order : orderList) {
                        row = spreadsheet.createRow(rowNum++);
                        row.setHeight((short) 400);
                        row.createCell(0).setCellValue(order.getId());
                        row.createCell(1).setCellValue(order.getName());
                        row.createCell(2).setCellValue(order.getWeight());
                        row.createCell(3).setCellValue(order.getShipFee());
                        row.createCell(4).setCellValue(order.getPrice());
                        row.createCell(5).setCellValue(order.getWard());
                        row.createCell(6).setCellValue(order.getProvince());
                        row.createCell(7).setCellValue(order.getDistinct());
                        row.createCell(8).setCellValue(order.getDistance());
                        row.createCell(9).setCellValue(order.getDescription());
                        row.createCell(10).setCellValue(order.getOrderDate().toString());
                        row.createCell(11).setCellValue(order.getExpectedDeliveryDate().toString());
                        row.createCell(12).setCellValue(order.isDeleted());
                        row.createCell(13).setCellValue(order.isRespond());
                        row.createCell(14).setCellValue(order.getShipperId());
                        row.createCell(15).setCellValue(order.getCusId());
                        row.createCell(16).setCellValue(order.getShipTime().toString());
                        row.createCell(17).setCellValue(order.getShipCount());
                        row.createCell(18).setCellValue(order.getCompletedTime().toString());
                        row.createCell(19).setCellValue(order.isConfirm());
                        row.createCell(20).setCellValue(order.getServiceId());
                    }

                    try (FileOutputStream out = new FileOutputStream(filePath)) {
                        workbook.write(out);
                    }
                    showExportSuccessDialog("");

                } catch (IOException ex) {
                    showExportSuccessDialog("không");
                    ex.printStackTrace();
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnPrint.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnPrint.setBackground(new Color(100, 221, 83));
            }

        });

        jcbFillter.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) jcbFillter.getSelectedItem();

                if (selectedItem.equals("Chưa xử lý")) {

                    setDataToTable("Pending");
                } else if (selectedItem.equals("Đang vận chuyển")) {

                    setDataToTable("Processing");
                } else if (selectedItem.equals("Thành công")) {

                    setDataToTable("Completed");
                } else if (selectedItem.equals("Đã xóa")) {

                    setDataToTable("Processing");
                }
            }

        });

    }

    private static void showExportSuccessDialog(String s) {
        JOptionPane.showMessageDialog(null, "Xuất file " + s + "thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    //
//  public void initTable(Shipper shipper) {
//  jlbID.setText("" + shipper.getId());
//  jlbName.setText(shipper.getName());
//  int selectedMonthIndex = jmcMonth.getMonth();
//  List<Order> listOrders = orderServiceDao.getOrderListForShipper(shipper, selectedMonthIndex + 1);
//  DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumnPending);
//  JTable table = new JTable(model);
//  setupTable(table);
//  initSearchListener();
//  initMonthChooserListener(shipper);
//}
}
