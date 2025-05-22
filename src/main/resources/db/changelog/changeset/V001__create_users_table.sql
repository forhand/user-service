CREATE TABLE users (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    username varchar(64) NOT NULL,
    age int NOT NULL DEFAULT 0,
    password varchar(128) NOT NULL,
    email varchar(64) NOT NULL UNIQUE,
    active boolean DEFAULT true NOT NULL
);