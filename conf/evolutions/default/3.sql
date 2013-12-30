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

insert into resources (id, daily_id, url, title, comment, tags, created_on)
values (4, 37, 'http://www.h2database.com/html/features.html#compatibility', 'H2 compatibility mode', null, 'h2, database', '2013-12-19');

insert into resources (id, daily_id, url, title, comment, tags, created_on)
values (5, 37, 'http://stackoverflow.com/questions/6761534/scala-with-keyword-usage', 'Scala ''with'' keyword' , null, 'scala', '2013-12-19');

insert into resources (id, daily_id, url, title, comment, tags, created_on)
values (6, 40, 'http://blog.orbeon.com/2011/04/scalas-optionsomenone.html', 'Forms Everywhere: The beauty of Scala''s Option/Some/None' , null, 'scala', '2013-12-22');

insert into resources (id, daily_id, url, title, comment, tags, created_on)
values (7, 40, 'https://groups.google.com/forum/#!searchin/securesocial/save$20method/securesocial/JzRsx5XX88U/W2tZEZr9xSoJ', 'SecureSocial.UserService.save method is really saveOrUpdate' , null, 'scala', '2013-12-22');

insert into resources (id, daily_id, url, title, comment, tags, created_on)
values (8, 43, 'http://stackoverflow.com/questions/17735636/testing-a-play2-application-with-securesocial-using-dependency-injection', 'Testing Play2 application with SecureSocial' , null, 'scala, testing, securesocial', '2013-12-29');

# --- !Downs

DROP TABLE resources;
DROP SEQUENCE resource_id_seq;
