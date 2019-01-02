/*
SQLyog Enterprise - MySQL GUI v7.14 
MySQL - 5.5.29 : Database - youyamvc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`youyamvc` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `youyamvc`;

/*Table structure for table `admin_user` */

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(20) DEFAULT '' COMMENT '用户名',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `real_name` varchar(25) DEFAULT '' COMMENT '真名',
  `email` varchar(30) DEFAULT '' COMMENT '邮箱',
  `telephone` varchar(20) DEFAULT '' COMMENT '座机号',
  `mobile_phone` varchar(20) DEFAULT '' COMMENT '手机号',
  `address` varchar(100) DEFAULT '' COMMENT '手机号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `super_admin` tinyint(2) DEFAULT '0' COMMENT '是否超级管理员',
  `role_id` bigint(20) DEFAULT '0' COMMENT '角色',
  PRIMARY KEY (`id`),
  KEY `idx_user_name` (`user_name`),
  KEY `FK_admin_user` (`role_id`),
  CONSTRAINT `FK_admin_user` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `admin_user` */

insert  into `admin_user`(`id`,`user_name`,`password`,`real_name`,`email`,`telephone`,`mobile_phone`,`address`,`create_time`,`update_time`,`super_admin`,`role_id`) values (1,'admin','admin','好的','','','','','2016-05-07 17:37:41','2016-06-01 14:08:14',1,1),(2,'test','test','','','','','','2016-06-01 21:12:34','2016-06-01 21:12:34',0,3);

/*Table structure for table `class_teacher` */

DROP TABLE IF EXISTS `class_teacher`;

CREATE TABLE `class_teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级关联老师表主键',
  `class_id` bigint(20) DEFAULT '0' COMMENT '班级表主键',
  `teacher_id` bigint(20) DEFAULT '0' COMMENT '老师表主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`),
  KEY `id` (`id`),
  KEY `FK_class_teacher` (`class_id`),
  KEY `FK_class_teacher_t` (`teacher_id`),
  CONSTRAINT `FK_class_teacher` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`),
  CONSTRAINT `FK_class_teacher_t` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `class_teacher` */

insert  into `class_teacher`(`id`,`class_id`,`teacher_id`) values (1,1,1),(2,19,2),(3,22,3);

/*Table structure for table `classes` */

DROP TABLE IF EXISTS `classes`;

CREATE TABLE `classes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级主键',
  `class_name` varchar(50) DEFAULT '' COMMENT '班级名称',
  `student_count` int(4) DEFAULT '0' COMMENT '班级学生人数',
  `school_id` bigint(20) DEFAULT '0' COMMENT '学校id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`),
  KEY `id` (`id`),
  KEY `FK_classes` (`school_id`),
  CONSTRAINT `FK_classes` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='班级';

/*Data for the table `classes` */

insert  into `classes`(`id`,`class_name`,`student_count`,`school_id`) values (1,'高中1班',11,2),(2,'初中2班',12,1),(3,'初三1班',13,1),(4,'4班',1,1),(5,'5班',1,3),(6,'6班',12,3),(7,'7班',12,1),(8,'8班',12,2),(9,'9班',11,3),(10,'10班',11,2),(11,'11班',12,2),(12,'12班',12,1),(13,'13班',13,1),(14,'14班',14,1),(15,'15班',15,1),(16,'16班',16,1),(17,'17班',17,1),(18,'18班',18,3),(19,'19班',19,1),(20,'20班',20,1),(21,'21班',21,1),(22,'1506',40,4),(23,'1507',50,4);

/*Table structure for table `dict` */

DROP TABLE IF EXISTS `dict`;

