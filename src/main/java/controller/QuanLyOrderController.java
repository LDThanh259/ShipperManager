/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Dao.OrderServiceDao;
import Dao.OrderServiceDaoImpl;
import com.toedter.calendar.JMonthChooser;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.Order;
import model.Shipper;
import model.TableOrder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.UpdateOrDeleteOrder;
import view.InsertOrderJFrame;

/**
 *
 * @author ADMIN
 */
public class QuanLyOrderController {

    private JLabel jlbID;
    private JLabel jlbName;
    private JMonthChooser jmcMonth;
    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
    private JButton btnPrint;
    private OrderServiceDao orderServiceDao = null;
    private String[] listColumn = {"ORDERS_ID", "ORDERS_NAME", "ORDERS_DELIVERY_LOCATION", "ORDERS_DELIVERY_TIME", "ORDERS_RECEIVE_LOCATION", "ORDERS_RECEIVE_TIME", "ORDERS_STATUS", "ORDERS_FEEDBACK", "ORDERS_DISTANCE", "ORDERS_PRICE", "SHIPPER_ID"};
    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyOrderController(JLabel jlbID, JLabel jlbName, JMonthChooser jmcMonth, JPanel jpnView, JTextField jtfSearch) {
        this.jlbID = jlbID;
        this.jlbName = jlbName;
        this.jmcMonth = jmcMonth;
        this.jpnView = jpnView;
        this.jtfSearch = jtfSearch;

        this.orderServiceDao = new OrderServiceDaoImpl();
    }

    public QuanLyOrderController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch, JButton btnPrint) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnPrint = btnPrint;
        this.orderServiceDao = new OrderServiceDaoImpl();
    }

    public void initTable() {
        List<Order> listOrders = orderServiceDao.getList();
        DefaultTableModel model = new TableOrder().setTableOrder(listOrders, listColumn);
        JTable table = new JTable(model);
        setupTable(table);
        initSearchListener();
        initMouseListener(table);
        //initMonthChooserListener();
    }

    public void initTable(Shipper shipper) {
        jlbID.setText("" + shipper.getShipper_Id());
        jlbName.setText(shipper.getName());
        int selectedMonthIndex = jmcMonth.getMonth();
        List<Order> listOrders = orderServiceDao.getOrderListForShipper(shipper, selectedMonthIndex + 1);
        DefaultTableModel model = new TableOrder().setTableOrder(listOrders, listColumn);
        JTable table = new JTable(model);
        setupTable(table);
        initSearchListener();
        //initMouseListener(table);
        initMonthChooserListener(shipper);
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

    private void initMouseListener(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.getSelectedRow();
                    selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex); // Chuyển đổi từ view sang model

                    if (selectedRowIndex != -1 && selectedRowIndex < model.getRowCount()) {

                        Order order = new Order();
                        order.setId((int) model.getValueAt(selectedRowIndex, 0));
                        order.setName((String) model.getValueAt(selectedRowIndex, 1));
                        order.setDelivery_Location((String) model.getValueAt(selectedRowIndex, 2));
                        order.setDelivery_Time((Date) model.getValueAt(selectedRowIndex, 3));

                        order.setReceive_Location((String) model.getValueAt(selectedRowIndex, 4));
                        // Kiểm tra giá trị null trước khi gán
                        Object receiveTimeValue = model.getValueAt(selectedRowIndex, 5);
                        if (receiveTimeValue instanceof Date) {
                            order.setReceive_Time((Date) receiveTimeValue);
                        } else {
                            order.setReceive_Time(null);
                        }

                        order.setStatus((String) model.getValueAt(selectedRowIndex, 6));
                        order.setFeedback((String) model.getValueAt(selectedRowIndex, 7) != null
                                ? (String) model.getValueAt(selectedRowIndex, 7) : "");
                        order.setDistance((double) model.getValueAt(selectedRowIndex, 8));
                        order.setPrice((double) model.getValueAt(selectedRowIndex, 9));
                        order.setShipper_ID((int) model.getValueAt(selectedRowIndex, 10));

                        UpdateOrDeleteOrder orderJFrame = new UpdateOrDeleteOrder(order);
                        orderJFrame.setTitle("Thông tin Shipper");
                        orderJFrame.setResizable(false);
                        orderJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        orderJFrame.setLocationRelativeTo(null);
                        orderJFrame.setVisible(true);
                    } else {
                        // Xử lý khi chỉ số hàng không hợp lệ
                        System.out.println("Chỉ số hàng không hợp lệ.");
                    }

                }
            }
        });
    }

    private void initMonthChooserListener(Shipper shipper) {
        jmcMonth.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("month")) {
                    int selectedMonthIndex = jmcMonth.getMonth();
                    List<Order> listOrders = orderServiceDao.getOrderListForShipper(shipper, selectedMonthIndex + 1);
                    DefaultTableModel model = new TableOrder().setTableOrder(listOrders, listColumn);
                    JTable table = new JTable(model);
                    setupTable(table);
                }
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
                insertOrderJFrame.setTitle("Thong tin Order");
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
                    String[] headers = {"ID", "Họ và tên", "Nơi đặt hàng", "Ngày đặt hàng", "Nơi nhận hàng", "Ngày nhận hàng", "Tình trạng", "Feedback", "Khoảng cách", "Giá", "shipper_id"};
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
                        row.createCell(2).setCellValue(order.getDelivery_Location());
                        row.createCell(3).setCellValue(order.getDelivery_Time());
                        row.createCell(4).setCellValue(order.getReceive_Location());
                        row.createCell(5).setCellValue(order.getReceive_Time());
                        row.createCell(6).setCellValue(order.getStatus());
                        row.createCell(7).setCellValue(order.getFeedback());
                        row.createCell(8).setCellValue(order.getDistance());
                        row.createCell(9).setCellValue(order.getPrice());
                        row.createCell(10).setCellValue(order.getShipper_ID());
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

    private static void showExportSuccessDialog(String s) {
        JOptionPane.showMessageDialog(null, "Xuất file " + s + "thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
