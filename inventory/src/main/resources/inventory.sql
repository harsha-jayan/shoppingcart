DROP DATABASE IF EXISTS inventory_database;
CREATE DATABASE inventory_database; 
USE inventory_database;

DROP TABLE IF EXISTS inventory;
CREATE TABLE IF NOT EXISTS inventory (
  productId varchar(20) NOT NULL,
  quantity int unsigned DEFAULT NULL,
  PRIMARY KEY (productId)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

INSERT INTO inventory (productId, quantity) VALUES
	('id3', 10),
	('id4', 20),
	('id5', 30);
commit;
show databases;
use inventory_database;
show tables;
select * from inventory;
