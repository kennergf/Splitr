package CA.Splitr.jwt;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
                .oauth2ResourceServer()
                    .jwt();
    }

    // @Bean
    // JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties){
    //     nimbusJwtDecoder jwtDecoder = NimbusJwtDecoder;
    // }
}
