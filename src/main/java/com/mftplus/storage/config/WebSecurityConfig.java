package com.mftplus.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        private DataSource dataSource;

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/user/edit").hasRole("USER1")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(
                        (logout) -> logout.permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager =
                new InMemoryUserDetailsManager();

        UserDetails user1 =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user123")
                        .roles("USER")
                        .build();

        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin123")
                        .roles("ADMIN")
                        .build();

        inMemoryUserDetailsManager.createUser(user1);
        inMemoryUserDetailsManager.createUser(user2);

        return inMemoryUserDetailsManager;
    }
}
