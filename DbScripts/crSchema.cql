CREATE USER stfsrv WITH PASSWORD 'stfsrv';

CREATE KEYSPACE IF NOT EXISTS stefan_srv1 WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE stefan_srv1;

CREATE TABLE User (
  userId text PRIMARY KEY,
  passwordHash text,
  displayName text,
  roleId uuid,
  blocked boolean,
  tsLogin timestamp
);

CREATE TABLE Role (
  roleId uuid PRIMARY KEY,
  description text,
  permissions list<text>
);

grant all on stefan_srv1.User to stfsrv;
grant all on stefan_srv1.Role to stfsrv;
