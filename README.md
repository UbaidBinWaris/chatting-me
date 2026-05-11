# Chatting-Me Application

A full-stack chat application with:
- 🔧 **Backend**: Spring Boot REST API + WebSocket (Pure API, No UI)
- 🎨 **Frontend**: Next.js 16 with React 19 & Tailwind CSS

### Key Features
✅ REST API for CRUD operations  
✅ WebSocket for real-time messaging  
✅ PostgreSQL database  
✅ Centralized configuration management  
✅ CORS enabled for cross-origin requests  
✅ Fully independent frontend (Next.js)  
✅ No UI in backend (API-only)  

---

## 🚀 Quick Start

### Prerequisites
- Java 21+ (for backend)
- Node.js 18+ (for frontend)
- PostgreSQL 12+ (database)
- npm or yarn

### Installation & Setup

1. **Install dependencies for both projects:**
   ```bash
   chmod +x install.sh
   ./install.sh
   ```

2. **Start both servers:**
   ```bash
   chmod +x start.sh
   ./start.sh
   ```

---

## 📋 Project Structure

```
chatting-me/
├── backend/                    # Spring Boot Backend (API + WebSocket)
│   ├── chat/
│   │   ├── src/main/java/
│   │   │   └── com/example/chat/
│   │   │       ├── config/             # Centralized configuration
│   │   │       ├── controller/         # REST endpoints
│   │   │       ├── entity/             # JPA entities
│   │   │       ├── repository/         # Data access layer
│   │   │       ├── service/            # Business logic
│   │   │       └── websocket/          # WebSocket handlers
│   │   ├── src/main/resources/
│   │   │   └── application.properties  # All configuration
│   │   └── build.gradle
│   ├── API.md                  # API documentation
│   ├── BACKEND.md              # Backend overview
│   └── CONFIGURATION.md        # Configuration guide
├── frontend/                   # Next.js Frontend
│   ├── app/                    # App Router pages
│   ├── public/                 # Static files
│   ├── package.json
│   └── .env.local              # Frontend environment
├── install.sh                  # Install dependencies
├── start.sh                    # Start both servers
├── BACKEND_CLEANUP.md          # Backend refactoring details
└── README.md
```

---

## 🌐 Services & Ports

| Service | URL | Port | Type |
|---------|-----|------|------|
| **Frontend** | http://localhost:8090 | 8090 | Next.js Web App |
| **Backend API** | http://localhost:8070/api | 8070 | REST API |
| **WebSocket** | ws://localhost:8070/ws | 8070 | Real-time Messaging |
| **Database** | localhost | 5432 | PostgreSQL |

---

## 🔧 Configuration

### Backend Configuration
All backend configuration is centralized in:
```
backend/chat/src/main/resources/application.properties
```

**Key Properties:**
```properties
# Server
server.port=8070
server.servlet.context-path=/api

# Frontend (CORS)
app.frontend.url=http://localhost:8090
app.frontend.allowed-origins=http://localhost:8090

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/chat_db_new
spring.datasource.username=chat_user_new
spring.datasource.password=chatdb@123@321

# WebSocket
app.websocket.enabled=true
app.websocket.path=/ws

# Application Limits
app.max-message-length=5000
app.max-users=10000
app.session-timeout=1800
```

**No hardcoded values** - Everything loaded via `AppConfig` class.

### Frontend Configuration
Environment file: `frontend/.env.local`
```env
NEXT_PUBLIC_BACKEND_URL=http://localhost:8070
```

---

## 🔌 API Reference

### REST API Base URL
```
http://localhost:8070/api
```

### Users Endpoints

**Get All Users**
```bash
GET /api/users
```

**Create User**
```bash
POST /api/users
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123"
}
```

**Get User by ID**
```bash
GET /api/users/{id}
```

**Update User**
```bash
PUT /api/users/{id}
Content-Type: application/json

{
  "username": "updated_name",
  "email": "new@example.com",
  "password": "newpass"
}
```

**Delete User**
```bash
DELETE /api/users/{id}
```

### Health Endpoints

**Health Check**
```bash
GET /api/health
```

**Configuration**
```bash
GET /api/health/config
```

### WebSocket

**Connect**
```javascript
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

### PostgreSQL Connection
```
Host: localhost
Port: 5432
Database: chat_db_new
User: chat_user_new
Password: chatdb@123@321
```

### Tables
- **users** - Stores user information
  - id (Primary Key)
  - username (Unique)
  - email
  - password
  - created_at
  - updated_at

---

## 📚 Detailed Documentation

| Document | Purpose |
|----------|---------|
| [API.md](backend/API.md) | Complete REST API & WebSocket documentation |
| [CONFIGURATION.md](backend/CONFIGURATION.md) | How to manage centralized configuration |
| [BACKEND.md](backend/BACKEND.md) | Backend architecture and overview |
| [BACKEND_CLEANUP.md](BACKEND_CLEANUP.md) | Backend refactoring details and changes |

---

## 🏗️ Architecture

### Backend Architecture
```
REST API (/api/users)        WebSocket (/ws)
    ↓                              ↓
Controllers              ChatWebSocketHandler
    ↓                              ↓
Services                   Broadcast Messages
    ↓                              ↓
Repositories                Connected Clients
    ↓
PostgreSQL Database
```

### Configuration Flow
```
application.properties
        ↓
    AppConfig.java (Spring loads via @ConfigurationProperties)
        ↓
    Injected into Controllers, Services, Config Classes
        ↓
    All values centralized, no hardcoding
