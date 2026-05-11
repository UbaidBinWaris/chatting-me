package com.example.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.example.chat.websocket.ChatWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * WebSocket Configuration
 * Enables real-time bidirectional communication
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    @Autowired
    private AppConfig appConfig;
    
    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        if (appConfig.getWebsocket().isEnabled()) {
            String wsPath = appConfig.getWebsocket().getPath();
            String allowedOrigins = appConfig.getFrontend().getAllowedOrigins();
            
            registry.addHandler(chatWebSocketHandler, wsPath)
                    .setAllowedOrigins(allowedOrigins);
        }
    }
}
