-- liquibase formatted sql

-- changeset Sergey_Chmutov:1
CREATE TABLE users
(
    id serial NOT NULL,
    email character varying(32) NOT NULL,
    first_name character varying(16)NOT NULL,
    last_name character varying(16) NOT NULL,
    phone character varying(25) NOT NULL,
    role character varying(25) NOT NULL,
    image character varying(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT email_unique UNIQUE (email)
);