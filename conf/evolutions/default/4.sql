# Users schema

# --- !Ups

CREATE SEQUENCE user_id_seq start with 100000;
CREATE TABLE users (
    id integer NOT NULL DEFAULT nextval('user_id_seq'),
    first_name nvarchar(255),
    last_name nvarchar(255),
    full_name nvarchar(255),
    email nvarchar(255),
    avatar_url nvarchar(max)
);

# --- !Downs

DROP TABLE users;
DROP SEQUENCE user_id_seq;