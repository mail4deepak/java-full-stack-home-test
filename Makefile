.PHONY: all clean install build test run-frontend run-backend

# Default target
all: install build

# Clean build artifacts
clean:
	@echo "Cleaning build artifacts..."
	rmdir /s /q  target
	rmdir /s /q  "src/main/frontend/node_modules"
	rmdir /s /q  "src/main/frontend/build"

# Install dependencies
install:
	@echo "Installing dependencies..."
	mvn clean install -DskipTests
	cd src/main/frontend && npm install	

# Build the application
build:
	@echo "Building the application..."
	mvn clean package -DskipTests
	cd src/main/frontend && npm run build

# Run tests
test:
	@echo "Running tests..."
	mvn test
	cd src/main/frontend && npm test

# Run frontend development server
run-frontend:
	@echo "Starting frontend development server..."
	cd src/main/frontend && npm start

# Run backend server
run-backend:
	@echo "Starting backend server..."
	mvn spring-boot:run

# Help command
help:
	@echo "Available commands:"
	@echo "  make install    - Install all dependencies"
	@echo "  make build      - Build the application"
	@echo "  make test       - Run all tests"
	@echo "  make run        - Run both frontend and backend"
	@echo "  make run-frontend - Run frontend development server"
	@echo "  make run-backend  - Run backend server"
	@echo "  make clean      - Clean build artifacts"
	@echo "  make help       - Show this help message" 