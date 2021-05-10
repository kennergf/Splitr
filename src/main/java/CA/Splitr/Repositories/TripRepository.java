package CA.Splitr.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import CA.Splitr.Models.Expense;
import CA.Splitr.Models.Summary;

@Repository
public class TripRepository {
    private static final Map<String, ArrayList<Expense>> trip = new HashMap<>();
    private static final Map<String, Boolean> tripActive = new HashMap<>();

    public String addExpense(String label, Expense expense) {
        // If it is a new label, add it to the Map
        if (!trip.containsKey(label)) {
            trip.put(label, new ArrayList<Expense>());
            tripActive.put(label, true);
        }

        // If the label is Active, add the expense to the list
        if (tripActive.get(label).equals(true)) {
            trip.get(label).add(expense);
            return label;
        } else {
            return null;
        }
    }

    public ArrayList<Expense> getExpenses(String label) {
        // If trip doesn't contain the key
        if (!trip.containsKey(label)) {
            return null;
        } else {
            var expenses = trip.get(label);
            // If contains the key but no expenses
            if (expenses.equals(null)) {
                return new ArrayList<Expense>();
            } else {
                return expenses;
            }
        }
    }

    public void close(String label) {
        if (tripActive.containsKey(label)) {
            tripActive.replace(label, false);
        }
    }

    public Summary getSummary(String label) {
        if (trip.containsKey(label)) {
            Summary summary = new Summary(label, tripActive.get(label), trip.get(label));

            return summary;
        }

        return null;
    }
}
