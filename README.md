# CSV Handler Project

This project is a Spring Boot application for uploading, storing, and managing CSV data with RESTful endpoints and in-memory H2 database.

---

## Features

- Upload a CSV file and store its content.
- Retrieve all records or a specific record by its code.
- Delete all records.
- Interactive API documentation with Swagger.

---

## Setup

### Prerequisites

- JDK 17+
- Maven 3.6+

### Running the Application

1. Clone the repository
2. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```
3. Access Swagger UI for testing: [Swagger UI](http://localhost:8080/swagger-ui/index.html).

---

## Endpoints

1. **Upload CSV**: `POST /api/v1/csv/upload`
   - Request: `multipart/form-data` with `file`
2. **Fetch All Records**: `GET /api/v1/csv/records`
3. **Fetch Record by Code**: `GET /api/v1/csv/records/{code}`
4. **Delete All Records**: `DELETE /api/v1/csv/records`

---

## Testing

- Access Swagger UI for testing endpoints: [Swagger UI](http://localhost:8080/swagger-ui/index.html).
- Run tests:
  ```bash
  mvn test
  ```


