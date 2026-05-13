<div align="center">

<h1>🏠 Airbnb API</h1>

<p>
  <strong>A production-ready RESTful backend API for an Airbnb-like rental platform</strong><br/>
  Built with <strong>Spring Boot 4</strong> · <strong>Java 21</strong> · <strong>Maven</strong>
</p>

<p>
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0.5-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven"/>
  <img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge" alt="MIT License"/>
  <img src="https://img.shields.io/badge/status-In%20Development-yellow?style=for-the-badge" alt="Status"/>
</p>

<p>
  <a href="#-overview">Overview</a> ·
  <a href="#-tech-stack">Tech Stack</a> ·
  <a href="#-project-structure">Structure</a> ·
  <a href="#-getting-started">Getting Started</a> ·
  <a href="#-api-reference">API Reference</a> ·
  <a href="#-configuration">Configuration</a> ·
  <a href="#-testing">Testing</a> ·
  <a href="#-roadmap">Roadmap</a> ·
  <a href="#-contributing">Contributing</a>
</p>

</div>

---

## 📖 Overview

**Airbnb API** is a scalable, modular RESTful web service that powers the backend of a property-rental platform similar to Airbnb. Built on the latest **Spring Boot 4.0.5** with **Java 21**, this project serves as the core engine for managing listings, bookings, users, and reviews through clean, well-structured HTTP endpoints.

This API follows **REST principles** and is designed for high maintainability, testability, and extensibility — making it easy to plug into any frontend (React, Vue, Flutter, etc.) or consume from a mobile app.

### Why This Project?

| Problem | This API Solves It By |
|---|---|
| Complex rental logic | Centralised, domain-driven service layer |
| Inconsistent data formats | Unified JSON request/response contracts |
| Hard-to-test backends | Full Spring MVC test support via `webmvc-test` |
| Tight coupling | Layered architecture (Controller → Service → Repository) |

---

## 🛠 Tech Stack

| Layer | Technology | Version |
|---|---|---|
| Language | Java (OpenJDK) | 21 LTS |
| Framework | Spring Boot | 4.0.5 |
| Web Layer | Spring Web MVC | (managed by Boot) |
| Build Tool | Apache Maven | 3.9+ |
| Testing | Spring Boot WebMVC Test | (managed by Boot) |
| Packaging | Maven Wrapper (`mvnw`) | Bundled |

> **Java 21** brings virtual threads (Project Loom), pattern matching, records, and sealed classes — all of which can be leveraged to write clean, modern Java inside this project.

---

## 📁 Project Structure

```
Airbnb-api/
│
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties        # Maven wrapper version config
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/Siddhesh/Airbnb/
│   │   │       ├── AirbnbApiApplication.java     # 🚀 Spring Boot entry point
│   │   │       │
│   │   │       ├── controller/                   # (planned) REST Controllers
│   │   │       │   ├── ListingController.java
│   │   │       │   ├── BookingController.java
│   │   │       │   └── UserController.java
│   │   │       │
│   │   │       ├── service/                      # (planned) Business Logic
│   │   │       │   ├── ListingService.java
│   │   │       │   ├── BookingService.java
│   │   │       │   └── UserService.java
│   │   │       │
│   │   │       ├── model/                        # (planned) Domain Models / Entities
│   │   │       │   ├── Listing.java
│   │   │       │   ├── Booking.java
│   │   │       │   └── User.java
│   │   │       │
│   │   │       ├── dto/                          # (planned) Request & Response DTOs
│   │   │       │   └── ...
│   │   │       │
│   │   │       ├── repository/                   # (planned) Data Access Layer
│   │   │       │   └── ...
│   │   │       │
│   │   │       └── exception/                    # (planned) Global Exception Handling
│   │   │           └── GlobalExceptionHandler.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties            # App configuration
│   │       ├── static/                           # Static assets (if any)
│   │       └── templates/                        # Thymeleaf templates (if any)
│   │
│   └── test/
│       └── java/
│           └── com/Siddhesh/Airbnb/
│               └── AirbnbApiApplicationTests.java # Spring context load test
│
├── .gitattributes
├── .gitignore
├── mvnw                                          # Unix Maven wrapper script
├── mvnw.cmd                                      # Windows Maven wrapper script
├── pom.xml                                       # Project Object Model
├── HELP.md                                       # Spring Initializr help notes
├── LICENSE                                       # MIT License
└── README.md                                     # ← You are here
```

### Architectural Overview

```
┌─────────────────────────────────────────────────────┐
│                    CLIENT (HTTP)                     │
│          (Browser / Mobile App / Postman)            │
└─────────────────────┬───────────────────────────────┘
                      │ REST (JSON)
                      ▼
┌─────────────────────────────────────────────────────┐
│               CONTROLLER LAYER                       │
│     Receives HTTP requests, validates input,         │
│     delegates to the Service layer                   │
└─────────────────────┬───────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────┐
│                SERVICE LAYER                         │
│     Business logic, rules, orchestration             │
└─────────────────────┬───────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────┐
│              REPOSITORY LAYER                        │
│     Database interactions (JPA / custom queries)     │
└─────────────────────┬───────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────┐
│                  DATABASE                            │
│        (PostgreSQL / MySQL / H2 in-memory)           │
└─────────────────────────────────────────────────────┘
```

