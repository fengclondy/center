CREATE TABLE `sync_time` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` bigint(20) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `last_sync_time` timestamp NULL DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


INSERT INTO `sync_time` (`id`,`type`,`start_time`,`last_sync_time`,`description`) VALUES (1,1,'2016-01-01 12:00:00','2012-03-13 22:14:01','内部大厅商品更新时间');
INSERT INTO `sync_time` (`id`,`type`,`start_time`,`last_sync_time`,`description`) VALUES (2,2,'2016-01-01 12:00:00','2017-03-15 13:26:00','内部包厢商品更新时间');
INSERT INTO `sync_time` (`id`,`type`,`start_time`,`last_sync_time`,`description`) VALUES (3,3,'2016-01-01 12:00:00','2012-03-13 22:14:01','京东+商品更新时间');
INSERT INTO `sync_time` (`id`,`type`,`start_time`,`last_sync_time`,`description`) VALUES (4,4,'2016-01-01 12:00:00','2012-03-15 13:26:00','外部商品更新时间');
INSERT INTO `sync_time` (`id`,`type`,`start_time`,`last_sync_time`,`description`) VALUES (5,5,'2016-01-01 12:00:00','2012-03-13 22:14:01','秒杀商品更新时间');
INSERT INTO `sync_time` (`id`,`type`,`start_time`,`last_sync_time`,`description`) VALUES (6,6,'2016-01-01 12:00:00','2012-03-15 13:26:00','店铺更新时间');
INSERT INTO `sync_time` (`id`,`type`,`start_time`,`last_sync_time`,`description`) VALUES (7,7,'2016-01-01 12:00:00','2012-03-15 13:26:00','全量时间');
INSERT INTO `sync_time` (`id`,`type`,`start_time`,`last_sync_time`,`description`) VALUES (8,8,'2016-01-01 12:00:00','2012-03-15 13:26:00','商品属性更新时间');

