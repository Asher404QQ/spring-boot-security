package ru.kors.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableAsync
public class ProjectConfig {

//    @Bean
//    public InitializingBean initializingBean() {
//        return () -> SecurityContextHolder.setStrategyName(
//                SecurityContextHolder.MODE_INHERITABLETHREADLOCAL
//        );
//    }

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
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

