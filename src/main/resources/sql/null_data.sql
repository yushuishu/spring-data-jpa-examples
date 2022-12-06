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

 Date: 05/12/2022 08:47:40
*/

DROP TABLE IF EXISTS "public"."ss_null_data";
CREATE TABLE "public"."ss_null_data" (
  "data_id" int8 NOT NULL DEFAULT nextval('ss_null_data_data_id_seq'::regclass),
  "create_time" timestamp(6),
  "create_user_id" int8,
  "update_time" timestamp(6),
  "update_user_id" int8,
  "data" varchar(255) COLLATE "pg_catalog"."default"
);

ALTER TABLE "public"."ss_null_data" ADD CONSTRAINT "ss_null_data_pkey" PRIMARY KEY ("data_id");
