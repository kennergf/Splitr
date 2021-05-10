package CA.Splitr.Services;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import CA.Splitr.Models.User;
import CA.Splitr.Repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    //private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User readUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void registerUser(String username, String password){
        User user = new User(username, password);
        userRepository.save(user);
    }
}
