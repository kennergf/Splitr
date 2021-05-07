package CA.Splitr.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import CA.Splitr.Models.Expense;
import CA.Splitr.Models.Summary;

@Repository
public class TripRepository {
    private Map<String, ArrayList<Expense>> trip;
    private Map<String, Boolean> tripActive;

    public TripRepository() {
        super();
        trip = new HashMap<>();
        tripActive = new HashMap<>();
    }

    public Map<String, ArrayList<Expense>> addExpense(String label, Expense expense) {
        if (!trip.containsKey(label)) {
            trip.put(label, new ArrayList<Expense>());
            tripActive.put(label, true);
        }

        if (tripActive.get(label).equals(true)) {
            trip.get(label).add(expense);
        }

        return trip;
    }

    public List<Expense> getExpenses(String label) {
        return trip.get(label);
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
