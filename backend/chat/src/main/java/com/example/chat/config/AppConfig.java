package com.example.chat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Centralized Application Configuration
 * All configuration values are loaded from application.properties
 * This eliminates hardcoded values throughout the application
 */
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    
    private Frontend frontend;
    private Api api;
    private Limits limits;
    private Websocket websocket;
    
    // ====== FRONTEND CONFIGURATION ======
    public static class Frontend {
        private String url;
        private String allowedOrigins;
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }
        
        public String getAllowedOrigins() {
            return allowedOrigins;
        }
        
        public void setAllowedOrigins(String allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }
    }
    
    // ====== API CONFIGURATION ======
    public static class Api {
        private String version;
        private String basePath;
        
        public String getVersion() {
            return version;
        }
        
        public void setVersion(String version) {
            this.version = version;
        }
        
        public String getBasePath() {
            return basePath;
        }
        
        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }
    }
    
    // ====== APPLICATION LIMITS ======
    public static class Limits {
        private int maxMessageLength;
        private int maxUsers;
        private int sessionTimeout;
        
        public int getMaxMessageLength() {
            return maxMessageLength;
        }
        
        public void setMaxMessageLength(int maxMessageLength) {
            this.maxMessageLength = maxMessageLength;
        }
        
        public int getMaxUsers() {
            return maxUsers;
        }
        
        public void setMaxUsers(int maxUsers) {
            this.maxUsers = maxUsers;
        }
        
        public int getSessionTimeout() {
            return sessionTimeout;
        }
        
        public void setSessionTimeout(int sessionTimeout) {
            this.sessionTimeout = sessionTimeout;
        }
    }
    
    // ====== WEBSOCKET CONFIGURATION ======
    public static class Websocket {
        private boolean enabled;
        private String path;
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public String getPath() {
            return path;
        }
        
        public void setPath(String path) {
            this.path = path;
        }
    }
    
    // ====== GETTERS & SETTERS ======
    public Frontend getFrontend() {
        if (frontend == null) {
            frontend = new Frontend();
        }
        return frontend;
    }
    
    public void setFrontend(Frontend frontend) {
        this.frontend = frontend;
    }
    
    public Api getApi() {
        if (api == null) {
            api = new Api();
        }
        return api;
    }
    
    public void setApi(Api api) {
        this.api = api;
    }
    
    public Limits getLimits() {
        if (limits == null) {
            limits = new Limits();
        }
        return limits;
    }
    
    public void setLimits(Limits limits) {
        this.limits = limits;
    }
    
    public Websocket getWebsocket() {
        if (websocket == null) {
            websocket = new Websocket();
        }
        return websocket;
    }
    
    public void setWebsocket(Websocket websocket) {
        this.websocket = websocket;
    }
}
