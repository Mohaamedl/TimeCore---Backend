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
