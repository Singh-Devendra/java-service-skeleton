DROP DATABASE IF EXISTS tms;
CREATE DATABASE IF NOT EXISTS tms;
USE tms;

DROP TABLE IF EXISTS employee, task, user;

/*!50503 set default_storage_engine = InnoDB */;
/*!50503 select CONCAT('storage engine: ', @@default_storage_engine) as INFO */;

CREATE TABLE employee (
    id 		INT NOT NULL AUTO_INCREMENT,
    name 	VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE task
( 
	id 			INT NOT NULL AUTO_INCREMENT, 
	description VARCHAR(255), 
	is_done     BOOLEAN, 
	target_date TIMESTAMP, 
	PRIMARY KEY (id) 
);

CREATE TABLE user
( 
	id          INT NOT NULL AUTO_INCREMENT,
	first_name 	VARCHAR(255), 
	last_name 	VARCHAR(255),
	PRIMARY KEY (id)
);
