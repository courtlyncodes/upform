# UpForm Backend

The UpForm backend is a Spring Boot API built to support a fitness tracking application. It allows users to create and manage workout sessions, log individual exercises, and track progress over time. The backend is designed with clean architecture principles and uses a service-repository pattern for separation of concerns.

## Tech Stack

- Java 17  
- Spring Boot 3  
- JPA with Hibernate  
- PostgreSQL (planned)  
- SLF4J + Logback for logging  
- RESTful API design  
- Gradle for build management

## Current Features

### User Management

- Create, update, retrieve, and delete users  
- Each user has associated workout sessions

### Workout Sessions

- Create, update, retrieve, and delete workout sessions  
- Sessions include date, difficulty, duration, notes, and a list of exercise logs  
- Filter by user ID and session ID for scoped access

### Exercise Logs

- Create, update, retrieve, and delete exercise logs  
- Each log includes name, sets, reps, weight, and perceived exertion  
- Logs are nested within a workout session

## Project Structure

- `dto`: Data Transfer Objects used for validation and response shaping  
- `model`: JPA entities mapped to database tables  
- `repository`: Spring Data JPA interfaces for persistence  
- `service`: Business logic layer  
- `exception`: Custom exceptions for clean error handling

## Next Steps

- Add validation annotations and custom exception messages  
- Implement full controller layer and secure endpoints with role-based auth  
- Integrate PostgreSQL and configure dev and prod environments  
- Add pagination and filtering options on GET endpoints  
- Write unit and integration tests using JUnit and Mockito  
- Deploy backend to a cloud provider (e.g. Render, Heroku, or AWS)  
- Connect with the frontend and support user authentication

## Future Features

- AI recommendations for progressive overload and rest days  
- Personalized goal tracking and workout suggestions  
- Admin dashboard for content and user management  
- Analytics endpoints for usage tracking and insights  
- OAuth2 authentication and JWT token support
