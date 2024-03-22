package Dao;

import database.JDBCUtil;
import java.util.List;
import model.Shipper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShipperDaoImpl implements ShipperDao {

    @Override
    public List<Shipper> getList() {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT * FROM Shipper;";

            List<Shipper> shippers = new ArrayList<>();

            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Shipper shipper = new Shipper();
                shipper.setShipper_Id(rs.getInt("SHIPPER_ID"));
                shipper.setName(rs.getString("SHIPPER_NAME"));
                shipper.setBirthDay(rs.getString("SHIPPER_BIRTHDAY"));
                shipper.setGender(rs.getString("SHIPPER_GENDER"));
                shipper.setStartWork(rs.getString("SHIPPER_STARTWORK"));
                shipper.setPhone(rs.getString("SHIPPER_PHONE"));
                shipper.setEmail(rs.getString("SHIPPER_EMAIL"));
                shipper.setAddress(rs.getString("SHIPPER_ADDRESS"));
                shipper.setDescription(rs.getString("SHIPPER_DESCRIPTION"));

                shippers.add(shipper);

            }

            JDBCUtil.closeConnection(conn);
            return shippers;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int Update(Shipper shipper) {
        int result = 0;
        try {

            Connection cons = JDBCUtil.getConnection();

            String sql = "UPDATE Shipper\n"
                    + "SET \n"
                    + "    SHIPPER_NAME = ?, \n"
                    + "    SHIPPER_BIRTHDAY = ?, \n"
                    + "    SHIPPER_GENDER = ?, \n"
                    + "    SHIPPER_STARTWORK = ?, \n"
                    + "    SHIPPER_PHONE = ?, \n"
                    + "    SHIPPER_EMAIL = ?, \n"
                    + "    SHIPPER_ADDRESS = ?, \n"
                    + "    SHIPPER_DESCRIPTION = ?\n"
                    + "WHERE \n"
                    + "    SHIPPER_ID = ?;";
            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, shipper.getName());
            ps.setString(2, shipper.getBirthDay());
            ps.setString(3, shipper.getGender());
            ps.setString(4, shipper.getStartWork());
            ps.setString(5, shipper.getPhone());
            ps.setString(6, shipper.getEmail());
            ps.setString(7, shipper.getAddress());
            ps.setString(8, shipper.getDescription());
            ps.setInt(9, shipper.getShipper_Id());

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

    @Override
    public int Delete(Shipper shipper) {
        int result = 0;
        try {
            Connection cons = JDBCUtil.getConnection();

            String sql = "DELETE FROM SHIPPER\n"
                    + "WHERE SHIPPER_ID = ?;";
            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, "" + shipper.getShipper_Id());

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
    public int Insert(Shipper shipper) {
        int result = 0;
        try {
            Connection cons = JDBCUtil.getConnection();

            String sql = "INSERT INTO SHIPPER (SHIPPER_NAME, SHIPPER_BIRTHDAY, "
                    + "SHIPPER_GENDER, SHIPPER_STARTWORK, SHIPPER_PHONE,"
                    + " SHIPPER_EMAIL, SHIPPER_ADDRESS, SHIPPER_DESCRIPTION)\n"
                    + "VALUES (? ,? ,?, ? ,? ,? ,? ,? );";
            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, shipper.getName());
            ps.setString(2, shipper.getBirthDay());
            ps.setString(3, shipper.getGender());
            ps.setString(4, shipper.getStartWork());
            ps.setString(5, shipper.getPhone());
            ps.setString(6, shipper.getEmail());
            ps.setString(7, shipper.getAddress());
            ps.setString(8, shipper.getDescription());

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
    public List<Integer> getListId() {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT SHIPPER_ID FROM Shipper;";

            List<Integer> shippers = new ArrayList<>();

            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                int shipperId = rs.getInt("SHIPPER_ID");
                shippers.add(shipperId);

            }

            JDBCUtil.closeConnection(conn);
            return shippers;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
