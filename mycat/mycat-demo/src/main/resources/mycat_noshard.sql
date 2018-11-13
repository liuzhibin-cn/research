CREATE SCHEMA `mycat_noshard` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `product` (
  `product_id` INT NOT NULL AUTO_INCREMENT COMMENT '产品ID',
  `sku` VARCHAR(25) NOT NULL DEFAULT '' COMMENT '产品编码',
  `category` VARCHAR(15) NOT NULL DEFAULT '' COMMENT '类目',
  `price` DECIMAL(8,2) NOT NULL DEFAULT 0 COMMENT '价格',
  `title` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '产品标题',
  `created_at` DATETIME NOT NULL DEFAULT '1900-01-01' COMMENT '创建时间',
  `last_update` TIMESTAMP NOT NULL DEFAULT current_timestamp on update current_timestamp COMMENT '最后更新时间',
  PRIMARY KEY (`product_id`),
  INDEX `uk_sku` (`sku` ASC)
) ENGINE = InnoDB AUTO_INCREMENT = 10000000 DEFAULT CHARACTER SET = utf8 COLLATE=utf8_general_ci COMMENT = '产品主表';

DROP TABLE IF EXISTS MYCAT_SEQUENCE;
CREATE TABLE MYCAT_SEQUENCE (name VARCHAR(64) NOT NULL,  current_value BIGINT(20) NOT NULL,  increment INT NOT NULL DEFAULT 1, PRIMARY KEY (name) ) ENGINE=InnoDB;

-- ----------------------------
-- Function structure for `mycat_seq_currval`
-- ----------------------------
DROP FUNCTION IF EXISTS `mycat_seq_currval`;
DELIMITER ;;
CREATE FUNCTION `mycat_seq_currval`(seq_name VARCHAR(64)) RETURNS varchar(64) CHARSET latin1
    DETERMINISTIC
BEGIN
    DECLARE retval VARCHAR(64);
    SET retval="-1,0";
    SELECT concat(CAST(current_value AS CHAR),",",CAST(increment AS CHAR) ) INTO retval FROM mycat_sequence  WHERE name = seq_name;
    RETURN retval ;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `mycat_seq_nextval`
-- ----------------------------
DROP FUNCTION IF EXISTS `mycat_seq_nextval`;
DELIMITER ;;
CREATE FUNCTION `mycat_seq_nextval`(seq_name VARCHAR(64)) RETURNS varchar(64) CHARSET latin1
    DETERMINISTIC
BEGIN
    DECLARE retval VARCHAR(64);
    DECLARE val BIGINT;
    DECLARE inc INT;
    DECLARE seq_lock INT;
    set val = -1;
    set inc = 0;
    SET seq_lock = -1;
    SELECT GET_LOCK(seq_name, 15) into seq_lock;
    if seq_lock = 1 then
      SELECT current_value + increment, increment INTO val, inc FROM mycat_sequence WHERE name = seq_name for update;
      if val != -1 then
          UPDATE mycat_sequence SET current_value = val WHERE name = seq_name;
      end if;
      SELECT RELEASE_LOCK(seq_name) into seq_lock;
    end if;
    SELECT concat(CAST((val - inc + 1) as CHAR),",",CAST(inc as CHAR)) INTO retval;
    RETURN retval;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `mycat_seq_setvals`
-- ----------------------------
DROP FUNCTION IF EXISTS `mycat_seq_nextvals`;
DELIMITER ;;
CREATE FUNCTION `mycat_seq_nextvals`(seq_name VARCHAR(64), count INT) RETURNS VARCHAR(64) CHARSET latin1
    DETERMINISTIC
BEGIN
    DECLARE retval VARCHAR(64);
    DECLARE val BIGINT;
    DECLARE seq_lock INT;
    SET val = -1;
    SET seq_lock = -1;
    SELECT GET_LOCK(seq_name, 15) into seq_lock;
    if seq_lock = 1 then
        SELECT current_value + count INTO val FROM mycat_sequence WHERE name = seq_name for update;
        IF val != -1 THEN
            UPDATE mycat_sequence SET current_value = val WHERE name = seq_name;
        END IF;
        SELECT RELEASE_LOCK(seq_name) into seq_lock;
    end if;
    SELECT CONCAT(CAST((val - count + 1) as CHAR), ",", CAST(val as CHAR)) INTO retval;
    RETURN retval;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `mycat_seq_setval`
-- ----------------------------
DROP FUNCTION IF EXISTS `mycat_seq_setval`;
DELIMITER ;;
CREATE FUNCTION `mycat_seq_setval`(seq_name VARCHAR(64), value BIGINT) RETURNS varchar(64) CHARSET latin1
    DETERMINISTIC
BEGIN
    DECLARE retval VARCHAR(64);
    DECLARE inc INT;
    SET inc = 0;
    SELECT increment INTO inc FROM mycat_sequence WHERE name = seq_name;
    UPDATE mycat_sequence SET current_value = value WHERE name = seq_name;
    SELECT concat(CAST(value as CHAR),",",CAST(inc as CHAR)) INTO retval;
    RETURN retval;
END
;;
DELIMITER ;

INSERT INTO MYCAT_SEQUENCE(name,current_value,increment) VALUES ('GLOBAL', 100000, 1);
INSERT INTO MYCAT_SEQUENCE(name,current_value,increment) VALUES ('ORDERDETAIL', 10000, 10);