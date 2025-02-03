use timecore;
-- drop table users;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       fullname VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       mobile VARCHAR(20) UNIQUE NULL,
                       password VARCHAR(255) NOT NULL,
                       status ENUM('ACTIVE', 'INACTIVE', 'BANNED') DEFAULT 'ACTIVE',
                       is_verified BOOLEAN DEFAULT FALSE,
                       two_factor_auth_enabled BOOLEAN DEFAULT FALSE,
                       two_factor_auth_send_to ENUM('MOBILE', 'EMAIL') NULL,
                       picture VARCHAR(255) NULL,
                       role ENUM('USER', 'ADMIN') DEFAULT 'USER',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE events
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    summary         VARCHAR(255)          NOT NULL,
    start_date_time datetime              NOT NULL,
    end_date_time   datetime              NOT NULL,
    location        VARCHAR(255)          NULL,
    `description`   TEXT                  NULL,
    uid             VARCHAR(255)          NULL,
    created_at      datetime              NULL,
    updated_at      datetime              NULL,
    CONSTRAINT pk_events PRIMARY KEY (id)
);

CREATE TABLE user_events
(
    event_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL,
    CONSTRAINT pk_user_events PRIMARY KEY (event_id, user_id)
);

ALTER TABLE events
    ADD CONSTRAINT uc_events_uid UNIQUE (uid);

ALTER TABLE user_events
    ADD CONSTRAINT fk_useeve_on_event FOREIGN KEY (event_id) REFERENCES events (id);

ALTER TABLE user_events
    ADD CONSTRAINT fk_useeve_on_user FOREIGN KEY (user_id) REFERENCES users (id);
