# Commits schema

# --- !Ups

CREATE SEQUENCE commit_id_seq start with 100000;
CREATE TABLE commits (
  id integer NOT NULL DEFAULT nextval('commit_id_seq'),
  repository_id int,
  daily_id int,
  sha varchar(255),
  author varchar(255),
  committer varchar(255),
  url text,
  message text
);

insert into commits (id, repository_id, daily_id, sha, author, committer, url, message)
values (1, 1, 54, 'dksdlkfjdslkjflkjlsdflklks', 'plawler', 'plawler', 'http://github.com/plawler/repos/commits/dksdlkfjdslkjflkjlsdflklks', 'fake test commit');

# --- !Downs

DROP TABLE commits;
DROP SEQUENCE commit_id_seq;