---

## 🚀 Getting Started

### Prerequisites

Before running this project, ensure you have the following installed:

| Tool | Minimum Version | Check Command |
|---|---|---|
| JDK (OpenJDK recommended) | 21 | `java -version` |
| Git | Any | `git --version` |
| Maven (optional) | 3.9+ | `mvn -version` |

> **Note:** Maven is optional — the project ships with `mvnw` / `mvnw.cmd` wrappers that automatically download the correct Maven version.

---

### Installation

**1. Clone the repository**

```bash
git clone https://github.com/Siddhesh/Airbnb-api.git
cd Airbnb-api
```

**2. Verify Java version**

```bash
java -version
# Should output: openjdk version "21.x.x" ...
```

**3. Build the project**

```bash
# On Linux / macOS
./mvnw clean install

# On Windows
mvnw.cmd clean install
```

**4. Run the application**

```bash
# On Linux / macOS
./mvnw spring-boot:run

# On Windows
mvnw.cmd spring-boot:run
```

The API will start on **`http://localhost:8080`** by default.

---

### Quick Verify

Once the server is running, open a browser or run:

```bash
curl http://localhost:8080/actuator/health
# Expected: {"status":"UP"}
```

---

## ⚙️ Configuration

All configuration lives in `src/main/resources/application.properties`.

### Default Configuration

```properties
# Application name
spring.application.name=Airbnb-api

# Server port (default: 8080)
# server.port=8080
```

### Common Configuration Options

```properties
# ── Server ────────────────────────────────────────────
server.port=8080
server.servlet.context-path=/api/v1

# ── Database (PostgreSQL example) ────────────────────
spring.datasource.url=jdbc:postgresql://localhost:5432/airbnb_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# ── JPA / Hibernate ───────────────────────────────────
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# ── Logging ───────────────────────────────────────────
logging.level.com.Siddhesh.Airbnb=DEBUG
logging.level.org.springframework.web=INFO

# ── Jackson (JSON formatting) ─────────────────────────
spring.jackson.serialization.indent-output=true
spring.jackson.date-format=yyyy-MM-dd
```

### Environment Profiles

It is strongly recommended to use Spring profiles for environment separation:

| Profile | File | Use Case |
|---|---|---|
| `dev` | `application-dev.properties` | Local development with H2 in-memory DB |
| `test` | `application-test.properties` | CI/CD test runs |
| `prod` | `application-prod.properties` | Production (PostgreSQL, secured secrets) |

**Activate a profile:**

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 📡 API Reference

> **Base URL:** `http://localhost:8080/api/v1`

The following endpoints represent the planned API surface area. Check the project's controller classes for the live, implemented routes.

### 🏠 Listings

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/listings` | Get all listings | No |
| `GET` | `/listings/{id}` | Get a specific listing | No |
| `GET` | `/listings/search?location=&checkin=&checkout=&guests=` | Search listings by filters | No |
| `POST` | `/listings` | Create a new listing | Yes (Host) |
| `PUT` | `/listings/{id}` | Update a listing | Yes (Owner) |
| `DELETE` | `/listings/{id}` | Delete a listing | Yes (Owner) |

**Example Response — `GET /listings/{id}`:**

```json
{
  "id": 1,
  "title": "Cozy Studio in Bandra",
  "description": "Bright and peaceful studio apartment near the sea.",
  "location": {
    "city": "Mumbai",
    "state": "Maharashtra",
    "country": "India",
    "latitude": 19.0596,
    "longitude": 72.8295
  },
  "pricePerNight": 3500.00,
  "currency": "INR",
  "maxGuests": 2,
  "amenities": ["WiFi", "Air Conditioning", "Kitchen"],
  "host": {
    "id": 42,
    "name": "Siddhesh"
  },
  "createdAt": "2026-04-16T19:17:00Z"
}
```

---

### 📅 Bookings

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/bookings` | Get all bookings for authenticated user | Yes |
| `GET` | `/bookings/{id}` | Get a specific booking | Yes |
| `POST` | `/bookings` | Create a new booking | Yes |
| `PUT` | `/bookings/{id}/cancel` | Cancel a booking | Yes |

**Example Request Body — `POST /bookings`:**

```json
{
  "listingId": 1,
  "checkInDate": "2026-06-01",
  "checkOutDate": "2026-06-07",
  "guests": 2
}
```

---

### 👤 Users

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/auth/register` | Register a new user | No |
| `POST` | `/auth/login` | Login and get token | No |
| `GET` | `/users/me` | Get current user profile | Yes |
| `PUT` | `/users/me` | Update profile | Yes |

---

### ⭐ Reviews

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/listings/{id}/reviews` | Get all reviews for a listing | No |
| `POST` | `/listings/{id}/reviews` | Submit a review | Yes (Guest) |

---

### Standard Error Response Format

All errors follow a consistent JSON structure:

