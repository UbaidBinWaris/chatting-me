# Chat Application Backend - API & WebSocket Server

A pure **REST API + WebSocket** backend built with Spring Boot. No frontend included - this is an independent service for the Next.js frontend.

---

## 🎯 Architecture

### What This Backend Provides

✅ **REST API** - CRUD operations for chat entities  
✅ **WebSocket** - Real-time bidirectional communication  
✅ **PostgreSQL** - Persistent data storage  
✅ **Centralized Config** - All constants from `application.properties`  
✅ **CORS Enabled** - Configured for Next.js frontend  
❌ **No UI** - API-only, frontend is separate  
❌ **No Static Files** - No HTML/CSS/JS serving  

### Layer Structure

```
UserController (/api/users)
        ↓
UserService (Business Logic)
        ↓
UserRepository (Data Access)
        ↓
User Entity → PostgreSQL Database

WebSocketConfig
        ↓
ChatWebSocketHandler (/ws)
        ↓
Real-time Message Broadcasting
```

---

## 🔌 API Endpoints

### REST API Base URL
```
http://localhost:8070/api
```

### Users Endpoints

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| **GET** | `/api/users` | Get all users | `200 OK` - Array of users |
| **GET** | `/api/users/{id}` | Get user by ID | `200 OK` or `404 Not Found` |
| **GET** | `/api/users/username/{username}` | Get user by username | `200 OK` or `404 Not Found` |
| **POST** | `/api/users` | Create new user | `201 Created` - New user object |
| **PUT** | `/api/users/{id}` | Update user | `200 OK` or `404 Not Found` |
| **DELETE** | `/api/users/{id}` | Delete user | `204 No Content` or `404 Not Found` |

### Health Check Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| **GET** | `/api/health` | Health status + config summary |
| **GET** | `/api/health/config` | Full configuration dump (debug) |

---

## 🔗 WebSocket Endpoint

### Connection URL
```
ws://localhost:8070/ws
```

### How It Works

1. **Client connects** → WebSocket endpoint `/ws`
2. **Client sends message** → Text message in JSON format
3. **Server broadcasts** → Message sent to all connected clients
4. **Connection closes** → Client removed from active sessions

### Example WebSocket Communication

**Client connects:**
```javascript
// Frontend (Next.js)
const ws = new WebSocket('ws://localhost:8070/ws');

ws.onopen = () => {
    console.log('Connected to WebSocket');
};

ws.onmessage = (event) => {
    const message = JSON.parse(event.data);
    console.log('Received:', message);
};

// Send message
ws.send(JSON.stringify({
    from: 'user123',
    text: 'Hello everyone!',
    timestamp: Date.now()
}));
```

---

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

### Entity Mapping
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
```

---

## 🔧 Configuration Management

### All Configuration in One Place
```
src/main/resources/application.properties
```

**Key Properties:**
```properties
# Server
server.port=8070
server.servlet.context-path=/api

# Frontend URLs (for CORS)
app.frontend.url=http://localhost:8090
app.frontend.allowed-origins=http://localhost:8090

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/chat_db_new
spring.datasource.username=chat_user_new
spring.datasource.password=chatdb@123@321

# WebSocket
app.websocket.enabled=true
app.websocket.path=/ws

# Limits
app.max-message-length=5000
app.max-users=10000
app.session-timeout=1800
```

**No hardcoded values** - Everything loaded from properties via `AppConfig` class.

See [CONFIGURATION.md](CONFIGURATION.md) for detailed configuration guide.

---

## 📚 API Documentation

### Create User

**Request:**
```bash
curl -X POST http://localhost:8070/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "secure_password"
  }'
```

**Response (201 Created):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "password": "secure_password",
  "createdAt": "2026-05-12T10:30:00",
  "updatedAt": "2026-05-12T10:30:00"
}
```

### Get All Users

**Request:**
```bash
curl http://localhost:8070/api/users
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "password": "...",
    "createdAt": "2026-05-12T10:30:00",
    "updatedAt": "2026-05-12T10:30:00"
  }
]
```

### Get User by ID

**Request:**
```bash
curl http://localhost:8070/api/users/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "password": "...",
  "createdAt": "2026-05-12T10:30:00",
  "updatedAt": "2026-05-12T10:30:00"
}
```

