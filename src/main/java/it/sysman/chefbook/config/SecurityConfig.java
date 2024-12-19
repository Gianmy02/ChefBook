package it.sysman.chefbook.config;

import it.sysman.chefbook.filters.JWTTokenGeneratorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests.requestMatchers("ricette/**", "user/signin").authenticated()
                .requestMatchers("autori/{id}", HttpMethod.DELETE.name()).hasRole("ADMIN")
                .anyRequest()
                .permitAll());
        http.formLogin();
        http.httpBasic();

        http.csrf(csrf -> csrf.disable())
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder defaultPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
