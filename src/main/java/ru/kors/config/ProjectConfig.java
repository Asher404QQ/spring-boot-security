package ru.kors.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kors.models.MyUser;
import ru.kors.security.services.InMemoryUserDetailsService;
import ru.kors.security.services.SecurityUser;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Configuration
public class ProjectConfig{
    private final Logger logger = LoggerFactory.getLogger(ProjectConfig.class);

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
                passwordEncoder.encode("password")
                , "USER");

        logger.info("User encoded password: {}", user.getPassword());

        return new InMemoryUserDetailsService(List.of(
                new SecurityUser(user)
        ));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

