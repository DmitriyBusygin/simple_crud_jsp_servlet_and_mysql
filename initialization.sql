# Создать БД, для нашего приложения
CREATE DATABASE simple_crud_jsp_servlet_and_mysql;

# Создать таблицу для хранения данных
CREATE TABLE simple_crud_jsp_servlet_and_mysql.users
(
    id           INT NOT NULL AUTO_INCREMENT,
    fio          VARCHAR(35) NULL,
    phoneNumber  INT NULL,
    technologies VARCHAR(50) NULL,
    PRIMARY KEY (id)
);