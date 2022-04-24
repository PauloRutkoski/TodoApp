CREATE DATABASE `db_todo` CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE db_todo;

-- TABLE `users`
CREATE TABLE `users` (
     `id`       BIGINT NOT NULL AUTO_INCREMENT,
     `username` VARCHAR(60) NOT NULL,
     `password` VARCHAR(60) NOT NULL,
     PRIMARY KEY (`id`)
);

-- TABLE `settings`
CREATE TABLE `settings` (
     `id`       BIGINT NOT NULL AUTO_INCREMENT,
     `theme`    SMALLINT NOT NULL,
     `user_id`  BIGINT NOT NULL,
     PRIMARY KEY (`id`),
     FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);

-- TABLE `tasks`
CREATE TABLE `tasks` (
     `id`       BIGINT NOT NULL AUTO_INCREMENT,
     `title`    VARCHAR(100) NOT NULL,
     `position` BIGINT NOT NULL,
     `status`   VARCHAR(20) NOT NULL,
     `user_id`  BIGINT NOT NULL,
     PRIMARY KEY (`id`),
     FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);