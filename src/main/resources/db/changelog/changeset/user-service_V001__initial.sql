CREATE TABLE users (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    username varchar(64) NOT NULL,
    password varchar(128) NOT NULL,
    active boolean DEFAULT true NOT NULL
);