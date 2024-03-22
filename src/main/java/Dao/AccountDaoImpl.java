/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import database.JDBCUtil;
import model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDaoImpl implements AccountDao{

    @Override
    public Account login(String username, String password) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT * FROM Account WHERE username LIKE ? AND password LIKE ?;";
        Account account = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
            }
            JDBCUtil.closeConnection(conn);
            return account;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
