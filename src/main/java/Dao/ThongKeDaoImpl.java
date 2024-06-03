/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import bean.CalculateProportion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.IncomeBean;
import database.JDBCUtil;
import model.Shipper;
import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author AD
 */
public class ThongKeDaoImpl implements ThongKeDao {

    /**
     *
     * @return
     */
    @Override
    public List<IncomeBean> getListIncomeByShipper(Shipper shipper) {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT TOP(1000) \n"
                    + "    [SHIPPER].[SHIPPER_ID],\n"
                    + "    [SHIPPER].[SHIPPER_NAME], \n"
                    + "    MONTH([ORD_COMPLETED_TIME]) AS MONTH_COMPLETED, \n"
                    + "    YEAR([ORD_COMPLETED_TIME]) AS YEAR_COMPLETED,\n"
                    + "    SUM([ORDERS].[ORD_SHIP_FEE]) AS INCOME\n"
                    + "FROM \n"
                    + "    [Shipper1].[dbo].[SHIPPER] \n"
                    + "JOIN \n"
                    + "    [ORDERS] ON [SHIPPER].[SHIPPER_ID] = [ORDERS].[SHIPPER_ID]\n"
                    + "WHERE \n"
                    + "    [SHIPPER].[SHIPPER_ID] = ? \n"
                    + "    AND [ORD_COMPLETED_TIME] IS NOT NULL \n"
                    + "    AND YEAR([ORD_COMPLETED_TIME]) = YEAR(GETDATE())\n"
                    + "GROUP BY \n"
                    + "    [SHIPPER].[SHIPPER_ID],\n"
                    + "    [SHIPPER].[SHIPPER_NAME], \n"
                    + "    MONTH([ORD_COMPLETED_TIME]), \n"
                    + "    YEAR([ORD_COMPLETED_TIME]);";

            List<IncomeBean> incomebeans = new ArrayList<>();

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, shipper.getId());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                IncomeBean incomebean = new IncomeBean();
                incomebean.setId(rs.getInt("SHIPPER_ID"));
                incomebean.setNameShipper(rs.getString("SHIPPER_NAME"));
                incomebean.setMonth(rs.getInt("MONTH_COMPLETED"));
                incomebean.setIncome(rs.getInt("INCOME"));

                incomebeans.add(incomebean);
            }
            rs.close();
            st.close();
            JDBCUtil.closeConnection(conn);
            return incomebeans;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<IncomeBean> getIncomeByShipperAndMonth(int month) {
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT TOP(1000) [SHIPPER].[SHIPPER_ID] as ID,[SHIPPER].[SHIPPER_NAME] as name,\n"
                    + "sum([ORDERS].[ORD_SHIP_FEE]) as money,\n"
                    + "MONTH([ORDERS].[ORD_COMPLETED_TIME]) as month\n"
                    + "FROM [Shipper1].[dbo].[SHIPPER] JOIN [ORDERS] ON "
                    + " [SHIPPER].[SHIPPER_ID] = [ORDERS].[SHIPPER_ID]\n"
                    + "WHERE ([SHIPPER].[SHIPPER_ISDELETED] =0) AND "
                    + "([ORDERS].[ORD_SHIP_COUNT]<=3) AND \n"
                    + "([ORDERS].[ORD_COMPLETED_TIME] IS NOT NULL)"
                    + " AND (MONTH([ORDERS].[ORD_COMPLETED_TIME])) = ?\n"
                    + " AND YEAR([ORD_COMPLETED_TIME]) = YEAR(GETDATE())\n"
                    + " GROUP BY [SHIPPER].[SHIPPER_ID],[SHIPPER].[SHIPPER_NAME] ,"
                    + "MONTH([ORDERS].[ORD_COMPLETED_TIME]);";
            List<IncomeBean> incomebeans = new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, month);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                IncomeBean incomebean = new IncomeBean();
                incomebean.setId(rs.getInt("ID"));
                incomebean.setNameShipper(rs.getString("name"));
                incomebean.setIncome(rs.getInt("money"));
                incomebean.setMonth(rs.getInt("month"));

                incomebeans.add(incomebean);
            }
            rs.close();
            ps.close();
            JDBCUtil.closeConnection(conn);
            return incomebeans;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CalculateProportion> getProportion() {
        try {
            Connection conn = JDBCUtil.getConnection();

            String sql = "SELECT TOP(1000) [SHIPPER].[SHIPPER_ID] as ID,[SHIPPER].[SHIPPER_NAME] as name,\n"
                    + "COUNT([ORDERS].[ORD_COMPLETED_TIME]) AS COMPLETE,\n"
                    + "COUNT([SHIPPER].[SHIPPER_ID] ) AS TOTAL              \n"
                    + "FROM [Shipper1].[dbo].[SHIPPER] JOIN [ORDERS] ON \n"
                    + "[SHIPPER].[SHIPPER_ID] = [ORDERS].[SHIPPER_ID]\n"
                    + "GROUP BY  [SHIPPER].[SHIPPER_ID], [SHIPPER].[SHIPPER_NAME] ;  ";

            List<CalculateProportion> calculateProportions = new ArrayList<>();

            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                CalculateProportion calculateProportion = new CalculateProportion();
                calculateProportion.setId(rs.getInt("ID"));
                calculateProportion.setName(rs.getString("name"));
                calculateProportion.setComplete(rs.getFloat("COMPLETE"));
                calculateProportion.setTotal(rs.getFloat("TOTAL"));
                calculateProportion.setProportion();

                calculateProportions.add(calculateProportion);
            }
            rs.close();
            st.close();
            JDBCUtil.closeConnection(conn);
            return calculateProportions;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
