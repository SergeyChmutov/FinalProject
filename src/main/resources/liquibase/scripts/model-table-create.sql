-- liquibase formatted sql

-- changeset Sergey Chmutov:1
CREATE TABLE IF NOT EXISTS avatars
(
    id serial NOT NULL,
    file_size bigint NOT NULL,
    media_type character varying(255) NOT NULL,
    data oid NOT NULL,

    CONSTRAINT avatars_pkey PRIMARY KEY (id)
);

-- changeset Sergey Chmutov:2
CREATE TABLE users
(
    id serial NOT NULL,
    email character varying(32) NOT NULL,
    password character varying(16) NOT NULL,
    first_name character varying(16) NOT NULL,
    last_name character varying(16) NOT NULL,
    phone character varying(25) NOT NULL,
    role character varying(25) NOT NULL,
    image character varying(255),
    avatar_id integer,

    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT avatar_id_fkey FOREIGN KEY (avatar_id)
        REFERENCES avatars (id)
);

-- changeset Sergey Chmutov:3
ALTER TABLE users
    ADD CONSTRAINT email_unique UNIQUE (email)
;

-- changeset Sergey Chmutov:4
CREATE TABLE images
(
    id serial NOT NULL,
    data oid NOT NULL,
    file_size bigint NOT NULL,
    media_type character varying(255) NOT NULL,
    path character varying(255) NOT NULL,

    CONSTRAINT images_pkey PRIMARY KEY (id)
);

-- changeset Sergey Chmutov:5
CREATE TABLE ads
(
    pk serial NOT NULL,
    description character varying(64) NOT NULL,
    price integer NOT NULL,
    title character varying(32) NOT NULL,
    image_id integer NOT NULL,
    user_id integer NOT NULL,

    CONSTRAINT ads_pkey PRIMARY KEY (pk),
    CONSTRAINT user_id_fkey FOREIGN KEY (user_id)
        REFERENCES users (id),
    CONSTRAINT image_id_fkey FOREIGN KEY (image_id)
        REFERENCES images (id)
);