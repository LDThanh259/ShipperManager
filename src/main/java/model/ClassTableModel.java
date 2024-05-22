package model;

import Dao.OrderServiceDao;
import Dao.OrderServiceDaoImpl;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ClassTableModel {

    public DefaultTableModel setTableShipper(List<Shipper> listItem, String[] listColumn, JTable table, String type) {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 10; // Chỉ có cột chứa nút mới có thể chỉnh sửa
            }
        };

        dtm.setColumnIdentifiers(listColumn);
        int columns = listColumn.length;
        Object[] obj = null;

        int rows = listItem.size();
        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                Shipper shipper = listItem.get(i);
                obj = new Object[columns];
                obj[0] = shipper.getId();
                obj[1] = shipper.getName();
                obj[2] = shipper.isGender();
                obj[3] = shipper.getPhoneNumber();
                obj[4] = shipper.getProvince();
                obj[5] = shipper.getDistinct();
                obj[6] = shipper.getWard();
                obj[7] = shipper.getLicensePlate();
                obj[8] = shipper.getStatus();
                obj[9] = shipper.getRating();
                obj[10] = "Edit"; // Gán giá trị tạm thời cho nút
                dtm.addRow(obj);
            }
        }

        // Tạo và thiết lập Button Editor và Renderer
        ButtonRenderer buttonRenderer = new ButtonRenderer();
        ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox(), type);

        // Áp dụng renderer và editor vào cột cuối cùng của table
        table.setModel(dtm);
        table.getColumnModel().getColumn(10).setCellRenderer(buttonRenderer);
        table.getColumnModel().getColumn(10).setCellEditor(buttonEditor);

        return dtm;
    }

    // Lớp xử lý việc hiển thị nút
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Lớp xử lý sự kiện của nút
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;
        private String type;

        public ButtonEditor(JCheckBox checkBox, String type) {
            super();
            button = new JButton();
            this.type = type;
            button.setOpaque(true);
            button.addActionListener(this);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            this.table = table;
            return button;
        }

        public Object getCellEditorValue() {
            if (type.equals("Shipper") && isPushed) {
                // TODO: Thực thi hành động khi nút được nhấn
                // Lấy thông tin shipper từ hàng được chọn
                int selectedRowIndex = table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
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

                // In ra thông tin shipper
                System.out.println(shipper.toString());
                // Truy cập đến đối tượng Shipper tương ứng ở đây thông qua row index và thực hiện hành động chỉnh sửa shipper
            }

            if (type.equals("Order") && isPushed) {
                // TODO: Thực thi hành động khi nút được nhấn
                System.out.println("itorValu");

                // TODO: Thực thi hành động khi nút được nhấn
                // Lấy thông tin shipper từ hàng được chọn
                OrderServiceDao orderServiceDao = new OrderServiceDaoImpl();
                int selectedRowIndex = table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                Order order = new Order();
                order.setId((int) model.getValueAt(selectedRowIndex, 0));
                orderServiceDao.getDataFromID(order);
                if (label.equals("Bắt Đầu")) {
                    if (order.getShipCount() > 3) {
                        orderServiceDao.Delete(order);
                        JOptionPane.showMessageDialog(null, "Đơn hàng đá quá số lần giao quy định, không thể giao nữa.Đơn hàng đã bị hủy");
                    } else if (order.getShipTime() != null) {
                        JOptionPane.showMessageDialog(null, "Đơn hàng đang được giao hàng");

                    } else {
                        orderServiceDao.UpdateOrderTime(order);
                        LocalDateTime currentTime = LocalDateTime.now();

                        order.setShipTime(currentTime);
                        JOptionPane.showMessageDialog(null, "Bắt đầu giao hàng");
                        System.out.println(order.getShipTime());
                    }
                } else if (label.equals("Xóa")) {
                    if (order.getShipTime() != null) {
                        JOptionPane.showMessageDialog(null, "Không thể xóa đơn hàng đang được giao");
                    } else {
                        orderServiceDao.Delete(order);
                        order.setDeleted(true);
                        JOptionPane.showMessageDialog(null, "Đơn hàng đã bị hủy");
                    }
                } else if (label.equals("Hoàn Thành")) {
                    // xu ly dieu kien 
                    System.out.println("" + order.getShipTime());
                    if (order.getShipTime() == null) {
                        JOptionPane.showMessageDialog(null, "Đơn hàng chưa được giao");

                    } else {
                        LocalDateTime currentTime = LocalDateTime.now();
                        orderServiceDao.UpdateCompleteTime(order);
                        order.setCompletedTime(currentTime);
                        JOptionPane.showMessageDialog(null, "Đơn hàng đã giao thành công");
                    }

                }
//                orderServiceDao.Update(order);
                // Truy cập đến đối tượng Shipper tương ứng ở đây thông qua row index và thực
                // hiện hành động chỉnh sửa shipper
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
        }
    }

