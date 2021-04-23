package utils;

import java.sql.Connection;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserAccount;

public class MyUtils {

    public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
    
    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

    // Сохранить Connection в attribute в request.
    // Данная информация хранения существует только во время запроса (request)
    // до тех пор, пока данные возвращаются приложению пользователя.
    public static void storeConnection(final ServletRequest request, final Connection conn) {
        request.setAttribute(MyUtils.ATT_NAME_CONNECTION, conn);
    }

    // Получить объект Connection сохраненный в attribute в request.
    public static Connection getStoredConnection(final ServletRequest request) {
        final Connection conn = (Connection) request.getAttribute(MyUtils.ATT_NAME_CONNECTION);
        return conn;
    }

    // Сохранить информацию пользователя, который вошел в систему (login) в Session.
    public static void storeLoginedUser(final HttpSession session, final UserAccount loginedUser) {
        // В JSP можно получить доступ через ${loginedUser}
        session.setAttribute("loginedUser", loginedUser);
    }

    // Получить информацию пользователя, сохраненная в Session.
    public static UserAccount getLoginedUser(final HttpSession session) {
        final UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
        return loginedUser;
    }

    // Сохранить информацию пользователя в Cookie.
    public static void storeUserCookie(final HttpServletResponse response, final UserAccount user) {
        System.out.println("Store user cookie");
        final Cookie cookieUserName = new Cookie(MyUtils.ATT_NAME_USER_NAME, user.getUserName());
        // 1 день (Конвертированный в секунды)
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }

    public static String getUserNameInCookie(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (final Cookie cookie : cookies) {
                if (MyUtils.ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // Удалить Cookie пользователя
    public static void deleteUserCookie(final HttpServletResponse response) {
        final Cookie cookieUserName = new Cookie(MyUtils.ATT_NAME_USER_NAME, null);
        // 0 секунд. (Данный Cookie будет сразу недействителен)
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }

}