package CA.Splitr.Repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import CA.Splitr.Models.User;

@Repository
public class UserRepository {
    private Map<String, User> users;

    public UserRepository() {
        super();
        this.users = new HashMap<>();
    }

    /**
     * Get the user by Username
     * @param username
     * @return User if found or null if not found
     */
    public User findByUsername(String username) {
        return users.get(username);
    }

    /**
     * 
     * @param user
     * @return true if saved, false if not saved
     */
    public boolean save(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        } else {
            users.put(user.getUsername(), user);
            return true;
        }
    }

    public boolean usernameExist(String username){
        return users.containsKey(username);
    }
}
