package CA.Splitr.Controllers;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import CA.Splitr.Models.Expense;

@RestController
public class SplitrController {
    public Map<String, ArrayList<Expense>> addExpense(@PathVariable("label") String label,
    @RequestHeader(name = "Authorization", required = true) String token,
    @RequestBody(required = true) Expense expense){
        return null;
    }
}
