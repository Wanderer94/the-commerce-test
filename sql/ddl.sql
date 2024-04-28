CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userid VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(20),
    email VARCHAR(100) UNIQUE
);
