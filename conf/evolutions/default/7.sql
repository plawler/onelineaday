# retire a project

# --- !Ups

ALTER TABLE projects ADD COLUMN retired_on timestamp without time zone;

# --- !Downs