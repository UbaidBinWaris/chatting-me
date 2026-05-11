#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}Starting dependency installation...${NC}\n"

# Install backend dependencies
echo -e "${BLUE}Installing backend dependencies...${NC}"
cd backend/chat
if [ -f "gradlew" ]; then
    chmod +x gradlew
    ./gradlew build --refresh-dependencies
else
    gradle build --refresh-dependencies
fi
cd ../..

echo -e "${GREEN}✓ Backend dependencies installed${NC}\n"

# Install frontend dependencies
echo -e "${BLUE}Installing frontend dependencies...${NC}"
cd frontend
npm install
cd ..

echo -e "${GREEN}✓ Frontend dependencies installed${NC}\n"

echo -e "${GREEN}All dependencies installed successfully!${NC}"
echo -e "${BLUE}Next step: Run 'npm run start' or './start.sh' to start both servers${NC}"
