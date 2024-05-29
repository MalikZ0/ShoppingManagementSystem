package consoleMenu;
/**
 * @author KumaraMalik
 */
public class User {
    //Instance variables in User class
    private String username;
    private String password;

    // User constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // get methods
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    // set methods
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
