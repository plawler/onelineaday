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
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (28, 1, 'Append or refresh Resource table on Daily view', 60, '2013-12-10', '2013-12-10');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (29, 1, 'Remove a resource', 30, '2013-12-11', '2013-12-11');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (30, 1, 'Rename the "Resources" column on the Daily view page. Remove hardcoded links.', 30, '2013-12-12', '2013-12-12');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (31, 1, 'Dynamic SQL for ProjectDaily list on Project view page', 75, '2013-12-13', '2013-12-13');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (32, 1, 'Complete installation of SecureSocial', 60, '2013-12-14', '2013-12-14');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (33, 1, 'Configuration of SecureSocial', 60, '2013-12-15', '2013-12-15');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (34, 1, 'Create user class for use with SecureSocial', 60, '2013-12-16', '2013-12-16');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (35, 1, 'Anorm parser for User', 60, '2013-12-17', '2013-12-17');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (36, 1, 'Anorm parser for User component types', 60, '2013-12-18', '2013-12-18');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (37, 1, 'Users table DDL', 60, '2013-12-19', '2013-12-19');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (38, 1, 'Test creation of Users and storage in database', 120, '2013-12-20', '2013-12-20');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (39, 1, 'Fix buttons on Daily view page', 60, '2013-12-21', '2013-12-21');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (40, 1, 'Modify UserService to store new user to database', 60, '2013-12-22', '2013-12-25');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (41, 1, 'Update the Projects entity to reference a User', 120, '2013-12-26', '2013-12-26');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (42, 1, 'Update User model to support usernames', 45, '2013-12-27', '2013-12-27');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (43, 1, 'Figure out unit testing secured controllers', 30, '2013-12-29', '2013-12-30');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (44, 1, 'Enforce unique usernames', 15, '2013-12-30', '2013-12-30');
-- insert into dailies (id, project_id, description, duration, created_on, completed_on) values (45, 1, 'Change users.identity_id to users.username', 60, '2013-12-30', null);
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (46, 1, 'Unit test paged list of dailies', 90, '2013-12-31', '2013-12-31');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (47, 1, 'Reorganize project dailies list', 60, '2014-01-01', '2014-01-02');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (48, 1, 'Cosmetic changes to project page', 60, '2014-01-02', '2014-01-02');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (49, 1, 'Add event calendar to project page', 180, '2014-01-03', '2014-01-03');
insert into dailies (id, project_id, description, duration, created_on, completed_on) values (50, 1, 'Test and fix project create with user_id', 45, '2014-01-04', null);

# --- !Downs

DROP TABLE dailies;
DROP SEQUENCE daily_id_seq;
