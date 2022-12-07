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

 Date: 07/12/2022 09:08:37
*/


-- ----------------------------
-- Table structure for ss_address
-- ----------------------------
DROP TABLE IF EXISTS "public"."ss_address";
CREATE TABLE "public"."ss_address" (
  "address_id" int8 NOT NULL DEFAULT nextval('ss_address_address_id_seq'::regclass),
  "address_description" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of ss_address
-- ----------------------------
INSERT INTO "public"."ss_address" VALUES (3, '常山');
INSERT INTO "public"."ss_address" VALUES (2, '南阳');
INSERT INTO "public"."ss_address" VALUES (1, '安徽庐江县');
INSERT INTO "public"."ss_address" VALUES (4, '安徽亳州');
INSERT INTO "public"."ss_address" VALUES (8, '五原郡九原县');
INSERT INTO "public"."ss_address" VALUES (9, '汝南汝阳');
INSERT INTO "public"."ss_address" VALUES (10, '江苏徐州');
INSERT INTO "public"."ss_address" VALUES (11, '山西省平陆县');
INSERT INTO "public"."ss_address" VALUES (6, '山西省解县');
INSERT INTO "public"."ss_address" VALUES (7, '河北省保定');
INSERT INTO "public"."ss_address" VALUES (5, '河北省楼桑村');

-- ----------------------------
-- Primary Key structure for table ss_address
-- ----------------------------
ALTER TABLE "public"."ss_address" ADD CONSTRAINT "ss_address_pkey" PRIMARY KEY ("address_id");
