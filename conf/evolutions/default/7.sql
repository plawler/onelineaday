# retire a project

# --- !Ups

ALTER TABLE projects ADD COLUMN retired_on timestamp;

# --- !Downs