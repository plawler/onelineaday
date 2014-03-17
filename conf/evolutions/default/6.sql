# Commits schema

# --- !Ups

CREATE SEQUENCE commit_id_seq start with 100000;
CREATE TABLE commits (
  id integer NOT NULL DEFAULT nextval('commit_id_seq'),
  daily_id int,
  sha varchar(255),
  author varchar(255),
  committer varchar(255),
  url text,
  message text,
);

# --- !Downs

DROP TABLE commits;
DROP SEQUENCE commit_id_seq;
