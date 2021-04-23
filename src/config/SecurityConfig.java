package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecurityConfig {

    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    
    // String: Role
    // List<String>: urlPatterns.
    private static final Map<String, List<String>> mapConfig = new HashMap<>();
    
    static {
        SecurityConfig.init();
    }
    
    private static void init() {
        
        // Конфигурация для роли "EMPLOYEE".
        final List<String> urlPatterns1 = new ArrayList<>();
        
        urlPatterns1.add("/userInfo");
        urlPatterns1.add("/employeeTask");
        
        SecurityConfig.mapConfig.put(SecurityConfig.ROLE_EMPLOYEE, urlPatterns1);
        
        // Конфигурация для роли "MANAGER".
        final List<String> urlPatterns2 = new ArrayList<>();
        
        urlPatterns2.add("/userInfo");
        urlPatterns2.add("/managerTask");
        
        SecurityConfig.mapConfig.put(SecurityConfig.ROLE_MANAGER, urlPatterns2);
    }
    
    public static Set<String> getAllAppRoles() {
        return SecurityConfig.mapConfig.keySet();
    }
    
    public static List<String> getUrlPatternsForRole(final String role) {
        return SecurityConfig.mapConfig.get(role);
    }
    
}