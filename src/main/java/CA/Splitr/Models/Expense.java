package CA.Splitr.Models;

public class Expense {
    private String username;
    private float value;

    public Expense(String username, float value) {
        super();
        setUsername(username);
        setValue(value);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
