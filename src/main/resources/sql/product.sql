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

 Date: 07/12/2022 09:10:21
*/


-- ----------------------------
-- Table structure for ss_product
-- ----------------------------
DROP TABLE IF EXISTS "public"."ss_product";
CREATE TABLE "public"."ss_product" (
  "product_id" int8 NOT NULL DEFAULT nextval('ss_product_product_id_seq'::regclass),
  "product_count" int4,
  "product_description" varchar(255) COLLATE "pg_catalog"."default",
  "product_name" varchar(255) COLLATE "pg_catalog"."default",
  "product_price" float8,
  "specification" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user_id" int8,
  "update_time" timestamp(6),
  "update_user_id" int8
)
;

-- ----------------------------
-- Records of ss_product
-- ----------------------------
INSERT INTO "public"."ss_product" VALUES (3, 802, '五常米', '大米', 3.5, '袋', NULL, NULL, NULL, NULL);
INSERT INTO "public"."ss_product" VALUES (4, 332, '4K屏电视', '电视机', 5688, '台', NULL, NULL, NULL, NULL);
INSERT INTO "public"."ss_product" VALUES (5, 231, '美味的橙汁', '橙汁', 8.6, '瓶', NULL, NULL, NULL, NULL);
INSERT INTO "public"."ss_product" VALUES (1, 500, '智能5G手机', '手机', 5000, '台', NULL, NULL, NULL, NULL);
INSERT INTO "public"."ss_product" VALUES (2, 2500, '内蒙古牛肉', '牛肉', 80, '200g', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Primary Key structure for table ss_product
-- ----------------------------
ALTER TABLE "public"."ss_product" ADD CONSTRAINT "ss_product_pkey" PRIMARY KEY ("product_id");
