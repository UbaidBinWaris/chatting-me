#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}Starting both servers...${NC}\n"

# Start backend
echo -e "${BLUE}Starting backend server on port 8070...${NC}"
cd backend/chat
if [ -f "gradlew" ]; then
    chmod +x gradlew
    ./gradlew bootRun > ../../backend.log 2>&1 &
else
    gradle bootRun > ../../backend.log 2>&1 &
fi
BACKEND_PID=$!
cd ../..
echo -e "${GREEN}✓ Backend started (PID: $BACKEND_PID)${NC}"
echo -e "  URL: http://localhost:8070\n"

# Start frontend
echo -e "${BLUE}Starting frontend server on port 8090...${NC}"
cd frontend
npm run start > ../frontend.log 2>&1 &
FRONTEND_PID=$!
cd ..
echo -e "${GREEN}✓ Frontend started (PID: $FRONTEND_PID)${NC}"
echo -e "  URL: http://localhost:8090\n"

echo -e "${GREEN}Both servers are running!${NC}"
echo -e "${BLUE}Backend logs:  tail -f backend.log${NC}"
echo -e "${BLUE}Frontend logs: tail -f frontend.log${NC}\n"

echo -e "${BLUE}To stop servers, run:${NC}"
echo -e "  kill $BACKEND_PID $FRONTEND_PID"

# Wait for both processes
wait $BACKEND_PID $FRONTEND_PID
