package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.JDBCUtil;
import model.Customer;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public List<Customer> getList(boolean is_delete) {
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT TOP (1000) [CUS_ID], [CUS_NAME], [CUS_PHONENUMBER], [CUS_EMAIL], [CUS_GENDER], [CUS_WARD], [CUS_PROVINCE], [CUS_DISTRICT] FROM [Shipper1].[dbo].[CUSTOMER]"
                    +"WHERE [isDeleted] = ?";

            List<Customer> customers = new ArrayList<>();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setBoolean(1, is_delete);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("CUS_ID"));
                customer.setName(rs.getString("CUS_NAME"));
                customer.setPhoneNumber(rs.getString("CUS_PHONENUMBER"));
                customer.setEmail(rs.getString("CUS_EMAIL"));
                customer.setGender(rs.getBoolean("CUS_GENDER"));
                customer.setWard(rs.getString("CUS_WARD"));
                customer.setProvince(rs.getString("CUS_PROVINCE"));
                customer.setDistinct(rs.getString("CUS_DISTRICT"));
                customers.add(customer);
            }

            rs.close();
            st.close();
            JDBCUtil.closeConnection(conn);

            return customers;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int Update(Customer customer) {
        int result = 0;
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "UPDATE [Shipper1].[dbo].[CUSTOMER]\n"
                    + "SET \n"
                    + "    [CUS_NAME] = ?,\n"
                    + "    [CUS_PHONENUMBER] = ?,\n"
                    + "    [CUS_EMAIL] = ?,\n"
                    + "    [CUS_GENDER] = ?,\n"
                    + "    [CUS_WARD] = ?,\n"
                    + "    [CUS_PROVINCE] = ?,\n"
                    + "    [CUS_DISTRICT] = ?\n"
                    + "WHERE \n"
                    + "    [CUS_ID] = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhoneNumber());
            ps.setString(3, customer.getEmail());
            ps.setBoolean(4, customer.getGender());
            ps.setString(5, customer.getWard());
            ps.setString(6, customer.getProvince());
            ps.setString(7, customer.getDistinct());
            ps.setInt(8, customer.getId());

            result = ps.executeUpdate();

            JDBCUtil.closeConnection(conn);

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public int Delete(Customer customer) {
        int result = 0;
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "UPDATE [Shipper1].[dbo].[CUSTOMER]\n"
                    + "SET \n"
                    + " [isDeleted] = 1 "
                    + "WHERE \n"
                    + "    [CUS_ID] = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, customer.getId());
            result = ps.executeUpdate();

            JDBCUtil.closeConnection(conn);

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public int Insert(Customer customer) {
        int result = 0;
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "INSERT INTO [Shipper1].[dbo].[CUSTOMER] (CUS_NAME, CUS_PHONENUMBER, CUS_EMAIL, CUS_GENDER, CUS_WARD, CUS_PROVINCE, CUS_DISTRICT)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhoneNumber());
            ps.setString(3, customer.getEmail());
            ps.setBoolean(4, customer.getGender());
            ps.setString(5, customer.getWard());
            ps.setString(6, customer.getProvince());
            ps.setString(7, customer.getDistinct());

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

            String sql = "SELECT CUS_ID FROM CUSTOMER WHERE [isDeleted] = 0 ;";

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
    public List<String> getListPhone() {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT CUS_PHONENUMBER FROM CUSTOMER WHERE [isDeleted] = 0 ;";

            List<String> customers = new ArrayList<>();

            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String customerPhone = rs.getString("CUS_PHONENUMBER");
                customers.add(customerPhone);
                System.out.println(customerPhone);
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

    public void getDataFromID(Customer customer) {
        // TODO Auto-generated method stub
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT  [CUS_ID]\r\n"
                    + "      ,[CUS_NAME]\r\n"
                    + "      ,[CUS_PHONENUMBER]\r\n"
                    + "      ,[CUS_EMAIL]\r\n"
                    + "      ,[CUS_GENDER]\r\n"
                    + "      ,[CUS_WARD]\r\n"
                    + "      ,[CUS_PROVINCE]\r\n"
                    + "      ,[CUS_DISTRICT] FROM [Shipper1].[dbo].[CUSTOMER] WHERE [CUS_ID] = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, customer.getId());

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                customer.setId(rs.getInt("CUS_ID"));
                customer.setName(rs.getString("CUS_NAME"));
                customer.setPhoneNumber(rs.getString("CUS_PHONENUMBER"));
                customer.setEmail(rs.getString("CUS_EMAIL"));
                customer.setGender(rs.getBoolean("CUS_GENDER"));
                customer.setWard(rs.getString("CUS_WARD"));
                customer.setProvince(rs.getString("CUS_PROVINCE"));
                customer.setDistinct(rs.getString("CUS_DISTRICT"));
            }

            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            System.out.println("loi tai cusimpl_setdata");
            ex.printStackTrace();
        }
        System.out.println();
    }

    public int getDataFromCusPhone(String phoneNumber, Customer customer) {
        int cus_id = 0;
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT  [CUS_ID]\r\n"
                    + "      ,[CUS_NAME]\r\n"
                    + "      ,[CUS_PHONENUMBER]\r\n"
                    + "      ,[CUS_EMAIL]\r\n"
                    + "      ,[CUS_GENDER]\r\n"
                    + "      ,[CUS_WARD]\r\n"
                    + "      ,[CUS_PROVINCE]\r\n"
                    + "      ,[CUS_DISTRICT] FROM [Shipper1].[dbo].[CUSTOMER] WHERE [CUS_PHONENUMBER] = ? AND [isDeleted] = 0 ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cus_id = rs.getInt("CUS_ID");
                customer.setId(rs.getInt("CUS_ID"));
                customer.setName(rs.getString("CUS_NAME"));
                customer.setPhoneNumber(rs.getString("CUS_PHONENUMBER"));
                customer.setEmail(rs.getString("CUS_EMAIL"));
                customer.setGender(rs.getBoolean("CUS_GENDER"));
                customer.setWard(rs.getString("CUS_WARD"));
                customer.setProvince(rs.getString("CUS_PROVINCE"));
                customer.setDistinct(rs.getString("CUS_DISTRICT"));
                System.out.println(rs.getString("CUS_NAME") + "dsad");
                return cus_id;
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("loi");
        return -1;
    }
}