CREATE TABLE `dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dict_key` varchar(50) DEFAULT '',
  `dict_value` varchar(1000) DEFAULT '',
  `dict_type` int(2) DEFAULT '0',
  `dict_desc` varchar(50) DEFAULT '',
  `dict_order` int(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `dict` */

insert  into `dict`(`id`,`dict_key`,`dict_value`,`dict_type`,`dict_desc`,`dict_order`) values (1,'d','d',0,'',0),(2,'e','e',0,'',0);

/*Table structure for table `module` */

DROP TABLE IF EXISTS `module`;

CREATE TABLE `module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module_name` varchar(20) DEFAULT '' COMMENT '模块唯一键',
  `module_url` varchar(200) DEFAULT '' COMMENT '模块URL',
  `module_category_id` bigint(20) DEFAULT '0' COMMENT '模块分类',
  `sort_num` int(2) DEFAULT '0' COMMENT '排序',
  `module_title` varchar(50) DEFAULT '' COMMENT '模块标题',
  PRIMARY KEY (`id`),
  KEY `FK_module` (`module_category_id`),
  CONSTRAINT `FK_module` FOREIGN KEY (`module_category_id`) REFERENCES `module_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='模块';

/*Data for the table `module` */

insert  into `module`(`id`,`module_name`,`module_url`,`module_category_id`,`sort_num`,`module_title`) values (1,'role','admin/role/list',4,1,'角色'),(2,'module','admin/module/list',4,2,'模块'),(3,'priority','admin/priority/list',4,3,'权限'),(4,'role_module_priority','admin/role_module_priority/list',4,5,'角色模块权限关联'),(5,'admin_user','admin/admin_user/list',2,1,'后台管理员'),(6,'user_web','admin/user_web/list',1,1,'网站用户'),(7,'module_category','admin/module_category/list',4,6,'模块分类'),(8,'school','admin/school/list',3,0,'学校'),(9,'classes','admin/classes/list',3,0,'班级'),(10,'teacher','admin/teacher/list',3,0,'老师'),(11,'student','admin/student/list',3,0,'学生'),(12,'class_teacher','admin/class_teacher/list',3,0,'班级关联老师列表'),(13,'dict','admin/dict/list',2,2,'数据字典');

/*Table structure for table `module_category` */

DROP TABLE IF EXISTS `module_category`;

CREATE TABLE `module_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module_category_name` varchar(20) DEFAULT '' COMMENT '模块名称',
  `sort_num` int(2) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='模块分类';

/*Data for the table `module_category` */

insert  into `module_category`(`id`,`module_category_name`,`sort_num`) values (1,'网站用户',1),(2,'管理员',2),(3,'演示功能',3),(4,'角色权限',4),(5,'其他',5);

/*Table structure for table `priority` */

DROP TABLE IF EXISTS `priority`;

CREATE TABLE `priority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `priority_name` varchar(20) DEFAULT '' COMMENT '权限名',
  `can_insert` char(1) DEFAULT '0' COMMENT '新增',
  `can_delete` char(1) DEFAULT '0' COMMENT '删除',
  `can_update` char(1) DEFAULT '0' COMMENT '编辑',
  `can_query` char(1) DEFAULT '0' COMMENT '查询',
  `can_truncate` char(1) DEFAULT '0' COMMENT '清空',
  `can_export` char(1) DEFAULT '0' COMMENT '导出',
  `can_import` char(1) DEFAULT '0' COMMENT '导入',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='权限';

/*Data for the table `priority` */

insert  into `priority`(`id`,`priority_name`,`can_insert`,`can_delete`,`can_update`,`can_query`,`can_truncate`,`can_export`,`can_import`) values (1,'超级','1','1','1','1','1','1','1'),(2,'高级','1','0','1','1','0','0','0'),(3,'普通','0','0','0','1','0','0','0');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(20) DEFAULT '' COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色';

/*Data for the table `role` */

insert  into `role`(`id`,`role_name`) values (1,'超级管理员'),(2,'高级管理员'),(3,'普通用户');

/*Table structure for table `role_module_priority` */

DROP TABLE IF EXISTS `role_module_priority`;

CREATE TABLE `role_module_priority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) DEFAULT '0' COMMENT '角色',
  `module_id` bigint(20) DEFAULT '0' COMMENT '模块',
  `priority_id` bigint(20) DEFAULT '0' COMMENT '权限',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_module` (`role_id`,`module_id`),
  KEY `FK_role_module_priority_m` (`module_id`),
  KEY `FK_role_module_priority_p` (`priority_id`),
  KEY `FK_role` (`role_id`),
  CONSTRAINT `FK_role_module_priority` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_role_module_priority_m` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_role_module_priority_p` FOREIGN KEY (`priority_id`) REFERENCES `priority` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='角色模块权限';

/*Data for the table `role_module_priority` */

insert  into `role_module_priority`(`id`,`role_id`,`module_id`,`priority_id`) values (6,1,1,1),(7,1,2,1),(9,1,3,1),(10,1,4,1),(11,1,5,1),(12,1,7,1),(13,1,11,1),(14,1,8,1),(15,1,9,1),(16,1,10,1),(17,1,12,1),(18,3,6,3),(19,1,6,1),(20,1,13,1);

/*Table structure for table `school` */

DROP TABLE IF EXISTS `school`;

CREATE TABLE `school` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学校主键',
  `school_name` varchar(50) DEFAULT '' COMMENT '学校名称',
  `head_img` varchar(50) DEFAULT '' COMMENT '学校头像',
  `class_count` int(4) DEFAULT '0' COMMENT '班级个数',
  `adress` text COMMENT '学校地址',
  `school_type` tinyint(2) DEFAULT '0' COMMENT '学校类型',
  `open` char(1) DEFAULT '0' COMMENT '是否开学',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `school_desc` longtext COMMENT '学校描述',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `school` */

insert  into `school`(`id`,`school_name`,`head_img`,`class_count`,`adress`,`school_type`,`open`,`create_time`,`school_desc`,`update_time`) values (1,'浙江大学','',0,'',1,'1',NULL,'','2016-05-31 15:48:04'),(2,'测试学校','',0,'',0,'0',NULL,'','2016-05-31 16:11:11'),(3,'测试学校','',20,'',0,'1',NULL,'','2016-05-31 15:48:18'),(4,'浙江大学','',0,'',0,'1','2016-05-28 09:08:55','','2016-05-31 16:11:18');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `name` varchar(50) DEFAULT '' COMMENT '学生名称',
  `class_id` bigint(20) DEFAULT NULL COMMENT '所属班级',
  `sex` tinyint(2) NOT NULL DEFAULT '0' COMMENT '性别',
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `admin_user_id` bigint(20) DEFAULT NULL COMMENT '管理员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`,`sex`),
  KEY `FK_student` (`admin_user_id`),
  CONSTRAINT `FK_student` FOREIGN KEY (`admin_user_id`) REFERENCES `admin_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`name`,`class_id`,`sex`,`id`,`admin_user_id`,`create_time`,`update_time`) values ('何**d',1,0,1,1,NULL,'2016-06-04 09:43:08'),('wll',1,0,2,1,'2016-05-07 17:24:55','2016-05-07 17:38:27'),('中国大学',1,1,3,1,'2016-06-04 09:43:26','2016-06-04 09:43:26');

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '老师主键',
  `teacher_name` varchar(50) DEFAULT '' COMMENT '老师名称',
  `age` int(4) DEFAULT '0' COMMENT '老师年龄',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `teacher` */

insert  into `teacher`(`id`,`teacher_name`,`age`) values (1,'何老师',31),(2,'王老师',30),(3,'何冬艳',32);

/*Table structure for table `user_web` */

DROP TABLE IF EXISTS `user_web`;

CREATE TABLE `user_web` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `user_name` varchar(20) DEFAULT '' COMMENT '登录名称',
  `user_password` varchar(100) DEFAULT '' COMMENT '登录密码存储加密后的值',
  `real_name` varchar(20) DEFAULT '' COMMENT '用户真名',
  `score_amount` decimal(12,2) DEFAULT '0.00' COMMENT '积分余额',
  `money_amount` decimal(12,2) DEFAULT '0.00' COMMENT '现金余额',
  `regist_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `account_status` tinyint(2) DEFAULT '0' COMMENT '账号状态0无效1有效',
  `sex` tinyint(1) DEFAULT '0' COMMENT '性别1男0女',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `head_img_src` varchar(100) DEFAULT '' COMMENT '头像地址',
  `account_level` tinyint(2) DEFAULT '0' COMMENT '账号等级',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机号',
  `nickname` varchar(30) DEFAULT '' COMMENT '昵称',
  `two_code_img_src` varchar(50) DEFAULT '' COMMENT '二维码图片',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `user_web` */

insert  into `user_web`(`id`,`user_name`,`user_password`,`real_name`,`score_amount`,`money_amount`,`regist_time`,`last_login_time`,`account_status`,`sex`,`birthday`,`head_img_src`,`account_level`,`mobile`,`nickname`,`two_code_img_src`) values (1,'11','','','0.00','0.00',NULL,NULL,0,0,NULL,'11',1,'1','','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
