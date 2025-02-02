# TimeCore Backend

A Spring Boot backend application for schedule management with secure authentication.

## Overview

TimeCore is a scheduling application backend built with Spring Boot that provides:

- Secure user authentication with JWT tokens
- Two-factor authentication support
- PDF document importing and processing
- RESTful API endpoints
- MySQL database integration

## Technologies

- Java 21
- Spring Boot 3.4.2
- Spring Security
- Spring Data JPA
- MySQL
- Lombok
- PDFBox
- JWT Authentication

## Prerequisites

- JDK 21+
- MySQL 8.0+
- Maven 3.9+

## Configuration

1. Create a `.env` file in the root directory with:

```properties
DB_MYSQL_URL=jdbc:mysql://127.0.0.1:3306/timecore
DB_MYSQL_USERNAME=your_username
DB_MYSQL_PASSWORD=your_password
```
