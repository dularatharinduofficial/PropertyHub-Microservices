# PropertyHub Microservices Architecture

## Project Overview
PropertyHub is an end-to-end, distributed microservices system designed for real estate management. The system is built using **Spring Boot (Java)** for backend microservices, orchestrated through a central **Spring Cloud API Gateway**, and consumed by a unified client application. 

This repository fulfills the requirements for the **Service-Oriented Computing** module coursework.

## Student Details
| Name | Student ID | Role | Key Responsibilities & Endpoints |
| :--- | :--- | :--- | :--- |
| **Dulara Tharindu** | ITBNM-2313-0084 | Gateway & Auth Lead / Full Stack | API Gateway, OAuth 2.0 (JWT), Rate Limiting, CORS, Client App, User Auth Service (Endpoints: `/auth/register`, `/auth/login`, `/auth/properties`) |

*(Note: As per the current implementation, this repository contains the core infrastructure and the User Auth/Property service managed by the Gateway Lead. Additional group members' services should be listed here as they are integrated.)*

## System Architecture
The system follows a strict microservices architecture:
1. **API Gateway:** Acts as the single entry point for all client requests. Handles routing, global CORS, and Redis-backed Rate Limiting.
2. **User Auth Service:** Handles user registration, JWT-based OAuth 2.0 authentication, and CRUD operations for Properties. Secured internally via API Keys.
3. **Client Application:** A frontend UI that interacts with the backend services exclusively through the API Gateway.
4. **Databases:** Each microservice maintains its own database (e.g., MongoDB for User Auth Service) to ensure loose coupling.

## Prerequisites
To run this project locally, ensure you have the following installed:
* [Docker](https://docs.docker.com/get-docker/) & Docker Compose
* Java 17+ (If running locally without Docker)
* Maven

## Running the System (Docker Compose)
The entire ecosystem is containerized. You can spin up the API Gateway, all microservices, databases, and the client application with a single command.

1. Clone the repository:
   ```bash
   git clone <your-repository-url>
   cd PropertyHub-Microservices
   ```
2. Start the system:
   ```bash
   docker-compose up -d --build
   ```
3. To stop the system:
   ```bash
   docker-compose down
   ```

## Security & Access
* **Gateway Entry Point:** All frontend requests must be sent to the API Gateway running on `http://localhost:8080`.
* **OAuth 2.0 (JWT):** The `/auth/login` endpoint returns a Bearer token. This token must be included in the `Authorization` header for protected routes.
* **API Key Internal Security:** Direct access to backend microservices is blocked. The Gateway injects a secure API Key header (`X-API-KEY`) before routing requests to backend services. 

## API Documentation (Swagger UI)
Each microservice includes interactive OpenAPI 3.0 documentation. Once the system is running, you can access them at:
* **User Auth Service:** `http://localhost:8081/swagger-ui/index.html` *(Port may vary based on your docker-compose config)*

## Repository Structure
```text
PropertyHub-Microservices/
├── api-gateway/            # Spring Cloud Gateway (Routing, CORS, Rate Limiting)
├── user-auth-service/      # Spring Boot Auth & Property Service (JWT, MongoDB)
├── client-app/             # Frontend Application
├── docker-compose.yml      # Root orchestration file
└── .gitignore              # Global gitignore (filtering compiled files & secrets)
```