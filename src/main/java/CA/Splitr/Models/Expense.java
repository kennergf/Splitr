package CA.Splitr.Models;

public class Expense {
    private long id;
    private long idUser;
    private float value;

    public Expense(long idUser, float value) {
        super();
        setIdUser(idUser);
        setValue(value);
    }

    public float getValue() {
        return value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
