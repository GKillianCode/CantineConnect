CREATE TABLE legal_guardian
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    public_id    VARCHAR(64)  NOT NULL UNIQUE,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    password     VARCHAR(30)  NOT NULL UNIQUE,
    phone_number VARCHAR(20)  NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE student_legal_guardian
(
    student_id BIGINT NOT NULL,
    legal_guardian_id BIGINT NOT NULL,
    PRIMARY KEY (student_id, legal_guardian_id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (legal_guardian_id) REFERENCES legal_guardian(id) ON DELETE CASCADE
);