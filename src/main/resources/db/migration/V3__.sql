CREATE TABLE roles
(
    id   INT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE token
(
    id        INT NOT NULL,
    value     VARCHAR(255) NULL,
    user_id   INT NULL,
    expiry_at datetime NULL,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

CREATE TABLE user
(
    id                INT    NOT NULL,
    name              VARCHAR(255) NULL,
    email             VARCHAR(255) NULL,
    hashed_password   VARCHAR(255) NULL,
    is_email_verified BIT(1) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);