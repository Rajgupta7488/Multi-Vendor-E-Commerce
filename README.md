# Multi-Vendor E-Commerce Platform

A robust, scalable multi-vendor e-commerce platform built with modern Java technologies including Spring Boot, Jakarta EE, and Spring Data JPA.

---

## 🚀 Features

- **Multi-Vendor Support**: Marketplace solution supporting multiple vendors
- **Category Management**: Full CRUD for product categories
- **RESTful API**: Clean REST API for easy integration
- **Modern Java Stack**: Java 24, Spring Boot 3.x, Jakarta EE
- **Data Persistence**: Efficiently handled with Spring Data JPA
- **Configurable Database**: MySQL, PostgreSQL, or H2 (for dev)
- **Enterprise-Ready**: Professional-grade layered architecture

---

## 🛠 Technology Stack

- **Java**: 24 (LTS)
- **Framework**: Spring Boot 3.x
- **Persistence**: Jakarta EE, Spring Data JPA
- **Web**: Spring MVC
- **Database**: MySQL/PostgreSQL/H2 (choose via config)
- **Build Tool**: Maven (pom.xml provided)
- **IDE Recommendation**: IntelliJ IDEA Ultimate (or Community)

---

## 📋 Prerequisites

- Java 24 or higher
- Maven 3.6+ (or Gradle 7+ if you adapt the build)
- MySQL, PostgreSQL, or H2
- Git

---

## 🔧 Installation & Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/Rajgupta7488/Multi-Vendor-E-Commerce.git
   cd Multi-Vendor-E-Commerce
   ```

2. **Configure Database**

   Edit `src/main/resources/application.properties` to match your DB:

   ```properties
   # MySQL Example
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

   *(Uncomment and edit for PostgreSQL or H2, see below)*

3. **Build the project**

   ```bash
   mvn clean install
   ```

4. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - UI/API: [http://localhost:8080](http://localhost:8080)
   - Swagger API Docs: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) (if Swagger enabled)

---

## 📊 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── ecommerce/
│   │       ├── controller/        # REST Controllers
│   │       ├── service/           # Business Logic Layer
│   │       │   └── impl/          # Service Implementations
│   │       ├── repository/        # Data Access Layer
│   │       ├── entity/            # JPA Entities
│   │       ├── dto/               # Data Transfer Objects
│   │       ├── config/            # Configuration Classes
│   │       └── exception/         # Custom Exceptions
│   └── resources/
│       ├── application.properties # Main config
│       └── data.sql              # (Optional) Seed data
└── test/                         # Tests
```

---

## 🔄 API Endpoints

### Category Management

| Method | Endpoint                              | Description             |
|--------|---------------------------------------|-------------------------|
| GET    | `/api/categories`                     | Get all categories      |
| GET    | `/api/categories/{id}`                | Get category by ID      |
| GET    | `/api/categories/name/{name}`         | Get category by name    |
| POST   | `/api/categories`                     | Create new category     |
| PUT    | `/api/categories/{id}`                | Update category         |
| DELETE | `/api/categories/{id}`                | Delete category         |

#### Example Request

```json
POST /api/categories
{
  "name": "Electronics",
  "description": "Electronic devices and gadgets",
  "active": true
}
```

#### Example Response

```json
{
  "id": 1,
  "name": "Electronics",
  "description": "Electronic devices and gadgets",
  "active": true,
  "createdAt": "2025-06-24T10:00:00Z"
}
```

---

## 🏗 Architecture

**Layered architecture:**
- **Controller Layer**: Handles HTTP requests/responses
- **Service Layer**: Business logic & validation
- **Repository Layer**: Data access (Spring Data JPA)
- **Entity Layer**: JPA entities for DB tables

**Key Components:**
- `CategoryServiceImpl`: Main service for categories
- Spring Data JPA for persistence
- Jakarta EE for enterprise patterns

---

## 🧪 Testing

Run all tests with:

```bash
mvn test
```

---

## 📝 Configuration

### Database

Edit `application.properties` for your DB:

```properties
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# PostgreSQL
# spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
# spring.datasource.driver-class-name=org.postgresql.Driver

# H2 (in-memory for dev)
# spring.datasource.url=jdbc:h2:mem:testdb
# spring.datasource.driver-class-name=org.h2.Driver
```

### JPA

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

## 🚀 Deployment

### Docker

1. Create Dockerfile:

   ```dockerfile
   FROM openjdk:24-jdk-slim
   VOLUME /tmp
   COPY target/*.jar app.jar
   ENTRYPOINT ["java","-jar","/app.jar"]
   ```

2. Build and run:

   ```bash
   mvn clean package
   docker build -t ecommerce-platform .
   docker run -p 8080:8080 ecommerce-platform
   ```

### Production Notes

- Use environment-specific configs
- Enable DB connection pooling
- Use caching (Redis/Hazelcast)
- Set up load balancing (if scaling)
- Apply security (JWT, OAuth2, HTTPS)

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style

- Follow Java conventions
- Use meaningful names
- Comment complex logic
- Ensure tests pass before PR

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👥 Author

- **Raj Gupta** - _Initial work_ - [Rajgupta7488](https://github.com/Rajgupta7488)

---

## 🙏 Acknowledgments

- Spring Boot & JPA guides [[1]](https://medium.com/javaguides/best-practices-for-spring-data-jpa-the-ultimate-guide-c2a84a4cd45e)
- Jakarta EE community
- Contributors & open-source community

---

## 📞 Support

- Create an issue on GitHub
- Documentation: [Wiki](https://github.com/Rajgupta7488/Multi-Vendor-E-Commerce/wiki)

---

Made with ❤️ using Spring Boot and Jakarta EE
