🎵 Songify
Welcome to Songify — a dynamic music catalog management system where you can explore, create, and organize your musical universe. Whether you're managing artists, albums, songs, or genres, Songify gives you the tools to keep your collection in perfect harmony.

🚀 Features
Artists: Add, update, and delete artists. Link them to albums and songs.

Albums: Manage album details and associate them with multiple artists.

Songs: Create and organize songs within albums, and assign genres.

Genres: Define musical styles and categorize your songs accordingly.

Relational Integrity: Smart deletion logic ensures that orphaned albums and songs are cleaned up automatically.

Security: Integrated Spring Security with JWT-based authentication and role-based access control.

Testing: Comprehensive unit and integration tests ensure reliability and maintainability across the system.

🛠️ Tech Stack
Java + Spring Boot: Backend framework for building robust REST APIs.

PostgreSQL: Relational database for storing musical data.

Flyway: Database migration tool for versioning and schema evolution.

Hibernate: ORM for seamless database interaction.

Spring Security: Authentication and authorization layer using JWT.

JUnit + Mockito: For writing clean unit and integration tests.

📦 Getting Started
Prerequisites
Java 17+

PostgreSQL installed and running

Maven or Gradle

Flyway configured (see application.properties)

Setup Instructions
Clone the repository

Configure the database Update your application.properties or application.yml with your PostgreSQL credentials:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/songify  
spring.datasource.username=your_username  
spring.datasource.password=your_password  
spring.jpa.hibernate.ddl-auto=none
Run Flyway migrations Flyway will automatically apply migrations on startup. Make sure your resources/db/migration folder contains the correct SQL scripts.

Start the application

📫 Contact
Have questions, suggestions, or want to collaborate? Feel free to reach out at: anastazjaglowska12345@gmail.com

💡 Inspiration
Songify was built to simplify music data management for developers, curators, and enthusiasts. Whether you're building a playlist app, a music analytics dashboard, or just love organizing music — Songify is your backstage pass to structured sound.
