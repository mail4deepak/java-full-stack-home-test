#!/bin/bash

# Function to show help
show_help() {
    echo "Available commands:"
    echo "  all            - Clean, install, build and test the project"
    echo "  clean          - Remove build artifacts and dependencies"
    echo "  install        - Install all dependencies"
    echo "  build          - Build the project"
    echo "  test           - Run all tests"
    echo "  run-frontend   - Start frontend development server"
    echo "  run-backend    - Start backend server"
    echo "  help           - Show this help message"
}

# Function to clean project
clean_project() {
    echo "Cleaning project..."
    rm -rf src/main/frontend/node_modules
    rm -rf src/main/frontend/build
    rm -rf target
    rm -rf .mvn
    rm -f mvnw
    rm -f mvnw.cmd
}

# Function to install dependencies
install_dependencies() {
    echo "Installing dependencies..."
    cd src/main/frontend && npm install
    cd ../../../ && ./mvnw clean install -DskipTests
}

# Function to build project
build_project() {
    echo "Building project..."
    cd src/main/frontend && npm run build
    cd ../../../ && ./mvnw package -DskipTests
}

# Function to run tests
run_tests() {
    echo "Running tests..."
    cd src/main/frontend && npm test
    cd ../../../ && ./mvnw test
}

# Function to run frontend
run_frontend() {
    echo "Starting frontend development server..."
    cd src/main/frontend && npm start
}

# Function to run backend
run_backend() {
    echo "Starting backend server..."
    ./mvnw spring-boot:run
}

# Main script
case "$1" in
    "all")
        clean_project
        install_dependencies
        build_project
        run_tests
        ;;
    "clean")
        clean_project
        ;;
    "install")
        install_dependencies
        ;;
    "build")
        build_project
        ;;
    "test")
        run_tests
        ;;
    "run-frontend")
        run_frontend
        ;;
    "run-backend")
        run_backend
        ;;
    "help"|"")
        show_help
        ;;
    *)
        echo "Unknown command: $1"
        show_help
        exit 1
        ;;
esac 