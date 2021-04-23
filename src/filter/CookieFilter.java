package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.UserAccount;
import utils.DBUtils;
import utils.MyUtils;

@WebFilter(filterName = "cookieFilter", urlPatterns = { "/*" })
public class CookieFilter implements Filter {

    public CookieFilter() {
    }

    @Override
    public void init(final FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpSession session = req.getSession();

        final UserAccount userInSession = MyUtils.getLoginedUser(session);
        //
        if (userInSession != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            chain.doFilter(request, response);
            return;
        }

        // Connection создан в JDBCFilter.
        final Connection conn = MyUtils.getStoredConnection(request);

        // Флаг(flag) для проверки Cookie.
        final String checked = (String) session.getAttribute("COOKIE_CHECKED");
        if (checked == null && conn != null) {
            final String userName = MyUtils.getUserNameInCookie(req);
            try {
                final UserAccount user = DBUtils.findUser(conn, userName);
                MyUtils.storeLoginedUser(session, user);
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            // Отметить проверенные Cookie.
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
        }

        chain.doFilter(request, response);
    }

}