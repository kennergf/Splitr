package CA.Splitr.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Repository;

import CA.Splitr.DTO.TripClosedDTO;
import CA.Splitr.Models.Expense;
import CA.Splitr.Models.Summary;

@Repository
public class TripRepository {
    private static final Map<String, ArrayList<Expense>> trip = new HashMap<>();
    private static final Map<String, Boolean> tripActive = new HashMap<>();
    private static long expenseIndex = 0;

    /**
     * Add the expense labeled to the system
     * @param label used to identify a group of expenses
     * @param expense is the description of the profuct or service paid
     * @return the label if added, or null if the label is inactive and could not be added
     */
    public String addExpense(String label, Expense expense) {
        // If it is a new label, add it to the Map
        if (!trip.containsKey(label)) {
            trip.put(label, new ArrayList<Expense>());
            tripActive.put(label, true);
        }

        // If the label is Active, add the expense to the list
        if (tripActive.get(label).equals(true)) {
            expense.setId(expenseIndex++);
            trip.get(label).add(expense);
            return label;
        } else {
            return null;
        }
    }

    /**
     * Remove the expense based on id and label
     * @param label
     * @param expenseId
     * @return expenseId if removed, or -1 if not found
     */
    // REF https://www.tutorialspoint.com/use-iterator-to-remove-an-element-from-a-collection-in-java
    public long removeExpense(String label, long expenseId){
        Iterator<Expense> iteractor = trip.get(label).iterator();
        while(iteractor.hasNext()){
            if(iteractor.next().getId() == expenseId){
                iteractor.remove();
                return expenseId;
            }
        }
        return -1;
    }

    /**
     * Return a List of expenses if any found, or null if label does not exist
     * @param label that identify a group of expenses
     * @return
     */
    public ArrayList<Expense> getExpenses(String label) {
        // If trip doesn't contain the key
        if (!trip.containsKey(label)) {
            return new ArrayList<Expense>();
        } else {
            ArrayList<Expense> expenses = trip.get(label);
            // If contains the key but no expenses
            if (expenses.equals(null)) {
                return new ArrayList<Expense>();
            } else {
                return expenses;
            }
        }
    }

    /**
     * Verify if the label exist
     * @param label
     * @return true or false
     */
    public boolean LabelExists(String label){
        return tripActive.containsKey(label);
    }

    /**
     * Close the label for new expenses
     * @param label
     * @return an object with the label and the status message
     */
    public TripClosedDTO close(String label) {
        if (tripActive.containsKey(label)) {
            if(tripActive.replace(label, false)){
                return new TripClosedDTO(label, "Closed!");
            }else{
                return new TripClosedDTO(label, "Already Closed!");
            }
        }else{
            return new TripClosedDTO(label, "Not Found!");
        }
    }

    /**
     * Get the summary for the label with information about the expenses and how it is split
     * @param label
     * @return The summary if found, or null if label does not exist
     */
    public Summary getSummary(String label) {
        if (trip.containsKey(label)) {
            Summary summary = new Summary(label, tripActive.get(label), trip.get(label));

            return summary;
        }

        return null;
    }
}
