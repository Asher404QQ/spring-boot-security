package ru.kors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kors.models.MyUser;
import ru.kors.security.services.InMemoryUserDetailsService;
import ru.kors.security.services.SecurityUser;
import ru.kors.security.services.Sha512PasswordEncoder;

import java.util.List;

@Configuration
public class ProjectConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        auth -> auth.anyRequest().authenticated()
                ).formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var user = new MyUser(1L, "anton",
                passwordEncoder.encode("password"), "USER");

        return new InMemoryUserDetailsService(List.of(
                new SecurityUser(user)
        ));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder spe = new StandardPasswordEncoder("secret");
        PasswordEncoder pbkdf2 = new Pbkdf2PasswordEncoder("secret", 185000, 1, 256);
        return new BCryptPasswordEncoder(1);
    }
}

