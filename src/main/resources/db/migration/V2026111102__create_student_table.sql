CREATE TABLE student
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    public_id    VARCHAR(64)  NOT NULL UNIQUE,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    liaison_code VARCHAR(64)  NOT NULL UNIQUE
);