# apps

Deployable Spring Boot microservices. Each service is an independently runnable JAR with its own `pom.xml`.

## Services

### order-service

Manages orders and user accounts.

**Tech stack:** Spring Boot 4.1.0 · Spring Data JPA · PostgreSQL · Apache Kafka · Spring Security · Resilience4j

**API endpoints:**

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/users/{username}` | Fetch a user by username |
| `POST` | `/api/users` | Register a new user |

**Domain model:**

| Entity | Table | Notes |
|---|---|---|
| `User` | `users` | Stores credentials (bcrypt-hashed password) |
| `Order` | `orders` | Belongs to a user, links to products via `order_products` join table |
| `Product` | `products` | Belongs to a `Category`; `image_urls` stored as a PostgreSQL `text[]` array |
| `Category` | `categories` | Uses a native PostgreSQL `category_type` enum |

**Configuration** (`src/main/resources/application.properties`):
- PostgreSQL datasource
- Kafka bootstrap servers
- Hibernate DDL auto

**Dependencies:** `shared-utils` (security config, exception handling)

### Running

```bash
cd apps/order-service
./mvnw spring-boot:run
```

Requires PostgreSQL running at `localhost:5432` and the schema from `libs/shared-utils/database/sql/create_tables.sql` applied.

## Adding a new service

1. Create a new directory under `apps/`.
2. Add a `pom.xml` with `spring-boot-starter-parent` as parent.
3. Register the module in the root `pom.xml` `<modules>` block.
4. Add `shared-utils` as a dependency to inherit shared security and exception handling.