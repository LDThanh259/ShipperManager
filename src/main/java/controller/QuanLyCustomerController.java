package controller;

import Dao.*;
import model.ClassTableModel;
import model.Customer;

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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
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
//import view.IncomeByShipper;
import view.InsertCustomerJFrame;
//import view.ListOrderJFrame;
import view.UpdateOrDeleteCustomerJFrame;

public class QuanLyCustomerController {

    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
    private JButton btnPrint;
    private CustomerServiceDao customerServiceDao = null;
    private String[] listColumn = {"CUS_ID", "CUS_NAME", "CUS_PHONENUMBER", "CUS_EMAIL", "CUS_GENDER", "CUS_WARD", "CUS_PROVINCE", "CUS_DISTRICT"};

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyCustomerController(JTextField jtfSearch) {

    }

    public QuanLyCustomerController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch, JButton btnPrint) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnPrint = btnPrint;
        this.customerServiceDao = new CustomerServiceDaoImpl();
    }

    public void setDataToTable() {
        List<Customer> listItem = customerServiceDao.getList();
        DefaultTableModel model = new ClassTableModel().setTableCustomer(listItem, listColumn);
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
                        JMenuItem editCustomerMenuItem = new JMenuItem("Chỉnh sửa thông tin khách hàng");
//                        JMenuItem orderListMenuItem = new JMenuItem("Danh sách đơn hàng");
//                        JMenuItem revenueMenuItem = new JMenuItem("Doanh thu");
                        JMenuItem listDeleteCustomerMenuItem = new JMenuItem("Danh sách khách hàng đã xóa");

                        popupMenu.add(editCustomerMenuItem);
//                        popupMenu.add(orderListMenuItem);
//                        popupMenu.add(revenueMenuItem);
                        popupMenu.add(listDeleteCustomerMenuItem);

                        //Shipper shipper = getShipperFromSelectedRow(selectedRowIndex, model);
                        Customer customer = new Customer();
                        customer.setId((int) model.getValueAt(selectedRowIndex, 0));
                        customer.setName((String) model.getValueAt(selectedRowIndex, 1));
                        customer.setEmail((String) model.getValueAt(selectedRowIndex, 2));
                        String genderString = (String) model.getValueAt(selectedRowIndex, 3);
                        boolean gender = Boolean.valueOf(genderString);
                        customer.setGender(gender);
                        boolean phoneNumberBoolean = (boolean) model.getValueAt(selectedRowIndex, 4);
                        String phoneNumberString = phoneNumberBoolean ? "Có" : "Không";
                        customer.setDistinct((String) model.getValueAt(selectedRowIndex, 5));
                        customer.setProvince((String) model.getValueAt(selectedRowIndex, 6));
                        customer.setWard((String) model.getValueAt(selectedRowIndex, 7));

                        editCustomerMenuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                UpdateOrDeleteCustomerJFrame customerJFrame = new UpdateOrDeleteCustomerJFrame(customer);
                                customerJFrame.setTitle("Thông tin khách hàng");
                                customerJFrame.setResizable(false);
                                customerJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                                customerJFrame.setLocationRelativeTo(null);
                                customerJFrame.setVisible(true);
                            }
                        });

//                        orderListMenuItem.addActionListener(new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent e) {
//                                // Xử lý sự kiện hiển thị danh sách đơn hàng
//                                //ListOrderJFrame listOrderJFrame = new ListOrderJFrame(shipper);
//                            }
//                        });
//
//                        revenueMenuItem.addActionListener(new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent e) {
//                                //IncomeByShipper incomeByShipper = new IncomeByShipper(shipper);
//                            }
//                        });
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
                Customer customer = new Customer();

//                shipper.setStartWork(dateString);
                InsertCustomerJFrame customerJFrame = new InsertCustomerJFrame(customer);
                customerJFrame.setTitle("Thông tin khách hàng");
                customerJFrame.setLocationRelativeTo(null);
                customerJFrame.setResizable(false);
                customerJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                customerJFrame.setVisible(true);
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
                        XSSFSheet spreadsheet = workbook.createSheet("Customer");

                        XSSFRow row = spreadsheet.createRow(2);
                        row.setHeight((short) 500);
                        Cell cell = row.createCell(0, CellType.STRING);
                        cell.setCellValue("DANH SÁCH KHÁCH HÀNG");

                        row = spreadsheet.createRow(3);
                        row.setHeight((short) 500);
                        //String[] headers = {"ID", "Họ và tên", "Ngày sinh", "Giới tính", "Ngày đầu làm việc", "Số điện thoại", "Email", "Địa chỉ", "Mô tả"};
                        for (int i = 0; i < listColumn.length; i++) {
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue(listColumn[i]);
                            spreadsheet.autoSizeColumn(i);
                        }

                        int rowNum = 4;

                        CustomerServiceDao customerServiceDao = new CustomerServiceDaoImpl();

                        List<Customer> customerList = customerServiceDao.getList();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        for (Customer customer : customerList) {
                            row = spreadsheet.createRow(rowNum++);
                            row.setHeight((short) 400);
                            row.createCell(0).setCellValue(customer.getId());
                            row.createCell(1).setCellValue(customer.getName());
                            row.createCell(2).setCellValue(customer.getEmail());
                            row.createCell(3).setCellValue(customer.getGender());
                            row.createCell(4).setCellValue(customer.getPhoneNumber());
                            row.createCell(5).setCellValue(customer.getDistinct());
                            row.createCell(6).setCellValue(customer.getProvince());
                            row.createCell(7).setCellValue(customer.getWard());

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
