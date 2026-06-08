# Expense Tracker API

[![CI](https://github.com/Oumaima-Ben-EL-Bey/expense-tracker-api/actions/workflows/ci.yml/badge.svg)](https://github.com/Oumaima-Ben-EL-Bey/expense-tracker-api/actions/workflows/ci.yml)

A REST API for tracking personal expenses and grouping them into categories. Built as a portfolio project to learn the Spring Boot backend ecosystem end-to-end — from REST design and JPA persistence through testing, containerization, CI/CD, and public deployment.

## Live Demo

- **API base URL:** https://expense-tracker-oumaima.fly.dev
- **Interactive API docs (Swagger UI):** https://expense-tracker-oumaima.fly.dev/swagger-ui.html

The live instance is seeded with a small amount of demo data so the endpoints return something meaningful. It is a public demo with no authentication and disposable data.

## Features

- Full CRUD for expenses and categories
- Each expense belongs to a category (a `@ManyToOne` relationship)
- Filtering expenses by category and by date range
- Pagination on the expense list
- A summary endpoint that totals expense amounts per category
- Request validation with structured, consistent error responses
- Interactive OpenAPI / Swagger UI documentation

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4
- **Persistence:** Spring Data JPA, Hibernate, PostgreSQL 16
- **Build:** Maven
- **Testing:** JUnit 5, Mockito, Testcontainers
- **Containerization:** Docker, Docker Compose
- **CI/CD:** GitHub Actions (build, test, and image publication to GHCR)
- **API docs:** springdoc-openapi (Swagger UI)

## API Overview

Full, interactive documentation is available at `/swagger-ui.html`. A summary:

### Expenses

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/expenses` | List expenses (paginated; filter by `categoryId`, `startDate`, `endDate`) |
| `GET` | `/expenses/{id}` | Get a single expense |
| `POST` | `/expenses` | Create an expense |
| `PUT` | `/expenses/{id}` | Update an expense |
| `DELETE` | `/expenses/{id}` | Delete an expense |
| `GET` | `/expenses/summary` | Total expense amount per category |

### Categories

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/categories` | List categories |
| `GET` | `/categories/{id}` | Get a single category |
| `POST` | `/categories` | Create a category |
| `PUT` | `/categories/{id}` | Update a category |
| `DELETE` | `/categories/{id}` | Delete a category |

## Running Locally

The whole stack (application + PostgreSQL) runs with Docker Compose — no local Java or database installation required.

**Prerequisites:** Docker and Docker Compose.

```bash
# 1. Provide the database environment variables
cp .env.example .env        # then edit .env if you want to change the password

# 2. Build and start the application and database
docker compose up --build
```

Once it's running:

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

Stop the stack with `Ctrl+C`, or `docker compose down` to remove the containers (add `-v` to also delete the database volume).

## Running the Tests

```bash
./mvnw test        # macOS/Linux
mvnw.cmd test      # Windows
```

The integration tests use Testcontainers, which starts a real PostgreSQL container, so **Docker must be running** for the test suite.

## About

This is a learning portfolio project: my first Java application and first public repository. The goal was to build a small but complete and cleanly-executed backend service using a mainstream Java stack. Authentication, a frontend, and orchestration (e.g. Kubernetes) are intentionally out of scope, reserved for follow-up projects.
