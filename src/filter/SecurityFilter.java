package filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UserAccount;
import request.UserRoleRequestWrapper;
import utils.AppUtils;
import utils.SecurityUtils;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    
    public SecurityFilter() {
    }
    
    @Override
    public void destroy() {
    }
    
    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) resp;
        
        final String servletPath = request.getServletPath();
        
        // Информация пользователя сохранена в Session
        // (После успешного входа в систему).
        final UserAccount loginedUser = AppUtils.getLoginedUser(request.getSession());
        
        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest wrapRequest = request;
        
        if (loginedUser != null) {
            // User Name
            final String userName = loginedUser.getUserName();
            
            // Роли (Role).
            final List<String> roles = loginedUser.getRoles();
            
            // Старый пакет request с помощью нового Request с информацией userName и Roles.
            wrapRequest = new UserRoleRequestWrapper(userName, roles, request);
        }
        
        // Страницы требующие входа в систему.
        if (SecurityUtils.isSecurityPage(request)) {
            
            // Если пользователь еще не вошел в систему,
            // Redirect (перенаправить) к странице логина.
            if (loginedUser == null) {
                
                final String requestUri = request.getRequestURI();
                
                // Сохранить текущую страницу для перенаправления (redirect) после успешного
                // входа в систему.
                final int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
                
                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }
            
            // Проверить пользователь имеет действительную роль или нет?
            final boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                
                final RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDeniedView.jsp");
                
                dispatcher.forward(request, response);
                return;
            }
        }
        
        chain.doFilter(wrapRequest, response);
    }
    
    @Override
    public void init(final FilterConfig fConfig) throws ServletException {
        
    }
    
}