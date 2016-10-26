CREATE SCHEMA `demo_db` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `demo_db`.`sys_user` (
  `usr_id` INT NOT NULL AUTO_INCREMENT,
  `usr_name` VARCHAR(30) NOT NULL DEFAULT '',
  `create_time` DATETIME NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (`usr_id`));