package CA.Splitr.Models;

import java.util.ArrayList;

public class Summary {
    private String label;
    private boolean active;
    private ArrayList<Expense> expenses;

    public Summary(String label, boolean active, ArrayList<Expense> expanses) {
        super();
        this.setLabel(label);
        this.setActive(active);
        this.setExpenses(expanses);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }
}
