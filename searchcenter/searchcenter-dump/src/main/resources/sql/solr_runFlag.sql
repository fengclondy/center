CREATE TABLE `solr_runflag` (
  `id` bigint(20) NOT NULL,
  `type` bigint(255) DEFAULT NULL,
  `runFlag` varchar(255) DEFAULT NULL,
  `modifiedTime` datetime DEFAULT NULL,
  `describe` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `solr_runflag` (`id`,`type`,`runFlag`,`modifiedTime`,`describe`) VALUES (1,1,'0','2017-03-14 20:30:37','内部大厅商品');
INSERT INTO `solr_runflag` (`id`,`type`,`runFlag`,`modifiedTime`,`describe`) VALUES (2,2,'0','2017-03-14 22:16:10','内部包厢商品');
INSERT INTO `solr_runflag` (`id`,`type`,`runFlag`,`modifiedTime`,`describe`) VALUES (3,3,'0','2017-03-14 20:41:17','京东+商品');
INSERT INTO `solr_runflag` (`id`,`type`,`runFlag`,`modifiedTime`,`describe`) VALUES (4,4,'0','2017-03-14 21:15:03','外部商品');
INSERT INTO `solr_runflag` (`id`,`type`,`runFlag`,`modifiedTime`,`describe`) VALUES (5,5,'0','2017-03-14 21:17:13','秒杀商品');
INSERT INTO `solr_runflag` (`id`,`type`,`runFlag`,`modifiedTime`,`describe`) VALUES (6,6,'0','2017-03-14 23:37:05','店铺');
INSERT INTO `solr_runflag` (`id`,`type`,`runFlag`,`modifiedTime`,`describe`) VALUES (7,7,'0','2017-03-14 23:37:05','全量导入');
INSERT INTO `solr_runflag` (`id`,`type`,`runFlag`,`modifiedTime`,`describe`) VALUES (8,8,'0','2017-03-14 23:37:05','商品属性');
