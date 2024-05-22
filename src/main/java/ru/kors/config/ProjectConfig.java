package ru.kors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.kors.repository.UserRepository;
import ru.kors.security.services.SimpleUser;

@Configuration
public class ProjectConfig{
    private final UserRepository userRepository;

    public ProjectConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        auth -> auth.anyRequest().authenticated()
                ).formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var userDetailsService = new InMemoryUserDetailsManager();

        var user1 = userRepository.findByUsername("user");
        var user2 = userRepository.findByUsername("admin");
        var user3 = userRepository.findByUsername("disabled");

        userDetailsService.createUser(new SimpleUser(user1));
        userDetailsService.createUser(new SimpleUser(user2));
        userDetailsService.createUser(new SimpleUser(user3));

        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

