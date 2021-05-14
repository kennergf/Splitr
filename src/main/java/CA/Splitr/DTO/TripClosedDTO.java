package CA.Splitr.DTO;

public class TripClosedDTO {
    private String label;
    private String status;

    public TripClosedDTO(String label, String status) {
        super();
        setLabel(label);
        setStatus(status);
    }

    public String getStatus() {
        return status;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
