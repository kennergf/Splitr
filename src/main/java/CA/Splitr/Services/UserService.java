package CA.Splitr.Services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import CA.Splitr.DTO.UserDTO;
import CA.Splitr.Models.User;
import CA.Splitr.Repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User readUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void registerUser(String username, String password, String role){
        User user = new User(username, password, role);
        userRepository.save(user);
    }

    public void createUser(UserDTO userDTO){
        if(userRepository.usernameExist(userDTO.getUsername())){
            throw new RuntimeException("Username is been used! Choose a different username!");
        }

        User user = new User(userDTO.getUsername(), bCryptPasswordEncoder.encode(userDTO.getPassword()), userDTO.getRole());
        userRepository.save(user);
    }
}
