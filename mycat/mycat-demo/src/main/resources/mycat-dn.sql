CREATE SCHEMA `mycat_dn1` DEFAULT CHARACTER SET utf8 ;
CREATE SCHEMA `mycat_dn2` DEFAULT CHARACTER SET utf8 ;
CREATE SCHEMA `mycat_dn3` DEFAULT CHARACTER SET utf8 ;
CREATE SCHEMA `mycat_dn4` DEFAULT CHARACTER SET utf8 ;



CREATE TABLE `order_header` (
  `order_id` BIGINT NOT NULL DEFAULT 0 COMMENT '订单ID',
  `member_id` BIGINT NOT NULL DEFAULT 0 COMMENT '会员ID',
  `status` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '订单状态',
  `total` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '总金额',
  `discount` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '优惠金额',
  `payment` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '付款金额',
  `pay_time` DATETIME NOT NULL DEFAULT '1900-01-01' COMMENT '付款时间',
  `pay_status` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '付款状态',
  `contact` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '收货人',
  `phone` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '电话',
  `address` VARCHAR(70) NOT NULL DEFAULT '' COMMENT '详细地址',
  `created_at` DATETIME NOT NULL DEFAULT current_timestamp COMMENT '下单时间',
  `last_update` TIMESTAMP NOT NULL DEFAULT current_timestamp on update current_timestamp COMMENT '最后更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE=utf8_general_ci COMMENT = '订单主表';

CREATE TABLE `order_detail` (
  `detail_id` BIGINT NOT NULL DEFAULT 0 COMMENT '订单明细ID',
  `order_id` BIGINT NOT NULL DEFAULT 0 COMMENT '订单ID',
  `product_id` INT NOT NULL DEFAULT 0 COMMENT '产品ID',
  `sku` VARCHAR(25) NOT NULL DEFAULT '' COMMENT '产品编码',
  `title` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '产品标题',
  `quantity` INT NOT NULL DEFAULT 0 COMMENT '数量',
  `price` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '价格',
  `subtotal` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '小计金额',
  `discount` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '优惠金额',
  `created_at` DATETIME NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  `last_update` DATETIME NOT NULL DEFAULT current_timestamp on update current_timestamp COMMENT '最后更新时间',
  PRIMARY KEY (`detail_id`),
  INDEX `ix_order_id` (`order_id` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE=utf8_general_ci COMMENT = '订单明细';




CREATE TABLE `member` (
  `member_id` BIGINT NOT NULL DEFAULT 0 COMMENT '会员ID',
  `account` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '登录账号',
  `password` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '昵称',
  `mobile` VARCHAR(11) NOT NULL DEFAULT '' COMMENT '邮箱',
  `email` VARCHAR(40) NOT NULL DEFAULT '' COMMENT '手机号',
  `created_at` DATETIME NOT NULL DEFAULT current_timestamp COMMENT '注册时间',
  `last_update` DATETIME NOT NULL DEFAULT current_timestamp on update current_timestamp COMMENT '最后更新时间',
  PRIMARY KEY (`member_id`),
  UNIQUE INDEX `account_UNIQUE` (`account` ASC)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE=utf8_general_ci COMMENT = '会员主表';

CREATE TABLE `member_account` (
  `account` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '登录账号',
  `account_hash` int not null default 0 comment 'account的hashcode，分片字段',
  `member_id` BIGINT NOT NULL DEFAULT 0 COMMENT '会员ID',
  PRIMARY KEY (`account`),
  INDEX ix_account_hash (account_hash asc)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE=utf8_general_ci COMMENT = '会员：登录账号与ID对应关系';

CREATE TABLE `member_order` (
  `member_id` BIGINT NOT NULL DEFAULT 0 COMMENT '会员ID',
  `order_id` BIGINT NOT NULL DEFAULT 0 COMMENT '订单ID',
  PRIMARY KEY (`member_id`, `order_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE=utf8_general_ci COMMENT = '会员：会员ID与订单ID对应关系';