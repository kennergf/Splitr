package CA.Splitr.Controllers;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import CA.Splitr.Models.Expense;
import CA.Splitr.Models.Summary;
import CA.Splitr.Repositories.TripRepository;

@RestController
public class SplitrController {

    @Autowired
    private TripRepository tripRepository;

    @PostMapping("/{label}/expense")
    public ResponseEntity<String> addExpense(@PathVariable("label") String label,
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody(required = true) Expense expense) {
        // TODO validate
        var result = tripRepository.addExpense(label, expense);

        if (result.equals(null)) {
            return ResponseEntity.ok("Label Closed!");
        } else {
            return ResponseEntity.ok("Added!");
        }
    }

    @GetMapping("/{label}")
    public ResponseEntity<ArrayList<Expense>> getExpenses(@PathVariable("label") String label) {
        var expenses = tripRepository.getExpenses(label);
        
        return ResponseEntity.ok(expenses);
    }

    @PostMapping("/{label}/close")
    public ResponseEntity<String> close(@PathVariable("label") String label) {
        tripRepository.close(label);
        return ResponseEntity.ok("Closed!");
    }

    @GetMapping("/{label}/summary")
    public ResponseEntity<Summary> getSummary(@PathVariable("label") String label) {
        return ResponseEntity.ok(tripRepository.getSummary(label));
    }
}
