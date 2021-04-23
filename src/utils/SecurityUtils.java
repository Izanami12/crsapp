package utils;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import config.SecurityConfig;

public class SecurityUtils {

    // ��������� ������� �� ������ 'request' ����� � ������� ��� ���.
    public static boolean isSecurityPage(final HttpServletRequest request) {
        final String urlPattern = UrlPatternUtils.getUrlPattern(request);
        
        final Set<String> roles = SecurityConfig.getAllAppRoles();
        
        for (final String role : roles) {
            final List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
    
    // ��������� ����� �� ������ 'request' ���������� ����?
    public static boolean hasPermission(final HttpServletRequest request) {
        final String urlPattern = UrlPatternUtils.getUrlPattern(request);
        
        final Set<String> allRoles = SecurityConfig.getAllAppRoles();
        
        for (final String role : allRoles) {
            if (!request.isUserInRole(role)) {
                continue;
            }
            final List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
}