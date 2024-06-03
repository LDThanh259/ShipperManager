package Dao;

import database.JDBCUtil;
import java.util.List;
import model.Shipper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShipperDaoImpl implements ShipperDao {

    @Override
    public List<Shipper> getList(boolean is_delete) {
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT TOP (1000) [SHIPPER_ID]\n"
                    + "      ,[SHIPPER_NAME]\n"
                    + "      ,[SHIPPER_GENDER]\n"
                    + "      ,[SHIPPER_PHONENUMBER]\n"
                    + "      ,[SHIPPER_PROVINCE]\n"
                    + "      ,[SHIPPER_DISTRICT]\n"
                    + "      ,[SHIPPER_WARD]\n"
                    + "      ,[SHIPPER_LICENSEPLATE]\n"
                    + "      ,[SHIPPER_STATUS]\n"
                    + "      ,[SHIPPER_RANK]\n"
                    + "      ,[SHIPPER_ISDELETED]\n"
                    + "  FROM [Shipper1].[dbo].[SHIPPER]\n"
                    + " WHERE [SHIPPER_ISDELETED] = ?;";

            List<Shipper> shippers = new ArrayList<>();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setBoolean(1, is_delete);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Shipper shipper = new Shipper();
                shipper.setId(rs.getInt("SHIPPER_ID"));
                shipper.setName(rs.getString("SHIPPER_NAME"));
                shipper.setGender(rs.getString("SHIPPER_GENDER"));
                shipper.setPhoneNumber(rs.getString("SHIPPER_PHONENUMBER"));
                shipper.setDistinct(rs.getString("SHIPPER_DISTRICT"));
                shipper.setProvince(rs.getString("SHIPPER_PROVINCE"));
                shipper.setWard(rs.getString("SHIPPER_WARD"));
                shipper.setLicensePlate(rs.getString("SHIPPER_LICENSEPLATE"));
                shipper.setStatus(rs.getString("SHIPPER_STATUS"));
                shipper.setRating(rs.getFloat("SHIPPER_RANK"));
                shipper.setIsDeleted(rs.getBoolean("SHIPPER_ISDELETED"));

                shippers.add(shipper);
            }

            rs.close();
            st.close();
            JDBCUtil.closeConnection(conn);

            return shippers;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int update(Shipper shipper) {
        int result = 0;
        try {
            Connection cons = JDBCUtil.getConnection();

            String sql = "UPDATE SHIPPER\n"
                    + "SET \n"
                    + "    SHIPPER_NAME = ?, \n"
                    + "    SHIPPER_GENDER = ?, \n"
                    + "    SHIPPER_PHONENUMBER = ?, \n"
                    + "    SHIPPER_DISTRICT = ?, \n"
                    + "    SHIPPER_PROVINCE = ?, \n"
                    + "    SHIPPER_WARD = ?, \n"
                    + "    SHIPPER_LICENSEPLATE = ?, \n"
                    + "    SHIPPER_STATUS = ?, \n"
                    + "    SHIPPER_RANK = ?, \n"
                    + "    SHIPPER_ISDELETED = ? \n"
                    + "WHERE \n"
                    + "    SHIPPER_ID = ?;";
            PreparedStatement ps = cons.prepareStatement(sql);

            ps.setString(1, shipper.getName());
            ps.setString(2, shipper.isGender());
            ps.setString(3, shipper.getPhoneNumber());
            ps.setNString(4, shipper.getDistinct());
            ps.setNString(5, shipper.getProvince());
            ps.setNString(6, shipper.getWard());
            ps.setString(7, shipper.getLicensePlate());
            ps.setString(8, shipper.getStatus());
            ps.setFloat(9, (float) shipper.getRating());
            ps.setBoolean(10, shipper.isIsDeleted());;
            ps.setInt(11, shipper.getId());

            result = ps.executeUpdate();

            ps.close();
            JDBCUtil.closeConnection(cons);
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Shipper shipper) {
        int result = 0;
        try {
            Connection cons = JDBCUtil.getConnection();

            String sql = "UPDATE SHIPPER\n"
                    + "SET \n"
                    + "    SHIPPER_ISDELETED = 1 \n"
                    + "WHERE \n"
                    + "    SHIPPER_ID = ?;";
            PreparedStatement ps = cons.prepareStatement(sql);

            ps.setInt(1, shipper.getId());

            result = ps.executeUpdate();
            ps.close();
            JDBCUtil.closeConnection(cons);

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public int insert(Shipper shipper) {
        shipper.setIsDeleted(false);
        int result = 0;
        try {
            Connection cons = JDBCUtil.getConnection();

            String sql = "INSERT INTO SHIPPER (SHIPPER_NAME,SHIPPER_GENDER, SHIPPER_PHONENUMBER, SHIPPER_DISTRICT, SHIPPER_PROVINCE, SHIPPER_WARD, SHIPPER_LICENSEPLATE, SHIPPER_STATUS, SHIPPER_RANK, SHIPPER_ISDELETED)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, shipper.getName());
            ps.setString(2, shipper.isGender());
            ps.setString(3, shipper.getPhoneNumber());
            ps.setNString(4, shipper.getDistinct());
            ps.setNString(5, shipper.getProvince());
            ps.setNString(6, shipper.getWard());
            ps.setString(7, shipper.getLicensePlate());
            ps.setString(8, shipper.getStatus());
            ps.setFloat(9, (float) shipper.getRating());
            ps.setBoolean(10, shipper.isIsDeleted());

            result = ps.executeUpdate();
            ps.close();
            JDBCUtil.closeConnection(cons);

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Integer> getListId() {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT SHIPPER_ID FROM SHIPPER WHERE [SHIPPER_ISDELETED] = 0;";

            List<Integer> shippers = new ArrayList<>();

            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int shipperId = rs.getInt("SHIPPER_ID");
                shippers.add(shipperId);
            }

            st.close();
            rs.close();
            JDBCUtil.closeConnection(conn);
            return shippers;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
