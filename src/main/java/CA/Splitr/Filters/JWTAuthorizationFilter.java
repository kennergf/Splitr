package CA.Splitr.Filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import CA.Splitr.Configuration.AuthenticationConfigurationConstants;

// REF https://www.techgeeknext.com/spring/spring-boot-security-token-authentication-jwt
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(AuthenticationConfigurationConstants.HEADER_STRING);
        if (header == null || !header.startsWith(AuthenticationConfigurationConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    // REF https://stackoverflow.com/questions/44640260/auth0-jwt-with-java
    // REF https://www.baeldung.com/java-json-web-tokens-jjwt
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AuthenticationConfigurationConstants.HEADER_STRING);
        if (token != null) {
            // parse the token.
            DecodedJWT verify = JWT.require(Algorithm.HMAC512(AuthenticationConfigurationConstants.SECRET))
                    .build().verify(token.replace(AuthenticationConfigurationConstants.TOKEN_PREFIX, ""));

            String username = verify.getSubject();
            String role = verify.getClaim("role").asString();

            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, getAuthorities(role));
            }
            return null;
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }
}
