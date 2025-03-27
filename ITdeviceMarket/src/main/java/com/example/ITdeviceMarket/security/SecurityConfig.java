package com.example.ITdeviceMarket.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import com.example.ITdeviceMarket.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//        @Bean
//        public UserDetailsService userDetailsService() {
//            return new UserService(); // Custom service for user authentication
//        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(); // Encrypt passwords for security
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(authorize -> {
                        authorize.requestMatchers("/register", "/login").permitAll() // Publicly accessible
                            .requestMatchers("/admin").hasAuthority("ADMIN") // Admin-only page
                            .requestMatchers("/order", "/receipt").hasAnyAuthority("USER", "ADMIN") // Protected pages
                            .requestMatchers("/error/**").permitAll()
                            .anyRequest().authenticated(); // All other requests require authentication
                    })
                    .formLogin(login -> login
                            .loginPage("/login") // Custom login page
                            .defaultSuccessUrl("/order", true) // Redirect after successful login
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout") // Redirect after logout
                            .permitAll()
                    );

            return http.build();
        }
}


