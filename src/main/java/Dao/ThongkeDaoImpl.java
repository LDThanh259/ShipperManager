/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import bean.IncomeBean;
import java.sql.Connection;
import database.JDBCUtil;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import model.Shipper;

public class ThongkeDaoImpl implements ThongkeDao {

    @Override
    public List<IncomeBean> getListIncome() {
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT\n"
                    + "    S.SHIPPER_ID AS Id,\n"
                    + "    S.SHIPPER_NAME AS [Name],\n"
                    + "    SUM(O.ORDERS_PRICE) AS [Income]\n"
                    + "FROM\n"
                    + "    dbo.SHIPPER AS S\n"
                    + "INNER JOIN\n"
                    + "    dbo.[Order] AS O\n"
                    + "ON\n"
                    + "    S.SHIPPER_ID = O.SHIPPER_ID\n"
                    + "WHERE\n"
                    + "    O.ORDERS_RECEIVE_TIME IS NULL\n"
                    + "GROUP BY\n"
                    + "    S.SHIPPER_ID, S.SHIPPER_NAME;";
            List<IncomeBean> list = new ArrayList<>();
            PreparedStatement ps = conn.prepareCall(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                IncomeBean incomeBean = new IncomeBean();
                incomeBean.setId(rs.getInt("Id"));
                incomeBean.setNameShipper(rs.getString("Name"));
                incomeBean.setIncome(rs.getFloat("Income"));
                list.add(incomeBean);
            }
            JDBCUtil.closeConnection(conn);
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ThongkeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<IncomeBean> getIncomeByShipperAndMonth(Shipper shipper) {
        List<IncomeBean> incomeList = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String sql = "SELECT\n"
                    + "    S.SHIPPER_ID AS Id,\n"
                    + "    S.SHIPPER_NAME AS [Name],\n"
                    + "    MONTH(O.ORDERS_RECEIVE_TIME) AS [Month],\n"
                    + "    SUM(O.ORDERS_PRICE) AS [Income]\n"
                    + "FROM\n"
                    + "    dbo.SHIPPER AS S\n"
                    + "INNER JOIN\n"
                    + "    dbo.[Order] AS O\n"
                    + "ON\n"
                    + "    S.SHIPPER_ID = O.SHIPPER_ID\n"
                    + "WHERE\n"
                    + "    S.SHIPPER_ID = ? "
                    + "    AND O.ORDERS_RECEIVE_TIME IS NOT NULL\n"
                    + "    AND YEAR(O.ORDERS_RECEIVE_TIME) = YEAR(GETDATE())\n"
                    + "GROUP BY\n"
                    + "    S.SHIPPER_ID, S.SHIPPER_NAME, MONTH(O.ORDERS_RECEIVE_TIME);";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, shipper.getShipper_Id());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                IncomeBean incomeBean = new IncomeBean();
                incomeBean.setId(rs.getInt("Id"));
                incomeBean.setNameShipper(rs.getString("Name"));
                incomeBean.setMonth(rs.getInt("Month"));
                incomeBean.setIncome(rs.getFloat("Income"));
                incomeList.add(incomeBean);
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(ThongkeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return incomeList;
    }

}
