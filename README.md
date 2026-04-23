# Smart Campus Sensor & Room Management API

## Overview
This is a RESTful JAX-RS API designed for managing campus rooms, sensors, and historical sensor readings in a Smart Campus scenario. It provides a robust backend interface for tracking physical spaces and IoT sensor data in real-time.

## Technology Stack
- Java
- Maven
- JAX-RS (Jersey)
- Jetty
- In-memory data structures

## API Base URL
The API is served locally. The base URL and versioned path is:
`http://localhost:8080/api/v1`

## Data Models
- **Room**: Represents a physical space on campus, containing properties `id` (String), `name` (String), `capacity` (int), and `sensorIds` (List<String>).
- **Sensor**: Represents an IoT device deployed within a room, tracking its `id` (String), `type` (String), `status` (String), `currentValue` (double), and `roomId` (String).
- **SensorReading**: Represents a single point-in-time data measurement from a specific sensor, including its `id` (String), `timestamp` (long), and `value` (double).

## Implemented Endpoints
- `GET /api/v1`
- `GET /api/v1/rooms`
- `POST /api/v1/rooms`
- `GET /api/v1/rooms/{roomId}`
- `DELETE /api/v1/rooms/{roomId}`
- `GET /api/v1/sensors`
- `POST /api/v1/sensors`
- `GET /api/v1/sensors/{sensorId}`
- `GET /api/v1/sensors/{sensorId}/readings`
- `POST /api/v1/sensors/{sensorId}/readings`

## Error Handling
- **409 Conflict**: Returned when attempting to delete a room that still has associated sensors.
- **422 Unprocessable Entity**: Returned when attempting to create a sensor linked to a non-existent `roomId`.
- **403 Forbidden**: Returned when attempting to post readings to a sensor that is currently in maintenance or otherwise unavailable.
- **500 Internal Server Error**: Catches unhandled, unexpected errors, providing a standardized JSON error response without leaking sensitive Java stack traces to the client.

## Logging
API requests and responses are monitored using a custom JAX-RS filter. The filter actively logs the incoming HTTP request method and URI, along with the outgoing response status code.

## How to Build
To compile the project and download dependencies, run:
```bash
mvn clean compile
```

## How to Run
To launch the embedded Jetty server, run:
```bash
mvn clean jetty:run
```

## Sample curl Commands

**Discovery**
```bash
curl -X GET http://localhost:8080/api/v1
```

**Create Room**
```bash
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id": "LIB-301", "name": "Main Library", "capacity": 200}'
```

**List Rooms**
```bash
curl -X GET http://localhost:8080/api/v1/rooms
```

**Get Room**
```bash
curl -X GET http://localhost:8080/api/v1/rooms/LIB-301
```

**Create Sensor**
```bash
curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id": "TEMP-001", "type": "TEMPERATURE", "status": "ACTIVE", "currentValue": 0.0, "roomId": "LIB-301"}'
```

**Filter Sensors by Type**
```bash
curl -X GET "http://localhost:8080/api/v1/sensors?type=TEMPERATURE"
```

**Post Reading**
```bash
curl -X POST http://localhost:8080/api/v1/sensors/TEMP-001/readings \
-H "Content-Type: application/json" \
-d '{"value": 22.5}'
```

**Get Readings**
```bash
curl -X GET http://localhost:8080/api/v1/sensors/TEMP-001/readings
```

**Delete Room**
```bash
curl -X DELETE http://localhost:8080/api/v1/rooms/LIB-301
```

## Coursework Question Answers

**JAX-RS resource lifecycle and in-memory data implications**
By default, JAX-RS resources are instantiated per-request. Because of this lifecycle, in-memory data structures (like repositories) must be implemented as Singletons (e.g., using static fields or dependency injection) and use thread-safe collections (like `ConcurrentHashMap`) to persist data safely across multiple concurrent HTTP requests.

**Why hypermedia/HATEOAS is useful**
HATEOAS (Hypermedia as the Engine of Application State) enables self-discoverability. It allows clients to navigate the API dynamically through links provided in responses, decoupling the client from hardcoded URIs and allowing the server to evolve its URL structure independently.

**IDs vs full objects for room lists**
Returning full objects in collections can lead to massive payloads (over-fetching). Returning only IDs (or lightweight summaries) in list endpoints improves performance, reduces bandwidth consumption, and forces the client to fetch detailed representations only when explicitly needed.

**Why DELETE is idempotent**
Idempotency means making multiple identical requests has the same effect as making a single request. `DELETE` is idempotent because deleting an already deleted resource should not alter the server's state further. Even if the first request returns a 204 (No Content) and subsequent ones return 404 (Not Found), the server's state remains unchanged.

**What happens with wrong media type on JSON-only endpoint**
If a client sends an unsupported media type (like XML or plain text) to an endpoint strictly annotated with `@Consumes(MediaType.APPLICATION_JSON)`, JAX-RS will automatically reject the request and return a `415 Unsupported Media Type` HTTP response.

**Why query parameters are better for filtering collections**
Path parameters (e.g., `/sensors/{id}`) identify specific individual resources, whereas query parameters (e.g., `/sensors?type=TEMP`) modify the retrieval of a collection. Query parameters are optional, composable, and better suited for operations like filtering, sorting, and pagination without cluttering the RESTful API hierarchy.

**Benefits of sub-resource locator pattern**
The sub-resource locator pattern (e.g., routing from `SensorResource` to `SensorReadingResource`) delegates the handling of nested endpoints to a separate class. This promotes the Single Responsibility Principle, keeps resource classes small, and allows complex hierarchical URIs to be managed modularly.

**Why 422 is more accurate than 404 for invalid linked roomId**
A `404 Not Found` indicates the target URI itself does not exist. A `422 Unprocessable Entity` indicates the server understands the content type and syntax of the request entity, but cannot process the contained instructions (e.g., validating a business rule like a `roomId` that does not exist in the referenced collection).

**Risks of exposing Java stack traces**
Exposing stack traces leaks internal implementation details, framework versions, and internal class structures to the client. This information can be weaponized by malicious actors to identify specific vulnerabilities in the server stack, leading to targeted security attacks.

**Why filters are better for cross-cutting logging**
Using a JAX-RS `ContainerRequestFilter` or `ContainerResponseFilter` centralizes logging logic, decoupling it from the business logic within individual resource methods. This prevents code duplication, ensures every endpoint is logged consistently, and adheres cleanly to aspect-oriented programming principles.
