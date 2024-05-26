package ru.kors.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kors.models.MyUser;
import ru.kors.security.services.InMemoryUserDetailsService;
import ru.kors.security.services.SecurityUser;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put("sha256", new StandardPasswordEncoder("secret"));
        encoders.put("bcrypt", new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B,
                SecureRandom.getInstanceStrong()));
        encoders.put("scrypt", new SCryptPasswordEncoder(
                16384, 8, 1, 32, 64
        ));


        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
}

