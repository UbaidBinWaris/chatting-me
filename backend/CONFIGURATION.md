# API Configuration & Constants Management

This document explains how all configuration and constants are centralized in the backend.

---

## 📋 Centralized Configuration

All application configuration is stored in **`application.properties`** and automatically loaded into the application via the **`AppConfig`** class.

### Why Centralize Configuration?

✅ **Single Source of Truth** - Change values once, applied everywhere  
✅ **Environment-Specific** - Different configs for dev/prod  
✅ **No Hardcoded Values** - Secure credentials management  
✅ **Easy Maintenance** - All constants in one place  

---

## 🔧 Configuration Classes

### 1. **AppConfig.java** (Core Configuration)
```
src/main/java/com/example/chat/config/AppConfig.java
```

**Purpose:** Load all properties from `application.properties` into Java objects  
**Classes:**
- `AppConfig` - Main configuration holder
- `Frontend` - Frontend URLs and CORS settings
- `Api` - API versioning and paths
- `Limits` - Application limits (max users, message length, etc.)
- `Websocket` - WebSocket configuration

**Usage in Controllers:**
```java
@Autowired
private AppConfig appConfig;

// Access configuration values
String frontendUrl = appConfig.getFrontend().getUrl();
int maxUsers = appConfig.getLimits().getMaxUsers();
boolean wsEnabled = appConfig.getWebsocket().isEnabled();
```

---

### 2. **CorsConfig.java** (CORS Configuration)
```
src/main/java/com/example/chat/config/CorsConfig.java
```

**Purpose:** Apply CORS settings globally using `AppConfig` values  
**Configuration:**
- Allowed origins: Loaded from `app.frontend.allowed-origins`
- Allowed methods: GET, POST, PUT, DELETE, OPTIONS
- Allowed headers: All
- Credentials: Allowed
- Max age: 3600 seconds

**Why centralized?**
- No @CrossOrigin annotations needed on controllers
- Changes to `application.properties` automatically apply
- Consistent CORS policy across all endpoints

---

### 3. **WebSocketConfig.java** (WebSocket Configuration)
```
src/main/java/com/example/chat/config/WebSocketConfig.java
```

**Purpose:** Configure WebSocket endpoints using `AppConfig` values  
**Configuration:**
- WebSocket path: Loaded from `app.websocket.path`
- Allowed origins: Loaded from `app.frontend.allowed-origins`
- Enable/disable: Controlled by `app.websocket.enabled`

---

## 📝 Application Properties

File: `src/main/resources/application.properties`

```properties
# ====== SERVER CONFIGURATION ======
server.port=8070                          # Server port
server.servlet.context-path=/api          # API context path

# ====== FRONTEND CONFIGURATION ======
app.frontend.url=http://localhost:8090    # Frontend URL
app.frontend.allowed-origins=http://localhost:8090  # CORS origins

# ====== API CONFIGURATION ======
app.api.version=v1                        # API version
app.api.base-path=/api                    # Base API path

# ====== APPLICATION CONSTANTS ======
app.max-message-length=5000               # Max message length
app.max-users=10000                       # Max connected users
app.session-timeout=1800                  # Session timeout (seconds)

# ====== WEBSOCKET CONFIGURATION ======
app.websocket.enabled=true                # Enable WebSocket
app.websocket.path=/ws                    # WebSocket endpoint path

# ====== DATABASE CONFIGURATION ======
spring.datasource.url=jdbc:postgresql://...
spring.datasource.username=chat_user_new
spring.datasource.password=chatdb@123@321

# ====== JPA/HIBERNATE CONFIGURATION ======
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
...
```

---

## 🔄 How It Works

```
1. Application Starts
   ↓
2. Spring Boot loads application.properties
   ↓
3. @ConfigurationProperties annotation on AppConfig
   ↓
4. Properties automatically mapped to AppConfig class
   ↓
5. @Autowired AppConfig injected into beans
   ↓
6. Controllers/Config classes use AppConfig.getXxx()
   ↓
7. All values loaded from properties, no hardcoding
```

---

## 📍 Using Configuration in Your Code

### In Controllers
```java
@RestController
public class MyController {
    
    @Autowired
    private AppConfig appConfig;
    
    @GetMapping("/example")
    public ResponseEntity<?> example() {
        // Use configuration values
        int limit = appConfig.getLimits().getMaxMessageLength();
        String wsPath = appConfig.getWebsocket().getPath();
        
        return ResponseEntity.ok(new Object());
    }
}
```

### In Services
```java
@Service
public class MyService {
    
    @Autowired
    private AppConfig appConfig;
    
    public void processMessage(String message) {
        int maxLength = appConfig.getLimits().getMaxMessageLength();
        
        if (message.length() > maxLength) {
            throw new IllegalArgumentException("Message too long");
        }
    }
}
```

### In Configuration Classes
```java
@Configuration
public class MyConfig {
    
    @Autowired
    private AppConfig appConfig;
    
    @Bean
    public SomeBean someBean() {
        String frontendUrl = appConfig.getFrontend().getUrl();
        // Use in configuration
        return new SomeBean(frontendUrl);
    }
}
```

---

## 🔐 Environment-Specific Configurations

### Development Environment
```properties
app.frontend.url=http://localhost:8090
server.port=8070
spring.jpa.show-sql=true
```

### Production Environment
```properties
app.frontend.url=https://yourdomain.com
server.port=8080
spring.jpa.show-sql=false
```

### Use Different Properties Files
```
src/main/resources/
├── application.properties          # Default
├── application-dev.properties      # Development
├── application-prod.properties     # Production
```

**Run with specific profile:**
```bash
java -jar app.jar --spring.profiles.active=prod
```

---

## ✅ Benefits Summary

| Benefit | How It Works |
|---------|-------------|
| **Single Source** | All config in application.properties |
| **Type-Safe** | AppConfig class provides typed access |
| **Flexible** | Change values without recompiling |
| **Secure** | Credentials not in code |
| **Testable** | Mock AppConfig in tests |
| **Documented** | Comments in properties file |
| **Scalable** | Easy to add new configuration |

---

## 🔍 Health Check Endpoints

Check current configuration:

```bash
# Basic health check
curl http://localhost:8070/api/health

# Full configuration dump
curl http://localhost:8070/api/health/config
```

---

## 📚 Related Files

- [`AppConfig.java`](src/main/java/com/example/chat/config/AppConfig.java) - Configuration classes
- [`CorsConfig.java`](src/main/java/com/example/chat/config/CorsConfig.java) - CORS settings
- [`WebSocketConfig.java`](src/main/java/com/example/chat/config/WebSocketConfig.java) - WebSocket settings
- [`application.properties`](src/main/resources/application.properties) - Configuration values
