package CA.Splitr.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import CA.Splitr.Models.Expense;
import CA.Splitr.Repositories.TripRepository;

@RestController
public class SplitrController {

    @Autowired
    private TripRepository tripRepository;

    @PostMapping("/{label}/expense")
    public Map<String, ArrayList<Expense>> addExpense(@PathVariable("label") String label,
    @RequestHeader(name = "Authorization", required = true) String token,
    @RequestBody(required = true) Expense expense){
        return null;
    }

    @GetMapping("/{label}")
    public List<Expense> getExpenses(@PathVariable("label") String label){
        return tripRepository.getExpenses(label);
    }

    @PostMapping("/{label}/close")
    public void close(@PathVariable("label") String label){
        tripRepository.close(label);
    }

    @GetMapping("/{label}/summary")
	public String getSummary(@PathVariable("label") String label) {
		return label;
	}
}
