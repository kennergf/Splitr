package CA.Splitr.Models;

public class Expense {
    private long id;
    private String username;
    private String description;
    private float value;

    public Expense(String username, String description, float value) {
        super();
        setUsername(username);
        setDescription(description);
        setValue(value);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
