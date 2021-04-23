package connection;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        
        return MySQLConnectionUtils.getMySQLConnection();
        
    }
    
    public static void closeQuietly(final Connection conn) {
        try {
            conn.close();
        } catch (final Exception e) {
        }
    }
    
    public static void rollbackQuietly(final Connection conn) {
        try {
            conn.rollback();
        } catch (final Exception e) {
        }
    }
}