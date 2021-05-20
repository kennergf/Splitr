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

    /**
     * Get the user by Username
     * 
     * @param username
     * @return User if found or null if not found
     */
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Create a new user if the username is available
     * 
     * @param userDTO
     * @return True if created, or False if it fails to create new user
     */
    public boolean create(UserDTO userDTO) {
        if (userRepository.usernameExist(userDTO.getUsername())) {
            throw new RuntimeException("Username is been used! Please choose a different username!");
        }

        // Create new User with password encrypted
        User user = new User(userDTO.getUsername(), bCryptPasswordEncoder.encode(userDTO.getPassword()),
                userDTO.getRole());
        return userRepository.save(user);
    }
}
