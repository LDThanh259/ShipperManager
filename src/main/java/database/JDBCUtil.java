package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection c = null;
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            String server = "LAPTOP-M9LQ60VB\\SQLEXPRESS";
            String user = "sa";
            String password = "123456789";
            String db = "ShipperDemo";
            String url = "jdbc:sqlserver://" + server + ":1433;DatabaseName=" + db + ";encrypt=true;trustServerCertificate=true;";
            c = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return c;
    }
    
    public static void closeConnection(Connection c) {
        try{
            if(c != null) {
                c.close();
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
