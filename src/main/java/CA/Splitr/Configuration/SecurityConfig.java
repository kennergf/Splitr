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
import CA.Splitr.Filters.JWTAuthorizationFilter;
import CA.Splitr.Services.AuthenticationUserDetailService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailService authenticationUserDetailService;

    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder,
            AuthenticationUserDetailService authenticationUserDetailService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationUserDetailService = authenticationUserDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                // .and()
                .antMatchers(HttpMethod.GET, "/user/*").permitAll().antMatchers(HttpMethod.POST, "/user/*").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()

                .anyRequest()
                // .authenticated()
                .permitAll().and().addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // Disable Session creation for Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(authenticationUserDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

}
