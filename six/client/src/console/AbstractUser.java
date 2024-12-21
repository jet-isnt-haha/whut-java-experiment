package console;

import java.io.Serializable;

/**
 * TODO 抽象用户类，为各用户子类提供模板
 *
 * @author gongjing
 * @date 2016/10/13
 */
public class AbstractUser implements Serializable {
    private String name;
    private String password;
    private String role;

    AbstractUser(String name,String password,String role){
        this.name=name;
        this.password=password;
        this.role=role;
    }

    @Override
    public String toString() {
        return "AbstractUser{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
