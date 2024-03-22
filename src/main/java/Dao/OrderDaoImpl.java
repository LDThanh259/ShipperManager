/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import database.JDBCUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.Shipper;

/**
 *
 * @author ADMIN
 */
public class OrderDaoImpl implements OrderDao {

    @Override
    public List<Order> getList() {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT * FROM [Order];";

            List<Order> orders = new ArrayList<>();

            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Order order = new Order();
                order.setId(rs.getInt("ORDERS_ID"));
                order.setName(rs.getString("ORDERS_NAME"));
                order.setDelivery_Location(rs.getString("ORDERS_DELIVERY_LOCATION"));
                order.setDelivery_Time(rs.getDate("ORDERS_DELIVERY_TIME"));
                order.setReceive_Location(rs.getString("ORDERS_RECEIVE_LOCATION"));
                order.setReceive_Time(rs.getDate("ORDERS_RECEIVE_TIME"));
                order.setStatus(rs.getString("ORDERS_STATUS"));
                order.setFeedback(rs.getString("ORDERS_FEEDBACK"));
                order.setDistance((double) rs.getFloat("ORDERS_DISTANCE"));
                order.setPrice((double) rs.getFloat("ORDERS_PRICE"));
                order.setShipper_ID(rs.getInt("SHIPPER_ID"));

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
    public List<Order> getOrderListForShipper(Shipper shipper) {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT * FROM [Order] WHERE SHIPPER_ID = ?;";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, shipper.getShipper_Id());

            List<Order> orders = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Order order = new Order();
                order.setId(rs.getInt("ORDERS_ID"));
                order.setName(rs.getString("ORDERS_NAME"));
                order.setDelivery_Location(rs.getString("ORDERS_DELIVERY_LOCATION"));
                order.setDelivery_Time(rs.getDate("ORDERS_DELIVERY_TIME"));
                order.setReceive_Location(rs.getString("ORDERS_RECEIVE_LOCATION"));
                order.setReceive_Time(rs.getDate("ORDERS_RECEIVE_TIME"));
                order.setStatus(rs.getString("ORDERS_STATUS"));
                order.setFeedback(rs.getString("ORDERS_FEEDBACK"));
                order.setDistance((double) rs.getFloat("ORDERS_DISTANCE"));
                order.setPrice((double) rs.getFloat("ORDERS_PRICE"));
                order.setShipper_ID(rs.getInt("SHIPPER_ID"));

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
            Connection cons = JDBCUtil.getConnection();

            String sql = "UPDATE [Order]\n"
                    + "SET \n"
                    + "    ORDERS_NAME = ?,\n"
                    + "    ORDERS_DELIVERY_LOCATION = ?,\n"
                    + "    ORDERS_DELIVERY_TIME = ?,\n"
                    + "    ORDERS_RECEIVE_LOCATION = ?,\n"
                    + "    ORDERS_RECEIVE_TIME = ?,\n"
                    + "    ORDERS_STATUS = ?,\n"
                    + "    ORDERS_FEEDBACK = ?,\n"
                    + "    ORDERS_DISTANCE = ?,\n"
                    + "    ORDERS_PRICE = ?\n,"
                    + "    SHIPPER_ID = ?\n"
                    + "WHERE \n"
                    + "    ORDERS_ID = ?;";
            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, order.getName());
            ps.setString(2, order.getDelivery_Location());
            ps.setDate(3, (Date) order.getDelivery_Time());
            ps.setString(4, order.getReceive_Location());
            ps.setDate(5, (Date) order.getReceive_Time());
            ps.setString(6, order.getStatus());
            ps.setString(7, order.getFeedback());
            ps.setDouble(8, order.getDistance());
            ps.setDouble(9, order.getPrice());
            ps.setInt(10, order.getShipper_ID());
            ps.setInt(11, order.getId());
            result = ps.executeUpdate();

            JDBCUtil.closeConnection(cons);
            
            return result;
        } catch (Exception ex) {
            //System.out.println("loi");
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public int Delete(Order order) {
        int result = 0;
        try {
            Connection cons = JDBCUtil.getConnection();

            String sql = "DELETE FROM [Order]\n"
                    + "WHERE ORDERS_ID = ?;";
            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, order.getId());
            result = ps.executeUpdate();

            JDBCUtil.closeConnection(cons);
            
            return result;
        } catch (Exception ex) {
            //System.out.println("loi");
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public int Insert(Order order) {
        int result = 0;
        try {
            Connection cons = JDBCUtil.getConnection();

            String sql = "INSERT INTO [Order] (ORDERS_NAME, ORDERS_DELIVERY_LOCATION, ORDERS_DELIVERY_TIME, ORDERS_RECEIVE_LOCATION, ORDERS_RECEIVE_TIME, ORDERS_STATUS, ORDERS_FEEDBACK, ORDERS_DISTANCE, ORDERS_PRICE, SHIPPER_ID)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, order.getName());
            ps.setString(2, order.getDelivery_Location());
            ps.setDate(3, (Date) order.getDelivery_Time());
            ps.setString(4, order.getReceive_Location());
            ps.setDate(5, (Date) order.getReceive_Time());
            ps.setString(6, order.getStatus());
            ps.setString(7, order.getFeedback());
            ps.setDouble(8, order.getDistance());
            ps.setDouble(9, order.getPrice());
            ps.setInt(10, order.getShipper_ID());

            result = ps.executeUpdate();

            JDBCUtil.closeConnection(cons);
            //return generatedKey;
            return result;
        } catch (Exception ex) {
            //System.out.println("loi");
            ex.printStackTrace();
        }
        return 0;
    }

}
