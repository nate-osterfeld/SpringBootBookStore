# Development Setup

This guide explains how to **clone, run, and use the Bookstore application locally**.

The project consists of **two Spring Boot services**:
* **BookStore API** – backend REST API
* **Web Application** – Thymeleaf frontend that communicates with the API using Feign

Both services run locally and communicate over HTTP.

---

# 1. Clone the Repository

```bash
git clone https://github.com/nate-osterfeld/SpringBootBookStore.git
cd SpringBootBookStore
```

---

# 2. Prerequisites

Make sure the following tools are installed:
* **Java 17+**
* **Maven**
* **IntelliJ IDEA** (recommended)

The project uses an **embedded H2 database**, so no database installation is required.

---

# 3. Running the Project

There are **two ways to run the application**.

---

## Option 1 — Run Services Manually

Start each Spring Boot service individually.

### Start the API

Navigate to the API module and run the Spring Boot application:

```
BookStoreApiApplication
```

The API will start on:

```
http://localhost:5000
```

---

### Start the Web Application

Navigate to the web module and run:

```
WebApplication
```

The frontend will start on:

```
http://localhost:4000
```

---

## Option 2 — Run Services Using IntelliJ Services Tab (Recommended)

This allows you to **start and stop all services from one place**.

### Step 1 – Open One Module

Open either the **api** or **web** directory in IntelliJ.

---

### Step 2 – Add the Other Module

1. Open the **Maven Tool Window**
2. Click the **"+" icon**
3. Select the other module directory

This loads both modules into the same IntelliJ project.

---

### Step 3 – Verify Spring Boot Configurations

IntelliJ should automatically create **Spring Boot run configurations** for:
* `BookStoreApiApplication`
* `WebApplication`

---

### Step 4 – Add Them to the Services Tab

Open the **Services window**:

```
View → Tool Windows → Services
```

Then:
1. Click the **"+" icon**
2. Select **Run Configuration Type**
3. Choose **Spring Boot**

Both services will now appear in the Services panel.

You can now:
* Start all services
* Stop services
* Restart services
* View logs

from a **single interface**.

---

# 4. Sitemap

Once both services are running:


## Frontend Routes

Main site:

```
http://localhost:4000
```

Admin pages:

```
http://localhost:4000/admin/authors
http://localhost:4000/admin/books
```

These pages allow you to manage bookstore data through the UI.

---

## API Documentation

Interactive API documentation is available through **Swagger**:

```
http://localhost:5000/swagger
```

This allows you to:
* View all API endpoints
* Test requests
* Inspect request/response models
* Honestly just use Postman

---

# 5. Database

The project *currently* uses an **H2 in-memory database**.

Characteristics:
* Automatically starts with the application
* Data resets when the server restarts
* No external database configuration required

This makes the project **easy to run locally without setup**.

---

## H2 Database Console

You can inspect and interact with the database using the **H2 web console**:

```
http://localhost:5000/h2-console
```

This is useful when you want to **verify that data is being stored correctly by JPA/Hibernate** or manually query the database while developing new features.

To connect:
1. Open the H2 console link above
2. Use the JDBC URL configured in the application (jdbc:h2:mem:test)
3. Enter username as `sa` (no password required)
4. Click **Connect**

Once connected, you can browse tables and execute SQL queries directly against the running application database.

---

# 6. Technologies Used

Backend:
* **Spring Boot**
* **Spring Data JPA**
* **Hibernate**
* **H2 Database**

Frontend:
* **Spring Boot**
* **Thymeleaf**
* **OpenFeign**

Tools:
* **Maven**
* **Swagger/OpenAPI**