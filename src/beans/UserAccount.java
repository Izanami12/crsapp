package beans;

import java.util.ArrayList;
import java.util.List;

public class UserAccount {
    public static final String GENDER_MALE = "M";
    public static final String GENDER_FEMALE = "F";

    private String userName;
    private String gender;
    private String password;

    private List<String> roles;

    public UserAccount() {

    }

    public UserAccount(final String userName, final String password, final String gender, final String... roles) {
        this.userName = userName;
        this.password = password;
        this.gender = gender;

        this.roles = new ArrayList<>();
        if (roles != null) {
            for (final String r : roles) {
                this.roles.add(r);
            }
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }
}