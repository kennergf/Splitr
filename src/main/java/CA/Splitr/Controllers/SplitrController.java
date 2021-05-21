package CA.Splitr.Controllers;

import java.util.ArrayList;
import java.util.Date;

import javax.websocket.server.PathParam;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import CA.Splitr.Configuration.AuthenticationConfigurationConstants;
import CA.Splitr.DTO.ExpenseDTO;
import CA.Splitr.DTO.TripClosedDTO;
import CA.Splitr.Models.Expense;
import CA.Splitr.Models.Summary;
import CA.Splitr.Repositories.TripRepository;
import CA.Splitr.Repositories.UserRepository;

@RestController
public class SplitrController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{label}/expense")
    public ResponseEntity<String> addExpense(@PathVariable("label") String label,
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody(required = true) ExpenseDTO expenseDTO) {

        // Token Validation
        // REF https://www.baeldung.com/java-json-web-tokens-jjwt
        DecodedJWT decoded = JWT.decode(token.replace(AuthenticationConfigurationConstants.TOKEN_PREFIX, ""));
        String username = decoded.getSubject();
        if (new Date(System.currentTimeMillis()).after(decoded.getExpiresAt())) {
            throw new RuntimeException("Token expired! Login again to get a valid token!");
        }
        if (!userRepository.usernameExist(username)) {
            throw new UsernameNotFoundException("Username not found!");
        }

        // Validate the value of expense provided
        float value = 0;
        String description = "";
        try {
            value = Float.parseFloat(expenseDTO.getValue());
            description = expenseDTO.getDescription();
        } catch (NullPointerException e) {
            return new ResponseEntity<String>("No value provided!", HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new ResponseEntity<String>("Value not valid!", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Try to add new Expense to the repository
        String result = tripRepository.addExpense(label, new Expense(username, description, value));

        if (result.equals(null)) {
            return new ResponseEntity<String>("Label Closed", HttpStatus.LOCKED);
        } else {
            return ResponseEntity.ok("Expense Added! ");
        }
    }

    @DeleteMapping("/{label}/remove")
    public ResponseEntity<String> removeExpense(@PathVariable("label") String label,
            @RequestHeader(name = "Authorization", required = true) String token,
            @PathParam("expenseId") String expenseId) {
        // Token Validation
        DecodedJWT decoded = JWT.decode(token.replace(AuthenticationConfigurationConstants.TOKEN_PREFIX, ""));
        String username = decoded.getSubject();
        if (new Date(System.currentTimeMillis()).after(decoded.getExpiresAt())) {
            throw new RuntimeException("Token expired! Login again to get a valid token!");
        }
        if (!userRepository.usernameExist(username)) {
            throw new UsernameNotFoundException("Username not found!");
        }

        long id = tripRepository.removeExpense(label, Long.parseLong(expenseId));
        if(id < 0){
            return new ResponseEntity<String>("Expense could not be removed", HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok("Expense Removed!");
        }
    }

    @GetMapping("/{label}")
    public ResponseEntity<ArrayList<Expense>> getExpenses(@PathVariable("label") String label,
            @RequestHeader(name = "Authorization", required = true) String token) {
        // Token Validation
        DecodedJWT decoded = JWT.decode(token.replace(AuthenticationConfigurationConstants.TOKEN_PREFIX, ""));
        String username = decoded.getSubject();
        if (new Date(System.currentTimeMillis()).after(decoded.getExpiresAt())) {
            throw new RuntimeException("Token expired! Login again to get a valid token!");
        }
        if (!userRepository.usernameExist(username)) {
            throw new UsernameNotFoundException("Username not found!");
        }

        ArrayList<Expense> expenses = tripRepository.getExpenses(label);

        return ResponseEntity.ok(expenses);
    }

    @PostMapping("/{label}/close")
    public ResponseEntity<TripClosedDTO> close(@PathVariable("label") String label,
            @RequestHeader(name = "Authorization", required = true) String token) {
        // Token Validation
        DecodedJWT decoded = JWT.decode(token.replace(AuthenticationConfigurationConstants.TOKEN_PREFIX, ""));
        String username = decoded.getSubject();
        if (new Date(System.currentTimeMillis()).after(decoded.getExpiresAt())) {
            throw new RuntimeException("Token expired! Login again to get a valid token!");
        }
        if (!userRepository.usernameExist(username)) {
            throw new UsernameNotFoundException("Username not found!");
        }

        if (!tripRepository.LabelExists(label)) {
            return new ResponseEntity<TripClosedDTO>(new TripClosedDTO(label, "NOT FOUND"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(tripRepository.close(label));
    }

    @GetMapping("/{label}/summary")
    public ResponseEntity<Summary> getSummary(@PathVariable("label") String label,
            @RequestHeader(name = "Authorization", required = true) String token) {
        // Token Validation
        DecodedJWT decoded = JWT.decode(token.replace(AuthenticationConfigurationConstants.TOKEN_PREFIX, ""));
        String username = decoded.getSubject();
        if (new Date(System.currentTimeMillis()).after(decoded.getExpiresAt())) {
            throw new RuntimeException("Token expired! Login again to get a valid token!");
        }
        if (!userRepository.usernameExist(username)) {
            throw new UsernameNotFoundException("Username not found!");
        }

        return ResponseEntity.ok(tripRepository.getSummary(label));
    }
}
