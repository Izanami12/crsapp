package utils;

import java.util.HashMap;
import java.util.Map;

import beans.UserAccount;
import config.SecurityConfig;

public class DataDAO {
    
    private static final Map<String, UserAccount> mapUsers = new HashMap<>();
    
    static {
        DataDAO.initUsers();
    }
    
    private static void initUsers() {
        
        // This user has a role as EMPLOYEE.
        final UserAccount emp = new UserAccount("employee1", "123", UserAccount.GENDER_MALE,
                SecurityConfig.ROLE_EMPLOYEE);
        
        // This user has 2 roles EMPLOYEE and MANAGER.
        final UserAccount mng = new UserAccount("manager1", "123", UserAccount.GENDER_MALE,
                SecurityConfig.ROLE_EMPLOYEE, SecurityConfig.ROLE_MANAGER);
        
        DataDAO.mapUsers.put(emp.getUserName(), emp);
        DataDAO.mapUsers.put(mng.getUserName(), mng);
    }
    
    // Find a User by userName and password.
    public static UserAccount findUser(final String userName, final String password) {
        final UserAccount u = DataDAO.mapUsers.get(userName);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }
    
}
