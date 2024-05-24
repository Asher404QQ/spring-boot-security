DROP TABLE IF EXISTS spring.users;
DROP TABLE IF EXISTS spring.authorities;
DROP SCHEMA IF EXISTS spring;

CREATE SCHEMA IF NOT EXISTS spring;

CREATE TABLE IF NOT EXISTS spring.users (
                                            id bigserial not null,
                                            username varchar(55) not null,
                                            password text not null,
                                            enabled int not null,
                                            primary key (id)
);

create table if not exists spring.authorities(
                                                 id bigserial not null,
                                                 username varchar(55) not null,
                                                 authority varchar(55) not null,
                                                 primary key (id)
);