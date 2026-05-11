package com.example.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS Configuration
 * All CORS settings are loaded from application.properties via AppConfig
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Autowired
    private AppConfig appConfig;
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowedOrigins = appConfig.getFrontend().getAllowedOrigins();
        
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
