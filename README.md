

# 🎵 Songify

**Songify** is a dynamic music catalog management system that lets you explore, create, and organize your musical universe. Whether you're managing artists, albums, songs, or genres, Songify gives you the tools to keep your collection in perfect harmony.

---

## 🚀 Features

- 🎤 **Artists**: Add, update, and delete artists. Link them to albums and songs.
- 💿 **Albums**: Manage album details and associate them with multiple artists.
- 🎶 **Songs**: Create and organize songs within albums, and assign genres.
- 🎼 **Genres**: Define musical styles and categorize your songs accordingly.
- 🧠 **Relational Integrity**: Smart deletion logic ensures orphaned albums and songs are cleaned up automatically.
- 🔐 **Security**: JWT-based authentication and role-based access control using Spring Security.
- 🧪 **Testing**: Includes both unit and integration tests for robust validation.
- 🗄️ **PostgreSQL**: Integrated relational database for scalable music data storage.

---

## 🛠️ Tech Stack

| Technology        | Purpose                                      |
|-------------------|----------------------------------------------|
| Java 17 + Spring Boot | Backend framework for RESTful APIs       |
| PostgreSQL        | Relational database                          |
| Flyway            | Database migration and versioning            |
| Hibernate         | ORM for database interaction                 |
| Spring Security   | Authentication and authorization             |
| JUnit + Mockito   | Unit and integration testing                 |

---

## 📦 Getting Started

### ✅ Prerequisites

- Java 17+
- PostgreSQL installed and running
- Maven or Gradle
- Flyway configured

### ⚙️ Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/songify.git
   cd songify
   ```

2. **Configure the database**  
   Update your `application.properties` or `application.yml`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/songify
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=none
   ```

3. **Run Flyway migrations**  
   Flyway will automatically apply migrations on startup. Ensure your SQL scripts are in `resources/db/migration`.

4. **Start the application**
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 📫 Contact

Have questions, suggestions, or want to collaborate?  
Reach out at: **anastazjaglowska12345@gmail.com**

---

## 💡 Inspiration

Songify was built to simplify music data management for developers, curators, and enthusiasts. Whether you're building a playlist app, a music analytics dashboard, or just love organizing music — Songify is your backstage pass to structured sound.

---


