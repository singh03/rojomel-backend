# Rojomel Backend - Spring Boot

This is the backend service for the Rojomel personal finance management app. It provides REST APIs to manage customers and finance entries (income/expense tracking).

---

## ⚙️ Tech Stack

- Java 21+
- Spring Boot
- Spring Data JPA
- MySQL (or any SQL database)
- Hibernate
- Maven

---

## 🚀 Setup Instructions

### 1. Clone the repo

```bash
git clone https://github.com/YOUR_USERNAME/rojomel-backend.git
cd rojomel-backend
```

2. Configure Database
Update application.properties (or application.yml) with your MySQL credentials:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/rojomel
spring.datasource.username=root
spring.datasource.password=your_password
Run the SQL DDL in pre.sql to create necessary tables.

3. Run Backend
bash
Copy
Edit
./mvnw spring-boot:run
The server will start on: http://localhost:8080

📘 API Endpoints
🔹 Customer APIs
Method	Endpoint	Description
GET	/api/customers	Get paginated customers
GET	/api/customers/{id}	Get customer by ID
POST	/api/customers	Create new customer

🔹 Finance Entry APIs
Method	Endpoint	Description
GET	/api/finance	Get all finance entries
POST	/api/finance	Create new income/expense entry

Each finance entry includes: income, expense, dateCreated, and the running outstandingBalance.