### Update User

**Request:**
```bash
curl -X PUT http://localhost:8070/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_updated",
    "email": "john.new@example.com",
    "password": "new_password"
  }'
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "john_updated",
  "email": "john.new@example.com",
  "password": "new_password",
  "createdAt": "2026-05-12T10:30:00",
  "updatedAt": "2026-05-12T11:00:00"
}
```

### Delete User

**Request:**
```bash
curl -X DELETE http://localhost:8070/api/users/1
```

**Response (204 No Content):**
```
(empty response)
```

---

## 🚀 Running the Backend

### Development Mode
```bash
cd backend/chat
./gradlew bootRun
```
- Server runs on: **http://localhost:8070**
- API available at: **http://localhost:8070/api**
- WebSocket at: **ws://localhost:8070/ws**

### Production Build & Run
```bash
cd backend/chat
./gradlew build
java -jar build/libs/chat-*.jar
```

### Using Startup Scripts
```bash
# From root directory
./install.sh     # Install dependencies
./start.sh       # Start both backend and frontend
```

---

## 📁 Project Structure

```
backend/chat/
├── src/main/java/com/example/chat/
│   ├── ChatApplication.java           # Main entry point
│   ├── config/
│   │   ├── AppConfig.java             # Configuration loader
│   │   ├── CorsConfig.java            # CORS configuration
│   │   └── WebSocketConfig.java       # WebSocket configuration
│   ├── controller/
│   │   ├── UserController.java        # User REST endpoints
│   │   └── HealthController.java      # Health & config endpoints
│   ├── entity/
│   │   └── User.java                  # User JPA entity
│   ├── repository/
│   │   └── UserRepository.java        # User data access
│   ├── service/
│   │   └── UserService.java           # User business logic
│   └── websocket/
│       └── ChatWebSocketHandler.java  # WebSocket handler
├── resources/
│   ├── application.properties         # Configuration
│   └── application.properties.example # Template
├── build.gradle                       # Dependencies
└── gradlew                           # Gradle wrapper
```

---

## 🔒 CORS Configuration

**Configured Origins:** `http://localhost:8090` (Next.js frontend)

**Allowed Methods:** GET, POST, PUT, DELETE, OPTIONS  
**Allowed Headers:** All  
**Credentials:** Allowed  
**Max Age:** 3600 seconds  

**Configuration File:** [CorsConfig.java](src/main/java/com/example/chat/config/CorsConfig.java)

---

## ✅ Next Steps

- [ ] Implement Message entity and endpoints
- [ ] Add Chat/Room entities for group messaging
- [ ] Implement authentication/authorization
- [ ] Add message validation and sanitization
- [ ] Enhance WebSocket with room-based messaging
- [ ] Create API documentation (Swagger/OpenAPI)
- [ ] Add unit tests and integration tests
- [ ] Deploy to production server

---

## 🛠️ Dependencies

| Dependency | Version | Purpose |
|-----------|---------|---------|
| Spring Boot | 4.0.6 | Framework |
| Spring Data JPA | Latest | ORM & Database |
| Spring WebSocket | Latest | Real-time communication |
| PostgreSQL Driver | Latest | Database driver |
| Lombok | Latest | Reduce boilerplate |

---

## 🔍 Troubleshooting

| Issue | Solution |
|-------|----------|
| **Connection Refused (8070)** | Backend not running, run `./gradlew bootRun` |
| **Database Connection Error** | Verify PostgreSQL is running: `psql -U postgres` |
| **CORS Error** | Check `app.frontend.allowed-origins` in properties |
| **WebSocket Connection Failed** | Verify `/ws` endpoint and CORS configuration |
| **Port Already in Use** | Change `server.port` in application.properties |
| **Tables Not Created** | Check `spring.jpa.hibernate.ddl-auto=update` is set |

---

## 📖 Related Documentation

- [CONFIGURATION.md](CONFIGURATION.md) - Centralized configuration guide
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [WebSocket Documentation](https://www.rfc-editor.org/rfc/rfc6455)
- [PostgreSQL](https://www.postgresql.org/)
