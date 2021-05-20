package CA.Splitr.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import CA.Splitr.Filters.JWTAuthenticationFilter;
import CA.Splitr.Filters.JWTBasicAuthenticationFilter;
import CA.Splitr.Services.AuthenticationUserDetailService;

// REF https://javatodev.com/spring-boot-jwt-authentication/#d375a47b0c4f
// REF https://www.techgeeknext.com/spring/spring-boot-security-token-authentication-jwt
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailService authenticationUserDetailService;

    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder,
            AuthenticationUserDetailService authenticationUserDetailService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationUserDetailService = authenticationUserDetailService;
    }

    // Configure the HTTP Security, including CORS, API that can receive request without login first, filter to add JWT and session
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                // .and()
                .antMatchers(HttpMethod.POST, AuthenticationConfigurationConstants.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.POST, AuthenticationConfigurationConstants.LOG_IN_URL).permitAll()

                .anyRequest()
                // .authenticated()
                .permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTBasicAuthenticationFilter(authenticationManager()))
                // Disable Session creation for Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(authenticationUserDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

}
