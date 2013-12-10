# Dailies schema

# --- !Ups

CREATE SEQUENCE daily_id_seq start with 100000;
CREATE TABLE dailies (
    id integer NOT NULL DEFAULT nextval('daily_id_seq'),
    project_id integer,
    description varchar(max),
    duration integer,
    created_on timestamp,
    completed_on timestamp
);

insert into dailies (id, project_id, description, duration, created_on, completed_on) values (1, 1, 'Download and install Play framework', 60, '2013-11-16', '2013-11-017');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (2, 1, 'Set up repo in Github', 60, '2013-11-16', '2013-11-16');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (3, 1, 'Project model', 30, '2013-11-17', '2013-11-17');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (4, 1, 'Listing projects from database', 60, '2013-11-17', '2013-11-17');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (5, 1, 'Finish CRUD for Project model', 60, '2013-11-17', '2013-11-17');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (6, 1, 'Bootstrap and Webjars dependencies', 30, '2013-11-18', '2013-11-18');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (7, 1, 'JQuery and Webjars dependencies', 30, '2013-11-19', '2013-11-19');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (8, 1, 'Bootstrap integration', 90, '2013-11-20', '2013-11-20');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (9, 1, 'Form buttons using Bootstrap', 60, '2013-11-21', '2013-11-21');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (10, 1, 'Bootstrap form helpers using scala views', 120, '2013-11-22', '2013-11-22');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (11, 1, 'Mapping method instead of tuples to bind to forms', 60, '2013-11-23', '2013-11-23');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (12, 1, 'Daily model', 60, '2013-11-24', '2013-11-24');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (13, 1, 'Daily controller and form binding', 60, '2013-11-25', '2013-11-25');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (14, 1, 'Hidden fields for views', 60, '2013-11-26', '2013-11-26');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (15, 1, 'Daily object for database access', 60, '2013-11-27', '2013-11-27');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (16, 1, 'Correctly referencing data in the form map', 20, '2013-11-28', '2013-11-28');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (17, 1, 'Computing the daily streak', 20, '2013-11-29', '2013-11-29');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (18, 1, 'Project list page needs streak and started on fields', 20, '2013-11-30', '2013-11-30');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (19, 1, 'Complete the table columns for project dailies', 45, '2013-12-01', '2013-12-01');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (20, 1, 'Daily view page', 30, '2013-12-02', '2013-12-02');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (21, 1, 'Daily view details', 30, '2013-12-03', '2013-12-03');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (22, 1, 'Delete a daily', 30, '2013-12-04', '2013-12-04');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (23, 1, 'Flesh out the Resource model case class', 60, '2013-12-05', '2013-12-05');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (24, 1, 'Resource SQL for table definition and create', 60, '2013-12-06', '2013-12-06');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (25, 1, 'Bind Resource mapping to modal form', 60, '2013-12-07', '2013-12-07');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (26, 1, 'Return response from ajax post to Resource controller', 60, '2013-12-08', '2013-12-08');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (27, 1, 'Post Resource via ajax to Resources.create endpoint', 120, '2013-12-09', '2013-12-09');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (28, 1, 'Append or refresh Resource table on Daily view', 60, '2013-12-10', null);

# --- !Downs

DROP TABLE dailies;
DROP SEQUENCE daily_id_seq;
