SET FOREIGN_KEY_CHECKS=0;
drop table if exists `SYS_USER`;
CREATE TABLE `SYS_USER` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cnname` VARCHAR(11),
  `username` VARCHAR(11),
  `password` VARCHAR(11),
  `telephone` VARCHAR(11),
  `mobile_phone` VARCHAR(11),
  `wechat_id` VARCHAR(11),
  `skill` VARCHAR(11),
  `department_id` VARCHAR(11),
  `login_count` VARCHAR(11),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `SYS_ROLE`;
CREATE TABLE `SYS_ROLE` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` VARCHAR(11),
  `role_level` VARCHAR(11),
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11),
  `role_id` int(11),
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(11),
  `permission_url` varchar(11),
  `method` varchar(11),
  `description` varchar(11),
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) ,
  `permission_id` int(11),
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into SYS_USER (username, password) values ('admin', 'admin');
insert into SYS_USER (username, password) values ('abel', 'abel');
insert into SYS_ROLE(name) values('ROLE_ADMIN');
insert into SYS_ROLE(name) values('ROLE_USER');
insert into user_role(USER_ID,ROLE_ID) values(1,1);
insert into user_role(USER_ID,ROLE_ID) values(2,2);
INSERT INTO `permission` VALUES ('1', 'ROLE_HOME', 'home', '/', null), ('2', 'ROLE_ADMIN', 'ABel', '/admin', null);
INSERT INTO `role_permission` VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '2', '1');


drop table if exists `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(11),
  `permission_url` varchar(50),
  `method` varchar(11),
  `description` varchar(11),
  `parent_id`  int(11),
  `level` int(4),
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `role_menu`;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id`  int(11),
  `menu_id`  int(11),
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

