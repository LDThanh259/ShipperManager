package Dao;

import database.JDBCUtil;
import model.Customer;
import model.Order;
import model.Shipper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    @Override
    public List<Order> getUnDeliveryOrders() {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT * FROM [ORDERS] WHERE ORD_TIME IS NULL AND ORD_COMPLETED_TIME IS NULL AND ORD_SHIP_COUNT = 0;";
            PreparedStatement ps = conn.prepareStatement(sql);

            List<Order> orders = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("ORD_ID"));
                getDataFromID(order);
                orders.add(order);
            }

            JDBCUtil.closeConnection(conn);
            return orders;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getCompletedOrders() {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT * FROM [ORDERS] WHERE ORD_COMPLETED_TIME IS NOT NULL;";
            PreparedStatement ps = conn.prepareStatement(sql);

            List<Order> orders = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("ORD_ID"));
                getDataFromID(order);
                orders.add(order);
            }

            JDBCUtil.closeConnection(conn);
            return orders;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getDeletedOrders(boolean isdeleted) {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT * FROM [ORDERS] WHERE ORD_IS_DELETED = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, isdeleted);

            List<Order> orders = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("ORD_ID"));
                System.out.println(rs.getInt("ORD_ID") + "a");
                getDataFromID(order);
                orders.add(order);
            }

            JDBCUtil.closeConnection(conn);
            return orders;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getList() {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT TOP (1000) *  FROM [newDatabaseOOP].[dbo].[ORDERS];";

            List<Order> orders = new ArrayList<>();

            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("ORD_ID"));
                getDataFromID(order);
//              order.setServiceId(rs.getInt("SV_ID")); // ID dịch vụ
                orders.add(order);
            }

            JDBCUtil.closeConnection(conn);
            return orders;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getOrderListForShipper(Shipper shipper, int month, String type) {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "";

            if (type == "Pending") {
                sql += "SELECT * FROM [ORDERS] \n" + "WHERE SHIPPER_ID = ? \n" + "AND ORD_IS_DELETED = 0 AND MONTH(ORD_ORDER_DATE) = ? AND ORD_TIME IS NULL AND ORD_COMPLETED_TIME IS NULL ;";
            } else if (type == "Processing") {
                sql += "SELECT * FROM [ORDERS] \n" + "WHERE SHIPPER_ID = ? \n" + "AND ORD_IS_DELETED = 0 AND MONTH(ORD_ORDER_DATE) = ? AND ORD_TIME IS NOT NULL AND ORD_COMPLETED_TIME IS NULL;";

            } else if (type == "Deleted") {
                sql += "SELECT * FROM [ORDERS] \n" + "WHERE SHIPPER_ID = ? \n" + "AND MONTH(ORD_ORDER_DATE) = ? AND ORD_IS_DELETED = 1";

            } else if (type == "Completed") {
                sql += "SELECT * FROM [ORDERS] \n" + "WHERE SHIPPER_ID = ? \n" + "AND ORD_IS_DELETED = 0 AND MONTH(ORD_ORDER_DATE) = ? AND ORD_TIME IS NOT NULL AND ORD_COMPLETED_TIME IS NOT NULL";

            }
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, shipper.getId());
            ps.setInt(2, month);

            List<Order> orders = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("ORD_ID"));
                getDataFromID(order);
