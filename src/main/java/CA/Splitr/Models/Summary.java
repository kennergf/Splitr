package CA.Splitr.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import CA.Splitr.DTO.PaidPerUserDTO;

public class Summary {
    private String label;
    private boolean active;
    private ArrayList<Expense> expenses;
    private float total;
    private float highestExpense;
    private float lowestExpense;
    //
    private Map<String, Float> paidPerUsers;

    public Summary(String label, boolean active, ArrayList<Expense> expanses) {
        super();
        this.label = label;
        this.active = active;
        this.expenses = expanses;
        //
        paidPerUsers = new HashMap<>();
        lowestExpense = 99999999999999999999F;
        highestExpense = -1;
        this.calculatePaidPerUser();
    }

    public float getLowestExpense() {
        return lowestExpense;
    }

    public float getHighestExpense() {
        return highestExpense;
    }

    public float getTotal() {
        return total;
    }

    public String getLabel() {
        return label;
    }

    public boolean isActive() {
        return active;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    private void calculatePaidPerUser() {
        for (Expense expense : expenses) {
            // Add the key if it does not exists
            if (!paidPerUsers.containsKey(expense.getUsername())) {
                paidPerUsers.put(expense.getUsername(), 0F);
            }
            // Sum the value to the paid for the user
            float sumPaid = paidPerUsers.get(expense.getUsername());
            float expenseValue = expense.getValue();
            paidPerUsers.replace(expense.getUsername(), sumPaid + expenseValue);
            // Calculate total
            total += expenseValue;
            // Calculate Lower Expense
            if (expenseValue < lowestExpense) {
                lowestExpense = expenseValue;
            }
            // Calculate Highest Expense
            if (expenseValue > highestExpense) {
                highestExpense = expenseValue;
            }
        }
    }

    public ArrayList<PaidPerUserDTO> getPaidPerUser() {
        ArrayList<PaidPerUserDTO> paidPerUserDTOs = new ArrayList<PaidPerUserDTO>();

        // REF https://www.javacodeexamples.com/java-hashmap-foreach-for-loop-example/2329
        paidPerUsers.forEach((key, value) -> paidPerUserDTOs
                .add(new PaidPerUserDTO(key, value, (value - (total / paidPerUsers.size())))));

        return paidPerUserDTOs;
    }

    public int getNumberPurchases() {
        return expenses.size();
    }

    public float getAvarageExpense() {
        return (total / getNumberPurchases());
    }
}
