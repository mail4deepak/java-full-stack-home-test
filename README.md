# Credit Card Management Application

A full-stack application for managing credit cards, built with Spring Boot and React.

## Features

- Add new credit cards with validation
- View list of existing credit cards
- Real-time updates
- Responsive design
- Input validation
- Error handling

## Prerequisites

- Java 22 or higher
- Node.js 20 or higher
- npm 10 or higher
- Maven 3.9 or higher

## Project Structure

```
java-full-stack-home-test/
├── src/
│   ├── main/
│   │   ├── java/           # Backend Java code
│   │   ├── resources/      # Backend configuration
│   │   └── frontend/       # React frontend code
│   └── test/              # Test files
├── pom.xml                # Maven configuration
└── Makefile              # Build and run commands
```

## Quick Start

### Using Makefile (Recommended)

1. Unzip the code and cd to it:
```bash
cd java-full-stack-home-test
```

2. Install dependencies and build:
```bash
make install
make build
```

3. Run the application:
```bash
make run-backend
make run-frontend
```

### Manual Setup on Windows

#### Backend Setup

1. Navigate to the project root:
```bash
cd java-full-stack-home-test
```

2. Build the backend:
```bash
mvn clean install
```

3. Run the backend:
```bash
mvn spring-boot:run
```

The backend will be available at `http://localhost:8080/api/v1`

#### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd src/main/frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```

The frontend will be available at `http://localhost:3000`

## Testing

### Backend Tests

Run backend tests:
```bash
mvn test
```

### Frontend Tests

Run frontend tests:
```bash
cd src/main/frontend
npm test
```

## LOGGING

### Log Config
Log Config is located at : "src/main/resources/logback-spring.xml"

### Log File Location
Log files are located in "logs" folder

## API Documentation

### Endpoints

#### Add Credit Card
- **POST** `/api/v1/cards`
- **Request Body:**
  ```json
  {
    "name": "John Doe",
    "cardNumber": "4111111111111111",
    "limit": 1000
  }
  ```
- **Response:**
  ```json
  {
    "id": 1,
    "name": "John Doe",
    "cardNumber": "4111111111111111",
    "limit": 1000,
    "balance": 0
  }
  ```

#### Get All Credit Cards
- **GET** `/api/v1/cards`
- **Response:**
  ```json
  [
    {
      "id": 1,
      "name": "John Doe",
      "cardNumber": "4111111111111111",
      "limit": 1000,
      "balance": 0
    }
  ]
  ```

## Development

### Backend Development

1. The backend is built with Spring Boot
2. Main components:
   - Controllers: Handle HTTP requests
   - Services: Business logic
   - Repositories: Data access
   - Models: Data structures
   - DTOs: Data transfer objects

### Frontend Development

1. The frontend is built with React and TypeScript
2. Main components:
   - CreditCardForm: Form for adding new cards
   - CreditCardList: Table for displaying cards
   - App: Main application component

## Available Scripts

### Backend

- `mvn clean install`: Build the application
- `mvn test`: Run tests
- `mvn spring-boot:run`: Run the application

### Frontend

- `npm start`: Start development server
- `npm test`: Run tests
- `npm run build`: Build for production
- `npm run eject`: Eject from Create React App

## Makefile Commands

- `make install`: Install all dependencies
- `make build`: Build both frontend and backend
- `make test`: Run all tests
- `make run`: Run both frontend and backend
- `make run-frontend`: Run frontend only
- `make run-backend`: Run backend only
- `make clean`: Clean build artifacts
- `make help`: Show all available commands

## Configuration

### Backend Configuration

modify `src/main/resources/application.properties`:

```properties
server.port=8080
```

### Frontend Configuration

modify `src/main/frontend/.env`:

```env
REACT_APP_API_URL=http://localhost:8080
```

## Troubleshooting

### Common Issues

1. **Port Conflicts**
   - Backend: Change `server.port` in `application.properties`
              Change port in .env file  
   - Frontend: Change port in `package.json` scripts

2. **CORS Issues**
   - Verify CORS configuration 
   - Check frontend API URL configuration

3. **Database Connection**
   - Verify database credentials
   - Check database service status

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please contact:
- Technical Support: support@example.com
- DevOps Team: devops@example.com 