```

---

## 🚀 Running the Project

### Option 1: Using Scripts (Recommended)

**First time:**
```bash
chmod +x install.sh start.sh
./install.sh
./start.sh
```

### Option 2: Manual Start

**Terminal 1 - Backend:**
```bash
cd backend/chat
./gradlew bootRun
```

**Terminal 2 - Frontend:**
```bash
cd frontend
npm run dev
```

---

## 🔐 Security Features

✅ **CORS Enabled** - Configured for frontend origin  
✅ **Database Credentials** - PostgreSQL user with limited permissions  
✅ **Configuration Management** - Secrets in properties, not in code  
✅ **No UI Exposure** - Backend is API-only  

---

## 🧪 Testing

### Test Backend
```bash
# Health check
curl http://localhost:8070/api/health

# Configuration
curl http://localhost:8070/api/health/config

# Create user
curl -X POST http://localhost:8070/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@test.com","password":"test123"}'
```

### Test WebSocket
```javascript
// Open browser console and paste:
const ws = new WebSocket('ws://localhost:8070/ws');
ws.onopen = () => console.log('Connected');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.send(JSON.stringify({from: 'me', text: 'Hello!'}));
```

---

## 📦 Technology Stack

### Backend
- **Framework**: Spring Boot 4.0.6
- **Language**: Java 21+
- **Database**: PostgreSQL 12+
- **Build Tool**: Gradle
- **ORM**: Spring Data JPA + Hibernate
- **Real-time**: WebSocket
- **Configuration**: @ConfigurationProperties

### Frontend
- **Framework**: Next.js 16.2.6
- **UI Library**: React 19.2.4
- **Styling**: Tailwind CSS 4
- **Language**: TypeScript 5
- **Build Tool**: npm/yarn

---

## 🛠️ Development Workflow

### Adding a New Endpoint

1. **Create Entity** (if needed)
   ```java
   @Entity
   public class Message { ... }
   ```

2. **Create Repository**
   ```java
   @Repository
   public interface MessageRepository extends JpaRepository<Message, Long> { ... }
   ```

3. **Create Service**
   ```java
   @Service
   public class MessageService { ... }
   ```

4. **Create Controller**
   ```java
   @RestController
   @RequestMapping("/messages")
   public class MessageController { ... }
   ```

5. **Update Configuration** (if needed)
   - Add constants to `application.properties`
   - Update `AppConfig.java` if new config sections needed

---

## 🔄 Environment-Specific Configuration

### Development (application.properties)
```properties
server.port=8070
spring.jpa.show-sql=true
app.frontend.allowed-origins=http://localhost:8090
```

### Production (application-prod.properties)
```properties
server.port=8080
spring.jpa.show-sql=false
app.frontend.allowed-origins=https://yourdomain.com
```

**Run with profile:**
```bash
java -jar app.jar --spring.profiles.active=prod
```

---

## 📈 Scalability

### Current Limits (Configurable)
- **Max Message Length**: 5000 characters
- **Max Users**: 10000
- **Session Timeout**: 1800 seconds
- **WebSocket**: Enabled by default

**Change limits in `application.properties`:**
```properties
app.max-message-length=10000
app.max-users=50000
app.session-timeout=3600
```

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| **Port 8070 in use** | Change `server.port` in properties |
| **Port 8090 in use** | Change port in `frontend/package.json` |
| **Database connection error** | Verify PostgreSQL is running and credentials are correct |
| **CORS error** | Check `app.frontend.allowed-origins` matches frontend URL |
| **WebSocket connection failed** | Ensure `/ws` endpoint is accessible and browser supports WebSocket |
| **Tables not created** | Verify `spring.jpa.hibernate.ddl-auto=update` |

---

## 📋 Checklist for Deployment

- [ ] Update `app.frontend.url` for production domain
- [ ] Update `app.frontend.allowed-origins` for production frontend URL
- [ ] Configure production database credentials
- [ ] Set `spring.jpa.show-sql=false` for performance
- [ ] Enable HTTPS for WebSocket (WSS)
- [ ] Set up environment-specific `application-prod.properties`
- [ ] Configure security headers and authentication
- [ ] Set up logging and monitoring
- [ ] Test all API endpoints
- [ ] Test WebSocket connections

---

## 🤝 Contributing

When contributing, ensure:
1. **No hardcoded values** - Use `AppConfig` and `application.properties`
2. **Follow API patterns** - REST endpoints follow the User API pattern
3. **Update documentation** - Document new endpoints and configuration
4. **Keep backend API-only** - No UI components in backend
5. **Test CORS** - Ensure frontend can reach new endpoints

---

## 📄 License

All rights reserved © 2026

---

## 👤 Author

UbaidBinWaris

---

## 🔗 Quick Links

- **Backend API**: http://localhost:8070/api
- **Frontend App**: http://localhost:8090
- **WebSocket**: ws://localhost:8070/ws
- **Health Check**: http://localhost:8070/api/health
- **Config Endpoint**: http://localhost:8070/api/health/config

---

## ✨ Recent Changes

### Backend Refactoring
- ✅ Removed all UI components from backend
- ✅ Centralized configuration in `AppConfig.java`
- ✅ Added global CORS configuration
- ✅ Implemented WebSocket support
- ✅ Created health check endpoints
- ✅ Updated all documentation

See [BACKEND_CLEANUP.md](BACKEND_CLEANUP.md) for details.
