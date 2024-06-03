package controller;

import Dao.OrderServiceDao;
import Dao.OrderServiceDaoImpl;
import model.ClassTableModel;
import model.Customer;
import model.Order;
import model.Shipper;

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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private OrderServiceDao orderServiceDao = null;
    private JComboBox<String> jcbFillter;
    static String changeordertoshipper;

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
        "ORD_SHIPFEE",};

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
        "SV_ID",};

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

    public String[] AddColumn(String[] oldList, List<String> columns) {
        List<String> tempList = new ArrayList<>(Arrays.asList(oldList));
        for (String column : columns) {
            if (!tempList.contains(column)) {
                tempList.add(column);
            }
        }
        return tempList.toArray(new String[0]);
    }

    public void setDataToTable(String status) {
        String shipperororder = "Order";
        List<Order> listOrders = null;
        String[] listColumn = null;
        JTable table = new JTable();

        if (status.equals("Pending")) {
            String[] listColumnPendingshipper = listColumnPending;
            List<String> newColumns = Arrays.asList("EDIT");
            listColumnPendingshipper = AddColumn(listColumnPendingshipper, newColumns);

            listColumn = listColumnPendingshipper;
            listOrders = orderServiceDao.getUnDeliveryOrders();
            initMouseListener(table);
        } else if (status.equals("Processing")) {
            listColumn = listColumnProcessing;
            listOrders = orderServiceDao.getDeliveryOrders();
            initMouseListener(table);
        } else if (status.equals("Deleted")) {
            listColumn = listColumnDeleted;
            listOrders = orderServiceDao.getDeletedOrders();

        } else if (status.equals("Completed")) {
            listColumn = listColumnCompleted;
            listOrders = orderServiceDao.getCompletedOrders();
        }

        DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumn, table, status, shipperororder);
        setupTable(table);
        initSearchListener();
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
                        JMenuItem DeleteOrderMenuItem = new JMenuItem("Xóa đơn hàng");

                        popupMenu.add(editOrderMenuItem);
                        popupMenu.add(DeleteOrderMenuItem);

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

                        DeleteOrderMenuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                LocalDateTime currentDate = LocalDateTime.now();
                                if (order.getShipCount() < 3 || order.getShipTime() != null) {
                                    JOptionPane.showMessageDialog(null, "Không thể xóa");
                                }
                                if (ChronoUnit.DAYS.between(order.getOrderDate(), currentDate) > 7) {
                                    System.out.println("Có thể xóa do đơn hàng đá quá số ngày quy định");
                                    order.setDeleted(true);
                                } else if (ChronoUnit.DAYS.between(order.getShipTime(), currentDate) > 7) {
                                    System.out.println("Có thể xóa do đơn hàng đá quá số ngày quy định");
                                    order.setDeleted(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Không thể xóa");
                                }
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

    public void initFilter(final Shipper shipper, Customer customer, String type) {

        if (type.equals("Shipper")) // Gọi hành động mặc định khi khởi tạo
        {
            jlbID.setText(shipper.getId() + "");
            jlbName.setText(shipper.getName());
        }
        if (type.equals("Customer")) {
            jlbID.setText(customer.getId() + "");
            jlbName.setText(customer.getName());
        }

        final int[] selectedMonthIndex = {LocalDate.now().getMonthValue() - 1};  // Sử dụng mảng để làm biến mutable
        jmcMonth.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("month")) {
                    selectedMonthIndex[0] = jmcMonth.getMonth();
                }
            }
        });

        // Thiết lập giá trị mặc định cho jcbFillter
        jcbFillter.setSelectedItem("Chưa xử lý");

        jcbFillter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(shipper, customer, type, selectedMonthIndex);
            }
        });

        if (type.equals("Shipper")) // Gọi hành động mặc định khi khởi tạo
        {
            updateTable(shipper, customer, type, selectedMonthIndex);
        }
        if (type.equals("Customer")) {
            updateTable(shipper, customer, type, selectedMonthIndex);
        }

    }

    private void updateTable(Shipper shipper, Customer customer, String type, int[] selectedMonthIndex) {
        String shipperororder = "";
        if (type.equals("Shipper")) // Gọi hành động mặc định khi khởi tạo
        {
            shipperororder = "Shipper";
        }
        if (type.equals("Customer")) {
            shipperororder = "Customer";
        }
        List<Order> listOrders = null;
        String[] listColumn = null;
        JTable table = new JTable();

        String selectedValue = (String) jcbFillter.getSelectedItem();
        String status = "Processing";  // Default status

        if (selectedValue.equals("Chưa xử lý")) {
            String[] listColumnPendingshipper = listColumnPending;
            List<String> newColumns;
            if ("Shipper".equals(type)) {
                newColumns = Arrays.asList("CANCEL", "DELIVERY");
                listColumnPendingshipper = AddColumn(listColumnPendingshipper, newColumns);
            }
            if ("Customer".equals(type)) {
                newColumns = Arrays.asList("CANCEL");
                listColumnPendingshipper = AddColumn(listColumnPendingshipper, newColumns);
            }
            status = "Pending";
            listColumn = listColumnPendingshipper;
        } else if (selectedValue.equals("Thành công")) {
            status = "Completed";
            listColumn = listColumnCompleted;
        } else if (selectedValue.equals("Đã xóa")) {
            status = "Deleted";
            listColumn = listColumnDeleted;
            if (type.equals("Shipper")) // Gọi hành động mặc định khi khởi tạo
            {

                listOrders = orderServiceDao.getOrderListForShipper(shipper, selectedMonthIndex[0] + 1, status);
                DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumn, table, status, shipperororder);
                setupTable(table);
                initSearchListener();
                return;
            }
            if (type.equals("Customer")) {
                listOrders = orderServiceDao.getOrderListForCustomer(customer, selectedMonthIndex[0] + 1, status);
                DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumn, table, status, shipperororder);
                setupTable(table);
                initSearchListener();
                return;
            }

        } else {
            if (type.equals("Shipper")) {
                String[] listColumnProcessingshipper = listColumnProcessing;
                List<String> newColumns = Arrays.asList("DELETE", "COMPLETE");
                listColumnProcessingshipper = AddColumn(listColumnProcessingshipper, newColumns);
                listColumn = listColumnProcessingshipper;
            } else if (type.equals("Customer")) {
                listColumn = listColumnProcessing;

            }

        }

        if (type.equals("Shipper")) // Gọi hành động mặc định khi khởi tạo
        {
            listOrders = orderServiceDao.getOrderListForShipper(shipper, selectedMonthIndex[0] + 1, status);
            System.out.println("" + listOrders.size());
            DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumn, table, status, shipperororder);
        }
        if (type.equals("Customer")) {
            listOrders = orderServiceDao.getOrderListForCustomer(customer, selectedMonthIndex[0] + 1, status);
            System.out.println("" + listOrders.size());
            DefaultTableModel model = new ClassTableModel().setTableOrder(listOrders, listColumn, table, status, shipperororder);
        }

        setupTable(table);
        initSearchListener();
        initMouseListener(table);
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
                System.out.println("insert");
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
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn nơi lưu tập tin Excel");

                // Tạo một FileFilter để chỉ chấp nhận các tập tin Excel
                FileFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
                fileChooser.setFileFilter(filter);

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();

                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }

                    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                        XSSFSheet spreadsheet = workbook.createSheet("Order");

                        XSSFRow row = spreadsheet.createRow(2);
                        row.setHeight((short) 500);
                        Cell cell = row.createCell(0, CellType.STRING);
                        cell.setCellValue("DANH SÁCH ĐƠN HÀNG");

                        row = spreadsheet.createRow(3);
                        row.setHeight((short) 500);
                        String[] listColumn = {
                            "ORD_ID",
                            "ORD_NAME",
                            "ORD_WEIGHT",
                            "ORD_SHIP_FEE",
                            "ORD_PRICE",
                            "ORD_WARD",
                            "ORD_PROVINCE",
                            "ORD_DISTRICT",
                            "ORD_DISTANCE",
                            "ORD_DESCRIPTION",
                            "ORD_ORDER_DATE",
                            "ORD_EXPECTED_DELIVERY_DATE",
                            "SHIPPER_ID",
                            "CUS_ID",
                            "CUS_RESPOND",
                            "ORD_IS_DELETED",
                            "ORD_TIME",
                            "ORD_SHIP_COUNT",
                            "ORD_COMPLETED_TIME",
                            "ORD_CONFIRM",
                            "SV_ID"
                        };
                        for (int i = 0; i < listColumn.length; i++) {
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue(listColumn[i]);
                            spreadsheet.autoSizeColumn(i);
                        }

                        int rowNum = 4;

                        OrderServiceDao orderServiceDao = new OrderServiceDaoImpl();

                        List<Order> orderList = orderServiceDao.getList();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                            row.createCell(10).setCellValue(order.getOrderDate());
                            row.createCell(11).setCellValue(order.getExpectedDeliveryDate());
                            row.createCell(12).setCellValue(order.getShipperId());
                            row.createCell(13).setCellValue(order.getCusId());
                            row.createCell(14).setCellValue(order.isRespond());
                            row.createCell(15).setCellValue(order.isDeleted());
                            row.createCell(16).setCellValue(order.getShipTime());
                            row.createCell(17).setCellValue(order.getShipCount());
                            row.createCell(18).setCellValue(order.getCompletedTime());
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
                        // Xử lý ngoại lệ tại đây, ví dụ: thông báo cho người dùng, ghi log, vv.
                    }
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

    }

    public static void showExportSuccessDialog(String s) {
        JOptionPane.showMessageDialog(null, "Xuất file " + s + "thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
