CREATE DATABASE IF NOT EXISTS appDB;
USE appDB;

CREATE TABLE IF NOT EXISTS universities (
    id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS students (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  surname VARCHAR(40) NOT NULL,
  age INT NOT NULL,
  grade INT NOT NULL,
  id_university INT NOT NULL,
  foreign key (id_university) references universities (id),
  PRIMARY KEY (ID)
);


INSERT INTO universities (name, address) VALUES
    ("РТУ МИРЭА", "119454, город Москва, пр-кт Вернадского, д.78."),
    ("РАНХиГС", "Адрес: 119571, г. Москва, вн. тер. г. муниципальный округ Тропарево-Никулино, пр-кт Вернадского, д. 82, стр. 1.");


INSERT INTO students (name, surname, age, grade, id_university) VALUES
    ("Евгений", "Карелин", 20, 3, 1),
    ("Даня", "Привезенцев", 20, 3, 1),
    ("Егор", "Киреев", 20, 3, 2),
    ("Андрей", "Волков", 20, 3, 2),
    ("Дмитрий", "Чуркин", 20, 3, 1);
    