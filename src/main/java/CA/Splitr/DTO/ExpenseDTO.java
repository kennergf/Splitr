package CA.Splitr.DTO;

public class ExpenseDTO {
    private String description;
    private String value;

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
