# Users schema

# --- !Ups

CREATE SEQUENCE user_id_seq start with 100000;
CREATE TABLE users (
    id integer NOT NULL DEFAULT nextval('user_id_seq'),
    first_name varchar(255),
    last_name varchar(255),
    full_name varchar(255),
    email varchar(255),
    avatar_url text,
    identity_id varchar(255),
    provider_id varchar(255),
    authentication_method varchar(255),
    hasher varchar(255),
    password varchar(255),
    salt varchar(255),
    token varchar(255),
    string varchar(255),
    access_token varchar(255),
    token_type varchar(255),
    expires_in int,
    refresh_token varchar(255)
);

insert into users (id, first_name, last_name, full_name, email, identity_id, provider_id, authentication_method, hasher, password)
values (1, 'Test', 'User', 'Test User', 'test.user@onelineaday.me', 'testuser', 'userpass', 'userPassword', 'bcrypt', '$2a$10$9POGiyvjgHnDxVSgOqDZrOSAsljnBvyVfCpFT5CW73yaXyY3HQ7xq');

insert into users (id, first_name, last_name, full_name, email, identity_id, provider_id, authentication_method, hasher, password)
values (2, 'Paul', 'Lawler', 'Paul Lawler', 'paul.lawler@gmail.com', 'paullawler', 'userpass', 'userPassword', 'bcrypt', '$2a$10$9POGiyvjgHnDxVSgOqDZrOSAsljnBvyVfCpFT5CW73yaXyY3HQ7xq');

# --- !Downs

DROP TABLE users;
DROP SEQUENCE user_id_seq;