package CA.Splitr.Services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import CA.Splitr.Models.User;

// REF https://javatodev.com/spring-boot-jwt-authentication/#d375a47b0c4f
@Service
public class AuthenticationUserDetailService implements UserDetailsService{

    private final UserService userService;

    public AuthenticationUserDetailService(UserService userService) {
        super();
        this.userService = userService;
    }

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userService.getByUsername(username);
        if(user.equals(null)){
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }
}
