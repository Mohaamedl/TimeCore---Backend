CREATE TABLE user
(
    id       BIGINT NOT NULL,
    fullname VARCHAR(255) NULL,
    email    VARCHAR(255) NULL,
    salt     VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    `role`   SMALLINT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);