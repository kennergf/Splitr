package CA.Splitr.Controllers;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import CA.Splitr.DTO.ExpenseDTO;
import CA.Splitr.DTO.TripClosedDTO;
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
            @RequestBody(required = true) ExpenseDTO expenseDTO) {
        // TODO validate
        float value = 0;
        try {
            value = Float.parseFloat(expenseDTO.getValue());
        } catch (NullPointerException e) {
            return new ResponseEntity<String>("No value provided!", HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new ResponseEntity<String>("Value not valid!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        var result = tripRepository.addExpense(label, new Expense(0, value));

        if (result.equals(null)) {
            return new ResponseEntity<String>("Label Closed", HttpStatus.LOCKED);
        } else {
            return ResponseEntity.ok("Expense Added! " + token);
        }
    }

    @GetMapping("/{label}")
    public ResponseEntity<ArrayList<Expense>> getExpenses(@PathVariable("label") String label) {
        var expenses = tripRepository.getExpenses(label);

        return ResponseEntity.ok(expenses);
    }

    @PostMapping("/{label}/close")
    public ResponseEntity<TripClosedDTO> close(@PathVariable("label") String label) {
        if (!tripRepository.LabelExists(label)) {
            return new ResponseEntity<TripClosedDTO>(new TripClosedDTO(label, "NOT FOUND"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(tripRepository.close(label));
    }

    @GetMapping("/{label}/summary")
    public ResponseEntity<Summary> getSummary(@PathVariable("label") String label) {
        return ResponseEntity.ok(tripRepository.getSummary(label));
    }
}
