package CA.Splitr.Filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import CA.Splitr.Configuration.AuthenticationConfigurationConstants;
import CA.Splitr.Models.User;

// REF https://javatodev.com/spring-boot-jwt-authentication/#d375a47b0c4f
// REF https://www.techgeeknext.com/spring/spring-boot-security-token-authentication-jwt
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // REF https://www.baeldung.com/java-json-web-tokens-jjwt
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),
                    credentials.getPassword(), new ArrayList<>()));

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // REF https://stackoverflow.com/questions/44640260/auth0-jwt-with-java
    // REF https://www.baeldung.com/java-json-web-tokens-jjwt
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
                        .getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + AuthenticationConfigurationConstants.DURATION))
                .sign(Algorithm.HMAC512(AuthenticationConfigurationConstants.SECRET));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"" + AuthenticationConfigurationConstants.HEADER_STRING + "\":\""
                + AuthenticationConfigurationConstants.TOKEN_PREFIX + token + "\"}");

        response.addHeader(AuthenticationConfigurationConstants.HEADER_STRING,
                AuthenticationConfigurationConstants.TOKEN_PREFIX + token);
    }
}
