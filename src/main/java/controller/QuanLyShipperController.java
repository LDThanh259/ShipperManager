package controller;

import Dao.*;
import model.ClassTableModel;
import model.Shipper;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
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
import view.InsertShipperJFrame;
import view.ListOrderJFrame;
import view.UpdateOrDeleteShipperJFrame;

public class QuanLyShipperController {

    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
    private JButton btnPrint;
    private ShipperServiceDao shipperServiceDao = null;
    private String[] listColumn = {"SHIPPER_ID", "SHIPPER_NAME", "SHIPPER_BIRTHDAY", "SHIPPER_GENDER", "SHIPPER_STARTWORK", "SHIPPER_PHONE", "SHIPPER_EMAIL", "SHIPPER_ADDRESS", "SHIPPER_DESCRIPTION"};

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyShipperController(JTextField jtfSearch) {

    }

    public QuanLyShipperController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch, JButton btnPrint) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnPrint = btnPrint;
        this.shipperServiceDao = new ShipperServiceDaoImpl();
    }

    public void setDataToTable() {
        List<Shipper> listItem = shipperServiceDao.getList();
        DefaultTableModel model = new ClassTableModel().setTableShipper(listItem, listColumn);
        JTable table = new JTable(model);

        // TableRowSorter<TableModel> cho phep sap xep thu tu cac cot theo comparator
        //https://docs.oracle.com/javase/8/docs/api/javax/swing/table/TableRowSorter.html
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        //demo comparator
//        // Define Comparator for sorting the "Age" column
//        Comparator<Integer> IDComparator = Comparator.comparingInt(Integer::intValue);
//
//        // Set the Comparator to sort the "Age" column
//        rowSorter.setComparator(1, IDComparator);
//
//        // Define Comparator for sorting the "Name" column alphabetically
//        Comparator<String> alphabetComparator = Comparator.naturalOrder();
//
//        // Set the Comparator to sort the "Name" column
//        rowSorter.setComparator(2, alphabetComparator);
// addDocumentListener su kien khi text trong jtfSearch thay dooi bao gom theem , xoas ki tu
        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    //https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/RowFilter.html
                    //public static <M,I> RowFilter<M,I> regexFilter(String regex,int... indices)
                    // regex la 1 bieu thu chinh quy https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/java/util/regex/Pattern.html

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

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRowIndex = table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                if (selectedRowIndex != -1) {
                    selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);
                    if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem editShipperMenuItem = new JMenuItem("Chỉnh sửa thông tin shipper");
                        JMenuItem orderListMenuItem = new JMenuItem("Danh sách đơn hàng");
                        JMenuItem revenueMenuItem = new JMenuItem("Doanh thu");

                        popupMenu.add(editShipperMenuItem);
                        popupMenu.add(orderListMenuItem);
                        popupMenu.add(revenueMenuItem);

                        //Shipper shipper = getShipperFromSelectedRow(selectedRowIndex, model);
                        Shipper shipper = new Shipper();
                        shipper.setShipper_Id((int) model.getValueAt(selectedRowIndex, 0));
                        shipper.setName((String) model.getValueAt(selectedRowIndex, 1));
                        shipper.setBirthDay((String) model.getValueAt(selectedRowIndex, 2));
                        shipper.setGender((String) model.getValueAt(selectedRowIndex, 3));
                        shipper.setStartWork((String) model.getValueAt(selectedRowIndex, 4));
                        shipper.setPhone((String) model.getValueAt(selectedRowIndex, 5));
                        shipper.setEmail((String) model.getValueAt(selectedRowIndex, 6));
                        shipper.setAddress((String) model.getValueAt(selectedRowIndex, 7));
                        shipper.setDescription((String) model.getValueAt(selectedRowIndex, 8) != null
                                ? (String) model.getValueAt(selectedRowIndex, 8) : "");

                        editShipperMenuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                UpdateOrDeleteShipperJFrame shipperJFrame = new UpdateOrDeleteShipperJFrame(shipper);
                                shipperJFrame.setTitle("Thông tin Shipper");
                                shipperJFrame.setResizable(false);
                                shipperJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                                shipperJFrame.setLocationRelativeTo(null);
                                shipperJFrame.setVisible(true);
                            }
                        });

                        orderListMenuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Xử lý sự kiện hiển thị danh sách đơn hàng
                                ListOrderJFrame listOrderJFrame = new ListOrderJFrame(shipper);
                            }
                        });

                        revenueMenuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Xử lý sự kiện hiển thị doanh thu
                            }
                        });

                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        // design
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();

        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();

    }

    public void setEvent() {
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Shipper shipper = new Shipper();
                LocalDate localDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateString = localDate.format(formatter);
                shipper.setBirthDay(dateString);
                shipper.setStartWork(dateString);

                InsertShipperJFrame shipperJFrame = new InsertShipperJFrame(shipper);
                shipperJFrame.setTitle("Thong tin Shipper");
                shipperJFrame.setLocationRelativeTo(null);
                shipperJFrame.setResizable(false);
                shipperJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                shipperJFrame.setVisible(true);
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
                String filePath = "F:\\JAVA\\NETBEAN\\ShipperMaven\\src\\main\\java\\export\\Danh_sach_shipper.xlsx";

                try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                    XSSFSheet spreadsheet = workbook.createSheet("Shipper");

                    XSSFRow row = spreadsheet.createRow(2);
                    row.setHeight((short) 500);
                    Cell cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue("DANH SÁCH SHIPPER");

                    row = spreadsheet.createRow(3);
                    row.setHeight((short) 500);
                    String[] headers = {"ID", "Họ và tên", "Ngày sinh", "Giới tính", "Ngày đầu làm việc", "Số điện thoại", "Email", "Địa chỉ", "Mô tả"};
                    for (int i = 0; i < headers.length; i++) {
                        cell = row.createCell(i, CellType.STRING);
                        cell.setCellValue(headers[i]);
                    }

                    int rowNum = 4;

                    ShipperServiceDao shipperServiceDao = new ShipperServiceDaoImpl();

                    List<Shipper> shipperList = shipperServiceDao.getList();

                    for (Shipper shipper : shipperList) {
                        row = spreadsheet.createRow(rowNum++);
                        row.setHeight((short) 400);
                        row.createCell(0).setCellValue(shipper.getShipper_Id());
                        row.createCell(1).setCellValue(shipper.getName());
                        row.createCell(2).setCellValue(shipper.getBirthDay());
                        row.createCell(3).setCellValue(shipper.getGender());
                        row.createCell(4).setCellValue(shipper.getStartWork());
                        row.createCell(5).setCellValue(shipper.getPhone());
                        row.createCell(6).setCellValue(shipper.getEmail());
                        row.createCell(7).setCellValue(shipper.getAddress());
                        row.createCell(8).setCellValue(shipper.getDescription());
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

    public static void showExportSuccessDialog(String s) {
        JOptionPane.showMessageDialog(null, "Xuất file " + s + "thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }


}
