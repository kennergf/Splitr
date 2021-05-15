package CA.Splitr.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CA.Splitr.DTO.UserDTO;
import CA.Splitr.Services.UserService;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String registerUser(){
        userService.registerUser("kenner", "kenner", "USER");
        return "OK";
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return ResponseEntity.ok("User Created!");
    }
}
