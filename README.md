# order-service

Manages orders and user accounts. Part of the [ecomm-monorepo](../../README.md).

**Tech stack:** Spring Boot 4.1.0 · Spring Data JPA · PostgreSQL · Redis · Apache Kafka · Spring Security · Resilience4j

## Running

Spring Boot automatically starts the required Docker containers on startup via `spring-boot-docker-compose`:

```bash
cd apps/order-service
./mvnw spring-boot:run
```

This brings up:
- `order-db` — PostgreSQL 16 on host port `5433`
- `order-redis` — Redis 7 on host port `6379`

Make sure Docker is running before starting the service.

## Configuration

`src/main/resources/application.yaml`:

| Property | Value |
|---|---|
| Datasource URL | `jdbc:postgresql://localhost:5433/orders` |
| Datasource username | `kj` |
| Hibernate DDL auto | `update` |
| Docker Compose file | `apps/order-service/compose.yml` |

## API Endpoints

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/users/{username}` | Fetch a user by username (Redis-cached) |
| `POST` | `/api/users` | Register a new user |

## Domain Model

| Entity | Table | Notes |
|---|---|---|
| `User` | `users` | Stores credentials (bcrypt-hashed password) |
| `Order` | `orders` | Belongs to a user, links to products via `order_products` join table |
| `Product` | `products` | Belongs to a `Category`; `image_urls` stored as a PostgreSQL `text[]` array |
| `Category` | `categories` | Uses a native PostgreSQL `category_type` enum |

## Caching

User details are cached in Redis via `shared-utils` `RedisService`. The cache key is the username and TTL is 10 hours.

To inspect cached data:

```bash
docker exec -it order-service-order-redis-1 redis-cli
> KEYS *
> GET <username>
```

## Dependencies

- [`shared-utils`](../../libs/shared-utils/README.md) — security config, Redis service, exception handling