//				order.setRespond(rs.getBoolean("CUS_RESPOND")); // Phản hồi từ khách hàng
                orders.add(order);
            }

            JDBCUtil.closeConnection(conn);
            return orders;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int Update(Order order) {
        int result = 0;
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "UPDATE [ORDERS]\n"
                    + "SET \n"
                    + "    ORD_NAME = ?,\n"
                    + "    ORD_WEIGHT = ?,\n"
                    + "    ORD_SHIP_FEE = ?,\n"
                    + "    ORD_PRICE = ?,\n"
                    + "    ORD_WARD = ?,\n"
                    + "    ORD_PROVINCE = ?,\n"
                    + "    ORD_DISTINCT = ?,\n"
                    + "    ORD_DISTANCE = ?,\n"
                    + "    ORD_DESCRIPTION = ?,\n"
                    + "    ORD_ORDER_DATE = ?,\n"
                    + "    ORD_EXPECTED_DELIVERY_DATE = ?,\n"
                    + "    SHIPPER_ID = ?,\n"
                    + "    CUS_ID = ?,\n"
                    + "    CUS_RESPOND = ?,\n"
                    + "    ORD_IS_DELETED = ?,\n"
                    + "    ORD_TIME = ?,\n"
                    + "    ORD_SHIP_COUNT = ?,\n"
                    + "    ORD_COMPLETED_TIME = ?,\n"
                    + "    ORD_CONFIRM = ?,\n"
                    + "    SV_ID = ?\n"
                    + "WHERE \n"
                    + "    ORD_ID = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, order.getName());
            ps.setDouble(2, order.getWeight());
            ps.setDouble(3, order.getShipFee());
            ps.setDouble(4, order.getPrice());
            ps.setString(5, order.getWard());
            ps.setString(6, order.getProvince());
            ps.setString(7, order.getDistinct());
            ps.setDouble(8, order.getDistance());
            ps.setString(9, order.getDescription());
            ps.setTimestamp(10, java.sql.Timestamp.valueOf(order.getOrderDate()));

            // Chuyển đổi LocalDate sang java.sql.Date
            if (order.getExpectedDeliveryDate() == null) {
                ps.setDate(11, null);
            } else {
                ps.setDate(11, java.sql.Date.valueOf(order.getExpectedDeliveryDate()));
            }

            ps.setInt(12, order.getShipperId());
            ps.setInt(13, order.getCusId());
            ps.setBoolean(14, order.isRespond());
            ps.setBoolean(15, order.isDeleted());

            if (order.getShipCount() > 3 && order.getCompletedTime() == null) {
                ps.setTimestamp(16, null);
            } else {
                ps.setTimestamp(16, java.sql.Timestamp.valueOf(order.getShipTime()));
            }

            ps.setInt(17, order.getShipCount());

            if (order.getCompletedTime() == null) {
                ps.setTimestamp(18, null);
            } else {
                ps.setTimestamp(18, java.sql.Timestamp.valueOf(order.getCompletedTime()));
            }

            ps.setBoolean(19, order.isConfirm());
            ps.setInt(20, order.getServiceId());
            ps.setInt(21, order.getId());

            result = ps.executeUpdate();

            JDBCUtil.closeConnection(conn);

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public int Delete(Order order) {
        int result = 0;
        try {
            Connection cons = JDBCUtil.getConnection();

            String sql = "UPDATE [ORDERS]\n" + "SET \n" + "ORD_IS_DELETED = 1\n" + "WHERE \n" + "ORD_ID = ?;";
            PreparedStatement ps = cons.prepareStatement(sql);
            //ps.setBoolean(1, order.isDeleted());
            ps.setInt(1, order.getId());
            result = ps.executeUpdate();
            JDBCUtil.closeConnection(cons);

            return result;
        } catch (Exception ex) {
            // System.out.println("loi");
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public int Insert(Order order) {
        int result = 0;
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "INSERT INTO [ORDERS] (ORD_NAME, ORD_WEIGHT, ORD_SHIP_FEE, ORD_PRICE, ORD_WARD, ORD_PROVINCE, ORD_DISTINCT, ORD_DISTANCE, ORD_DESCRIPTION, ORD_ORDER_DATE, ORD_EXPECTED_DELIVERY_DATE, SHIPPER_ID, CUS_ID, CUS_RESPOND, ORD_IS_DELETED, ORD_TIME, ORD_SHIP_COUNT, ORD_COMPLETED_TIME, ORD_CONFIRM, SV_ID)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, order.getName());
            ps.setDouble(2, order.getWeight());
            ps.setDouble(3, order.getShipFee());
            ps.setDouble(4, order.getPrice());
            ps.setString(5, order.getWard());
            ps.setString(6, order.getProvince());
            ps.setString(7, order.getDistinct());
            ps.setDouble(8, order.getDistance());
            ps.setString(9, order.getDescription());
            ps.setTimestamp(10, java.sql.Timestamp.valueOf(order.getOrderDate()));
            // Chuyển đổi LocalDate sang java.sql.Date
            ps.setDate(11, null);

            ps.setInt(12, order.getShipperId());
            ps.setInt(13, order.getCusId());
            ps.setBoolean(14, order.isRespond());
            ps.setBoolean(15, order.isDeleted());
            ps.setDate(16, null); // shiptime
            ps.setInt(17, order.getShipCount());
            ps.setDate(18, null); // completetime
            ps.setBoolean(19, order.isConfirm());
            ps.setInt(20, 1); // service

            result = ps.executeUpdate();

            JDBCUtil.closeConnection(conn);

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Integer> getListID() {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT CUS_ID FROM CUSTOMER;";

            List<Integer> customers = new ArrayList<>();

            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("CUS_ID");
                customers.add(customerId);
            }

            st.close();
            rs.close();
            JDBCUtil.closeConnection(conn);
            return customers;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int checkPhoneNumberExists(String phoneNumber, Customer customer) {
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT CUS_ID, [CUS_WARD]\r\n"
                    + "      ,[CUS_PROVINCE]\r\n"
                    + "      ,[CUS_DISTRICT] FROM CUSTOMER WHERE CUS_PHONENUMBER = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer.setId(rs.getInt("CUS_ID"));
                customer.setWard(rs.getString("CUS_WARD"));
                customer.setProvince(rs.getString("CUS_PROVINCE"));
                customer.setDistinct(rs.getString("CUS_DISTRICT"));
                System.out.println(rs.getString("CUS_PROVINCE") + "_" + rs.getString("CUS_DISTRICT") + "_" + rs.getString("CUS_WARD"));
                return customer.getId();
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public void getDataFromID(Order order) {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT [ORD_NAME], [ORD_WEIGHT], [ORD_SHIP_FEE], [ORD_PRICE], [ORD_WARD], [ORD_PROVINCE], [ORD_DISTINCT], [ORD_DISTANCE], [ORD_DESCRIPTION], [ORD_ORDER_DATE], [ORD_EXPECTED_DELIVERY_DATE], [ORD_IS_DELETED], [ORD_TIME], [ORD_SHIP_COUNT], [ORD_COMPLETED_TIME], [ORD_CONFIRM], [SV_ID], [SHIPPER_ID], [CUS_ID], [CUS_RESPOND] FROM [newDatabaseOOP].[dbo].[ORDERS] WHERE [ORD_ID] = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, order.getId());

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                order.setName(rs.getString("ORD_NAME"));
                order.setWeight(rs.getDouble("ORD_WEIGHT"));
                order.setShipFee(rs.getDouble("ORD_SHIP_FEE"));
                order.setPrice(rs.getDouble("ORD_PRICE"));
                order.setWard(rs.getString("ORD_WARD"));
                order.setProvince(rs.getString("ORD_PROVINCE"));
                order.setDistinct(rs.getString("ORD_DISTINCT"));
                order.setDistance(rs.getDouble("ORD_DISTANCE"));
                order.setDescription(rs.getString("ORD_DESCRIPTION"));

                order.setOrderDate(rs.getTimestamp("ORD_ORDER_DATE").toLocalDateTime());

                // Properly handle the expected delivery date
                Date expectedDeliveryDate = rs.getDate("ORD_EXPECTED_DELIVERY_DATE");
                if (expectedDeliveryDate != null) {
                    order.setExpectedDeliveryDate(expectedDeliveryDate.toLocalDate());
                } else {
                    order.setExpectedDeliveryDate(null);
                }

                order.setDeleted(rs.getBoolean("ORD_IS_DELETED"));

                Timestamp completedTimeTimestamp = rs.getTimestamp("ORD_COMPLETED_TIME");
                if (completedTimeTimestamp != null) {
                    order.setCompletedTime(completedTimeTimestamp.toLocalDateTime());
                } else {
                    order.setCompletedTime(null);
                }

                order.setShipCount(rs.getInt("ORD_SHIP_COUNT"));
                order.setConfirm(rs.getBoolean("ORD_CONFIRM"));
                order.setServiceId(rs.getInt("SV_ID"));
                order.setShipperId(rs.getInt("SHIPPER_ID"));
                order.setCusId(rs.getInt("CUS_ID"));
                order.setRespond(rs.getBoolean("CUS_RESPOND"));
            }

            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            System.out.println("loi tai orderimpl_setdata");
            ex.printStackTrace();
        }
    }

    @Override
    public int UpdateOrderTime(Order order) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "UPDATE [ORDERS] SET ORD_TIME = ? WHERE ORD_ID = ?;";

            ps = conn.prepareStatement(sql);

            // Lấy ngày giờ hiện tại
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            // Thiết lập các giá trị cho truy vấn
            ps.setTimestamp(1, currentTime);
            ps.setInt(2, order.getId());

            result = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Đảm bảo tài nguyên JDBC được đóng
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    JDBCUtil.closeConnection(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int UpdateShipCount(Order order) {
        int result = 0;
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "UPDATE [ORDERS]\n"
                    + "SET \n"
                    + "    ORD_SHIP_COUNT = ?\n"
                    + "WHERE \n"
                    + "    ORD_ID = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            int count = order.getShipCount()+1;
            ps.setInt(1, count);
            ps.setInt(2, order.getId());

            result = ps.executeUpdate();

            JDBCUtil.closeConnection(conn);

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public int UpdateCompleteTime(Order order) {
        int result = 0;
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "UPDATE [ORDERS]\n"
                    + "SET \n"
                    + "    ORD_COMPLETED_TIME = ?\n"
                    + "WHERE \n"
                    + "    ORD_ID = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            // Lấy ngày giờ hiện tại
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            // Thiết lập các giá trị cho truy vấn
            ps.setTimestamp(1, currentTime);
            ps.setInt(2, order.getId());

            result = ps.executeUpdate();

            JDBCUtil.closeConnection(conn);

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}
//order.setName(rs.getString("ORD_NAME"));
//order.setWeight(rs.getDouble("ORD_WEIGHT"));
//order.setShipFee(rs.getDouble("ORD_SHIP_FEE")); // Phí vận chuyển
//order.setPrice(rs.getDouble("ORD_PRICE")); // Giá
//order.setWard(rs.getString("ORD_WARD")); // Phường/Xã
//order.setProvince(rs.getString("ORD_PROVINCE")); // Tỉnh/Thành phố
//order.setDistinct(rs.getString("ORD_DISTINCT")); // Quận/Huyện
//order.setDistance(rs.getDouble("ORD_DISTANCE")); // Khoảng cách
//order.setDescription(rs.getString("ORD_DESCRIPTION")); // Mô tả
//order.setOrderDate(rs.getDate("ORD_ORDER_DATE")); // Ngày đặt hàng
//order.setExpectedDeliveryDate(rs.getDate("ORD_EXPECTED_DELIVERY_DATE")); // Ngày dự kiến giao hàng
//order.setShipperId(rs.getInt("SHIPPER_ID")); // ID người vận chuyển
//order.setCusId(rs.getInt("CUS_ID")); // ID khách hàng
//order.setRespond(rs.getBoolean("CUS_RESPOND")); // Phản hồi từ khách hàng
//order.setDeleted(rs.getBoolean("ORD_IS_DELETED")); // Đã xóa
//order.setShipTime(rs.getTimestamp("ORD_TIME")); // Thời gian
//order.setShipCount(rs.getInt("ORD_SHIP_COUNT")); // Số lần vận chuyển
//order.setCompletedTime(rs.getTimestamp("ORD_COMPLETED_TIME")); // Thời gian hoàn thành
//order.setConfirm(rs.getBoolean("ORD_CONFIRM")); // Xác nhận
//order.setServiceId(rs.getInt("SV_ID")); // ID dịch vụ
