package CA.Splitr.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import CA.Splitr.Models.User;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// REF https://javatodev.com/spring-boot-jwt-authentication/#d375a47b0c4f
@Service
public class AuthenticationUserDetailService implements UserDetailsService{
    private final UserService userService;

    public AuthenticationUserDetailService(UserService userService) {
        super();
        this.userService = userService;
    }

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userService.readUserByUsername(username);
        if(user.equals(null)){
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), null);
    }
}
