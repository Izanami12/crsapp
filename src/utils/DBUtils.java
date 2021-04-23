package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.UserAccount;

public class DBUtils {

    public static UserAccount findUser(final Connection conn, //
            final String userName, final String password) throws SQLException {

        final String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a " //
                + " where a.User_Name = ? and a.password= ?";

        final PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, password);
        final ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            final String gender = rs.getString("Gender");
            final UserAccount user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
            user.setGender(gender);
            return user;
        }
        return null;
    }

    public static UserAccount findUser(final Connection conn, final String userName) throws SQLException {

        final String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a "//
                + " where a.User_Name = ? ";

        final PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);

        final ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            final String password = rs.getString("Password");
            final String gender = rs.getString("Gender");
            final UserAccount user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
            user.setGender(gender);
            return user;
        }
        return null;
    }

}
