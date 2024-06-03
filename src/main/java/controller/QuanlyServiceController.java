package controller;

import Dao.*;
import model.ClassTableModel;
import model.*;

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
import view.InsertServiceJFrame;
//import view.ListOrderJFrame;
import view.UpdateOrDeleteServiceJFrame;

public class QuanlyServiceController {

    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
    private JButton btnPrint;
    private ServiceSERVICEDao serviceSERVICEDao = null;
    private String[] listColumn = {"SV_ID", "SV_NAME", "SV_MAXDISTANCE","SV_MAXWEIGHT","SV_PRICE"};

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanlyServiceController(JTextField jtfSearch) {

    }

    public QuanlyServiceController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch, JButton btnPrint) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnPrint = btnPrint;
        this.serviceSERVICEDao = new ServiceSERVICEDaoImpl();
    }

    public void setDataToTable() {
        List<Service> listItem = serviceSERVICEDao.getList();
        DefaultTableModel model = new ClassTableModel().setTableService(listItem, listColumn);
        JTable table = new JTable(model);

        // TableRowSorter<TableModel> cho phep sap xep thu tu cac cot theo comparator
        //https://docs.oracle.com/javase/8/docs/api/javax/swing/table/TableRowSorter.html
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

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
                        JMenuItem editServiceMenuItem = new JMenuItem("Chỉnh sửa thông tin dịch vụ");
//                        JMenuItem orderListMenuItem = new JMenuItem("Danh sách đơn hàng");
//                        JMenuItem revenueMenuItem = new JMenuItem("Doanh thu");
//                        JMenuItem listDeleteShipperMenuItem = new JMenuItem("Danh sách shipper đã xóa");

                        popupMenu.add(editServiceMenuItem);
//                        popupMenu.add(orderListMenuItem);
//                        popupMenu.add(revenueMenuItem);
//                        popupMenu.add(listDeleteShipperMenuItem);

                        //Shipper shipper = getShipperFromSelectedRow(selectedRowIndex, model);
                        Service service = new Service();
                        service.setId((int) model.getValueAt(selectedRowIndex, 0));
                        service.setName((String) model.getValueAt(selectedRowIndex, 1));
                        service.setMaxDistance((int) model.getValueAt(selectedRowIndex,2));
                        service.setMaxWeight((int) model.getValueAt(selectedRowIndex,3));
                        service.setPrice((int) model.getValueAt(selectedRowIndex,4));

                        editServiceMenuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                UpdateOrDeleteServiceJFrame serviceJFrame = new UpdateOrDeleteServiceJFrame(service);
                                serviceJFrame.setTitle("Thông tin Dịch vụ");
                                serviceJFrame.setResizable(false);
                                serviceJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                                serviceJFrame.setLocationRelativeTo(null);
                                serviceJFrame.setVisible(true);
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
                Service service = new Service();
//                LocalDate localDate = LocalDate.now();
//                Date date = java.sql.Date.valueOf(localDate); // Convert LocalDate to Date
//                shipper.setDob(date);

//                shipper.setStartWork(dateString);
                InsertServiceJFrame serviceJFrame = new InsertServiceJFrame(service);
                serviceJFrame.setTitle("Thong tin Dịch vụ");
                serviceJFrame.setLocationRelativeTo(null);
                serviceJFrame.setResizable(false);
                serviceJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                serviceJFrame.setVisible(true);
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
                    
                    if(!filePath.toLowerCase().endsWith(".xlsx"))
                    {
                            filePath += ".xlsx";
                    }
                    
                    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                        XSSFSheet spreadsheet = workbook.createSheet("Service");

                        XSSFRow row = spreadsheet.createRow(2);
                        row.setHeight((short) 500);
                        Cell cell = row.createCell(0, CellType.STRING);
                        cell.setCellValue("DANH SÁCH DICH VU");

                        row = spreadsheet.createRow(3);
                        row.setHeight((short) 500);
                        //String[] headers = {"ID", "Họ và tên", "Ngày sinh", "Giới tính", "Ngày đầu làm việc", "Số điện thoại", "Email", "Địa chỉ", "Mô tả"};
                        for (int i = 0; i < listColumn.length; i++) {
                            cell = row.createCell(i, CellType.STRING);
                            cell.setCellValue(listColumn[i]);
                            spreadsheet.autoSizeColumn(i);
                        }

                        int rowNum = 4;

                        ServiceSERVICEDao serviceSERVICEDao = new ServiceSERVICEDaoImpl();

                        List<Service> serviceList = serviceSERVICEDao.getList();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        for (Service service : serviceList) {
                            row = spreadsheet.createRow(rowNum++);
                            row.setHeight((short) 400);
                            row.createCell(0).setCellValue(service.getId());
                            row.createCell(1).setCellValue(service.getName());
                            row.createCell(2).setCellValue(service.getMaxDistance()); // Chuyển đổi ngày sang chuỗi với định dạng "yyyy-MM-dd"
                            row.createCell(3).setCellValue(service.getMaxWeight());
                            row.createCell(4).setCellValue(service.getPrice());
                            
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
