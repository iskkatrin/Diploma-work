-- liquibase formatted sql
-- changeset myUser:1
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(10) NOT NULL
);




INSERT INTO users (user_id, email, first_name, last_name, password, phone, role)
VALUES
('4','admin@example.com', 'Ivan', 'Ivanov', '$2a$12$k4Bff7hs/AJU31fUooSEru7j1foR8r5xHNwwDHkNv5w4uhpPa3ztC', '+37165915855', 'ADMIN'),
('5','user1@example.com', 'John', 'Doe', '$2a$12$ayiE5ZGZNirlaVt53pNPhO4NqbfIKoNRWMrQP9SMhlm6PfB/TX2Va', '+782554856565', 'USER'),
('6', 'user2@example.com', 'Sofa', 'Smith', '$2a$12$QcKae71vQHNTzeTssaGyPegfN6mmPGXDEAAbF2trZcGdfNK1JHGim', '+44525525255', 'USER');


