CREATE DATABASE IF NOT EXISTS appDB;
CREATE USER IF NOT EXISTS `user`@`%` IDENTIFIED BY 'password';
GRANT SELECT,UPDATE,INSERT,DELETE ON appDB.* TO 'user'@'%';
FLUSH PRIVILEGES;

USE appDB;

CREATE TABLE IF NOT EXISTS Institute (
  id_inst INT(9) NOT NULL AUTO_INCREMENT,
  institute_name VARCHAR(200) NOT NULL,
  PRIMARY KEY (id_inst)
) DEFAULT CHARSET=utf8;


INSERT INTO Institute (id_inst, institute_name) VALUES
(1, 'Институт информационных технологий'),
(2, 'Институт искусственного интеллекта'),
(3, 'Институт киберпезопасности и цифровых технологий'),
(4, 'Институт перспективных технологий и индустриального программирования'),
(5, 'Институт радиоэлектроники и информатики'),
(6, 'Институт технологий управления'),
(7, 'Институт тонких химических технологий');

CREATE TABLE IF NOT EXISTS students (
  ID INT(9) NOT NULL AUTO_INCREMENT,
  name VARCHAR(25) NOT NULL,
  surname VARCHAR(40) NOT NULL,
  course INT(1) NOT NULL,
  id_inst INT(9) NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (id_inst) REFERENCES Institute (id_inst)
) DEFAULT CHARSET=utf8;

INSERT INTO students (name, surname, course, id_inst) VALUES
('Андрей', 'Волков', 3, 2),
('Данил', 'Виноградов', 3, 1),
('Евгений', 'Карелин', 1, 5),
('Андрей', 'Глухов', 4, 6),
('Леонид', 'Деев', 2, 3),
('Яна', 'Зарожина', 3, 4),
('Александр', 'Крылов', 4, 7);
