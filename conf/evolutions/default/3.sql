# --- !Ups

CREATE SEQUENCE resource_id_seq start with 100000;
CREATE TABLE resources (
    id integer NOT NULL DEFAULT nextval('resource_id_seq'),
    daily_id integer,
    url varchar(max),
    title varchar(255),
    comment varchar(500),
    tags varchar(255),
    created_on timestamp
);

insert into resources (id, daily_id, url, title, comment, tags, created_on)
values (1, 35, 'https://markatta.com/codemonkey/blog/2012/06/16/parsing-results-with-anorm-in-play-framework-2/', 'Parsing complex types with Anorm', null, 'anorm', '2013-12-17');

insert into resources (id, daily_id, url, title, comment, tags, created_on)
values (2, 35, 'https://markatta.com/codemonkey/blog/2012/08/10/unparsing-with-anorm-in-play-framework-2/', 'Unparsing schema into complex types with Anorm', null, 'anorm', '2013-12-17');

insert into resources (id, daily_id, url, title, comment, tags, created_on)
values (3, 35, 'http://blog.lunatech.com/2013/07/04/play-securesocial-slick', 'Play 2, SecureSocial and Slick', null, 'play2, securesocial, database', '2013-12-17');

# --- !Downs

DROP TABLE resources;
DROP SEQUENCE resource_id_seq;
