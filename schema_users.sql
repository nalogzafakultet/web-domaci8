CREATE TABLE accounts (
	id integer PRIMARY KEY AUTO_INCREMENT,
	username varchar(30) NOT NULL UNIQUE,
	password varchar(30) NOT NULL
);