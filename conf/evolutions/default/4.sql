# Users schema

# --- !Ups

CREATE SEQUENCE user_id_seq start with 100000;
CREATE TABLE users (
    id integer NOT NULL DEFAULT nextval('user_id_seq'),
    first_name nvarchar(255),
    last_name nvarchar(255),
    full_name nvarchar(255),
    email nvarchar(255),
    avatar_url nvarchar(max),
    identity_id nvarchar(255),
    provider_id nvarchar(255),
    authentication_method nvarchar(255),
    hasher nvarchar(255),
    password nvarchar(255),
    salt nvarchar(255),
    token nvarchar(255),
    string nvarchar(255),
    access_token nvarchar(255),
    token_type nvarchar(255),
    expires_in int,
    refresh_token nvarchar(255)
);

insert into users (id, first_name, last_name, full_name, email, identity_id, provider_id, authentication_method, hasher, password)
values (1, 'Test', 'User', 'Test User', 'test.user@onelineaday.me', 'test.user@onelineaday.me', 'userpass', 'userPassword', 'bcrypt', '$2a$10$9POGiyvjgHnDxVSgOqDZrOSAsljnBvyVfCpFT5CW73yaXyY3HQ7xq');

# --- !Downs

DROP TABLE users;
DROP SEQUENCE user_id_seq;