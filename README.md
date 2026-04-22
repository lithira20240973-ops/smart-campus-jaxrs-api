# Smart Campus Sensor & Room Management API

A clean, database-less RESTful API for managing smart campus sensors and rooms. Built exclusively with standard JAX-RS (Jersey) and Maven, without the use of Spring Boot.

## Architecture & Tech Stack
- **Framework**: JAX-RS (Jakarta RESTful Web Services)
- **Implementation**: Eclipse Jersey 
- **JSON Binding**: Jackson
- **Build Tool**: Maven
- **Database**: None (In-memory storage)

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+

### Running the Application Locally
We use the Jetty Maven Plugin for quick deployment and testing. 
To start the API, open your terminal at the root of the project and run:

```bash
mvn clean jetty:run
```

Once the server has started, the API will be available at:
`http://localhost:8080/api/v1/sensors`

## Project Structure
- `com.smartcampus.config`: Contains the JAX-RS `Application` configuration.
- `com.smartcampus.model`: Domain entities (e.g. `Sensor`, `Room`).
- `com.smartcampus.resource`: REST controllers / API endpoints.
- `com.smartcampus.repository`: In-memory data access stubs.
- `com.smartcampus.exception`: Global error handlers and `ExceptionMapper`s.
- `com.smartcampus.mapper`: Converters for Entity-to-DTO logic.
- `com.smartcampus.filter`: JAX-RS filters for logging, authentication, etc.