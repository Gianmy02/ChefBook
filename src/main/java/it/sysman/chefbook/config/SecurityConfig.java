package it.sysman.chefbook.config;

import it.sysman.chefbook.filters.JWTTokenGeneratorFilter;
import it.sysman.chefbook.filters.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).cors(cors -> cors.disable());
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("autori/signup", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers("autori/{id}", HttpMethod.DELETE.name()).hasRole("ADMIN")
                .anyRequest().authenticated());
        http.formLogin(login -> login.disable());
        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable())
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder defaultPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
