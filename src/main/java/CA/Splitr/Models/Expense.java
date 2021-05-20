package CA.Splitr.Models;

public class Expense {
    private long id;
    private String username;
    private float value;

    public Expense(String username, float value) {
        super();
        setUsername(username);
        setValue(value);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
