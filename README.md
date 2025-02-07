##TimeCore - Backend

###Technologies

Java 21

Spring Boot

Spring Security

MySQL

JWT Authentication

PDF Processing

Email Integration

###Features

User authentication with JWT

Two-Factor Authentication (2FA)

PDF schedule import/export

CSV/Binary data export

Queue system for verifications

Operation history tracking

Email notifications

Prerequisites

Java JDK 21

MySQL 8.0+

Maven 3.9+

Gmail Account (for email notifications)

Quick Start

1. Clone the repository

git clone https://github.com/yourusername/timecore-backend.git
cd timecore-backend

2. Configure the environment

Create a .env file in the project root with the following content:

DB_MYSQL_URL=jdbc:mysql://localhost:3306/timecore
DB_MYSQL_USERNAME=your_username
DB_MYSQL_PASSWORD=your_password
MAIL_USERNAME=your_gmail@gmail.com
MAIL_PASSWORD=your_app_password

3. Create the database

CREATE DATABASE timecore;

4. Build and run the application

./mvnw clean install
./mvnw spring-boot:run

5. Verify installation

The API will be available at http://localhost:8080.

To test, you can access the health check endpoint:

GET http://localhost:8080/api/health

API Documentation

Authentication Endpoints

POST /auth/signup - Register a new user

POST /auth/signin - Login

POST /auth/two-factor/otp/{otp} - Verify 2FA

User Endpoints

GET /api/users/profile - Get user profile

PUT /api/users/profile - Update profile

PUT /api/users/profile/password - Update password

POST /api/users/verification/{type}/send-otp - Send verification code

PATCH /api/users/enable-two-factor/verify-otp/{otp} - Enable 2FA

Event Endpoints

POST /api/events/import - Import PDF schedule

GET /api/events - Get all events

GET /api/events/user/{userId} - Get user events

POST /api/events/{eventId}/users/{userId} - Add user to event

DELETE /api/events/{id} - Delete event

Data Structures

Queue for verification codes

Stack for operation history

File handling for CSV/Binary exports

Security

JWT-based authentication

Password encryption with BCrypt

Two-Factor Authentication

CORS configuration

Frontend Integration

The React frontend application should be running on port 5173.
If using a different port, update the CORS configuration in AppConfig.java.

Documentation

API documentation is available at /swagger-ui.html

Code follows Javadoc documentation standards

Error Handling

Global exception handling

Standardized validation error responses

Proper use of HTTP status codes

Contributing

See workflow.md and coding-standards.md for contribution guidelines.

License

Distributed under the MIT License. See LICENSE for more details.

