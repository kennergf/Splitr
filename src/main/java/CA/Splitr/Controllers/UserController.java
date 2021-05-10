package CA.Splitr.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import CA.Splitr.Services.UserService;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("user/register")
    public String registerUser(){
        userService.registerUser("kenner", "kenner");
        return "OK";
    }
}
