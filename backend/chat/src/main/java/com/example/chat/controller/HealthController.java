package com.example.chat.controller;

import com.example.chat.config.AppConfig;
import com.example.chat.websocket.ChatWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * Health Check and Configuration API
 * Provides endpoints to verify the API is running and check configuration
 */
@RestController
@RequestMapping("/health")
public class HealthController {
    
    @Autowired
    private AppConfig appConfig;
    
    /**
     * GET /api/health - Basic health check
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("api", appConfig.getApi().getVersion());
        response.put("timestamp", System.currentTimeMillis());
        response.put("websocket_enabled", appConfig.getWebsocket().isEnabled());
        response.put("websocket_path", appConfig.getWebsocket().getPath());
        response.put("connected_clients", ChatWebSocketHandler.getConnectedClientCount());
        response.put("frontend_url", appConfig.getFrontend().getUrl());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/health/config - Get all configuration (for debugging)
     */
    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> getConfig() {
        Map<String, Object> response = new HashMap<>();
        
        // API Configuration
        Map<String, Object> apiConfig = new HashMap<>();
        apiConfig.put("version", appConfig.getApi().getVersion());
        apiConfig.put("base_path", appConfig.getApi().getBasePath());
        
        // Frontend Configuration
        Map<String, Object> frontendConfig = new HashMap<>();
        frontendConfig.put("url", appConfig.getFrontend().getUrl());
        frontendConfig.put("allowed_origins", appConfig.getFrontend().getAllowedOrigins());
        
        // Application Limits
        Map<String, Object> limitsConfig = new HashMap<>();
        limitsConfig.put("max_message_length", appConfig.getLimits().getMaxMessageLength());
        limitsConfig.put("max_users", appConfig.getLimits().getMaxUsers());
        limitsConfig.put("session_timeout", appConfig.getLimits().getSessionTimeout());
        
        // WebSocket Configuration
        Map<String, Object> wsConfig = new HashMap<>();
        wsConfig.put("enabled", appConfig.getWebsocket().isEnabled());
        wsConfig.put("path", appConfig.getWebsocket().getPath());
        
        response.put("api", apiConfig);
        response.put("frontend", frontendConfig);
        response.put("limits", limitsConfig);
        response.put("websocket", wsConfig);
        
        return ResponseEntity.ok(response);
    }
}
