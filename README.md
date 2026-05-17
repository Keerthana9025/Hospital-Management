# Hospital Appointment Booking System
### Backend — Java | Spring Boot | PostgreSQL | Liquibase | JWT

---

## Project Structure

```
hospital/
├── pom.xml
└── src/main/
    ├── java/com/hospital/
    │   ├── HospitalApplication.java
    │   ├── controller/     → REST API endpoints
    │   ├── service/        → Business logic
    │   ├── repository/     → Database access (Spring Data JPA)
    │   ├── model/          → Entity classes
    │   ├── dto/            → Request / Response objects
    │   ├── security/       → JWT + Spring Security
    │   ├── exception/      → Custom exceptions + Global handler
    │   └── config/         → Security config
    └── resources/
        ├── application.properties
        └── db/changelog/   → Liquibase migration files
```

---

## Setup Instructions

### 1. Create PostgreSQL Database
```sql
CREATE DATABASE hospital_db;
```

### 2. Update application.properties
```properties
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
```

### 3. Run the Project
```bash
mvn spring-boot:run
```
Liquibase will automatically create all tables on first run.

---

## API Endpoints

### Auth
| Method | Endpoint             | Description       | Access  |
|--------|----------------------|-------------------|---------|
| POST   | /api/auth/register   | Register new user | Public  |
| POST   | /api/auth/login      | Login, get JWT    | Public  |

### Patients
| Method | Endpoint              | Description         | Access       |
|--------|-----------------------|---------------------|--------------|
| POST   | /api/patients         | Register patient    | Admin/Patient|
| GET    | /api/patients         | Get all patients    | Admin        |
| GET    | /api/patients/{id}    | Get patient by ID   | Admin/Patient|
| PUT    | /api/patients/{id}    | Update patient      | Admin/Patient|
| DELETE | /api/patients/{id}    | Delete patient      | Admin        |

### Doctors
| Method | Endpoint                          | Description              | Access |
|--------|-----------------------------------|--------------------------|--------|
| POST   | /api/doctors                      | Add doctor               | Admin  |
| GET    | /api/doctors                      | List all doctors         | All    |
| GET    | /api/doctors/available            | List available doctors   | All    |
| GET    | /api/doctors/specialization?name= | Filter by specialization | All    |
| GET    | /api/doctors/{id}                 | Get doctor by ID         | All    |
| PUT    | /api/doctors/{id}                 | Update doctor            | Admin  |
| DELETE | /api/doctors/{id}                 | Delete doctor            | Admin  |

### Appointments
| Method | Endpoint                               | Description           | Access |
|--------|----------------------------------------|-----------------------|--------|
| POST   | /api/appointments                      | Book appointment      | All    |
| GET    | /api/appointments                      | All appointments      | Admin  |
| GET    | /api/appointments/{id}                 | Get by ID             | All    |
| GET    | /api/appointments/patient/{patientId}  | Patient's appointments| All    |
| GET    | /api/appointments/doctor/{doctorId}    | Doctor's appointments | All    |
| PATCH  | /api/appointments/{id}/status?status=  | Update status         | Admin/Doctor|
| PATCH  | /api/appointments/{id}/notes           | Add doctor notes      | Doctor |
| DELETE | /api/appointments/{id}                 | Cancel appointment    | All    |

---

## Sample Requests (Postman)

### Register
```json
POST /api/auth/register
{
  "username": "admin1",
  "password": "Admin@123",
  "email": "admin@hospital.com",
  "role": "ROLE_ADMIN"
}
```

### Login
```json
POST /api/auth/login
{
  "username": "admin1",
  "password": "Admin@123"
}
→ Returns: { "token": "eyJhbGci..." }
```

### Book Appointment (Add JWT token in Authorization header)
```json
POST /api/appointments
Authorization: Bearer <your_token>
{
  "patientId": 1,
  "doctorId": 2,
  "appointmentDate": "2026-06-15",
  "appointmentTime": "10:30:00",
  "reason": "Fever and headache"
}
```

### Update Appointment Status
```
PATCH /api/appointments/1/status?status=CONFIRMED
Authorization: Bearer <your_token>
```

---

## Tech Stack

| Layer      | Technology              |
|------------|-------------------------|
| Language   | Java 17                 |
| Framework  | Spring Boot 3.2         |
| Security   | Spring Security + JWT   |
| Database   | PostgreSQL              |
| Migration  | Liquibase               |
| ORM        | Spring Data JPA         |
| Build      | Maven                   |
| IDE        | IntelliJ IDEA           |

---

## Key Features
- JWT-based authentication with role-based access (ADMIN, DOCTOR, PATIENT)
- Doctor availability check before booking
- Scheduling conflict prevention (no double-booking)
- Global exception handling with proper HTTP status codes
- Database versioning with Liquibase
- Bean validation on all request bodies