```json
{
  "timestamp": "2026-05-14T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Listing with ID 99 not found",
  "path": "/api/v1/listings/99"
}
```

---

## 🧪 Testing

The project uses **Spring Boot WebMVC Test** for end-to-end controller layer testing without starting a full server.

### Run All Tests

```bash
./mvnw test
```

### Run a Specific Test Class

```bash
./mvnw test -Dtest=AirbnbApiApplicationTests
```

### Test Coverage Report (with JaCoCo — recommended setup)

Add JaCoCo to `pom.xml`:

```xml
<plugin>
  <groupId>org.jacoco</groupId>
  <artifactId>jacoco-maven-plugin</artifactId>
  <version>0.8.12</version>
  <executions>
    <execution>
      <goals><goal>prepare-agent</goal></goals>
    </execution>
    <execution>
      <id>report</id>
      <phase>test</phase>
      <goals><goal>report</goal></goals>
    </execution>
  </executions>
</plugin>
```

Then run:

```bash
./mvnw test jacoco:report
# Report generated at: target/site/jacoco/index.html
```

### Writing Tests — Example

```java
@WebMvcTest(ListingController.class)
class ListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListingService listingService;

    @Test
    void shouldReturnListingById() throws Exception {
        when(listingService.findById(1L)).thenReturn(mockListing());

        mockMvc.perform(get("/api/v1/listings/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.title").exists());
    }
}
```

---

## 🔐 Security (Planned)

The following security mechanisms are planned for future implementation:

- **JWT Authentication** — stateless token-based auth via `spring-boot-starter-security` + `jjwt`
- **Role-based Access Control (RBAC)** — `GUEST`, `HOST`, `ADMIN` roles
- **Password Hashing** — BCrypt encoding
- **CORS Configuration** — restrict to known frontend origins
- **Rate Limiting** — prevent abuse on public endpoints

---

## 🐳 Docker Support (Planned)

A `Dockerfile` and `docker-compose.yml` will be added to containerise the application alongside a PostgreSQL database.

**Planned `docker-compose.yml` structure:**

```yaml
version: '3.9'
services:
  api:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/airbnb_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: secret
    depends_on:
      - db

  db:
    image: postgres:16
    environment:
      POSTGRES_DB: airbnb_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: secret
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

---

## 🗺 Roadmap

- [x] Spring Boot project scaffold
- [x] Maven Wrapper setup
- [x] Application entry point (`AirbnbApiApplication`)
- [ ] Domain model design (Listing, Booking, User, Review)
- [ ] JPA / Hibernate integration
- [ ] Controller, Service, Repository layers
- [ ] Input validation (`@Valid`, Bean Validation)
- [ ] Global exception handling (`@ControllerAdvice`)
- [ ] JWT Authentication & Authorization
- [ ] Search & filtering endpoints
- [ ] Unit + integration tests (80%+ coverage)
- [ ] Swagger / OpenAPI documentation
- [ ] Dockerfile & Docker Compose
- [ ] CI/CD pipeline (GitHub Actions)
- [ ] Production deployment (AWS / Railway / Render)

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

### How to Contribute

1. **Fork** the repository
2. **Create** your feature branch
   ```bash
   git checkout -b feature/listing-search
   ```
3. **Commit** your changes with a descriptive message
   ```bash
   git commit -m "feat: add listing search by location and date range"
   ```
4. **Push** to your branch
   ```bash
   git push origin feature/listing-search
   ```
5. **Open a Pull Request** against `main`

### Commit Message Convention

This project follows [Conventional Commits](https://www.conventionalcommits.org/):

| Prefix | When to Use |
|---|---|
| `feat:` | A new feature |
| `fix:` | A bug fix |
| `docs:` | Documentation changes |
| `test:` | Adding or updating tests |
| `refactor:` | Code refactoring |
| `chore:` | Build or config changes |

### Code Style

- Follow standard **Java naming conventions**
- Use `@Slf4j` for logging (Lombok recommended)
- Write **Javadoc** for all public methods
- Keep controllers thin — all business logic belongs in the service layer
- Every new endpoint must have at least one corresponding test

---

## 🐛 Known Issues / Limitations

- The project is currently in early bootstrap phase — no domain models, services, or controllers exist yet
- No database configuration is included out-of-the-box (H2 or PostgreSQL must be added manually)
- Security is not yet implemented — **do not deploy to production without adding authentication**

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](./LICENSE) file for full details.

```
MIT License — Copyright (c) 2026 Siddhesh
```

---

## 👨‍💻 Author

**Siddhesh**

- GitHub: [@Siddhesh](https://github.com/Siddhesh)
- Package: `com.Siddhesh.Airbnb`

---

## 🙏 Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot) — The backbone of this project
- [Spring Initializr](https://start.spring.io/) — Project bootstrapping
- [Airbnb](https://www.airbnb.com/) — Inspiration for the domain model
- [Conventional Commits](https://www.conventionalcommits.org/) — Commit message standard

---

<div align="center">
  <sub>Built with ☕ and Spring Boot · Made in India 🇮🇳</sub>
</div>
