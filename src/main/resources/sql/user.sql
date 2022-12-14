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

 Date: 07/12/2022 09:08:24
*/


-- ----------------------------
-- Table structure for ss_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."ss_user";
CREATE TABLE "public"."ss_user" (
  "user_id" int8 NOT NULL DEFAULT nextval('ss_user_user_id_seq'::regclass),
  "create_time" timestamp(6),
  "create_user_id" int8,
  "update_time" timestamp(6),
  "update_user_id" int8,
  "integrate" int4,
  "user_age" int4,
  "user_name" varchar(255) COLLATE "pg_catalog"."default",
  "address_id" int8
)
;

-- ----------------------------
-- Records of ss_user
-- ----------------------------
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (2, '2022-11-01 15:27:54', NULL, '2022-11-01 08:50:35', NULL, 20, 25, '关羽', 6);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (1, '2022-11-01 15:27:32', NULL, '2022-12-06 17:41:16.774', NULL, 10, 18, '刘备', 5);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (7, '2022-12-04 09:05:31', NULL, '2022-12-04 09:05:36', NULL, 35, 15, '周瑜', 1);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (8, '2022-12-05 09:06:49', NULL, '2022-12-05 09:06:52', NULL, 45, 22, '吕布', 8);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (9, '2022-12-05 09:07:46', NULL, '2022-12-05 09:07:48', NULL, 25, 30, '曹操', 4);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (10, '2022-12-05 09:08:22', NULL, '2022-12-05 09:08:25', NULL, 25, 20, '袁绍', 9);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (3, '2022-11-02 15:28:23', NULL, '2022-11-02 15:29:00', NULL, 20, 20, '张飞', 7);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (4, '2022-12-03 14:41:05', NULL, '2022-12-03 14:41:09', NULL, 50, 30, '赵云', 3);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (5, '2022-12-04 09:04:34', NULL, '2022-12-04 09:04:37', NULL, 30, 25, '诸葛亮', 2);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (6, '2022-12-04 09:05:06', NULL, '2022-12-04 09:05:11', NULL, 40, 18, '诸葛瑾', 2);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (11, '2022-12-06 09:30:49', NULL, '2022-12-06 09:30:52', NULL, 35, 30, '张昭', 10);
INSERT INTO "ss_user" ("user_id", "create_time", "create_user_id", "update_time", "update_user_id", "integrate", "user_age", "user_name", "address_id") VALUES (12, '2022-12-06 09:36:14', NULL, '2022-12-06 09:36:17', NULL, 20, 18, '周仓', 11);

-- ----------------------------
-- Primary Key structure for table ss_user
-- ----------------------------
ALTER TABLE "public"."ss_user" ADD CONSTRAINT "ss_user_pkey" PRIMARY KEY ("user_id");
