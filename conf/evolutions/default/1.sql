# Projects schema

# --- !Ups

CREATE SEQUENCE project_id_seq start with 100000;
CREATE TABLE projects (
    id integer NOT NULL DEFAULT nextval('project_id_seq'),
    user_id integer,
    name varchar(255),
    description varchar(500),
    created_on timestamp
);

insert into projects (id, user_id, name, description, created_on)
values (1, 2, 'The One Line a Day Project',
        'One Line a Day is my personal project to build personal projects. The idea is that you have a tremendous amount of high-quality work inside of you but do not have a system for getting it out. One Line a Day is that system so that day by day, your work can emerge from an idea to a real, live project.',
        '2013-11-01');

insert into projects (id, user_id, name, description, created_on)
values (2, 2, 'Off-Season Cycling Program', 'This is a project designed to build a base of cardiovascular and muscular endurance and stamina for cycling. This is necessary to prepare myself for the high intensity training during the regular season which requires a foundation of fitness in order to sustain the repeated power intervals that make up the bulk of race focused training.',
        '2013-11-30');

# --- !Downs

DROP TABLE projects;
DROP SEQUENCE project_id_seq;