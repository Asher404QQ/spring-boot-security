package ru.kors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.kors.repository.UserRepository;
import ru.kors.security.services.InMemoryUserDetailsService;
import ru.kors.security.services.SecurityUser;

import javax.sql.DataSource;
import java.util.List;

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
    public UserDetailsService userDetailsService(DataSource dataSource) {
        String usersByUsernameQuery = """
                select us.username, us.password, us.enabled
                from spring.users us
                where us.username = ?;
                """;
        String authsByUserQuery = """
                select ats.username, ats.authority
                from spring.authorities ats
                where ats.username = ?
                """;

        var udm = new JdbcUserDetailsManager(dataSource);
        udm.setUsersByUsernameQuery(usersByUsernameQuery);
        udm.setAuthoritiesByUsernameQuery(authsByUserQuery);

        return udm;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

