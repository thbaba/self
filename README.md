# Self
### Jungian Psychologist Application

**Project Overview**

`Self` is a web application inspired by Jungian psychology, 
designed to help users explore their inner world, reflect on personal experiences, and gain insights about their psyche.
designed to help users explore their inner world through 
journaling, AI-driven analysis, and a knowledge base of archetypes and symbols 
to facilitate self-awareness and personal growth.

**Purpose**

- Encourage users to write on personal experiences.
- Provide `AI-driven` psychological analysis and reflective questions tailored to each user.
- Educate users about Jungian archetypes and symbols for deeper understanding.

### Architecture
- `Spring Boot` Backend
- `REST API`s implemented for User, Insight, Analysis and Wiki modules.
- Uses `JdbcTemplate` for database operations.
- Uses `flyway` to handle `database migrations`.
- `JWT` authentication secures endpoints.
- Integrates AI engine to generate analyses and reflective questions.
- Tries apply `DDD` and `clean architecture` principles.

### Testing Practices

##### Test Infrastructure
- `Testcontainers` provides PostgreSQL instances for integration tests.
- SQL scripts populate test data for deterministic test scenarios.
- `Mocks` and `stubs` ensure unit tests remain isolated from external dependencies.
- `WireMock` allows the system to simulate external services.

##### Unit Tests

**Service layer tests:**
- UserRegistrationServiceTest `verifies` registration logic for adults and prevents registration for underage users.
- AnalysisServiceTest tests AI integration, analysis generation, and `exception handling`.

**Entity tests:**
- UserTest ensures isAdult() behaves correctly based on the BirthDate.
- UserBuilderTest confirms proper construction of immutable User instances.
- BirthDateTest validates age calculations around the 18-year boundary.

**AI Engine tests:**
- AIEngineUnitTest uses `WireMock` to simulate AI server responses for analysis and question generation, including `error handling`.

##### Integration Tests

**Repository layer tests:**
- UserRepositoryIntegrationTest ensures users can be persisted, queried, and blocked from duplicate registration.
- AnalysisRepositoryIntegrationTest verifies insight retrieval, analysis saving, and proper linkage between users, insights, and analyses.
- Uses `TestContainers`.


### Self API Documentation

This document explains how to use the endpoints of Insight API, Analysis API, Wiki API, and Registration API.

---

#### **1. User Registration**

**URI:**
```
POST /api/registration
```

**Description:** Registers a new user and returns a JWT token.

**Request Body:**

```json
{
  "birthDate": "2000-05-15",
  "gender": "MALE"
}
```

**Successful Response (HTTP 200):**

```json
{
  "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Error Response (HTTP 500):**

```json
{
  "error": "Registration failed!"
}
```

---

#### **2. Insight Endpoints**

##### **2.1 Save Insight**

**URI:**

```
POST /api/insight
```

**Description:** Saves a new insight for the authenticated user.

**Request Body:**

```json
{
  "title": "My Personal Reflection",
  "content": "Today I realized the importance of self-awareness."
}
```

**Successful Response (HTTP 201):**

- No content, status code 201 Created

**Error Response (HTTP 500):**

```java
/* no body */
```

##### **2.2 List Insights**

**URI:**

```
GET /api/insight?pageSize=10&page=0
```

**Description:** Returns a paginated list of insights for the authenticated user.

**Successful Response (HTTP 200):**

```json
[
  {
    "id": "a1b2c3d4",
    "title": "My Personal Reflection",
    "date": "2025-08-15T06:00:00",
    "author": "user123"
  }
]
```

##### **2.3 Get Single Insight**

**URI:**

```
GET /api/insight/{id}
```

**Description:** Retrieves a single insight by ID.

**Successful Response (HTTP 200):**

```json
{
  "id": "a1b2c3d4",
  "title": "My Personal Reflection",
  "date": "2025-08-15T06:00:00",
  "author": "user123"
}
```

**Error Response (HTTP 404):**

```java
/* no body */
```

---

#### **3. Analysis Endpoints**

##### **3.1 Analyze Insight**

**URI:**

```
POST /api/analysis/{insightID}
```

**Description:** Analyzes a user's insight and saves it.

**Successful Response (HTTP 200):**

```json
{
  "id": "analysis123",
  "analysis": "Shows strong self-awareness and unresolved patterns."
}
```

**Error Response (HTTP 200):**

```json
{
  "id": "random-id",
  "analysis": "Standard message!"
}
```

##### **3.2 Get Analysis by Insight ID**

**URI:**

```
GET /api/analysis/{insightID}
```

**Description:** Retrieves a previously generated analysis.

**Successful Response (HTTP 200):**

```json
{
  "id": "analysis123",
  "analysis": "Shows strong self-awareness and unresolved patterns."
}
```

**Error Response (HTTP 500):**

```json
{}
```

### **3.3 Generate Reflective Question**

**URI:**

```
GET /api/analysis/question
```

**Description:** Generates a reflective question based on past insights.

**Successful Response (HTTP 200):**

```json
{
  "question": "What recurring patterns do you notice in your writings?"
}
```

**Error Response (HTTP 500):**

```json
{}
```

##### **3.4 Get AI Model**

**URI:**

```
GET /api/analysis/model
```

**Successful Response (HTTP 200):**

```json
{
  "model": "gemma3:27b"
}
```

---

## **4. Wiki Endpoints**

### **4.1 Get Archetypes**

**URI:**

```
GET /api/wiki/archetype?pageSize=10&page=0
```

**Successful Response (HTTP 200):**

```json
[
  {
    "id": "arch1",
    "name": "Hero",
    "brief": "Represents courage and overcoming challenges."
  }
]
```

##### **4.2 Get Single Archetype**

**URI:**

```
GET /api/wiki/archetype/{id}
```

**Successful Response (HTTP 200):**

```json
{
  "id": "arch1",
  "name": "Hero",
  "brief": "Represents courage and overcoming challenges.",
  "description": "The Hero archetype is about bravery, achieving goals despite obstacles."
}
```

**Error Response (HTTP 404):**

```json
{
  "error": "There is no such archetype."
}
```

##### **4.3 Get Symbols**

**URI:**

```
GET /api/wiki/symbol?pageSize=10&page=0
```

**Successful Response (HTTP 200):**

```json
[
  {
    "id": "sym1",
    "name": "Circle",
    "brief": "Represents unity and wholeness."
  }
]
```

##### **4.4 Get Single Symbol**

**URI:**

```
GET /api/wiki/symbol/{id}
```

**Successful Response (HTTP 200):**

```json
{
  "id": "sym1",
  "name": "Circle",
  "brief": "Represents unity and wholeness.",
  "description": "The circle is an ancient symbol of completeness and infinity."
}
```

**Error Response (HTTP 404):**

```json
{
  "error": "There is no such symbol."
}
```

