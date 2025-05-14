param(
    [Parameter()]
    [ValidateSet('all', 'clean', 'install', 'build', 'test', 'run-frontend', 'run-backend', 'help')]
    [string]$Command = 'help'
)

$ErrorActionPreference = "Stop"

function Show-Help {
    Write-Host "Available commands:"
    Write-Host "  all            - Clean, install, build and test the project"
    Write-Host "  clean          - Remove build artifacts and dependencies"
    Write-Host "  install        - Install all dependencies"
    Write-Host "  build          - Build the project"
    Write-Host "  test           - Run all tests"
    Write-Host "  run-frontend   - Start frontend development server"
    Write-Host "  run-backend    - Start backend server"
    Write-Host "  help           - Show this help message"
}

function Clean-Project {
    Write-Host "Cleaning project..."
    Remove-Item -Path "src/main/frontend/node_modules" -Recurse -Force -ErrorAction SilentlyContinue
    Remove-Item -Path "src/main/frontend/build" -Recurse -Force -ErrorAction SilentlyContinue
    Remove-Item -Path "target" -Recurse -Force -ErrorAction SilentlyContinue
    Remove-Item -Path ".mvn" -Recurse -Force -ErrorAction SilentlyContinue
    Remove-Item -Path "mvn.cmd" -Force -ErrorAction SilentlyContinue
}

function Install-Dependencies {
    Write-Host "Installing dependencies..."
    Set-Location "src/main/frontend"
    npm install
    Set-Location "../../../"
    mvn clean install -DskipTests
}

function Build-Project {
    Write-Host "Building project..."
    Set-Location "src/main/frontend"
    npm run build
    Set-Location "../../../"
    mvn package -DskipTests
}

function Run-Tests {
    Write-Host "Running tests..."
    Set-Location "src/main/frontend"
    npm test
    Set-Location "../../../"
    mvn test
}

function Run-Frontend {
    Write-Host "Starting frontend development server..."
    Set-Location "src/main/frontend"
    npm start
}

function Run-Backend {
    Write-Host "Starting backend server..."
    mvn spring-boot:run
}

switch ($Command) {
    'all' {
        Clean-Project
        Install-Dependencies
        Build-Project
        Run-Tests
    }
    'clean' { Clean-Project }
    'install' { Install-Dependencies }
    'build' { Build-Project }
    'test' { Run-Tests }
    'run-frontend' { Run-Frontend }
    'run-backend' { Run-Backend }
    'help' { Show-Help }
} 