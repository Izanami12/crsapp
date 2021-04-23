package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionUtils {
    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
        final String hostName = "localhost";
        final String dbName = "users";
        final String userName = "root";
        final String password = "Password";
        return MySQLConnectionUtils.getMySQLConnection(hostName, dbName, userName, password);
    }
    
    public static Connection getMySQLConnection(final String hostName, final String dbName, final String userName,
            final String password) throws SQLException, ClassNotFoundException {
        
        Class.forName("com.mysql.jdbc.Driver");
        
        // URL Connection MySQL:
        // jdbc:mysql://localhost:3306/simplehr
        final String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        
        final Connection conn = DriverManager.getConnection(connectionURL, userName, password);
        return conn;
    }
}
