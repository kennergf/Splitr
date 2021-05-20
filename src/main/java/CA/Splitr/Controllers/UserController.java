package CA.Splitr.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CA.Splitr.Configuration.AuthenticationConfigurationConstants;
import CA.Splitr.DTO.UserDTO;
import CA.Splitr.Services.UserService;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(AuthenticationConfigurationConstants.SIGN_UP_URL)
    public ResponseEntity<String> create(@RequestBody UserDTO userDTO) {
        if (userService.create(userDTO)) {
            return ResponseEntity.ok("User Created!");
        }else{
            return new ResponseEntity<String>("User NOT created!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
