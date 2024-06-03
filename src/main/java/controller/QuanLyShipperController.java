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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

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
import view.IncomeForShipper;
//import view.IncomeByShipper;
import view.InsertShipperJFrame;
import view.ListOrderJFrame;
//import view.ListOrderJFrame;
import view.UpdateOrDeleteShipperJFrame;

public class QuanLyShipperController {

    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
    private JComboBox<String> jcbFillter;
    private JButton btnPrint;
    private ShipperServiceDao shipperServiceDao = null;
    private String[] listColumn = {"SHIPPER_ID", "SHIPPER_NAME", "SHIPPER_GENDER", "SHIPPER_PHONE", "SHIPPER_PROVINCE", "SHIPPER_DISTINCT", "SHIPPER_WARD", "SHIPPER_LICENSEPLATE", "SHIPPER_STATUS", "SHIPPER_RATING", "Edit"};

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyShipperController(JTextField jtfSearch) {

    }

    public QuanLyShipperController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch, JComboBox<String> jcbFillter, JButton btnPrint) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.jcbFillter = jcbFillter;
        this.btnPrint = btnPrint;
        this.shipperServiceDao = new ShipperServiceDaoImpl();
    }

    public void setDataToTable(boolean isDeleted) {
        List<Shipper> listItem = shipperServiceDao.getList(isDeleted);

//        DefaultTableModel model = new ClassTableModel().setTableShipper(listItem, listColumn);
//        JTable table = new JTable(model);
        JTable table = new JTable();
        ClassTableModel model = new ClassTableModel();
        table.setModel(model.setTableShipper(listItem, listColumn, table,"Shipper",true));
// Có thể cần thiết lập kích thước cột, v.v. ở đây

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
                        shipper.setId((int) model.getValueAt(selectedRowIndex, 0));
                        shipper.setName((String) model.getValueAt(selectedRowIndex, 1));
                        shipper.setGender((String) model.getValueAt(selectedRowIndex, 2));
                        shipper.setPhoneNumber((String) model.getValueAt(selectedRowIndex, 3));
                        shipper.setProvince((String) model.getValueAt(selectedRowIndex, 4));
                        shipper.setDistinct((String) model.getValueAt(selectedRowIndex, 5));
                        shipper.setWard((String) model.getValueAt(selectedRowIndex, 6));
                        shipper.setLicensePlate((String) model.getValueAt(selectedRowIndex, 7));
                        shipper.setStatus((String) model.getValueAt(selectedRowIndex, 8));
                        shipper.setRating((double) model.getValueAt(selectedRowIndex, 9));
                        //shipper.setIsDeleted((boolean) model.getValueAt(selectedRowIndex, 10));

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
                                IncomeForShipper incomeForShipper = new IncomeForShipper(shipper);
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
//                Shipper shipper = new Shipper();
//
//                InsertShipperJFrame shipperJFrame = new InsertShipperJFrame(shipper);
                InsertShipperJFrame shipperJFrame = new InsertShipperJFrame();
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

        jcbFillter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) jcbFillter.getSelectedItem();
                if (selectedValue.equals("Chưa xóa")) {
                    System.out.println("Chưa xóa");
                    setDataToTable(false);
                } else {
                     System.out.println("Đã xóa");
                List<Shipper> listItem = shipperServiceDao.getList(true);

                // Remove the "Edit" column from the listColumn array
                List<String> listColumnWithoutEdit = new ArrayList<>(Arrays.asList(listColumn));
                listColumnWithoutEdit.remove("Edit");

                // Convert back to array
                String[] listColumnArray = listColumnWithoutEdit.toArray(new String[0]);

                // Set table with modified listColumn array
                JTable table = new JTable();
                ClassTableModel model = new ClassTableModel();
                table.setModel(model.setTableShipper(listItem, listColumnArray, table,"Shipper",false));
                rowSorter = new TableRowSorter<>(table.getModel());
                table.setRowSorter(rowSorter);

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
            }

        });

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

                    // Kiểm tra xem tên file có kết thúc bằng .xlsx chưa
                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        // Nếu không có, thêm đuôi .xlsx vào sau tên file
                        filePath += ".xlsx";
                    }

                    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                        XSSFSheet spreadsheet = workbook.createSheet("Shipper");

                        XSSFRow row = spreadsheet.createRow(2);
                        row.setHeight((short) 500);
                        Cell cell = row.createCell(0, CellType.STRING);
                        cell.setCellValue("DANH SÁCH SHIPPER");

                        row = spreadsheet.createRow(3);
                        row.setHeight((short) 500);
                        //String[] headers = {"ID", "Họ và tên", "Ngày sinh", "Giới tính", "Ngày đầu làm việc", "Số điện thoại", "Email", "Địa chỉ", "Mô tả"};
                        for (int i = 0; i < listColumn.length-1; i++) {
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue(listColumn[i]);
                            spreadsheet.autoSizeColumn(i);
                        }

                        int rowNum = 4;

                        ShipperServiceDao shipperServiceDao = new ShipperServiceDaoImpl();

                        List<Shipper> shipperList = shipperServiceDao.getList(false);

//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        for (Shipper shipper : shipperList) {
                            row = spreadsheet.createRow(rowNum++);
                            row.setHeight((short) 400);
                            row.createCell(0).setCellValue(shipper.getId());
                            row.createCell(1).setCellValue(shipper.getName());
                            row.createCell(2).setCellValue(shipper.isGender());
                            row.createCell(3).setCellValue(shipper.getPhoneNumber());
                            row.createCell(4).setCellValue(shipper.getDistinct());
                            row.createCell(5).setCellValue(shipper.getProvince());
                            row.createCell(6).setCellValue(shipper.getWard());
                            row.createCell(7).setCellValue(shipper.getLicensePlate());
                            row.createCell(8).setCellValue(shipper.getStatus());
                            row.createCell(9).setCellValue(shipper.getRating());
                            //row.createCell(10).setCellValue(shipper.isIsDeleted());

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