//    public DefaultTableModel setTableOrder1(List<Order> listOrders, String[] listColumn, JTable table, String type) {
//        DefaultTableModel dtm = null;
//        DefaultTableModel temp = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//
//        DefaultTableModel tableProccesing = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return column == 14;
//            }
//        };
//        //dtm = temp;
//
//        int columnCount = listColumn.length;
//        //dtm.setColumnIdentifiers(listColumn);
//        int columns = listColumn.length;
//        Object[] obj = null;
//
//        int rows = listOrders.size();
//        if (rows > 0) {
//            // Danh sach pending
//            if (columnCount == 10) {
//                dtm = temp;
//                dtm.setColumnIdentifiers(listColumn);
//
//                for (Order order : listOrders) {
//                    obj = new Object[columns];
//                    obj[0] = order.getId();
//                    obj[1] = order.getName();
//                    obj[2] = order.getWeight();
//                    obj[3] = order.getPrice();
//                    obj[4] = order.getWard();
//                    obj[5] = order.getOrderDate();
//                    obj[6] = order.getShipperId();
//                    obj[7] = order.getCusId();
//                    obj[8] = order.isDeleted();
//                    obj[9] = order.getServiceId();
//
//                    dtm.addRow(obj);
//                }
//
//            } // danh sach processing
//            else if (columnCount == 15) {
//                dtm = tableProccesing;
//                dtm.setColumnIdentifiers(listColumn);
//                for (Order order : listOrders) {
//                    obj = new Object[columns];
//                    obj[0] = order.getId();
//                    obj[1] = order.getName();
//                    obj[2] = order.getShipFee();
//                    obj[3] = order.getPrice();
//                    obj[4] = order.getWard();
//                    obj[5] = order.getDistance();
//                    obj[6] = order.getOrderDate();
//                    obj[7] = order.getShipperId();
//                    obj[8] = order.getCusId();
//                    obj[9] = order.isRespond();
//                    obj[10] = order.getShipTime();
//                    obj[11] = order.getShipCount();
//                    obj[12] = order.getCompletedTime();
//                    obj[13] = order.getServiceId();
//                    obj[14] = "Giao hang";
//                    dtm.addRow(obj);
//
//                }
//
//                ButtonRenderer buttonRenderer = new ButtonRenderer();
//                ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox(), type);
//
//                // Áp dụng renderer và editor vào cột cuối cùng của table
//                table.setModel(dtm);
//                table.getColumnModel().getColumn(14).setCellRenderer(buttonRenderer);
//                table.getColumnModel().getColumn(14).setCellEditor(buttonEditor);
//            } // danh sach da xoa
//            else if (columnCount == 11) {
//                dtm = temp;
//                dtm.setColumnIdentifiers(listColumn);
//                for (Order order : listOrders) {
//                    obj = new Object[columns];
//                    obj[0] = order.getId();
//                    obj[1] = order.getName();
//                    obj[2] = order.getPrice();
//                    obj[3] = order.getOrderDate();
//                    obj[4] = order.getShipperId();
//                    obj[5] = order.getCusId();
//                    obj[6] = order.isRespond();
//                    obj[7] = order.getShipTime();
//                    obj[8] = order.getShipCount();
//                    obj[9] = order.getCompletedTime();
//                    obj[10] = order.getServiceId();
//
//                    dtm.addRow(obj);
//
//                }
//            }
//            return dtm;
//        } else {
//            return null;
//        }
//    }
    public DefaultTableModel setTableOrder(List<Order> listOrders, String[] listColumn, JTable table, String type) {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column == 13 || column == 14 || column == 15 || column == 11 || column == 10); // Chỉ có cột chứa nút mới có thể chỉnh sửa
            }
        };

        dtm.setColumnIdentifiers(listColumn);
        int columnCount = listColumn.length;

        int columns = listColumn.length;
        System.out.println(type);
        Object[] obj = null;

        int rows = listOrders.size();
        if (rows > 0) {
            // Danh sach pending
            if (type == "Pending") {
                for (Order order : listOrders) {
                    obj = new Object[columns];
                    obj[0] = order.getId();
                    obj[1] = order.getName();
                    obj[2] = order.getWeight();
                    obj[3] = order.getPrice();
                    obj[4] = order.getWard();
                    obj[5] = order.getOrderDate();
                    obj[6] = order.getShipperId();
                    obj[7] = order.getCusId();
                    obj[8] = order.isDeleted();
                    obj[9] = order.getServiceId();
                    if (order.getShipFee() != 0) {
                        obj[10] = order.getShipFee();
                    }
                    obj[11] = "Bắt Đầu";
                    dtm.addRow(obj);

                }
                ButtonRenderer buttonRenderer = new ButtonRenderer();
                ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox(), "Order");

                // Áp dụng renderer và editor vào cột cuối cùng của table
                table.setModel(dtm);
                table.getColumnModel().getColumn(11).setCellRenderer(buttonRenderer);
                table.getColumnModel().getColumn(11).setCellEditor(buttonEditor);

            } // danh sach da xoa
            else if (type == "Deleted") {

                for (Order order : listOrders) {
                    obj = new Object[columns];
                    obj[0] = order.getId();
                    obj[1] = order.getName();
                    obj[2] = order.getPrice();
                    obj[3] = order.getOrderDate();
                    obj[4] = order.getShipperId();
                    obj[5] = order.getCusId();
                    obj[6] = order.isRespond();
                    obj[7] = order.getShipTime();
                    obj[8] = order.getShipCount();
                    obj[9] = order.getCompletedTime();
                    obj[10] = order.getServiceId();

                    dtm.addRow(obj);
                }
            } else if (type == "Processing") {
                for (Order order : listOrders) {
                    Vector<Object> rowData = new Vector<>();
                    rowData.add(order.getId());
                    rowData.add(order.getName());
                    rowData.add(order.getShipFee());
                    rowData.add(order.getPrice());
                    rowData.add(order.getWard());
                    rowData.add(order.getDistance());
                    rowData.add(order.getOrderDate());
                    rowData.add(order.getShipperId());
                    rowData.add(order.getCusId());
                    rowData.add(order.isRespond());
                    rowData.add(order.getShipTime());
                    rowData.add(order.getShipCount());
                    rowData.add(order.getServiceId());
                    rowData.add("Bắt Đầu"); // Sử dụng checkbox
                    rowData.add("Xóa");
                    rowData.add("Hoàn Thành");

                    dtm.addRow(rowData);
                }
                ButtonRenderer buttonRenderer = new ButtonRenderer();
                ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox(), "Order");

                // Áp dụng renderer và editor vào cột cuối cùng của table
                table.setModel(dtm);
                for (int i = 13; i < 16; i++) {
                    table.getColumnModel().getColumn(i).setCellRenderer(buttonRenderer);
                    table.getColumnModel().getColumn(i).setCellEditor(buttonEditor);
                }
            } else if (type == "Completed") {
                for (Order order : listOrders) {
                    obj = new Object[columns];
                    obj[0] = order.getId();
                    obj[1] = order.getName();
                    obj[2] = order.getPrice();
                    obj[3] = order.getOrderDate();
                    obj[4] = order.getShipperId();
                    obj[5] = order.getCusId();
                    obj[6] = order.isRespond();
                    obj[7] = order.getShipCount();
                    obj[8] = order.getCompletedTime();
                    obj[9] = order.getServiceId();
                    obj[10] = order.getShipFee();
                    System.out.println(order.getShipFee() + "sd");
                    dtm.addRow(obj);
                    table.setModel(dtm);
                }
            }
            return dtm;
        } else {
            return null;
        }
    }

    public DefaultTableModel setTableCustomer(List<Customer> listItem, String[] listColumn) {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dtm.setColumnIdentifiers(listColumn);
        int columns = listColumn.length;
        Object[] obj = null;

        int rows = listItem.size();
        if (rows > 0) {
            for (Customer customer : listItem) {
                obj = new Object[columns];
                obj[0] = customer.getId();
                obj[1] = customer.getName();
                obj[2] = customer.getPhoneNumber();
                obj[3] = customer.getEmail();
                obj[4] = customer.getGender();
                obj[5] = customer.getDistinct();
                obj[6] = customer.getProvince();
                obj[7] = customer.getWard();
                dtm.addRow(obj);
            }
        }
        return dtm;
    }

}
