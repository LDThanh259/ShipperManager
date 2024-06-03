package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import database.JDBCUtil;
import model.Service;

/**
*
* @author ADMIN
*/



public class ServiceDaoImpl implements ServiceDao {

    
	@Override
	public List<Service> getList()
	{
	    try {
	        Connection conn = JDBCUtil.getConnection();

	        String sql = "SELECT TOP(1000) [SV_ID],[SV_NAME], [SV_MAXDISTANCE], [SV_MAXWEIGHT], [SV_PRICE] FROM [Shipper1].[dbo].[SERVICE];";

	        List<Service> services = new ArrayList<>();

	        PreparedStatement st = conn.prepareStatement(sql);

	        ResultSet rs = st.executeQuery();

	        while (rs.next()) {
	            Service service = new Service();
	            service.setId(rs.getInt("SV_ID"));
                    service.setName(rs.getString("SV_NAME"));
	            service.setMaxDistance(rs.getInt("SV_MAXDISTANCE"));
	            service.setMaxWeight(rs.getInt("SV_MAXWEIGHT"));
	            service.setPrice(rs.getInt("SV_PRICE"));
	      
	            services.add(service);
	        }
                rs.close();
                st.close();
	        JDBCUtil.closeConnection(conn);
	        return services;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}

	@Override
	public int Update(Service service) {
	    int result = 0;
	    try {
	        Connection conn = JDBCUtil.getConnection();

	        String sql = "UPDATE SERVICE\n"
	                + "SET \n"
	                + "    SV_NAME = ?,\n"
	                + "    SV_MAXDISTANCE = ?,\n"
	                + "    SV_MAXWEIGHT = ?,\n"
	                + "    SV_PRICE = ?\n"
	                + "WHERE \n"
	                + "    SV_ID = ?;";
	        PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, service.getName());
	        ps.setInt(2, service.getMaxDistance());
	        ps.setInt(3, service.getMaxWeight());
	        ps.setInt(4, service.getPrice());
	      
	        ps.setInt(5, service.getId());

	        result = ps.executeUpdate();
                ps.close();
	        JDBCUtil.closeConnection(conn);

	        return result;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return result;
	}

	@Override
	public int Delete(Service service) {
	    int result = 0;
	    try {
	        Connection conn = JDBCUtil.getConnection();

	        String sql = "DELETE FROM SERVICE\n"
	                + "WHERE SV_ID = ?;";
	        PreparedStatement ps = conn.prepareStatement(sql);

	        ps.setInt(1, service.getId());

	        result = ps.executeUpdate();
                ps.close();
	        JDBCUtil.closeConnection(conn);

	        return result;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return result;
	}

	@Override
	public int Insert(Service service) {
	    int result = 0;
	    try {
	        Connection conn = JDBCUtil.getConnection();

	        String sql = "INSERT INTO SERVICE (SV_NAME, SV_MAXDISTANCE, SV_MAXWEIGHT, SV_PRICE)\n"
	                + "VALUES (?, ?, ?, ?);";
	        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

	        ps.setInt(2, service.getMaxDistance());
	        ps.setInt(3, service.getMaxWeight());
	        ps.setInt(4, service.getPrice());
	        ps.setString(1, service.getName());

	        result = ps.executeUpdate();
                ps.close();
	        JDBCUtil.closeConnection(conn);
                
	        return result;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return result;
	}

	@Override
 	public List<Integer> getListID() {
		try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT SV_ID FROM SERVICE;";

            List<Integer> services = new ArrayList<>();

            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int serviceId = rs.getInt("SV_ID");
                services.add(serviceId);
            }

            st.close();
            rs.close();
            JDBCUtil.closeConnection(conn);
            return services;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    	return null;
	}
	
	@Override
    public String getDataFromID(int serviceid)
    {
		try {
			Service service = new Service();
	        Connection conn = JDBCUtil.getConnection();

	        String sql = "SELECT [SV_ID],[SV_NAME], [SV_MAXDISTANCE], [SV_MAXWEIGHT], [SV_PRICE] FROM [Shipper1].[dbo].[SERVICE] WHERE [SV_ID] = ?";

	        
	        PreparedStatement st = conn.prepareStatement(sql);
	        st.setInt(1, serviceid);

	        ResultSet rs = st.executeQuery();

	        if (rs.next()) {
	        	service.setName(rs.getString("SV_NAME"));
	        	service.setMaxDistance(rs.getInt("SV_MAXDISTANCE"));
	        	service.setMaxWeight(rs.getInt("SV_MAXWEIGHT"));
	        	service.setPrice(rs.getInt("SV_PRICE"));
	        }
	        
	        JDBCUtil.closeConnection(conn);
	        return service.getName();
	    } catch (SQLException ex) {
	        System.out.println("loi tai orderimpl_setdata");
	        ex.printStackTrace();
	    }
		return null;
    }

	@Override
	public List<Service> selectShipFeeforOrd(double weight, double distance) {
	    try {
	        List<Service> services = new ArrayList<>();
	        Connection conn = JDBCUtil.getConnection();

	        String sql = "SELECT [SV_ID],[SV_NAME], [SV_MAXDISTANCE], [SV_MAXWEIGHT], [SV_PRICE] FROM [Shipper1].[dbo].[SERVICE] WHERE [SV_MAXWEIGHT] > ? AND [SV_MAXDISTANCE] > ?";

	        PreparedStatement st = conn.prepareStatement(sql);
	        st.setDouble(1, weight);
	        st.setDouble(2, distance);
	        ResultSet rs = st.executeQuery();

	        while (rs.next()) {
	            Service service = new Service(); // Create a new Service object for each record
	            service.setId(rs.getInt("SV_ID"));
	            service.setName(rs.getString("SV_NAME"));
	            service.setMaxDistance(rs.getInt("SV_MAXDISTANCE"));
	            service.setMaxWeight(rs.getInt("SV_MAXWEIGHT"));
	            service.setPrice(rs.getInt("SV_PRICE"));
	            System.out.println(service.toString());
	            services.add(service);
	        }
	        JDBCUtil.closeConnection(conn);
	        return services;
	    } catch (SQLException ex) {
	        System.out.println("loi tai orderimpl_setdata");
	        ex.printStackTrace();
	    }
	    return null;
	}

   


}
