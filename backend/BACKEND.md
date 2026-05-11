# Backend - Chat Application API

Spring Boot REST API backend for the chat application with PostgreSQL database integration.

---

## 🏗️ Architecture

```
backend/
├── src/main/java/com/example/chat/
│   ├── ChatApplication.java       # Main Spring Boot entry point
│   ├── entity/                    # JPA entities (database models)
│   │   └── User.java
│   ├── repository/                # Data access layer (JPA repositories)
│   │   └── UserRepository.java
│   ├── service/                   # Business logic layer
│   │   └── UserService.java
│   ├── controller/                # REST API endpoints
│   │   └── UserController.java
│   └── dto/                       # Data Transfer Objects (optional)
├── resources/
│   └── application.properties     # Configuration
└── build.gradle
```

---

## 🗄️ Database Configuration

### PostgreSQL Connection Details
```properties
Database:
User: 
Password: 
Host: localhost
Port: 5432
```

### Connection Properties (application.properties)
```properties
spring.datasource.url=jdbc:
spring.datasource.username=
spring.datasource.password=
```

---

## 🔄 Database Initialization

When you start the application, Hibernate will automatically:
1. **Create tables** based on entity annotations
2. **Update existing schema** if entities change (ddl-auto=update)
3. **Log all queries** if show-sql=true (currently disabled for production)

### Current Configuration
```properties
spring.jpa.hibernate.ddl-auto=update  # Auto-update database schema
spring.jpa.show-sql=false              # Don't log SQL queries
```

---

## 📊 Database Entities

### User Entity
Stores user information in the `users` table.

**Columns:**
- `id` - Primary key (auto-increment)
- `username` - Unique username
- `email` - User email
- `password` - Hashed password
- `created_at` - Timestamp of creation
- `updated_at` - Timestamp of last update

**Example:**
```java
User user = new User();
user.setUsername("john_doe");
user.setEmail("john@example.com");
user.setPassword("hashed_password");
```

---

## 🌐 REST API Endpoints

### Users API (`/api/users`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/username/{username}` | Get user by username |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Example Requests

**Create User:**
```bash
curl -X POST http://localhost:8070/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "secure_password"
  }'
```

**Get All Users:**
```bash
curl http://localhost:8070/api/users
```

**Get User by Username:**
```bash
curl http://localhost:8070/api/users/username/john_doe
```

**Update User:**
```bash
curl -X PUT http://localhost:8070/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_updated",
    "email": "john.new@example.com",
    "password": "new_password"
  }'
```

**Delete User:**
```bash
curl -X DELETE http://localhost:8070/api/users/1
```

---

## 🚀 Running the Backend

### Development Mode
```bash
./gradlew bootRun
```
Server runs on: **http://localhost:8070**

### Production Build & Run
```bash
./gradlew build
java -jar build/libs/chat-*.jar
```

### Clean & Build
```bash
./gradlew clean build
```

---

## 📚 Project Structure Details

### Layers

**1. Controller Layer** (`UserController.java`)
- Handles HTTP requests/responses
- Maps URLs to service methods
- Includes CORS configuration
- Validates input and returns proper HTTP status codes

**2. Service Layer** (`UserService.java`)
- Contains business logic
- Handles data validation
- Coordinates between controller and repository
- Ensures consistent operations

**3. Repository Layer** (`UserRepository.java`)
- Extends `JpaRepository` for CRUD operations
- Custom query methods (findByUsername, findByEmail, etc.)
- Automatically generates SQL queries

**4. Entity Layer** (`User.java`)
- JPA annotated POJO
- Maps to database table `users`
- Includes lifecycle callbacks (@PrePersist, @PreUpdate)

---

## 🔧 Dependencies

| Dependency | Purpose |
|------------|---------|
| `spring-boot-starter-data-jpa` | ORM and database abstraction |
| `spring-boot-starter-webmvc` | REST controller support |
| `spring-boot-starter-security` | Authentication/Authorization |
| `postgresql` | PostgreSQL JDBC driver |
| `lombok` | Reduce boilerplate code |

---

## 🛡️ CORS Configuration

The backend is configured to accept requests from the frontend:
```java
@CrossOrigin(origins = "http://localhost:8090")
```

**To change allowed origins:** Edit `UserController.java` or create a global CORS config.

---

## 🔍 Troubleshooting

| Issue | Solution |
|-------|----------|
| **Connection refused (5432)** | Ensure PostgreSQL is running on port 5432 |
| **Database doesn't exist** | Create database: `createdb chat_db_new` |
| **Permission denied** | Ensure user `chat_user_new` has permissions |
| **Port 8070 in use** | Change `server.port` in application.properties |
| **Tables not created** | Verify `spring.jpa.hibernate.ddl-auto=update` is set |

### Check PostgreSQL Connection
```bash
# Connect to PostgreSQL
psql -U postgres

# List databases
\l

# Connect to chat_db_new
\c chat_db_new

# List tables
\dt

# Check user permissions
SELECT * FROM information_schema.table_privileges WHERE grantee = 'chat_user_new';
```

---

## 📝 Environment Properties

Copy `.example` files to actual property files and configure as needed:

```bash
# Backend properties (already configured)
cat src/main/resources/application.properties
```

---

## 🚦 Application Startup Order

1. **PostgreSQL Server** must be running
2. **Database & User** must be created
3. **Backend Application** starts → connects to DB → creates/updates tables
4. **REST API** becomes available at http://localhost:8070

---

## ✅ Next Steps

1. [x] PostgreSQL database configured
2. [x] Spring Data JPA integrated
3. [x] User entity and repository created
4. [x] REST endpoints implemented
5. [ ] Create Message/Chat entities
6. [ ] Add authentication endpoints
7. [ ] Implement WebSocket for real-time chat
8. [ ] Add API documentation (Swagger/OpenAPI)

---

## 🔗 Related Documentation

- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate Documentation](https://hibernate.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Spring Security](https://spring.io/projects/spring-security)
