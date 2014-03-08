# Repositories schema

# --- !Ups

CREATE SEQUENCE repository_id_seq start with 100000;
CREATE TABLE repositories (
  id integer NOT NULL DEFAULT nextval('repository_id_seq'),
  user_id int,
  project_id int,
  github_id int,
  name varchar(255),
  owner varchar(255)
);

insert into repositories (id, user_id, project_id, github_id, name, owner)
values (1, 2, 1, 14459879, 'onelineaday', 'plawler')

# --- !Downs

DROP TABLE repositories;
DROP SEQUENCE repository_id_seq;