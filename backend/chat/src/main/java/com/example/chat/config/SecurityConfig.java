package com.example.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Configuration
 * Configures security policies for API endpoints
 * - Public endpoints: /api/health, /api/users (development)
 * - Protected endpoints: (to be implemented with auth)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF for API (stateless)
            .authorizeHttpRequests(authz -> authz
                // Public endpoints - no authentication required (without context-path prefix)
                .requestMatchers("/health").permitAll()
                .requestMatchers("/health/**").permitAll()
                .requestMatchers("/users").permitAll()  // Development: allow all users endpoints
                .requestMatchers("/users/**").permitAll()
                .requestMatchers("/ws").permitAll()  // WebSocket endpoint
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> {});  // Enable HTTP Basic Auth for testing
        
        return http.build();
    }
}
