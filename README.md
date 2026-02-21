# Songify – Music Catalog Management System

Songify is a structured and extensible music catalog management platform built with Spring Boot. It provides a robust API for managing artists, albums, songs, and genres, enabling users to organize and maintain a comprehensive music library. The project emphasizes clean architecture, relational integrity, secure access control, and production‑grade engineering practices.

## Key Features

- **Artist Management**: Create, update, and delete artists, and associate them with albums and songs.  
- **Album Management**: Maintain album metadata and link albums to one or multiple artists.  
- **Song Management**: Add and organize songs within albums, assign genres, and maintain consistent relationships.  
- **Genre Management**: Define musical genres and categorize songs accordingly.  
- **Relational Integrity**: Built‑in logic ensures that dependent entities (such as orphaned albums or songs) are automatically cleaned up when parent records are removed.  
- **Security**: JWT‑based authentication and role‑based authorization implemented with Spring Security.  
- **Testing**: Comprehensive unit and integration test coverage to ensure reliability and correctness.  
- **PostgreSQL Integration**: A relational database backend designed for scalability and structured data management.

## Technology Stack

| Technology | Purpose |
|-----------|----------|
| Java 17 + Spring Boot | Backend framework for RESTful APIs |
| PostgreSQL | Relational database for persistent storage |
| Flyway | Database versioning and migration management |
| Hibernate | ORM for database interaction |
| Spring Security | Authentication and authorization |
| JUnit + Mockito | Unit and integration testing |

## Getting Started

### Prerequisites
- Java 17 or higher  
- PostgreSQL installed and running  
- Maven or Gradle  
- Flyway configured for database migrations  

### Setup Instructions

Clone the repository:

```
git clone https://github.com/your-username/songify.git
cd songify
```

Configure the database by updating `application.properties` or `application.yml`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/songify
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=none
```

Flyway migrations will run automatically on application startup.  
Ensure your SQL migration scripts are located in `src/main/resources/db/migration`.

Start the application:

```
./mvnw spring-boot:run
```

## Contact

For questions, suggestions, or collaboration opportunities, contact:  
**anastazjaglowska12345@gmail.com**

## Project Motivation

Songify was created to provide a clean, maintainable, and scalable foundation for music‑related applications. Whether used as the backend for a playlist service, a music analytics dashboard, or a personal cataloging tool, Songify offers a structured and reliable approach to managing musical data.
