# E-Commerce Management System

A comprehensive Spring Boot application featuring JWT authentication, role-based access control (RBAC), and complete product management capabilities for e-commerce platforms. This system provides secure user authentication, product catalog management with image upload, and cloud storage integration.

## 🚀 Features

### Authentication & Security
- JWT-based stateless authentication
- Role-based access control (RBAC) with USER and ADMIN roles
- BCrypt password encryption
- Secure endpoint protection with Spring Security
- Custom JWT authentication filter

### Product Management
- Complete CRUD operations for products
- Image upload and storage capabilities
- Product search functionality
- Category and brand management
- Stock quantity tracking
- Price management with BigDecimal precision

### Cloud Integration
- AWS S3 SDK integration for scalable file storage
- Multi-database support (H2 for development, PostgreSQL for production)
- RESTful API design for easy integration

## 📁 Project Structure

```
ecom-proj/
├── src/
│   ├── main/
│   │   ├── java/com/medhansh/ecom_proj/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java           # Spring Security configuration
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java           # Authentication endpoints
│   │   │   │   └── ProductController.java        # Product management endpoints
│   │   │   ├── model/
│   │   │   │   ├── Product.java                  # Product entity
│   │   │   │   └── User.java                     # User entity with roles
│   │   │   ├── repo/
│   │   │   │   ├── ProductRepo.java              # Product repository
│   │   │   │   └── UserRepo.java                 # User repository
│   │   │   ├── service/
│   │   │   │   ├── ProductService.java           # Product business logic
│   │   │   │   └── UserService.java              # User authentication service
│   │   │   ├── util/
│   │   │   │   └── JwtUtil.java                  # JWT token utilities
│   │   │   └── EcomProjApplication.java          # Main application class
│   │   └── resources/
│   │       ├── application.properties            # Application configuration
│   │       └── data1.sql                         # Initial data setup
│   └── test/
│       └── java/com/medhansh/ecom_proj/
│           └── EcomProjApplicationTests.java
├── target/                                       # Compiled classes
├── pom.xml                                       # Maven dependencies
└── README.md
```

## 🔧 Technologies Used

### Backend Framework
- **Spring Boot 3.2.5** - Main application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence layer
- **Hibernate ORM** - Object-relational mapping

### Authentication & Security
- **JWT (JJWT 0.11.5)** - JSON Web Token implementation
- **BCrypt** - Password hashing algorithm

### Database
- **H2 Database** - In-memory database for development
- **PostgreSQL** - Production database support

### Cloud & Storage
- **AWS SDK S3** - Cloud file storage integration

### Development Tools
- **Lombok** - Reduce boilerplate code
- **Maven** - Dependency management and build tool
- **Java 17** - Programming language

## 📚 API Endpoints

### Authentication Endpoints

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securePassword123"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securePassword123"
}
```
**Response:** JWT token string

### Product Management Endpoints

#### Get All Products
```http
GET /api/products
Authorization: Bearer <jwt_token>
```

#### Get Product by ID
```http
GET /api/product/{id}
Authorization: Bearer <jwt_token>
```

#### Add New Product (ADMIN only)
```http
POST /api/product
Authorization: Bearer <jwt_token>
Content-Type: multipart/form-data

Form Data:
- product: {
    "name": "iPhone 15",
    "description": "Latest iPhone model",
    "brand": "Apple",
    "price": 999.99,
    "category": "Electronics",
    "releaseDate": "2024-09-15",
    "productAvailable": true,
    "stockQuantity": 100
  }
- imageFile: [binary file]
```

#### Update Product (ADMIN only)
```http
PUT /api/product/{id}
Authorization: Bearer <jwt_token>
Content-Type: multipart/form-data

Form Data:
- product: [Updated product JSON]
- imageFile: [New image file]
```

#### Delete Product (ADMIN only)
```http
DELETE /api/product/{id}
Authorization: Bearer <jwt_token>
```

#### Get Product Image
```http
GET /api/product/{productId}/image
Authorization: Bearer <jwt_token>
```

#### Search Products
```http
GET /api/products/search?keyword=iPhone
Authorization: Bearer <jwt_token>
```

## 🛡️ Security Configuration

### Role-Based Access Control

- **Public Endpoints:**
  - `/api/auth/**` - Registration and login
  - `/h2-console/**` - H2 database console (development)

- **USER Role Access:**
  - `GET /api/products` - View all products
  - `GET /api/product/{id}` - View specific product
  - `GET /api/product/{id}/image` - View product images
  - `GET /api/products/search` - Search products

- **ADMIN Role Access:**
  - All USER permissions plus:
  - `POST /api/product` - Add new products
  - `PUT /api/product/{id}` - Update products
  - `DELETE /api/product/{id}` - Delete products

### JWT Token Format
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/medhansh17/ecom-proj.git
   cd ecom-proj
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
   - Database URL: `jdbc:h2:mem:medhansh`

### Configuration

#### Database Configuration (application.properties)
```properties
# H2 Database (Development)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:medhansh
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# File Upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

## 🧪 Testing

### Using Postman or cURL

1. **Register a new user**
2. **Login to get JWT token**
3. **Use the token in Authorization header for protected endpoints**
4. **Test CRUD operations on products**

### Sample Test Flow
```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Get products (use token from login response)
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 📊 Database Schema

### User Table
- `id` (BIGINT, Primary Key)
- `username` (VARCHAR, Unique)
- `password` (VARCHAR, BCrypt hashed)

### User_Roles Table
- `user_id` (BIGINT, Foreign Key)
- `roles` (VARCHAR) - ROLE_USER, ROLE_ADMIN

### Product Table
- `id` (INTEGER, Primary Key)
- `name` (VARCHAR)
- `description` (VARCHAR)
- `brand` (VARCHAR)
- `price` (DECIMAL)
- `category` (VARCHAR)
- `release_date` (TIMESTAMP)
- `product_available` (BOOLEAN)
- `stock_quantity` (INTEGER)
- `image_name` (VARCHAR)
- `image_type` (VARCHAR)
- `image_date` (BLOB)

## 🔮 Future Enhancements

- Shopping cart functionality
- Order management system
- Payment gateway integration
- Product reviews and ratings
- Email notifications
- Advanced search filters
- Inventory management
- Analytics dashboard

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Medhansh** - [GitHub Profile](https://github.com/medhansh17)

---

⭐ If you found this project helpful, please give it a star!
