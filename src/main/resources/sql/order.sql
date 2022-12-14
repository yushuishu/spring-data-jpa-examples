/*
 Navicat Premium Data Transfer

 Source Server         : 谁书
 Source Server Type    : PostgreSQL
 Source Server Version : 130004
 Source Host           : localhost:5432
 Source Catalog        : jpa_examples
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 130004
 File Encoding         : 65001

 Date: 07/12/2022 09:10:07
*/


-- ----------------------------
-- Table structure for ss_order
-- ----------------------------
DROP TABLE IF EXISTS "public"."ss_order";
CREATE TABLE "public"."ss_order" (
  "order_id" int8 NOT NULL DEFAULT nextval('ss_order_order_id_seq'::regclass),
  "create_time" timestamp(6),
  "create_user_id" int8,
  "update_time" timestamp(6),
  "update_user_id" int8,
  "count" int8,
  "price" float8,
  "product_id" int8,
  "product_name" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "uid" varchar(255) COLLATE "pg_catalog"."default",
  "user_name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of ss_order
-- ----------------------------
INSERT INTO "ss_order" ("order_id", "create_time", "create_user_id", "update_time", "update_user_id", "count", "price", "product_id", "product_name", "status", "uid", "user_name") VALUES (1, '2022-11-27 16:27:23', 1, '2022-11-27 16:27:28', 1, 10, '50000', 1, '手机', 1, '123123423', '张三');
INSERT INTO "ss_order" ("order_id", "create_time", "create_user_id", "update_time", "update_user_id", "count", "price", "product_id", "product_name", "status", "uid", "user_name") VALUES (2, '2022-11-27 16:29:10', 1, '2022-11-27 16:29:16', 1, 1, '5000', 1, '手机', 2, '2354634456', '李四');
INSERT INTO "ss_order" ("order_id", "create_time", "create_user_id", "update_time", "update_user_id", "count", "price", "product_id", "product_name", "status", "uid", "user_name") VALUES (3, '2022-12-02 08:36:14', 1, '2022-12-02 08:38:05', 1, 10, '800', 2, '牛肉', 2, '67324535', '张三');
INSERT INTO "ss_order" ("order_id", "create_time", "create_user_id", "update_time", "update_user_id", "count", "price", "product_id", "product_name", "status", "uid", "user_name") VALUES (4, '2022-12-02 10:19:01', 1, '2022-12-02 10:19:07', 1, 20, '172', 5, '橙汁', 1, '73455235523', '王五');
INSERT INTO "ss_order" ("order_id", "create_time", "create_user_id", "update_time", "update_user_id", "count", "price", "product_id", "product_name", "status", "uid", "user_name") VALUES (5, '2022-12-02 10:20:24', 1, '2022-12-02 10:20:28', 1, 1, '5688', 4, '4K屏电视', 1, '234453454', '王五');
INSERT INTO "ss_order" ("order_id", "create_time", "create_user_id", "update_time", "update_user_id", "count", "price", "product_id", "product_name", "status", "uid", "user_name") VALUES (6, '2022-12-02 10:21:16', 1, '2022-12-02 10:21:20', 1, 2, '7', 3, '无常大米', 3, '234523423', '佩奇');

-- ----------------------------
-- Primary Key structure for table ss_order
-- ----------------------------
ALTER TABLE "public"."ss_order" ADD CONSTRAINT "ss_order_pkey" PRIMARY KEY ("order_id");
