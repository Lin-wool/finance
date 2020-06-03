/*
 Navicat Premium Data Transfer

 Source Server         : ucloud
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 117.50.18.49:3306
 Source Schema         : finance

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 01/06/2020 14:22:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for swipe
-- ----------------------------
DROP TABLE IF EXISTS `swipe`;
CREATE TABLE `swipe`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号（唯一）',
  `asin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品asin',
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `price` double(10, 3) NULL DEFAULT NULL COMMENT '产品价格',
  `commission` double(10, 3) NULL DEFAULT NULL COMMENT '产品佣金',
  `receive` double(10, 3) NULL DEFAULT NULL COMMENT '收到卖家返款金额',
  `rebate` double(10, 3) NULL DEFAULT NULL COMMENT '返款到买家金额',
  `order_date` datetime(0) NULL DEFAULT NULL COMMENT '订单日期',
  `rebate_date` datetime(0) NULL DEFAULT NULL COMMENT '返款日期',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单详情',
  PRIMARY KEY (`id`, `order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
