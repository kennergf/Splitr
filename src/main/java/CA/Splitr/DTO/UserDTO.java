package CA.Splitr.DTO;

public class UserDTO {
    private String username;
    private String password;
    private String role;
    
    public String getUsername() {
        return username;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
