# Projects schema

# --- !Ups

CREATE SEQUENCE task_id_seq;
CREATE TABLE projects (
    id integer NOT NULL DEFAULT nextval('task_id_seq'),
    name varchar(255),
    description varchar(500),
    created_on timestamp
);

# --- !Downs

DROP TABLE projects;
DROP SEQUENCE task_id_seq;