package model;

import Dao.OrderServiceDao;
import Dao.OrderServiceDaoImpl;
import Dao.ShipperServiceDao;
import Dao.ShipperServiceDaoImpl;
import controller.OrderController;
import controller.QuanLyOrderController;
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
import java.util.Random;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import view.UpdateOrDeleteCustomerJFrame;
import view.UpdateOrDeleteOrder;
import view.UpdateOrDeleteShipperJFrame;

public class ClassTableModel {

    public DefaultTableModel setTableShipper(List<Shipper> listItem, String[] listColumn, JTable table, String type, boolean deleted) {
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
                if (deleted == true) {
                    obj[10] = "Edit"; // Gán giá trị tạm thời cho nút
                }
                dtm.addRow(obj);
            }
        }

        // Tạo và thiết lập Button Editor và Renderer
        if (deleted == true) {
            ButtonRenderer buttonRenderer = new ButtonRenderer();
            ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox(), type);

            // Áp dụng renderer và editor vào cột cuối cùng của table
            table.setModel(dtm);
            table.getColumnModel().getColumn(10).setCellRenderer(buttonRenderer);
            table.getColumnModel().getColumn(10).setCellEditor(buttonEditor);
        }

        return dtm;
    }

    // Lớp xử lý việc hiển thị nút
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            //Thiết lập nút là mờ đục để có thể thấy màu nền.
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                //set mau cho hang neu duoc chon
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
                // Truy cập đến đối tượng Shipper tương ứng ở đây thông qua row index và thực hiện hành động chỉnh sửa shipper
                UpdateOrDeleteShipperJFrame updateOrDeleteShipperJFrame = new UpdateOrDeleteShipperJFrame(shipper);
                updateOrDeleteShipperJFrame.setVisible(true);
                updateOrDeleteShipperJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            }

            if (type.equals("Customer") && isPushed) {
                int selectedRowIndex = table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                Customer customer = new Customer();
                customer.setId((int) model.getValueAt(selectedRowIndex, 0));
                customer.setName((String) model.getValueAt(selectedRowIndex, 1));
                customer.setPhoneNumber((String) model.getValueAt(selectedRowIndex, 2));
                customer.setEmail((String) model.getValueAt(selectedRowIndex, 3));
                customer.setGender((boolean) model.getValueAt(selectedRowIndex, 4));
                customer.setWard((String) model.getValueAt(selectedRowIndex, 5));
                customer.setProvince((String) model.getValueAt(selectedRowIndex, 6));
                customer.setDistinct((String) model.getValueAt(selectedRowIndex, 7));

                UpdateOrDeleteCustomerJFrame customerJFrame = new UpdateOrDeleteCustomerJFrame(customer);
                customerJFrame.setVisible(true);
                customerJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            if (type.equals("Order") && isPushed) {
                // TODO: Thực thi hành động khi nút được nhấn
                OrderServiceDao orderServiceDao = new OrderServiceDaoImpl();
                int selectedRowIndex = table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                Order order = new Order();
                order.setId((int) model.getValueAt(selectedRowIndex, 0));
                orderServiceDao.getDataFromID(order);

                if (label.equals("Giao hàng")) {
                    if (order.getShipCount() > 3 && order.getShipTime() != null) {
                        orderServiceDao.Delete(order);
                        JOptionPane.showMessageDialog(null, "Đơn hàng đá quá số lần giao quy định, không thể giao nữa.Đơn hàng đã bị xoa");
                    } else if (order.getShipTime() != null) {
                        JOptionPane.showMessageDialog(null, "Đơn hàng đang được giao hàng");

                    } else {
                        System.out.println("0" + order.toString());
                        //orderServiceDao.UpdateOrderTime(order);
                        LocalDateTime currentTime = LocalDateTime.now();
                        order.setShipTime(currentTime);
                        orderServiceDao.Update(order);
                        System.out.println("2" + order.toString());
                        if (orderServiceDao.Update(order) > 0) {
                            JOptionPane.showMessageDialog(null, "Bắt đầu giao hàng");

                        }

                    }
                }
                if (label.equals("Hủy")) {
                    if (order.getShipTime() != null) {
                        JOptionPane.showMessageDialog(null, "Không thể xóa đơn hàng đang được giao");
                    } else {
                        ShipperServiceDao shipperServiceDao = new ShipperServiceDaoImpl();
                        List<Integer> listId = shipperServiceDao.getListId();

                        // Kiểm tra xem danh sách có rỗng hay không để tránh lỗi
                        if (listId != null && !listId.isEmpty()) {
                            // Chọn ngẫu nhiên một ID từ danh sách
                            int randomIndex = new Random().nextInt(listId.size());
                            int randomShipperId = listId.get(randomIndex);

                            // Cập nhật ShipperID của đối tượng order
                            int result = orderServiceDao.UpdateShipperID(order, randomShipperId);

                            if (result > 0) {
                                JOptionPane.showMessageDialog(null, "Đơn hàng đã chuyển cho shipper có id: " + randomShipperId);
                            } else {
                                JOptionPane.showMessageDialog(null, "Cập nhật thất bại. Vui lòng thử lại.");
                            }
                        } else {
                            // Xử lý trường hợp danh sách ID rỗng (nếu cần)
                            JOptionPane.showMessageDialog(null, "Danh sách ID rỗng.");
                        }
                    }
                }

                if (label.equals("Hoàn thành")) {
                    // xu ly dieu kien 
                    if (order.getShipTime() == null) {
                        JOptionPane.showMessageDialog(null, "Đơn hàng chưa được giao");

                    } else {
                        LocalDateTime currentTime = LocalDateTime.now();
                        orderServiceDao.UpdateCompleteTime(order);
                        order.setCompletedTime(currentTime);
                        JOptionPane.showMessageDialog(null, "Đơn hàng đã giao thành công");
                    }

                }

                if (label.equals("Huy")) {
                    order.setDeleted(true);
                    orderServiceDao.Update(order);
                    JOptionPane.showMessageDialog(null, "Xóa đơn hàng thành công");
                }

                if (label.equals("Xóa")) {
//                    OrderController orderController = new OrderController();
//                    orderController.DeleteOrder(order);

                    int result = orderServiceDao.UpdateShipCount(order);
                    if (result > 0) {
                        // Kiểm tra số lần giao hàng sau khi cập nhật
                        if (order.getShipCount() > 3 && order.getShipTime() != null) {
                            //orderServiceDao.Delete(order);
                            order.setDeleted(true);
                            orderServiceDao.Update(order);
                            JOptionPane.showMessageDialog(null, "Đơn hàng đã quá số lần giao quy định, không thể giao nữa. Đơn hàng đã bị xóa.");
                        } else if (order.getShipCount() >= 3) {
                            int count = 3 - order.getShipCount();
                            model.setValueAt(order.getShipCount() + 1, selectedRowIndex, 11);
                            JOptionPane.showMessageDialog(null, "Còn lại " + count + " lần giao hàng.");
                        } else {
                            int count = order.getShipCount() + 1;
                            model.setValueAt(count, selectedRowIndex, 11);
                            JOptionPane.showMessageDialog(null, "Đơn hàng có thể tiếp tục giao.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cập nhật thất bại. Vui lòng thử lại.");
                    }

                } else if (label.equals("Chỉnh sửa")) {
                    UpdateOrDeleteOrder updateOrDeleteOrder = new UpdateOrDeleteOrder(order);
                    updateOrDeleteOrder.setVisible(true);
                    updateOrDeleteOrder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
                orderServiceDao.Update(order);
                // Truy cập đến đối tượng Shipper tương ứng ở đây thông qua row index và thực
                // hiện hành động chỉnh sửa shipper
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            //kết thúc việc chỉnh sửa ô và đặt lại isPushed về false.
            isPushed = false;
            return super.stopCellEditing();
        }

        public void actionPerformed(ActionEvent e) {
            //thông báo rằng việc chỉnh sửa ô đã hoàn thành.
            fireEditingStopped();
        }
    }

    public DefaultTableModel setTableOrder(List<Order> listOrders, String[] listColumn, JTable table, String type, String shipperororder) {

        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column == 13 || column == 11 || column == 12 || column == 14); // Chỉ có cột chứa nút mới có thể chỉnh sửa
            }
        };

        dtm.setColumnIdentifiers(listColumn);
        int columnCount = listColumn.length;

        int columns = listColumn.length;
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
                    } else {
                        obj[10] = null;
                    }
                    if (shipperororder.equals("Order")) {
                        obj[11] = "Chỉnh sửa";
                    } else if (shipperororder.equals("Shipper")) {
                        obj[11] = "Hủy";
                        obj[12] = "Giao hàng";
                    } else if (shipperororder.equals("Customer")) {
                        obj[11] = "Huy";
                    }
                    dtm.addRow(obj);

                }
                ButtonRenderer buttonRenderer = new ButtonRenderer();
                ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox(), "Order");

                // Áp dụng renderer và editor vào cột cuối cùng của table
                table.setModel(dtm);
                table.getColumnModel().getColumn(11).setCellRenderer(buttonRenderer);
                table.getColumnModel().getColumn(11).setCellEditor(buttonEditor);
                if (shipperororder.equals("Shipper")) {
                    table.getColumnModel().getColumn(12).setCellRenderer(buttonRenderer);
                    table.getColumnModel().getColumn(12).setCellEditor(buttonEditor);
                }

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
                    table.setModel(dtm);

                }
            } else if (type == "Processing") {
                for (Order order : listOrders) {
                    Object[] rowData = new Object[15]; // Tạo mảng với kích thước 15 (số cột)
                    rowData[0] = order.getId(); // 0
                    rowData[1] = order.getName();
                    rowData[2] = order.getShipFee();
                    rowData[3] = order.getPrice();
                    rowData[4] = order.getWard();
                    rowData[5] = order.getDistance();
                    rowData[6] = order.getOrderDate();
                    rowData[7] = order.getShipperId();
                    rowData[8] = order.getCusId();
                    rowData[9] = order.isRespond();
                    rowData[10] = order.getShipTime();
                    rowData[11] = order.getShipCount();
                    rowData[12] = order.getServiceId();

                    if (shipperororder.equals("Shipper")) {
                        rowData[13] = "Xóa";
                        rowData[14] = "Hoàn thành";
                    }

                    dtm.addRow(rowData);
                }
                ButtonRenderer buttonRenderer = new ButtonRenderer();
                ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox(), "Order");

                // Áp dụng renderer và editor vào cột cuối cùng của table
                table.setModel(dtm);

                if (shipperororder.equals("Shipper")) {
                    table.getColumnModel().getColumn(14).setCellRenderer(buttonRenderer);
                    table.getColumnModel().getColumn(14).setCellEditor(buttonEditor);
                    table.getColumnModel().getColumn(13).setCellRenderer(buttonRenderer);
                    table.getColumnModel().getColumn(13).setCellEditor(buttonEditor);
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
                    dtm.addRow(obj);
                    table.setModel(dtm);
                }
            }
            return dtm;
        } else {
            return null;
        }
    }

    public DefaultTableModel setTableCustomer(List<Customer> listItem, String[] listColumn, JTable table, String type, boolean isdelete) {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
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
                obj[5] = customer.getWard();
                obj[6] = customer.getProvince();
                obj[7] = customer.getDistinct();
                if (isdelete == true) {
                    obj[8] = "Edit"; // Gán giá trị tạm thời cho nút
                }
                dtm.addRow(obj);
            }
        }

        if (isdelete == true) {
            // Tạo và thiết lập Button Editor và Renderer
            ButtonRenderer buttonRenderer = new ButtonRenderer();
            ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox(), type);

            // Áp dụng renderer và editor vào cột cuối cùng của table
            table.setModel(dtm);
            table.getColumnModel().getColumn(8).setCellRenderer(buttonRenderer);
            table.getColumnModel().getColumn(8).setCellEditor(buttonEditor);
        }

        return dtm;
    }

    public DefaultTableModel setTableService(List<Service> listItem, String[] listColumn) {
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
            for (Service service : listItem) {
                obj = new Object[columns];
                obj[0] = service.getId();
                obj[1] = service.getName();
                obj[2] = service.getMaxDistance();
                obj[3] = service.getMaxWeight();
                obj[4] = service.getPrice();

                dtm.addRow(obj);
            }
        }
        return dtm;
    }
}
