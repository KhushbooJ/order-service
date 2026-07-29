
CREATE TYPE categories AS ENUM ('ELECTRONICS',
    'BEAUTY',
    'FOOD',
    'PET_PRODUCTS',
    'DAILY_NEEDS',
    'FASHION',
    'FURNITURE',
    'WELLNESS');

CREATE TABLE users (
       id BIGSERIAL PRIMARY KEY,
       username VARCHAR(20) NOT NULL,
       email VARCHAR(100) NOT NULL,
       password VARCHAR(20) NOT NULL,
       phoneNumber VARCHAR(10) NOT NULL
);

CREATE TABLE product_categories (
    id BIGSERIAL PRIMARY KEY,
    category categories NOT NULL
);

CREATE TABLE products (
      id BIGSERIAL PRIMARY KEY,
      name VARCHAR NOT NULL,
      category_id BIGSERIAL REFERENCES product_categories(id),
      image_urls TEXT[],
      price NUMERIC(10,2)
);

CREATE TABLE inventory (
    id BIGSERIAL PRIMARY KEY,
    city VARCHAR(50),
    productId BIGSERIAL REFERENCES products(id),
    stock NUMERIC
);

CREATE TABLE carts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL REFERENCES users(id),
    product_id BIGSERIAL REFERENCES products(id),
    quantity INTEGER NOT NULL,
    cart_total NUMERIC(10,2)
);

CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL REFERENCES users(id),
    type VARCHAR(10) NOT NULL,
    line1 TEXT,
    line2 TEXT,
    landmark TEXT,
    CITY VARCHAR(50),
    STATE VARCHAR(50)
);

CREATE TABLE transactions (
      id BIGSERIAL PRIMARY KEY,
      session_id INTEGER NOT NULL,
      user_id BIGSERIAL REFERENCES users(id),
      status VARCHAR(10),
      amount NUMERIC(10,2),
      created_at TIMESTAMP
);
