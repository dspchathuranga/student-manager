# Student Management System

The Student Management System is a Spring Boot API project designed for managing students data. This README provides an overview of the project and instructions on how to set it up using Docker Compose.

#### [Check out the Documentation in GitHub Pages site](https://dspchathuranga.github.io/student-manager/)

## Table of Contents
- [Project Overview](#project-overview)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Accessing API Documentation](#accessing-api-documentation)
- [Running Tests](#running-tests)

## Project Overview

The Student Management System is a Spring Boot application that provides CRUD (Create, Read, Update, Delete) operations for students data. The API documentation is available using Swagger, making it easy to explore and interact with the endpoints.

ER Diagram

![spreadsheet-creation](https://user-images.githubusercontent.com/5466387/275286192-37c4dc82-81a5-47f8-a69d-b657a8e426cd.png)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17 or later
- [Maven](https://maven.apache.org/install.html) for building the project
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Getting Started

To get started with the Student Management System, follow these steps:

1. Clone the repository to your local machine:

    ```sh
    git clone https://github.com/dspchathuranga/student-manager.git
    ```

2. Build the project using Maven:

    ```sh
    cd student-management
    mvn clean install -DskipTests
    ```

3. You can change environment variables `docker-compose.yaml` file in the project directory:

    ```yaml
    version: '3.8'
    services:
      database_postgres:
        container_name: student-manager-postgres
        image: postgres
        restart: always
        environment:
          - POSTGRES_USER=postgres
          - POSTGRES_PASSWORD=postgres
        ports:
          - '5432:5432'
      spring_boot_app:
        container_name: student-manager-app
        image: student-manager/student-manager-image:1.0
        build: .
        ports:
          - 8080:8080
        environment:
          - DATABASE_URL=jdbc:postgresql://database_postgres:5432/postgres
          - DATABASE_USERNAME=postgres
          - DATABASE_PASSWORD=postgres
        depends_on:
          - database_postgres
    ```

4. Start the application and database services using Docker Compose:

    ```sh
    docker-compose up
    ```

The application will be accessible at `http://localhost:8080`.

## Accessing API Documentation

The API documentation is available via Swagger, which allows you to interact with the endpoints and understand the available features. To access the API documentation, follow these steps:

1. Run the Spring Boot application using Docker Compose as described in the "Getting Started with Docker Compose" section.

2. Open a web browser and go to the Swagger UI by navigating to the following URL:

    ```
    http://localhost:8080/swagger-ui.html
    ```

3. Use the Swagger UI to explore and test the API endpoints. You can find descriptions and example requests and responses for each endpoint.

## Running Tests

Testing is an essential part of ensuring the quality and correctness of the Student Management System. We use [JUnit](https://junit.org/junit5/) in combination with [Spring Test](https://spring.io/guides/gs/testing/).

Before running the tests, make sure the `application.properties` configuration:

```properties
# Database Configuration
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/postgres}
spring.datasource.username=${DATABASE_USERNAME:postgres}
spring.datasource.password=${DATABASE_PASSWORD:postgres}

# Flyway Configuration
spring.flyway.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/postgres}
spring.flyway.user=${DATABASE_USERNAME:postgres}
spring.flyway.password=${DATABASE_PASSWORD:postgres}
```

#### Using Maven

For Maven-based projects, execute the following command in your project's root directory:

```shell
mvn test
```

## Contributing

If you would like to contribute to the project, please submit a pull request. We welcome your contributions and ideas.

---

© 2023 DSP Chathuranga
