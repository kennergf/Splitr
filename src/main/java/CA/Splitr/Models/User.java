package CA.Splitr.Models;

public class User {
    private long id;
    private String username;
    private String password;
    private String role;

    public User() {
        super();
    }

    public User(String username, String password, String role) {
        super();
        setUsername(username);
        setPassword(password);
        setRole(role);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
