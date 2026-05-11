# Chatting-Me Application

A full-stack chat application with a Spring Boot backend and Next.js frontend.

---

## 🚀 Quick Start

### Prerequisites
- Java 21+ (for backend)
- Node.js 18+ (for frontend)
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
├── backend/               # Spring Boot Backend
│   └── chat/
│       ├── src/
│       ├── build.gradle
│       └── gradlew
├── frontend/              # Next.js Frontend
│   ├── app/
│   ├── public/
│   ├── package.json
│   └── .env.local
├── install.sh             # Dependency installation script
├── start.sh               # Start both servers script
└── README.md
```

---

## 🔧 Configuration

### Environment Variables

#### Frontend (.env.local)
```env
# Backend API Configuration
NEXT_PUBLIC_BACKEND_URL=http://localhost:8070
```

#### Backend (application.properties)
```properties
# Server Configuration
server.port=8070

# Frontend URL
app.frontend.url=http://localhost:8090
```

---

## 🌐 Application URLs & Ports

| Service | URL | Port | Purpose |
|---------|-----|------|---------|
| **Frontend** | http://localhost:8090 | 8090 | Next.js Web Application |
| **Backend** | http://localhost:8070 | 8070 | Spring Boot API Server |

---

## 📝 Running the Project

### Option 1: Using Scripts (Recommended)

**First time setup:**
```bash
./install.sh
./start.sh
```

### Option 2: Manual Setup

**Backend:**
```bash
cd backend/chat
./gradlew bootRun
```

**Frontend (in another terminal):**
```bash
cd frontend
npm run dev
```

### Option 3: Production Build & Run

**Backend:**
```bash
cd backend/chat
./gradlew build
java -jar build/libs/chat-*.jar
```

**Frontend:**
```bash
cd frontend
npm run build
npm run start
```

---

## 🛠️ Development

### Backend Development
- Navigate to `backend/chat`
- Run `./gradlew bootRun` for development
- Spring Boot will auto-reload on code changes
- API runs on **http://localhost:8070**

### Frontend Development
- Navigate to `frontend`
- Run `npm run dev`
- Next.js dev server will run on **http://localhost:8090**
- Hot reload enabled for live development

---

## 📚 Technology Stack

### Backend
- **Framework:** Spring Boot
- **Runtime:** Java 21+
- **Build:** Gradle
- **Server Port:** 8070

### Frontend
- **Framework:** Next.js 16.2.6
- **UI Library:** React 19.2.4
- **Styling:** Tailwind CSS
- **Language:** TypeScript
- **Server Port:** 8090

---

## 🔗 Cross-Origin Configuration

The backend and frontend communicate via:
- **Frontend → Backend:** `http://localhost:8070` (via `NEXT_PUBLIC_BACKEND_URL`)
- **Backend → Frontend:** `http://localhost:8090` (via `app.frontend.url`)

Ensure CORS is properly configured in the backend if needed.

---

## 📦 Build & Deployment

### Backend Build
```bash
cd backend/chat
./gradlew build
```

### Frontend Build
```bash
cd frontend
npm run build
```

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| Port 8070 already in use | Change `server.port` in `backend/chat/src/main/resources/application.properties` |
| Port 8090 already in use | Change port in `frontend/package.json` scripts (dev/start) |
| Dependencies not installing | Delete `node_modules` and `.gradle` folders, then run `./install.sh` again |
| Backend not starting | Ensure Java 21+ is installed: `java -version` |
| Frontend not starting | Ensure Node 18+ is installed: `node -v` |

---

## 📄 License

All rights reserved © 2026

---

## 👤 Author

UbaidBinWaris
