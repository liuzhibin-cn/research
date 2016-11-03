CREATE SCHEMA `demo_db` DEFAULT CHARACTER SET utf8 ;
USE `demo_db`;
DROP TABLE IF EXISTS `demo_db`.`ord_order`;
CREATE TABLE `demo_db`.`ord_order` (
  `ord_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `ord_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
  `status` varchar(10) NOT NULL DEFAULT '' COMMENT '订单状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ord_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `demo_db`.`ord_address`;
CREATE TABLE `demo_db`.`ord_address` (
  `ord_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID',
  `province` varchar(10) NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(10) NOT NULL DEFAULT '' COMMENT '城市',
  `district` varchar(10) NOT NULL DEFAULT '' COMMENT '区县',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT '联系电话',
  `contact` varchar(20) NOT NULL DEFAULT '' COMMENT '联系人',
  `address` varchar(70) NOT NULL DEFAULT '' COMMENT '详细地址',
  PRIMARY KEY (`ord_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `demo_db`.`ord_order_detail`;
CREATE TABLE `demo_db`.`ord_order_detail` (
  `line_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  `ord_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID',
  `itm_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '产品ID',
  `itm_name` varchar(50) NOT NULL DEFAULT '' COMMENT '产品名称',
  `qty` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '数量',
  `price` decimal(12,3) NOT NULL DEFAULT '0.000' COMMENT '价格',
  `amt` decimal(12,3) NOT NULL DEFAULT '0.000' COMMENT '金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;