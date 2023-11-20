CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    author VARCHAR(255),
    seller_number VARCHAR(255),
    product_type VARCHAR(255),
    price DECIMAL(10, 2),
    title VARCHAR(255)
);

CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    login VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE telephones (
    id SERIAL PRIMARY KEY,
    manufacturer VARCHAR(255),
    battery_capacity INTEGER,
    seller_number VARCHAR(255),
    product_type VARCHAR(255),
    price DECIMAL(10, 2),
    name VARCHAR(255)
);

CREATE TABLE washing_machines (
    id SERIAL PRIMARY KEY,
    manufacturer VARCHAR(255),
    tank_capacity INTEGER,
    seller_number VARCHAR(255),
    product_type VARCHAR(255),
    price DECIMAL(10, 2),
    name VARCHAR(255)
);