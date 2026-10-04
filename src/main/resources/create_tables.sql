CREATE TYPE categorytype AS ENUM (
    'ELECTRONICS',
    'BEAUTY',
    'FOOD',
    'PET_PRODUCTS',
    'DAILY_NEEDS',
    'FASHION',
    'FURNITURE',
    'WELLNESS'
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    category categorytype
);

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    price DOUBLE PRECISION,
    category BIGINT REFERENCES categories(id),
    image_urls TEXT[]
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    user_id BIGINT
);

CREATE TABLE IF NOT EXISTS order_products (
    order_id BIGINT REFERENCES orders(id),
    product_id BIGINT REFERENCES products(id)
);