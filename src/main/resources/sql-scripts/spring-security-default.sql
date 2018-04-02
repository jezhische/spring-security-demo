-- CREATE USER 'root'@'localhost' IDENTIFIED BY 'password';
-- GRANT ALL PRIVILEGES ON * . * TO 'root'@'localhost';

-- DROP DATABASE IF EXISTS USERSECURITY;
CREATE DATABASE IF NOT EXISTS USERSECURITY;
USE USERSECURITY;

set foreign_key_checks=0;
DROP TABLE if EXISTS users;
set foreign_key_checks=1;
create table users (
  username VARCHAR (50) not null,
  password VARCHAR (68) not null,
  enabled tinyint (1) not null,
  PRIMARY KEY (username)
) engine=InnoDB DEFAULT CHARSET=latin1;

drop table if exists authoriries;
create table authorities(
  username VARCHAR (50) not null,
  authority VARCHAR (50) not null,
  UNIQUE KEY authorities_idx_1 (username, authority),
  CONSTRAINT authorities_ibfk_1
  FOREIGN KEY (username)
  REFERENCES users (username)
) engine=InnoDB DEFAULT CHARSET=latin1;

