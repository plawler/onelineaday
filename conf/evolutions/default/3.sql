# --- !Ups

CREATE SEQUENCE resource_id_seq start with 100000;
CREATE TABLE resources (
    id integer NOT NULL DEFAULT nextval('resource_id_seq'),
    daily_id integer,
    url varchar(max),
    title varchar(255),
    comment varchar(500),
    tags varchar(255),
    created_on timestamp
);

# --- !Downs

DROP TABLE resources;
DROP SEQUENCE resource_id_seq;
