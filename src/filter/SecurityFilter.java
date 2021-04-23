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
        
        // ���������� ������������ ��������� � Session
        // (����� ��������� ����� � �������).
        final UserAccount loginedUser = AppUtils.getLoginedUser(request.getSession());
        
        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest wrapRequest = request;
        
        if (loginedUser != null) {
            // User Name
            final String userName = loginedUser.getUserName();
            
            // ���� (Role).
            final List<String> roles = loginedUser.getRoles();
            
            // ������ ����� request � ������� ������ Request � ����������� userName � Roles.
            wrapRequest = new UserRoleRequestWrapper(userName, roles, request);
        }
        
        // �������� ��������� ����� � �������.
        if (SecurityUtils.isSecurityPage(request)) {
            
            // ���� ������������ ��� �� ����� � �������,
            // Redirect (�������������) � �������� ������.
            if (loginedUser == null) {
                
                final String requestUri = request.getRequestURI();
                
                // ��������� ������� �������� ��� ��������������� (redirect) ����� ���������
                // ����� � �������.
                final int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
                
                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }
            
            // ��������� ������������ ����� �������������� ���� ��� ���?
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