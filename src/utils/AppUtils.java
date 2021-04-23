package utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import beans.UserAccount;

public class AppUtils {
    
    private static int REDIRECT_ID = 0;

    private static final Map<Integer, String> id_uri_map = new HashMap<>();
    private static final Map<String, Integer> uri_id_map = new HashMap<>();

    // Сохранить информацию пользователя в Session.
    public static void storeLoginedUser(final HttpSession session, final UserAccount loginedUser) {
        // На JSP можно получить доступ через ${loginedUser}
        session.setAttribute("loginedUser", loginedUser);
    }

    // Получить информацию пользователя, сохраненную в Session.
    public static UserAccount getLoginedUser(final HttpSession session) {
        final UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
        return loginedUser;
    }

    public static int storeRedirectAfterLoginUrl(final HttpSession session, final String requestUri) {
        Integer id = AppUtils.uri_id_map.get(requestUri);

        if (id == null) {
            id = AppUtils.REDIRECT_ID++;

            AppUtils.uri_id_map.put(requestUri, id);
            AppUtils.id_uri_map.put(id, requestUri);
            return id;
        }

        return id;
    }

    public static String getRedirectAfterLoginUrl(final HttpSession session, final int redirectId) {
        final String url = AppUtils.id_uri_map.get(redirectId);
        if (url != null) {
            return url;
        }
        return null;
    }

}