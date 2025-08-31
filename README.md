# Ecom Project - JWT Authentication & RBAC

This Spring Boot project implements JWT-based authentication and Role-Based Access Control (RBAC) for an e-commerce API. It allows users to register, login, and access protected endpoints using JWT tokens. Only users with the `ROLE_ADMIN` role can add, update, or delete products.

## Features

- User registration and login
- JWT token generation and validation
- Role-based access control (RBAC)
- Secure endpoints for product management

## Project Structure

```
src/main/java/com/medhansh/ecom_proj/
├── model/
│   └── User.java
├── repo/
│   └── UserRepo.java
├── service/
│   └── UserService.java
├── util/
│   └── JwtUtil.java
├── config/
│   └── SecurityConfig.java
└── controller/
    └── AuthController.java
```

## File Explanations

### 1. `User.java` (Entity)

Defines the `User` entity with fields for `id`, `username`, `password`, and a set of roles. Roles are stored as a set of strings (e.g., `ROLE_USER`, `ROLE_ADMIN`).

### 2. `UserRepo.java` (Repository)

Spring Data JPA repository for `User` entities. Provides a method to find users by username.

### 3. `UserService.java` (Service)

Implements `UserDetailsService` for Spring Security. Loads user details from the database and maps roles for authentication.

### 4. `JwtUtil.java` (JWT Utility)

Handles JWT token creation and extraction. Uses a secret key to sign tokens and includes roles as claims.

### 5. `SecurityConfig.java` (Security Configuration)

Configures Spring Security:

- Disables CSRF (stateless API)
- Permits `/api/auth/**` endpoints
- Restricts product management endpoints to `ROLE_ADMIN`
- Adds a JWT authentication filter
- Uses BCrypt for password encoding

### 6. `AuthController.java` (Authentication Controller)

REST controller for authentication:

- `POST /api/auth/register`: Registers a new user with `ROLE_USER`
- `POST /api/auth/login`: Authenticates user and returns a JWT token

## Usage

### 1. Register a User

```
POST /api/auth/register
Content-Type: application/json
{
  "username": "user1",
  "password": "password"
}
```

### 2. Login

```
POST /api/auth/login
Content-Type: application/json
{
  "username": "user1",
  "password": "password"
}
```

Response: JWT token string

### 3. Access Protected Endpoints

Include the JWT token in the `Authorization` header:

```
Authorization: Bearer <token>
```

- Only users with `ROLE_ADMIN` can add, update, or delete products via `/api/product` endpoints.

## Dependencies

- Spring Boot
- Spring Security
- jjwt (Java JWT)
- Lombok
- Jakarta Persistence

## Notes

- The JWT secret should be kept safe and ideally loaded from environment variables.
- Passwords are stored securely using BCrypt.
- You can extend the `User` entity to support more roles or user details.

---

For any questions or issues, please refer to the code comments or reach out to the maintainer.
