package CA.Splitr.DTO;

public class PaidPerUserDTO {
    private String username;
    private float value;
    private float balance;

    public PaidPerUserDTO(String username, float value, float balance) {
        super();
        this.username = username;
        this.value = value;
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    public String getUsername() {
        return username;
    }

    public float getValue() {
        return value;
    }

    public float getValueToBeReceived() {
        if (balance < 0) {
            return balance * -1F;
        } else {
            return 0;
        }
    }

    public float getValueToBePaid() {
        if (balance > 0) {
            return balance;
        } else {
            return 0;
        }
    }
}
