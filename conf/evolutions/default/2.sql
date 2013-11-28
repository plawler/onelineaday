# Dailies schema

# --- !Ups

CREATE SEQUENCE daily_id_seq;
CREATE TABLE dailies (
    id integer NOT NULL DEFAULT nextval('daily_id_seq'),
    project_id integer,
    description varchar(max),
    duration integer,
    created_on timestamp,
    completed_on timestamp
);

# --- !Downs

DROP TABLE dailies;
DROP SEQUENCE daily_id_seq;
