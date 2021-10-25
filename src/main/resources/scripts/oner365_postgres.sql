/*
 Navicat Premium Data Transfer

 Source Server         : localhost_postgresql
 Source Server Type    : PostgreSQL
 Source Server Version : 130001
 Source Host           : localhost:5432
 Source Catalog        : oner365
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 130001
 File Encoding         : 65001

 Date: 25/10/2021 09:27:22
*/


-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS "public"."hibernate_sequence";
CREATE TABLE "public"."hibernate_sequence" (
  "next_val" int8
)
;
ALTER TABLE "public"."hibernate_sequence" OWNER TO "postgres";
COMMENT ON TABLE "public"."hibernate_sequence" IS '序列表';

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for nt_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_data_source_config";
CREATE TABLE "public"."nt_data_source_config" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "connect_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "db_type" varchar(20) COLLATE "pg_catalog"."default",
  "ds_type" varchar(20) COLLATE "pg_catalog"."default",
  "db_name" varchar(50) COLLATE "pg_catalog"."default",
  "ip_address" varchar(20) COLLATE "pg_catalog"."default",
  "port" int4,
  "user_name" varchar(20) COLLATE "pg_catalog"."default",
  "password" varchar(32) COLLATE "pg_catalog"."default",
  "driver_name" varchar(255) COLLATE "pg_catalog"."default",
  "url" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "dept_code" varchar(40) COLLATE "pg_catalog"."default",
  "dept_name" varchar(40) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."nt_data_source_config" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_data_source_config"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_data_source_config"."connect_name" IS '连接名称';
COMMENT ON COLUMN "public"."nt_data_source_config"."db_type" IS '数据库类型';
COMMENT ON COLUMN "public"."nt_data_source_config"."ds_type" IS '数据源类型';
COMMENT ON COLUMN "public"."nt_data_source_config"."db_name" IS '数据库名称';
COMMENT ON COLUMN "public"."nt_data_source_config"."ip_address" IS 'ip地址';
COMMENT ON COLUMN "public"."nt_data_source_config"."port" IS '端口';
COMMENT ON COLUMN "public"."nt_data_source_config"."user_name" IS '账号';
COMMENT ON COLUMN "public"."nt_data_source_config"."password" IS '密码';
COMMENT ON COLUMN "public"."nt_data_source_config"."driver_name" IS '驱动名称';
COMMENT ON COLUMN "public"."nt_data_source_config"."url" IS '连接地址';
COMMENT ON COLUMN "public"."nt_data_source_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_data_source_config"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."nt_data_source_config"."dept_code" IS '部门编号';
COMMENT ON COLUMN "public"."nt_data_source_config"."dept_name" IS '部门名称';
COMMENT ON TABLE "public"."nt_data_source_config" IS '数据源表';

-- ----------------------------
-- Records of nt_data_source_config
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_data_source_config" VALUES ('4028e591736543e001736547e9950000', 'cloud', 'mysql', 'db', 'cloud', '192.168.1.1', 3306, 'root', '1234', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://192.168.1.1:3306/cloud', '2020-07-19 12:14:38', '2020-07-19 14:29:33', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_gateway_route
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_gateway_route";
CREATE TABLE "public"."nt_gateway_route" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "filters" varchar(255) COLLATE "pg_catalog"."default",
  "predicates" varchar(255) COLLATE "pg_catalog"."default",
  "route_order" int4 NOT NULL,
  "uri" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(8) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."nt_gateway_route" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_gateway_route"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_gateway_route"."filters" IS '过滤规则';
COMMENT ON COLUMN "public"."nt_gateway_route"."predicates" IS '表达式';
COMMENT ON COLUMN "public"."nt_gateway_route"."route_order" IS '排序';
COMMENT ON COLUMN "public"."nt_gateway_route"."uri" IS '地址';
COMMENT ON COLUMN "public"."nt_gateway_route"."status" IS '状态';
COMMENT ON TABLE "public"."nt_gateway_route" IS '路由表';

-- ----------------------------
-- Records of nt_gateway_route
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_gateway_route" VALUES ('bazl-dna-files', '[{"name":"StripPrefix","args":{"parts":"1"}}]', '[{"name":"Path","args":{"pattern":"/files/**"}}]', 1, 'lb://bazl-dna-files', '1');
INSERT INTO "public"."nt_gateway_route" VALUES ('bazl-dna-monitor', '[{"args": {"parts": "1"}, "name": "StripPrefix"}]', '[{"args": {"pattern": "/monitor/**"}, "name": "Path"}]', 1, 'lb://bazl-dna-monitor', '1');
INSERT INTO "public"."nt_gateway_route" VALUES ('bazl-dna-system', '[{"args": {"parts": "1"}, "name": "StripPrefix"}]', '[{"args": {"pattern": "/system/**"}, "name": "Path"}]', 1, 'lb://bazl-dna-system', '1');
INSERT INTO "public"."nt_gateway_route" VALUES ('bazl-dna-elasticsearch', '[{"args": {"parts": "1"}, "name": "StripPrefix"}]', '[{"args": {"pattern": "/elasticsearch/**"}, "name": "Path"}]', 1, 'lb://bazl-dna-elasticsearch', '1');
INSERT INTO "public"."nt_gateway_route" VALUES ('bazl-dna-generator', '[{"args": {"parts": "1"}, "name": "StripPrefix"}]', '[{"args": {"pattern": "/generator/**"}, "name": "Path"}]', 1, 'lb://bazl-dna-generator', '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_dict_item";
CREATE TABLE "public"."nt_sys_dict_item" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_item_type_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_item_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_item_name" varchar(32) COLLATE "pg_catalog"."default",
  "dict_item_order" int4,
  "parent_id" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(8) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."nt_sys_dict_item" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_dict_item"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_dict_item"."dict_item_type_id" IS '字典类型编号';
COMMENT ON COLUMN "public"."nt_sys_dict_item"."dict_item_code" IS '字典编号';
COMMENT ON COLUMN "public"."nt_sys_dict_item"."dict_item_name" IS '字典名称';
COMMENT ON COLUMN "public"."nt_sys_dict_item"."dict_item_order" IS '排序';
COMMENT ON COLUMN "public"."nt_sys_dict_item"."parent_id" IS '上级id';
COMMENT ON COLUMN "public"."nt_sys_dict_item"."status" IS '状态';
COMMENT ON TABLE "public"."nt_sys_dict_item" IS '字典表';

-- ----------------------------
-- Records of nt_sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_dict_item" VALUES ('1101', 'sys_normal_disable', '1', '有效', 1, NULL, '1');
INSERT INTO "public"."nt_sys_dict_item" VALUES ('1102', 'sys_normal_disable', '0', '无效', 2, NULL, '1');
INSERT INTO "public"."nt_sys_dict_item" VALUES ('1103', 'sys_user_sex', '0', '男', 1, NULL, '1');
INSERT INTO "public"."nt_sys_dict_item" VALUES ('1104', 'sys_user_sex', '1', '女', 2, NULL, '1');
INSERT INTO "public"."nt_sys_dict_item" VALUES ('4028b88174aa011e0174aa0a28c30004', 'sys_task_group', 'DEFAULT', '默认', 1, NULL, '1');
INSERT INTO "public"."nt_sys_dict_item" VALUES ('4028b88174aa011e0174aa0a51bc0005', 'sys_task_group', 'SYSTEM', '系统', 2, NULL, '1');
INSERT INTO "public"."nt_sys_dict_item" VALUES ('4028b88174aa011e0174aa0b35eb0006', 'sys_task_status', '1', '正常', 1, NULL, '1');
INSERT INTO "public"."nt_sys_dict_item" VALUES ('4028b88174aa011e0174aa0b66630007', 'sys_task_status', '0', '暂停', 2, NULL, '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_dict_item_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_dict_item_type";
CREATE TABLE "public"."nt_sys_dict_item_type" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_item_type_name" varchar(32) COLLATE "pg_catalog"."default",
  "dict_type_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_item_type_des" varchar(255) COLLATE "pg_catalog"."default",
  "dict_item_type_order" int4,
  "status" varchar(8) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."nt_sys_dict_item_type" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_dict_item_type"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_dict_item_type"."dict_item_type_name" IS '类型名称';
COMMENT ON COLUMN "public"."nt_sys_dict_item_type"."dict_type_code" IS '类型编号';
COMMENT ON COLUMN "public"."nt_sys_dict_item_type"."dict_item_type_des" IS '描述';
COMMENT ON COLUMN "public"."nt_sys_dict_item_type"."dict_item_type_order" IS '排序';
COMMENT ON COLUMN "public"."nt_sys_dict_item_type"."status" IS '状态';
COMMENT ON TABLE "public"."nt_sys_dict_item_type" IS '字典类型表';

-- ----------------------------
-- Records of nt_sys_dict_item_type
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_dict_item_type" VALUES ('sys_normal_disable', '状态', 'sys_normal_disable', '22ee22', NULL, '1');
INSERT INTO "public"."nt_sys_dict_item_type" VALUES ('sys_task_group', '任务分组', 'sys_task_group', '任务分组', NULL, '1');
INSERT INTO "public"."nt_sys_dict_item_type" VALUES ('sys_task_status', '任务状态', 'sys_task_status', NULL, NULL, '1');
INSERT INTO "public"."nt_sys_dict_item_type" VALUES ('sys_user_sex', '性别', 'sys_user_sex', '111', NULL, '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_fastdfs_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_fastdfs_file";
CREATE TABLE "public"."nt_sys_fastdfs_file" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "display_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "fastdfs_url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "file_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "file_path" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "file_suffix" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "is_directory" bool,
  "file_size" varchar(64) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."nt_sys_fastdfs_file" OWNER TO "postgres";

-- ----------------------------
-- Records of nt_sys_fastdfs_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_job
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_job";
CREATE TABLE "public"."nt_sys_job" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "job_info" varchar(32) COLLATE "pg_catalog"."default",
  "job_logo" varchar(32) COLLATE "pg_catalog"."default",
  "job_logo_url" varchar(32) COLLATE "pg_catalog"."default",
  "job_order" int4 NOT NULL,
  "parent_id" varchar(32) COLLATE "pg_catalog"."default",
  "status" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6)
)
;
ALTER TABLE "public"."nt_sys_job" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_job"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_job"."job_name" IS '职位名称';
COMMENT ON COLUMN "public"."nt_sys_job"."job_info" IS '职位信息';
COMMENT ON COLUMN "public"."nt_sys_job"."job_logo" IS '职位Logo';
COMMENT ON COLUMN "public"."nt_sys_job"."job_logo_url" IS 'Logo地址';
COMMENT ON COLUMN "public"."nt_sys_job"."job_order" IS '排序';
COMMENT ON COLUMN "public"."nt_sys_job"."parent_id" IS '上级id';
COMMENT ON COLUMN "public"."nt_sys_job"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_job"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_job"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."nt_sys_job" IS '用户职位表';

-- ----------------------------
-- Records of nt_sys_job
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_job" VALUES ('1', '研发部', '管理员', NULL, NULL, 1, '1', '1', '2020-04-24 14:31:41', '2020-09-05 20:43:36');
INSERT INTO "public"."nt_sys_job" VALUES ('4028b881745e46ae01745e4c3cd90008', '财务部', '财务', NULL, NULL, 2, NULL, '0', '2020-09-05 20:44:49', '2020-09-18 22:36:12');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_log";
CREATE TABLE "public"."nt_sys_log" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_ip" varchar(32) COLLATE "pg_catalog"."default",
  "method_name" varchar(8) COLLATE "pg_catalog"."default",
  "operation_name" varchar(255) COLLATE "pg_catalog"."default",
  "operation_path" varchar(255) COLLATE "pg_catalog"."default",
  "operation_context" text COLLATE "pg_catalog"."default",
  "create_time" timestamp(6)
)
;
ALTER TABLE "public"."nt_sys_log" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_log"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_log"."operation_ip" IS '操作ip';
COMMENT ON COLUMN "public"."nt_sys_log"."method_name" IS '请求方法';
COMMENT ON COLUMN "public"."nt_sys_log"."operation_name" IS '名称';
COMMENT ON COLUMN "public"."nt_sys_log"."operation_path" IS '请求路径';
COMMENT ON COLUMN "public"."nt_sys_log"."operation_context" IS '请求内容';
COMMENT ON COLUMN "public"."nt_sys_log"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."nt_sys_log" IS '系统日志表';

-- ----------------------------
-- Records of nt_sys_log
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca75794680000', '127.0.0.1', 'POST', 'system', '/system/auth/login', NULL, '2021-10-22 17:31:47.149');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca75796240001', '127.0.0.1', 'POST', 'system', '/system/user/list', NULL, '2021-10-22 17:31:47.613');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca75796240002', '127.0.0.1', 'POST', 'route', '/route/list', NULL, '2021-10-22 17:31:47.614');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca757967c0003', '127.0.0.1', 'POST', 'monitor', '/monitor/task/list', NULL, '2021-10-22 17:31:47.637');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca757a9bd0004', '127.0.0.1', 'POST', 'system', '/system/org/list', NULL, '2021-10-22 17:31:52.636');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca757afc20005', '127.0.0.1', 'POST', 'system', '/system/role/list', NULL, '2021-10-22 17:31:54.176');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca757b5220006', '127.0.0.1', 'POST', 'system', '/system/job/list', NULL, '2021-10-22 17:31:55.554');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca757bc010007', '127.0.0.1', 'POST', 'system', '/system/role/list', NULL, '2021-10-22 17:31:57.312');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca757c8a00008', '127.0.0.1', 'POST', 'system', '/system/role/editStatus/ff80808172a624c00172a67c70c30007', NULL, '2021-10-22 17:32:00.543');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca7581c8d0009', '127.0.0.1', 'POST', 'system', '/system/role/list', NULL, '2021-10-22 17:32:22.028');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca7582786000a', '127.0.0.1', 'POST', 'system', '/system/job/list', NULL, '2021-10-22 17:32:24.837');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca7582f71000b', '127.0.0.1', 'POST', 'system', '/system/role/list', NULL, '2021-10-22 17:32:26.864');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca75837e5000c', '127.0.0.1', 'POST', 'system', '/system/role/editStatus/ff80808172a624c00172a67c70c30007', NULL, '2021-10-22 17:32:29.029');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca7585856000d', '127.0.0.1', 'POST', 'system', '/system/dict/findTypeList', NULL, '2021-10-22 17:32:37.332');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca7585a84000e', '127.0.0.1', 'POST', 'system', '/system/menu/list', NULL, '2021-10-22 17:32:37.892');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca758615f000f', '127.0.0.1', 'POST', 'system', '/system/menuType/list', NULL, '2021-10-22 17:32:39.646');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca7587c740010', '127.0.0.1', 'POST', 'system', '/system/menuType/editStatusById/4028e5917365edc4017365efa54d0000', NULL, '2021-10-22 17:32:46.579');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca758aa770011', '127.0.0.1', 'POST', 'monitor', '/monitor/task/list', NULL, '2021-10-22 17:32:58.359');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca758b5770012', '127.0.0.1', 'POST', 'route', '/route/list', NULL, '2021-10-22 17:33:01.174');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca758e4790013', '127.0.0.1', 'POST', 'system', '/system/log/list', NULL, '2021-10-22 17:33:13.209');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca758f4890014', '127.0.0.1', 'POST', 'monitor', '/monitor/task/list', NULL, '2021-10-22 17:33:17.32');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca756b2017ca75900ec0015', '127.0.0.1', 'PUT', 'monitor', '/monitor/task/run', NULL, '2021-10-22 17:33:20.492');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca75a83017ca75ae8480000', '127.0.0.1', 'POST', 'monitor', '/monitor/task/list', NULL, '2021-10-22 17:35:25.224');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca75a83017ca75af3b60001', '127.0.0.1', 'PUT', 'monitor', '/monitor/task/run', NULL, '2021-10-22 17:35:28.181');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca76a26017ca76b1e5a0000', '127.0.0.1', 'POST', 'monitor', '/monitor/task/list', NULL, '2021-10-22 17:53:07.635');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca76a26017ca76b27bd0001', '127.0.0.1', 'PUT', 'monitor', '/monitor/task/run', NULL, '2021-10-22 17:53:10.075');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca76f3b017ca76f67a00000', '127.0.0.1', 'POST', 'monitor', '/monitor/task/list', NULL, '2021-10-22 17:57:48.545');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca76f3b017ca76f84e50001', '127.0.0.1', 'PUT', 'monitor', '/monitor/task/run', NULL, '2021-10-22 17:57:56.068');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca77122017ca771792f0000', '127.0.0.1', 'POST', 'monitor', '/monitor/task/list', NULL, '2021-10-22 18:00:04.109');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca77122017ca77184a50001', '127.0.0.1', 'PUT', 'monitor', '/monitor/task/run', NULL, '2021-10-22 18:00:07.076');
INSERT INTO "public"."nt_sys_log" VALUES ('ff8080817ca77122017ca771f6e80002', '127.0.0.1', 'PUT', 'monitor', '/monitor/task/run', NULL, '2021-10-22 18:00:36.327');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_menu";
CREATE TABLE "public"."nt_sys_menu" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_type_id" varchar(255) COLLATE "pg_catalog"."default",
  "menu_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "component" varchar(64) COLLATE "pg_catalog"."default",
  "path" varchar(64) COLLATE "pg_catalog"."default",
  "menu_order" int4 NOT NULL,
  "menu_description" varchar(255) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(8) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "another_name" varchar(32) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."nt_sys_menu" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_menu"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_menu"."menu_type_id" IS '菜单类型编号';
COMMENT ON COLUMN "public"."nt_sys_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "public"."nt_sys_menu"."parent_id" IS '上级菜单';
COMMENT ON COLUMN "public"."nt_sys_menu"."component" IS '元素';
COMMENT ON COLUMN "public"."nt_sys_menu"."path" IS '路径';
COMMENT ON COLUMN "public"."nt_sys_menu"."menu_order" IS '排序';
COMMENT ON COLUMN "public"."nt_sys_menu"."menu_description" IS '描述';
COMMENT ON COLUMN "public"."nt_sys_menu"."icon" IS 'Logo';
COMMENT ON COLUMN "public"."nt_sys_menu"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_menu"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."nt_sys_menu"."another_name" IS '别名';
COMMENT ON TABLE "public"."nt_sys_menu" IS '系统菜单表';

-- ----------------------------
-- Records of nt_sys_menu
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_menu" VALUES ('101', '1', '系统设置', '-1', 'Layout', '/system', 1, '系统设置', 'system', '1', '2020-05-11 14:00:21', '2020-05-11 14:00:25', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('1011', '1', '单位管理', '101', 'system/org/index', '/org', 11, '单位管理', 'tree', '1', '2020-05-11 14:03:06', '2020-05-11 14:03:11', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('1012', '1', '角色管理', '101', 'system/role/index', '/role', 12, '角色管理', 'peoples', '1', '2020-05-11 14:08:39', '2020-05-11 14:08:43', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('1013', '1', '用户职位', '101', 'system/job/index', '/job', 13, '用户职位', 'post', '1', '2020-08-05 21:48:33', '2020-08-05 21:48:37', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('1014', '1', '用户管理', '101', 'system/user/index', '/user', 14, '用户管理', 'user', '1', '2020-09-05 20:30:00', '2020-09-05 20:30:00', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('1015', '1', '字典管理', '101', 'system/dict/index', '/dict', 15, '字典管理', 'dict', '1', '2020-09-05 20:30:00', '2020-09-05 20:30:00', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('1016', '1', '菜单管理', '101', 'system/menu/index', '/menu', 16, '菜单管理', 'tree-table', '1', '2020-09-05 20:30:00', '2020-09-05 20:30:00', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('201', '1', '系统监控', '-1', 'Layout', '/monitor', 2, '系统监控', 'monitor', '1', '2020-09-03 17:20:13', '2020-09-17 20:57:13', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('2011', '1', '服务监控', '201', 'monitor/service/index', '/service', 21, '服务监控', 'druid', '1', '2020-09-03 17:21:32', '2020-09-17 20:57:02', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('2012', '1', '服务器监控', '201', 'monitor/server/index', '/server', 22, '服务器监控', 'server', '1', '2020-09-03 17:22:24', '2020-09-12 17:27:11', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('2013', '1', '定时任务', '201', 'monitor/task/index', '/task', 23, '定时任务', 'job', '1', '2020-09-19 11:57:23', '2020-09-19 11:57:25', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('2014', '1', '路由监控', '201', 'monitor/route/index', '/route', 24, '路由监控', 'cascader', '1', '2020-09-19 11:57:23', '2020-09-19 11:57:23', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('2015', '1', '缓存监控', '201', 'monitor/cache/index', '/cache', 25, NULL, 'time-range', '1', '2020-12-08 14:00:34', '2020-12-08 14:00:51', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('2016', '1', 'ES监控', '201', 'monitor/elasticsearch/index', '/elasticsearch', 26, 'ES监控', 'drag', '1', '2020-12-25 14:47:37', '2020-12-25 14:50:14', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('2017', '1', '队列监控', '201', 'monitor/rabbitmq/index', '/rabbitmq', 27, '队列监控', 'example', '1', '2020-12-25 14:48:35', '2020-12-25 14:50:19', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('2018', '1', '文档监控', '201', 'monitor/fastdfs/index', '/fastdfs', 28, '文档监控', 'documentation', '1', '2020-12-25 14:49:33', '2020-12-25 14:50:24', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('301', '1', '日志管理', '-1', 'Layout', '/log', 3, '日志管理', 'log', '1', '2020-11-16 15:36:00', '2020-11-16 15:36:00', NULL);
INSERT INTO "public"."nt_sys_menu" VALUES ('3011', '1', '服务日志', '301', 'system/log/index', '/syslog', 31, '服务日志', 'druid', '1', '2020-11-16 15:36:00', '2020-11-16 15:36:00', NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_menu_oper
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_menu_oper";
CREATE TABLE "public"."nt_sys_menu_oper" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."nt_sys_menu_oper" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_menu_oper"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_menu_oper"."menu_id" IS '菜单编号';
COMMENT ON COLUMN "public"."nt_sys_menu_oper"."operation_id" IS '操作编号';
COMMENT ON TABLE "public"."nt_sys_menu_oper" IS '菜单操作权限表';

-- ----------------------------
-- Records of nt_sys_menu_oper
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_menu_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_menu_type";
CREATE TABLE "public"."nt_sys_menu_type" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "type_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "type_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(8) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
ALTER TABLE "public"."nt_sys_menu_type" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_menu_type"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_menu_type"."type_code" IS '类型标识';
COMMENT ON COLUMN "public"."nt_sys_menu_type"."type_name" IS '类型名称';
COMMENT ON COLUMN "public"."nt_sys_menu_type"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_menu_type"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_menu_type"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."nt_sys_menu_type" IS '菜单类型表';

-- ----------------------------
-- Records of nt_sys_menu_type
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_menu_type" VALUES ('1', 'nt_sys', '统一用户认证', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "public"."nt_sys_menu_type" VALUES ('2', 'nt_mix', '混合库', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "public"."nt_sys_menu_type" VALUES ('3', 'nt_database', 'DNA数据库比对管理系统', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "public"."nt_sys_menu_type" VALUES ('4', 'nt_case', '综合受理接案平台', '1', '2020-05-12 09:14:12', '2020-05-12 09:14:42');
INSERT INTO "public"."nt_sys_menu_type" VALUES ('4028e5917365edc4017365efa54d0000', '222', '22', '0', '2020-07-19 00:00:00', '2021-10-22 17:32:46.583');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_message
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_message";
CREATE TABLE "public"."nt_sys_message" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "queue_type" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "queue_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "message_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "message_name" varchar(64) COLLATE "pg_catalog"."default",
  "type_id" varchar(64) COLLATE "pg_catalog"."default",
  "context" varchar(255) COLLATE "pg_catalog"."default",
  "send_user" varchar(32) COLLATE "pg_catalog"."default",
  "receive_user" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
ALTER TABLE "public"."nt_sys_message" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_message"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_message"."queue_type" IS '队列类型';
COMMENT ON COLUMN "public"."nt_sys_message"."queue_key" IS '队列标识';
COMMENT ON COLUMN "public"."nt_sys_message"."message_type" IS '消息类型';
COMMENT ON COLUMN "public"."nt_sys_message"."message_name" IS '消息名称';
COMMENT ON COLUMN "public"."nt_sys_message"."type_id" IS '类型编号';
COMMENT ON COLUMN "public"."nt_sys_message"."context" IS '内容';
COMMENT ON COLUMN "public"."nt_sys_message"."send_user" IS '发送人';
COMMENT ON COLUMN "public"."nt_sys_message"."receive_user" IS '接收人';
COMMENT ON COLUMN "public"."nt_sys_message"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_message"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."nt_sys_message" IS '系统消息表';

-- ----------------------------
-- Records of nt_sys_message
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_message" VALUES ('40288112729c3ca101729cab601d0000', 'sysMessageDirect', 'sysMessageKey', 'test', '测试', NULL, '内容', 'admin', 'admin', '2020-06-10 13:19:20', '2020-06-10 13:19:20');
INSERT INTO "public"."nt_sys_message" VALUES ('ff80808174dcef8f0174dcf068ec0000', 'sysMessageDirect', 'sysMessageKey', 'test', '测试', NULL, '123456', 'admin', 'admin', '2020-09-30 10:56:17', NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_operation
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_operation";
CREATE TABLE "public"."nt_sys_operation" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(8) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
ALTER TABLE "public"."nt_sys_operation" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_operation"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_operation"."operation_type" IS '操作类型';
COMMENT ON COLUMN "public"."nt_sys_operation"."operation_name" IS '操作名称';
COMMENT ON COLUMN "public"."nt_sys_operation"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_operation"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_operation"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."nt_sys_operation" IS '操作权限表';

-- ----------------------------
-- Records of nt_sys_operation
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_operation" VALUES ('1', 'select', '查询', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "public"."nt_sys_operation" VALUES ('2', 'add', '添加', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "public"."nt_sys_operation" VALUES ('3', 'edit', '编辑', '1', '2013-01-01 10:00:00', '2020-07-19 13:09:58');
INSERT INTO "public"."nt_sys_operation" VALUES ('4', 'save', '保存', '1', '2013-01-01 10:00:00', '2020-07-19 14:12:47');
INSERT INTO "public"."nt_sys_operation" VALUES ('5', 'delete', '删除', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "public"."nt_sys_operation" VALUES ('6', 'imported', '导入', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "public"."nt_sys_operation" VALUES ('7', 'exported', '导出', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_organization
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_organization";
CREATE TABLE "public"."nt_sys_organization" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "org_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "org_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(32) COLLATE "pg_catalog"."default",
  "ancestors" varchar(255) COLLATE "pg_catalog"."default",
  "org_order" int4,
  "org_area_code" varchar(255) COLLATE "pg_catalog"."default",
  "org_credit_code" varchar(255) COLLATE "pg_catalog"."default",
  "org_logo" varchar(64) COLLATE "pg_catalog"."default",
  "org_logo_url" varchar(64) COLLATE "pg_catalog"."default",
  "org_type" varchar(32) COLLATE "pg_catalog"."default",
  "create_user" varchar(64) COLLATE "pg_catalog"."default",
  "status" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "business_name" varchar(32) COLLATE "pg_catalog"."default",
  "business_phone" varchar(32) COLLATE "pg_catalog"."default",
  "technical_name" varchar(32) COLLATE "pg_catalog"."default",
  "technical_phone" varchar(32) COLLATE "pg_catalog"."default",
  "config_id" varchar(64) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."nt_sys_organization" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_organization"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_organization"."org_name" IS '机构名称';
COMMENT ON COLUMN "public"."nt_sys_organization"."org_code" IS '机构编号';
COMMENT ON COLUMN "public"."nt_sys_organization"."parent_id" IS '上级编号';
COMMENT ON COLUMN "public"."nt_sys_organization"."ancestors" IS '总称';
COMMENT ON COLUMN "public"."nt_sys_organization"."org_order" IS '编号';
COMMENT ON COLUMN "public"."nt_sys_organization"."org_area_code" IS '地区';
COMMENT ON COLUMN "public"."nt_sys_organization"."org_credit_code" IS '行政区号';
COMMENT ON COLUMN "public"."nt_sys_organization"."org_logo" IS 'Logo';
COMMENT ON COLUMN "public"."nt_sys_organization"."org_logo_url" IS 'Logo地址';
COMMENT ON COLUMN "public"."nt_sys_organization"."org_type" IS '类型';
COMMENT ON COLUMN "public"."nt_sys_organization"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."nt_sys_organization"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_organization"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_organization"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."nt_sys_organization"."business_name" IS '负责人';
COMMENT ON COLUMN "public"."nt_sys_organization"."business_phone" IS '负责人电话';
COMMENT ON COLUMN "public"."nt_sys_organization"."technical_name" IS '技术负责人';
COMMENT ON COLUMN "public"."nt_sys_organization"."technical_phone" IS '负责人电话';
COMMENT ON COLUMN "public"."nt_sys_organization"."config_id" IS '数据源id';
COMMENT ON TABLE "public"."nt_sys_organization" IS '机构表';

-- ----------------------------
-- Records of nt_sys_organization
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_organization" VALUES ('110101', '北京市公安局东城分局', '110101000000', '13', '-1,2,13', 1, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110102', '北京市公安局西城分局', '110102000000', '13', '-1,2,13', 2, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110105', '北京市公安局朝阳分局', '110105000000', '13', '-1,2,13', 3, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110106', '北京市公安局丰台分局', '110106000000', '13', '-1,2,13', 4, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110107', '北京市公安局石景山分局', '110107000000', '13', '-1,2,13', 5, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110108', '北京市公安局海淀分局', '110108000000', '13', '-1,2,13', 6, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110109', '北京市公安局门头沟分局', '110109000000', '13', '-1,2,13', 7, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110111', '北京市公安局房山分局', '110111000000', '13', '-1,2,13', 8, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110112', '北京市公安局通州分局', '110112000000', '13', '-1,2,13', 9, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110113', '北京市公安局顺义分局', '110113000000', '13', '-1,2,13', 10, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110114', '北京市公安局昌平分局', '110114000000', '13', '-1,2,13', 11, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110115', '北京市公安局大兴分局', '110115000000', '13', '-1,2,13', 12, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110116', '北京市公安局怀柔分局', '110116000000', '13', '-1,2,13', 13, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110117', '北京市公安局平谷分局', '110117000000', '13', '-1,2,13', 14, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110228', '北京市公安局密云分局', '110228000000', '13', '-1,2,13', 15, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('110229', '北京市公安局延庆分局', '110229000000', '13', '-1,2,13', 16, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('13', '北京市公安局治安管理总队', '110000200000', '2', '-1,2', 17, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('14', '北京市公安局刑事侦查总队', '110000210000', '2', '-1,2', 18, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('15', '北京市公安局公共交通安全保卫总队', '110084000000', '2', '-1,2', 19, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('16', '北京市公安局内部单位保卫局', '110086000000', '2', '-1,2', 20, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('17', '北京市公安局经济犯罪侦查总队', '110317000000', '2', '-1,2', 21, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('18', '北京市公安局便衣侦查总队', '110318000000', '2', '-1,2', 22, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('19', '北京市公安局禁毒总队', '110319000000', '2', '-1,2', 23, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('2', '北京市公安局', '110000000000', '-1', '-1', 1, NULL, NULL, NULL, NULL, NULL, '1', '1', '2020-06-11 17:33:58', '2020-09-17 21:21:04', NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('20', '北京市公安局公安交通管理局', '110320000000', '2', '-1,2', 24, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('21', '北京海关缉私局', '110000H00500', '2', '-1,2', 25, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('22', '北京市森林公安局', '110000S00500', '2', '-1,2', 26, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('3', '北京市法医中心', '110000000000', '2', '-1,2', 2, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-12 13:12:49', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('31', '北京市公安局天安门地区分局', '110091000000', '2', '-1,2', 27, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('32', '北京市公安局北京西站分局', '110092000000', '2', '-1,2', 28, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('33', '北京市公安局经济技术开发区分局', '110095000000', '2', '-1,2', 29, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('34', '北京市公安局清河分局', '110097000000', '2', '-1,2', 30, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."nt_sys_organization" VALUES ('4', '北京市法医中心实验室', '110000000001', '3', '-1,2,3', 1, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_role";
CREATE TABLE "public"."nt_sys_role" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "role_code" varchar(32) COLLATE "pg_catalog"."default",
  "role_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "role_des" varchar(32) COLLATE "pg_catalog"."default",
  "status" varchar(8) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
ALTER TABLE "public"."nt_sys_role" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_role"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_role"."role_code" IS '角色编号';
COMMENT ON COLUMN "public"."nt_sys_role"."role_name" IS '角色名称';
COMMENT ON COLUMN "public"."nt_sys_role"."role_des" IS '角色描述';
COMMENT ON COLUMN "public"."nt_sys_role"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_role"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."nt_sys_role" IS '角色表';

-- ----------------------------
-- Records of nt_sys_role
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_role" VALUES ('1', '22222222', '超管', '权限最大用户', '1', '2018-10-10 16:08:02', '2020-12-25 14:49:43');
INSERT INTO "public"."nt_sys_role" VALUES ('ff80808172a150110172a159d9c40000', '1591844919746', '职员', '普通用户', '1', '2020-06-11 11:08:40', '2020-09-19 12:00:16');
INSERT INTO "public"."nt_sys_role" VALUES ('ff80808172a624c00172a67c70c30007', '3333', '警员', '一般警员', '0', '2020-06-12 11:04:33', '2021-10-22 17:32:29.032');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_role_menu";
CREATE TABLE "public"."nt_sys_role_menu" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_id" varchar(255) COLLATE "pg_catalog"."default",
  "role_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_type_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."nt_sys_role_menu" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_role_menu"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_role_menu"."menu_id" IS '菜单id';
COMMENT ON COLUMN "public"."nt_sys_role_menu"."role_id" IS '角色id';
COMMENT ON COLUMN "public"."nt_sys_role_menu"."menu_type_id" IS '类型id';
COMMENT ON TABLE "public"."nt_sys_role_menu" IS '角色菜单表';

-- ----------------------------
-- Records of nt_sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_role_menu" VALUES ('11101', '101', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('111011', '1011', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('111012', '1012', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('111013', '1013', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('111014', '1014', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('111015', '1015', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('111016', '1016', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('11201', '201', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('112011', '2011', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('112012', '2012', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('112013', '2013', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('112014', '2014', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('112015', '2015', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('112016', '2016', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('112017', '2017', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('112018', '2018', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('11301', '301', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('113011', '3011', '1', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('ff80808172a150110172a159d9c400001101', '101', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('ff80808172a150110172a159d9c400001201', '201', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('ff80808172a150110172a159d9c4000012011', '2011', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('ff80808172a150110172a159d9c4000012012', '2012', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO "public"."nt_sys_role_menu" VALUES ('ff80808172a150110172a159d9c4000012013', '2013', 'ff80808172a150110172a159d9c40000', '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role_menu_oper
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_role_menu_oper";
CREATE TABLE "public"."nt_sys_role_menu_oper" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "role_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_type_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."nt_sys_role_menu_oper" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_role_menu_oper"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_role_menu_oper"."menu_id" IS '菜单id';
COMMENT ON COLUMN "public"."nt_sys_role_menu_oper"."operation_id" IS '操作id';
COMMENT ON COLUMN "public"."nt_sys_role_menu_oper"."role_id" IS '角色id';
COMMENT ON COLUMN "public"."nt_sys_role_menu_oper"."menu_type_id" IS '类型id';
COMMENT ON TABLE "public"."nt_sys_role_menu_oper" IS '角色菜单操作权限表';

-- ----------------------------
-- Records of nt_sys_role_menu_oper
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_sequence
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_sequence";
CREATE TABLE "public"."nt_sys_sequence" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "table_name" varchar(32) COLLATE "pg_catalog"."default",
  "sequence" int4,
  "status" varchar(8) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6)
)
;
ALTER TABLE "public"."nt_sys_sequence" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_sequence"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_sequence"."table_name" IS '表名';
COMMENT ON COLUMN "public"."nt_sys_sequence"."sequence" IS '序号';
COMMENT ON COLUMN "public"."nt_sys_sequence"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_sequence"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."nt_sys_sequence" IS '序列表';

-- ----------------------------
-- Records of nt_sys_sequence
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_task
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_task";
CREATE TABLE "public"."nt_sys_task" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "task_group" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "task_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "concurrent" varchar(1) COLLATE "pg_catalog"."default",
  "invoke_param" varchar COLLATE "pg_catalog"."default",
  "cron_expression" varchar(255) COLLATE "pg_catalog"."default",
  "invoke_target" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "misfire_policy" varchar(20) COLLATE "pg_catalog"."default",
  "remark" varchar(500) COLLATE "pg_catalog"."default",
  "execute_status" varchar(1) COLLATE "pg_catalog"."default",
  "status" varchar(1) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
ALTER TABLE "public"."nt_sys_task" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_task"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_task"."task_group" IS '任务组';
COMMENT ON COLUMN "public"."nt_sys_task"."task_name" IS '任务名称';
COMMENT ON COLUMN "public"."nt_sys_task"."concurrent" IS '线程';
COMMENT ON COLUMN "public"."nt_sys_task"."invoke_param" IS '参数';
COMMENT ON COLUMN "public"."nt_sys_task"."cron_expression" IS '表达式';
COMMENT ON COLUMN "public"."nt_sys_task"."invoke_target" IS '目标';
COMMENT ON COLUMN "public"."nt_sys_task"."misfire_policy" IS '策略';
COMMENT ON COLUMN "public"."nt_sys_task"."remark" IS '备注';
COMMENT ON COLUMN "public"."nt_sys_task"."execute_status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_task"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_task"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_task"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."nt_sys_task"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."nt_sys_task" IS '系统任务表';

-- ----------------------------
-- Records of nt_sys_task
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_task" VALUES ('8a818b3774bef2910174bef387ec0000', 'DEFAULT', 'test', '1', '{"taskId": "8a818b3774bef2910174bef387ec0000", "taskParam": {}, "concurrent": null, "taskServerName": "bazl-dna-system"}', '0/10 * * * * ?', 'systemTask.taskRun()', '0', '', '1', '0', NULL, 'liutao', NULL);
INSERT INTO "public"."nt_sys_task" VALUES ('ff80808175cfe8900175d012ecc00002', 'DEFAULT', '删除3天日志', '1', '{"taskId": null, "taskParam": {}, "concurrent": null, "taskServerName": null}', '0 0 1 * * ?', 'systemTask.taskDeleteLog(3)', '3', NULL, NULL, '0', NULL, 'admin', NULL);
INSERT INTO "public"."nt_sys_task" VALUES ('ff80808175d015c90175d05958e50001', 'DEFAULT', '111', '1', '{"taskId": null, "taskParam": null, "concurrent": null, "taskServerName": null}', '0 0 1 * * ?', 'systemTask.taskParams(''121'')', '3', NULL, NULL, '0', NULL, 'admin', NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_task_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_task_log";
CREATE TABLE "public"."nt_sys_task_log" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "task_group" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "task_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "task_message" varchar(500) COLLATE "pg_catalog"."default",
  "executeip" varchar(255) COLLATE "pg_catalog"."default",
  "execute_server_name" varchar(255) COLLATE "pg_catalog"."default",
  "execute_ip" varchar(64) COLLATE "pg_catalog"."default",
  "exception_info" varchar(2000) COLLATE "pg_catalog"."default",
  "invoke_target" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(500) COLLATE "pg_catalog"."default",
  "start_time" timestamp(6),
  "stop_time" timestamp(6),
  "status" varchar(1) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
ALTER TABLE "public"."nt_sys_task_log" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_task_log"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_task_log"."task_group" IS '任务组';
COMMENT ON COLUMN "public"."nt_sys_task_log"."task_name" IS '任务名称';
COMMENT ON COLUMN "public"."nt_sys_task_log"."task_message" IS '任务消息';
COMMENT ON COLUMN "public"."nt_sys_task_log"."executeip" IS '执行ip';
COMMENT ON COLUMN "public"."nt_sys_task_log"."execute_server_name" IS '服务名称';
COMMENT ON COLUMN "public"."nt_sys_task_log"."execute_ip" IS '执行ip';
COMMENT ON COLUMN "public"."nt_sys_task_log"."exception_info" IS '异常信息';
COMMENT ON COLUMN "public"."nt_sys_task_log"."invoke_target" IS '请求参数';
COMMENT ON COLUMN "public"."nt_sys_task_log"."remark" IS '备注';
COMMENT ON COLUMN "public"."nt_sys_task_log"."start_time" IS '开始时间';
COMMENT ON COLUMN "public"."nt_sys_task_log"."stop_time" IS '停止时间';
COMMENT ON COLUMN "public"."nt_sys_task_log"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_task_log"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_task_log"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."nt_sys_task_log"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."nt_sys_task_log" IS '任务日志表';

-- ----------------------------
-- Records of nt_sys_task_log
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_task_log" VALUES ('ff80808175cfe8900175cff033bc0000', 'DEFAULT', 'test', '执行时间：10毫秒', NULL, 'bazl-dna-system', '127.0.0.1', NULL, 'systemTask.taskRun()', NULL, NULL, NULL, '1', '2020-11-16 15:23:47', NULL, NULL);
INSERT INTO "public"."nt_sys_task_log" VALUES ('ff80808175cfe8900175d007c2f00001', 'DEFAULT', 'test', '执行时间：7毫秒', NULL, 'bazl-dna-system', '127.0.0.1', NULL, 'systemTask.taskRun()', NULL, NULL, NULL, '1', '2020-11-16 15:49:31', NULL, NULL);
INSERT INTO "public"."nt_sys_task_log" VALUES ('ff80808175d015c90175d019bced0000', 'DEFAULT', 'test', '执行时间：219毫秒', NULL, 'bazl-dna-system', '127.0.0.1', NULL, 'systemTask.taskRun()', NULL, NULL, NULL, '1', '2020-11-16 16:09:09', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_user";
CREATE TABLE "public"."nt_sys_user" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "real_name" varchar(32) COLLATE "pg_catalog"."default",
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "sex" char(1) COLLATE "pg_catalog"."default" DEFAULT '0'::bpchar,
  "user_type" varchar(2) COLLATE "pg_catalog"."default",
  "email" varchar(32) COLLATE "pg_catalog"."default",
  "id_card" varchar(32) COLLATE "pg_catalog"."default",
  "last_ip" varchar(32) COLLATE "pg_catalog"."default",
  "last_time" timestamp(6),
  "phone" varchar(32) COLLATE "pg_catalog"."default",
  "user_code" varchar(32) COLLATE "pg_catalog"."default",
  "id_type" varchar(8) COLLATE "pg_catalog"."default",
  "status" varchar(8) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "is_admin" varchar(8) COLLATE "pg_catalog"."default",
  "active_status" varchar(10) COLLATE "pg_catalog"."default",
  "default_password" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6)
)
;
ALTER TABLE "public"."nt_sys_user" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_user"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_user"."user_name" IS '账号';
COMMENT ON COLUMN "public"."nt_sys_user"."password" IS '密码';
COMMENT ON COLUMN "public"."nt_sys_user"."real_name" IS '姓名';
COMMENT ON COLUMN "public"."nt_sys_user"."avatar" IS '头像';
COMMENT ON COLUMN "public"."nt_sys_user"."sex" IS '性别';
COMMENT ON COLUMN "public"."nt_sys_user"."user_type" IS '类型';
COMMENT ON COLUMN "public"."nt_sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "public"."nt_sys_user"."id_card" IS '身份证';
COMMENT ON COLUMN "public"."nt_sys_user"."last_ip" IS '登录ip';
COMMENT ON COLUMN "public"."nt_sys_user"."last_time" IS '登录时间';
COMMENT ON COLUMN "public"."nt_sys_user"."phone" IS '电话';
COMMENT ON COLUMN "public"."nt_sys_user"."user_code" IS '编号';
COMMENT ON COLUMN "public"."nt_sys_user"."id_type" IS '证件类型';
COMMENT ON COLUMN "public"."nt_sys_user"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_user"."remark" IS '备注';
COMMENT ON COLUMN "public"."nt_sys_user"."is_admin" IS '是否管理员';
COMMENT ON COLUMN "public"."nt_sys_user"."active_status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_user"."default_password" IS '默认密码';
COMMENT ON COLUMN "public"."nt_sys_user"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."nt_sys_user" IS '用户表';

-- ----------------------------
-- Records of nt_sys_user
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_user" VALUES ('1', 'admin', 'C4CA4238A0B923820DCC509A6F75849B', '超管', 'http://47.92.219.7/group1/M00/00/04/rBqcll9Qsc-ADt_9AAAk7-agPWw680.jpg', '0', '1', 'admin@qq.com', '110103197707250933', '', '2020-09-04 15:53:30', '13800138000', '', 'beijing', '1', '123', '1', '1', '123456', '2018-10-09 14:19:44');
INSERT INTO "public"."nt_sys_user" VALUES ('2', 'shy', 'C4CA4238A0B923820DCC509A6F75849B', '张3', '', '0', '1', NULL, NULL, NULL, NULL, '13800138000', NULL, NULL, '1', NULL, '0', '1', '123456', '2020-05-12 09:47:07');
INSERT INTO "public"."nt_sys_user" VALUES ('5', 'ls', 'C4CA4238A0B923820DCC509A6F75849B', '王老师', '', '0', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, '1', '1', NULL, '2020-05-12 17:05:23');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_job
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_user_job";
CREATE TABLE "public"."nt_sys_user_job" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "position_order" int4 NOT NULL,
  "status" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "job_id" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."nt_sys_user_job" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_user_job"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_user_job"."position_order" IS '排序';
COMMENT ON COLUMN "public"."nt_sys_user_job"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_user_job"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_user_job"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."nt_sys_user_job"."job_id" IS '职位编号';
COMMENT ON COLUMN "public"."nt_sys_user_job"."user_id" IS '用户编号';
COMMENT ON TABLE "public"."nt_sys_user_job" IS '用户职位表';

-- ----------------------------
-- Records of nt_sys_user_job
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_user_job" VALUES ('4028b881745ea0f901745ea77e07001a', 1, '1', '2020-09-05 22:24:29', NULL, '1', '1');
INSERT INTO "public"."nt_sys_user_job" VALUES ('ff8080817640f2d4017640f8fca70001', 1, '1', '2020-12-08 14:10:28', NULL, '1', '2');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_org
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_user_org";
CREATE TABLE "public"."nt_sys_user_org" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "position_order" int4 NOT NULL,
  "status" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "org_id" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."nt_sys_user_org" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_user_org"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_user_org"."position_order" IS '排序';
COMMENT ON COLUMN "public"."nt_sys_user_org"."status" IS '状态';
COMMENT ON COLUMN "public"."nt_sys_user_org"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."nt_sys_user_org"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."nt_sys_user_org"."org_id" IS '机构编号';
COMMENT ON COLUMN "public"."nt_sys_user_org"."user_id" IS '用户编号';
COMMENT ON TABLE "public"."nt_sys_user_org" IS '用户机构权限表';

-- ----------------------------
-- Records of nt_sys_user_org
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_user_org" VALUES ('4028b881745ea0f901745ea77e0f001b', 1, '1', '2020-09-05 22:24:29', NULL, '3', '1');
INSERT INTO "public"."nt_sys_user_org" VALUES ('4028b881745ea0f901745ea77e14001c', 1, '1', '2020-09-05 22:24:29', NULL, '4', '1');
INSERT INTO "public"."nt_sys_user_org" VALUES ('4028b881745ea0f901745ea77e19001d', 1, '1', '2020-09-05 22:24:29', NULL, '110101', '1');
INSERT INTO "public"."nt_sys_user_org" VALUES ('4028b881745ea0f901745ea77e23001e', 1, '1', '2020-09-05 22:24:29', NULL, '110102', '1');
INSERT INTO "public"."nt_sys_user_org" VALUES ('4028b881745ea0f901745ea77e2a001f', 1, '1', '2020-09-05 22:24:29', NULL, '110105', '1');
INSERT INTO "public"."nt_sys_user_org" VALUES ('4028b881745ea0f901745ea77e320020', 1, '1', '2020-09-05 22:24:29', NULL, '14', '1');
INSERT INTO "public"."nt_sys_user_org" VALUES ('4028b881745ea0f901745ea77e3a0021', 1, '1', '2020-09-05 22:24:29', NULL, '15', '1');
INSERT INTO "public"."nt_sys_user_org" VALUES ('4028b881745ea0f901745ea77e420022', 1, '1', '2020-09-05 22:24:29', NULL, '16', '1');
INSERT INTO "public"."nt_sys_user_org" VALUES ('ff8080817640f2d4017640f8fcb10002', 1, '1', '2020-12-08 14:10:28', NULL, '110101', '2');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."nt_sys_user_role";
CREATE TABLE "public"."nt_sys_user_role" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "role_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."nt_sys_user_role" OWNER TO "postgres";
COMMENT ON COLUMN "public"."nt_sys_user_role"."id" IS '主键';
COMMENT ON COLUMN "public"."nt_sys_user_role"."role_id" IS '角色编号';
COMMENT ON COLUMN "public"."nt_sys_user_role"."user_id" IS '用户编号';
COMMENT ON TABLE "public"."nt_sys_user_role" IS '用户角色权限表';

-- ----------------------------
-- Records of nt_sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO "public"."nt_sys_user_role" VALUES ('4028b881745ea0f901745ea77dfa0018', '1', '1');
INSERT INTO "public"."nt_sys_user_role" VALUES ('4028b881745ea0f901745ea77dfe0019', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO "public"."nt_sys_user_role" VALUES ('ff80808172a7b2ce0172bacc3b9c0009', '1', '5');
INSERT INTO "public"."nt_sys_user_role" VALUES ('ff8080817640f2d4017640f8fc990000', 'ff80808172a150110172a159d9c40000', '2');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_blob_triggers";
CREATE TABLE "public"."qrtz_blob_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "blob_data" bytea
)
;
ALTER TABLE "public"."qrtz_blob_triggers" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_calendars";
CREATE TABLE "public"."qrtz_calendars" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "calendar_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "calendar" bytea NOT NULL
)
;
ALTER TABLE "public"."qrtz_calendars" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_cron_triggers";
CREATE TABLE "public"."qrtz_cron_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "cron_expression" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "time_zone_id" varchar(80) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."qrtz_cron_triggers" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
INSERT INTO "public"."qrtz_cron_triggers" VALUES ('ProjectScheduler', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO "public"."qrtz_cron_triggers" VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', '0 0 1 * * ?', 'Asia/Shanghai');
INSERT INTO "public"."qrtz_cron_triggers" VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', '0 0 1 * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_fired_triggers";
CREATE TABLE "public"."qrtz_fired_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "entry_id" varchar(95) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "instance_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "fired_time" int8 NOT NULL,
  "sched_time" int8 NOT NULL,
  "priority" int4 NOT NULL,
  "state" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(200) COLLATE "pg_catalog"."default",
  "job_group" varchar(200) COLLATE "pg_catalog"."default",
  "is_nonconcurrent" varchar(10) COLLATE "pg_catalog"."default",
  "requests_recovery" bool
)
;
ALTER TABLE "public"."qrtz_fired_triggers" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_job_details";
CREATE TABLE "public"."qrtz_job_details" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "job_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(250) COLLATE "pg_catalog"."default",
  "job_class_name" varchar(250) COLLATE "pg_catalog"."default" NOT NULL,
  "is_durable" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "is_nonconcurrent" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "is_update_data" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "requests_recovery" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "job_data" bytea
)
;
ALTER TABLE "public"."qrtz_job_details" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
INSERT INTO "public"."qrtz_job_details" VALUES ('ProjectScheduler', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', NULL, 'com.oner365.monitor.util.QuartzDisallowConcurrentExecution', 'false', 'true', 'false', 'false', E'\\254\\355\\000\\005sr\\000\\025org.quartz.JobDataMap\\237\\260\\203\\350\\277\\251\\260\\313\\002\\000\\000xr\\000&org.quartz.utils.StringKeyDirtyFlagMap\\202\\010\\350\\303\\373\\305](\\002\\000\\001Z\\000\\023allowsTransientDataxr\\000\\035org.quartz.utils.DirtyFlagMap\\023\\346.\\255(v\\012\\316\\002\\000\\002Z\\000\\005dirtyL\\000\\003mapt\\000\\017Ljava/util/Map;xp\\001sr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\014w\\010\\000\\000\\000\\020\\000\\000\\000\\001t\\000\\017TASK_PROPERTIESsr\\000"com.oner365.monitor.entity.SysTask\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\016L\\000\\012concurrentt\\000\\022Ljava/lang/String;L\\000\\012createTimet\\000\\020Ljava/util/Date;L\\000\\012createUserq\\000~\\000\\011L\\000\\016cronExpressionq\\000~\\000\\011L\\000\\015executeStatusq\\000~\\000\\011L\\000\\002idq\\000~\\000\\011L\\000\\013invokeParamt\\000(Lcom/oner365/monitor/entity/InvokeParam;L\\000\\014invokeTargetq\\000~\\000\\011L\\000\\015misfirePolicyq\\000~\\000\\011L\\000\\006remarkq\\000~\\000\\011L\\000\\006statusq\\000~\\000\\011L\\000\\011taskGroupq\\000~\\000\\011L\\000\\010taskNameq\\000~\\000\\011L\\000\\012updateTimeq\\000~\\000\\012xpt\\000\\0011pt\\000\\006liutaot\\000\\0160/10 * * * * ?t\\000\\0011t\\000 8a818b3774bef2910174bef387ec0000sr\\000&com.oner365.monitor.entity.InvokeParam\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\004L\\000\\012concurrentq\\000~\\000\\011L\\000\\006taskIdq\\000~\\000\\011L\\000\\011taskParamt\\000!Lcom/alibaba/fastjson/JSONObject;L\\000\\016taskServerNameq\\000~\\000\\011xppt\\000 8a818b3774bef2910174bef387ec0000sr\\000\\037com.alibaba.fastjson.JSONObject\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\001L\\000\\003mapq\\000~\\000\\003xpsq\\000~\\000\\005?@\\000\\000\\000\\000\\000\\020w\\010\\000\\000\\000\\020\\000\\000\\000\\000xt\\000\\017bazl-dna-systemt\\000\\024systemTask.taskRun()t\\000\\0010t\\000\\000t\\000\\0010t\\000\\007DEFAULTt\\000\\004testpx\\000');
INSERT INTO "public"."qrtz_job_details" VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', NULL, 'com.oner365.monitor.util.QuartzDisallowConcurrentExecution', 'false', 'true', 'false', 'false', E'\\254\\355\\000\\005sr\\000\\025org.quartz.JobDataMap\\237\\260\\203\\350\\277\\251\\260\\313\\002\\000\\000xr\\000&org.quartz.utils.StringKeyDirtyFlagMap\\202\\010\\350\\303\\373\\305](\\002\\000\\001Z\\000\\023allowsTransientDataxr\\000\\035org.quartz.utils.DirtyFlagMap\\023\\346.\\255(v\\012\\316\\002\\000\\002Z\\000\\005dirtyL\\000\\003mapt\\000\\017Ljava/util/Map;xp\\001sr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\014w\\010\\000\\000\\000\\020\\000\\000\\000\\001t\\000\\017TASK_PROPERTIESsr\\000"com.oner365.monitor.entity.SysTask\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\016L\\000\\012concurrentt\\000\\022Ljava/lang/String;L\\000\\012createTimet\\000\\020Ljava/util/Date;L\\000\\012createUserq\\000~\\000\\011L\\000\\016cronExpressionq\\000~\\000\\011L\\000\\015executeStatusq\\000~\\000\\011L\\000\\002idq\\000~\\000\\011L\\000\\013invokeParamt\\000(Lcom/oner365/monitor/entity/InvokeParam;L\\000\\014invokeTargetq\\000~\\000\\011L\\000\\015misfirePolicyq\\000~\\000\\011L\\000\\006remarkq\\000~\\000\\011L\\000\\006statusq\\000~\\000\\011L\\000\\011taskGroupq\\000~\\000\\011L\\000\\010taskNameq\\000~\\000\\011L\\000\\012updateTimeq\\000~\\000\\012xpt\\000\\0011pt\\000\\005admint\\000\\0130 0 1 * * ?pt\\000 ff80808175cfe8900175d012ecc00002sr\\000&com.oner365.monitor.entity.InvokeParam\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\004L\\000\\012concurrentq\\000~\\000\\011L\\000\\006taskIdq\\000~\\000\\011L\\000\\011taskParamt\\000!Lcom/alibaba/fastjson/JSONObject;L\\000\\016taskServerNameq\\000~\\000\\011xpppsr\\000\\037com.alibaba.fastjson.JSONObject\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\001L\\000\\003mapq\\000~\\000\\003xpsq\\000~\\000\\005?@\\000\\000\\000\\000\\000\\020w\\010\\000\\000\\000\\020\\000\\000\\000\\000xpt\\000\\033systemTask.taskDeleteLog(3)t\\000\\0013pt\\000\\0010t\\000\\007DEFAULTt\\000\\020\\345\\210\\240\\351\\231\\2443\\345\\244\\251\\346\\227\\245\\345\\277\\227px\\000');
INSERT INTO "public"."qrtz_job_details" VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', NULL, 'com.oner365.monitor.util.QuartzDisallowConcurrentExecution', 'false', 'true', 'false', 'false', E'\\254\\355\\000\\005sr\\000\\025org.quartz.JobDataMap\\237\\260\\203\\350\\277\\251\\260\\313\\002\\000\\000xr\\000&org.quartz.utils.StringKeyDirtyFlagMap\\202\\010\\350\\303\\373\\305](\\002\\000\\001Z\\000\\023allowsTransientDataxr\\000\\035org.quartz.utils.DirtyFlagMap\\023\\346.\\255(v\\012\\316\\002\\000\\002Z\\000\\005dirtyL\\000\\003mapt\\000\\017Ljava/util/Map;xp\\001sr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\014w\\010\\000\\000\\000\\020\\000\\000\\000\\001t\\000\\017TASK_PROPERTIESsr\\000"com.oner365.monitor.entity.SysTask\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\016L\\000\\012concurrentt\\000\\022Ljava/lang/String;L\\000\\012createTimet\\000\\020Ljava/util/Date;L\\000\\012createUserq\\000~\\000\\011L\\000\\016cronExpressionq\\000~\\000\\011L\\000\\015executeStatusq\\000~\\000\\011L\\000\\002idq\\000~\\000\\011L\\000\\013invokeParamt\\000(Lcom/oner365/monitor/entity/InvokeParam;L\\000\\014invokeTargetq\\000~\\000\\011L\\000\\015misfirePolicyq\\000~\\000\\011L\\000\\006remarkq\\000~\\000\\011L\\000\\006statusq\\000~\\000\\011L\\000\\011taskGroupq\\000~\\000\\011L\\000\\010taskNameq\\000~\\000\\011L\\000\\012updateTimeq\\000~\\000\\012xpt\\000\\0011pt\\000\\005admint\\000\\0130 0 1 * * ?pt\\000 ff80808175d015c90175d05958e50001sr\\000&com.oner365.monitor.entity.InvokeParam\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\004L\\000\\012concurrentq\\000~\\000\\011L\\000\\006taskIdq\\000~\\000\\011L\\000\\011taskParamt\\000!Lcom/alibaba/fastjson/JSONObject;L\\000\\016taskServerNameq\\000~\\000\\011xpppppt\\000\\034systemTask.taskParams(''121'')t\\000\\0013pt\\000\\0010t\\000\\007DEFAULTt\\000\\003111px\\000');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_locks";
CREATE TABLE "public"."qrtz_locks" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "lock_name" varchar(40) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."qrtz_locks" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
INSERT INTO "public"."qrtz_locks" VALUES ('ProjectScheduler', 'STATE_ACCESS');
INSERT INTO "public"."qrtz_locks" VALUES ('ProjectScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_paused_trigger_grps";
CREATE TABLE "public"."qrtz_paused_trigger_grps" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."qrtz_paused_trigger_grps" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_scheduler_state";
CREATE TABLE "public"."qrtz_scheduler_state" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "instance_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "last_checkin_time" int8 NOT NULL,
  "checkin_interval" int8 NOT NULL
)
;
ALTER TABLE "public"."qrtz_scheduler_state" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_simple_triggers";
CREATE TABLE "public"."qrtz_simple_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "repeat_count" int8 NOT NULL,
  "repeat_interval" int8 NOT NULL,
  "times_triggered" int8 NOT NULL
)
;
ALTER TABLE "public"."qrtz_simple_triggers" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_simprop_triggers";
CREATE TABLE "public"."qrtz_simprop_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "str_prop_1" varchar(512) COLLATE "pg_catalog"."default",
  "str_prop_2" varchar(512) COLLATE "pg_catalog"."default",
  "str_prop_3" varchar(512) COLLATE "pg_catalog"."default",
  "int_prop_1" int4,
  "int_prop_2" int4,
  "long_prop_1" int8,
  "long_prop_2" int8,
  "dec_prop_1" numeric(13,4),
  "dec_prop_2" numeric(13,4),
  "bool_prop_1" varchar(1) COLLATE "pg_catalog"."default",
  "bool_prop_2" varchar(1) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."qrtz_simprop_triggers" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_triggers";
CREATE TABLE "public"."qrtz_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "job_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(250) COLLATE "pg_catalog"."default",
  "next_fire_time" int8,
  "prev_fire_time" int8,
  "priority" int4,
  "trigger_state" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_type" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "start_time" int8 NOT NULL,
  "end_time" int8,
  "calendar_name" varchar(200) COLLATE "pg_catalog"."default",
  "misfire_instr" int2,
  "job_data" bytea
)
;
ALTER TABLE "public"."qrtz_triggers" OWNER TO "postgres";

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
INSERT INTO "public"."qrtz_triggers" VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', NULL, 1634922000000, -1, 5, 'PAUSED', 'CRON', 1634896785000, 0, NULL, 2, E'\\\\x');
INSERT INTO "public"."qrtz_triggers" VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', NULL, 1634922000000, -1, 5, 'PAUSED', 'CRON', 1634896785000, 0, NULL, 2, E'\\\\x');
INSERT INTO "public"."qrtz_triggers" VALUES ('ProjectScheduler', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', NULL, 1634896790000, -1, 5, 'PAUSED', 'CRON', 1634896785000, 0, NULL, 0, E'\\\\x');
COMMIT;

-- ----------------------------
-- Indexes structure for table nt_data_source_config
-- ----------------------------
CREATE UNIQUE INDEX "uk_dsc_connect_name" ON "public"."nt_data_source_config" USING btree (
  "connect_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_data_source_config
-- ----------------------------
ALTER TABLE "public"."nt_data_source_config" ADD CONSTRAINT "nt_data_source_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_gateway_route
-- ----------------------------
ALTER TABLE "public"."nt_gateway_route" ADD CONSTRAINT "nt_gateway_route_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_dict_item
-- ----------------------------
ALTER TABLE "public"."nt_sys_dict_item" ADD CONSTRAINT "nt_sys_dict_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_dict_item_type
-- ----------------------------
CREATE UNIQUE INDEX "nt_sys_dict_item_type_dict_type_code_uindex" ON "public"."nt_sys_dict_item_type" USING btree (
  "dict_type_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_dict_item_type
-- ----------------------------
ALTER TABLE "public"."nt_sys_dict_item_type" ADD CONSTRAINT "nt_sys_dict_item_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_fastdfs_file
-- ----------------------------
ALTER TABLE "public"."nt_sys_fastdfs_file" ADD CONSTRAINT "nt_sys_fastdfs_file_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_job
-- ----------------------------
CREATE UNIQUE INDEX "uk_nsj_job_name" ON "public"."nt_sys_job" USING btree (
  "job_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_job
-- ----------------------------
ALTER TABLE "public"."nt_sys_job" ADD CONSTRAINT "nt_sys_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_log
-- ----------------------------
ALTER TABLE "public"."nt_sys_log" ADD CONSTRAINT "nt_sys_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_menu
-- ----------------------------
CREATE INDEX "idx_menu_type_id" ON "public"."nt_sys_menu" USING btree (
  "menu_type_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_parent_id" ON "public"."nt_sys_menu" USING btree (
  "parent_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_menu
-- ----------------------------
ALTER TABLE "public"."nt_sys_menu" ADD CONSTRAINT "nt_sys_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_menu_oper
-- ----------------------------
ALTER TABLE "public"."nt_sys_menu_oper" ADD CONSTRAINT "nt_sys_menu_oper_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_menu_type
-- ----------------------------
CREATE UNIQUE INDEX "uk_smt_type_code" ON "public"."nt_sys_menu_type" USING btree (
  "type_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_menu_type
-- ----------------------------
ALTER TABLE "public"."nt_sys_menu_type" ADD CONSTRAINT "nt_sys_menu_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_message
-- ----------------------------
CREATE INDEX "idx_message_type" ON "public"."nt_sys_message" USING btree (
  "message_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_message
-- ----------------------------
ALTER TABLE "public"."nt_sys_message" ADD CONSTRAINT "nt_sys_message_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_operation
-- ----------------------------
ALTER TABLE "public"."nt_sys_operation" ADD CONSTRAINT "nt_sys_operation_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_organization
-- ----------------------------
CREATE INDEX "idx_sys_org_org_code" ON "public"."nt_sys_organization" USING btree (
  "org_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_sys_org_parent_id" ON "public"."nt_sys_organization" USING btree (
  "parent_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_organization
-- ----------------------------
ALTER TABLE "public"."nt_sys_organization" ADD CONSTRAINT "nt_sys_organization_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_role
-- ----------------------------
ALTER TABLE "public"."nt_sys_role" ADD CONSTRAINT "nt_sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_role_menu
-- ----------------------------
ALTER TABLE "public"."nt_sys_role_menu" ADD CONSTRAINT "nt_sys_role_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_role_menu_oper
-- ----------------------------
ALTER TABLE "public"."nt_sys_role_menu_oper" ADD CONSTRAINT "nt_sys_role_menu_oper_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_sequence
-- ----------------------------
ALTER TABLE "public"."nt_sys_sequence" ADD CONSTRAINT "nt_sys_sequence_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_task
-- ----------------------------
ALTER TABLE "public"."nt_sys_task" ADD CONSTRAINT "nt_sys_task_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_task_log
-- ----------------------------
ALTER TABLE "public"."nt_sys_task_log" ADD CONSTRAINT "nt_sys_task_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_user
-- ----------------------------
CREATE INDEX "idx_user_user_name" ON "public"."nt_sys_user" USING btree (
  "user_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_user
-- ----------------------------
ALTER TABLE "public"."nt_sys_user" ADD CONSTRAINT "nt_sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_user_job
-- ----------------------------
ALTER TABLE "public"."nt_sys_user_job" ADD CONSTRAINT "nt_sys_user_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_user_org
-- ----------------------------
ALTER TABLE "public"."nt_sys_user_org" ADD CONSTRAINT "nt_sys_user_org_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_user_role
-- ----------------------------
ALTER TABLE "public"."nt_sys_user_role" ADD CONSTRAINT "nt_sys_user_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table qrtz_blob_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_blob_triggers" ADD CONSTRAINT "QRTZ_BLOB_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_calendars
-- ----------------------------
ALTER TABLE "public"."qrtz_calendars" ADD CONSTRAINT "QRTZ_CALENDARS_pkey" PRIMARY KEY ("sched_name", "calendar_name");

-- ----------------------------
-- Primary Key structure for table qrtz_cron_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_cron_triggers" ADD CONSTRAINT "QRTZ_CRON_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_fired_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_fired_triggers" ADD CONSTRAINT "QRTZ_FIRED_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "entry_id");

-- ----------------------------
-- Primary Key structure for table qrtz_job_details
-- ----------------------------
ALTER TABLE "public"."qrtz_job_details" ADD CONSTRAINT "QRTZ_JOB_DETAILS_pkey" PRIMARY KEY ("sched_name", "job_name", "job_group");

-- ----------------------------
-- Primary Key structure for table qrtz_locks
-- ----------------------------
ALTER TABLE "public"."qrtz_locks" ADD CONSTRAINT "QRTZ_LOCKS_pkey" PRIMARY KEY ("sched_name", "lock_name");

-- ----------------------------
-- Primary Key structure for table qrtz_paused_trigger_grps
-- ----------------------------
ALTER TABLE "public"."qrtz_paused_trigger_grps" ADD CONSTRAINT "QRTZ_PAUSED_TRIGGER_GRPS_pkey" PRIMARY KEY ("sched_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_scheduler_state
-- ----------------------------
ALTER TABLE "public"."qrtz_scheduler_state" ADD CONSTRAINT "QRTZ_SCHEDULER_STATE_pkey" PRIMARY KEY ("sched_name", "instance_name");

-- ----------------------------
-- Primary Key structure for table qrtz_simple_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_simple_triggers" ADD CONSTRAINT "QRTZ_SIMPLE_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_simprop_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_simprop_triggers" ADD CONSTRAINT "QRTZ_SIMPROP_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Indexes structure for table qrtz_triggers
-- ----------------------------
CREATE INDEX "sched_name" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_triggers" ADD CONSTRAINT "QRTZ_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Foreign Keys structure for table nt_sys_dict_item
-- ----------------------------
ALTER TABLE "public"."nt_sys_dict_item" ADD CONSTRAINT "idx_dict_item_type_id" FOREIGN KEY ("dict_item_type_id") REFERENCES "public"."nt_sys_dict_item_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_menu
-- ----------------------------
ALTER TABLE "public"."nt_sys_menu" ADD CONSTRAINT "fk_sm_menu_type_id" FOREIGN KEY ("menu_type_id") REFERENCES "public"."nt_sys_menu_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_menu_oper
-- ----------------------------
ALTER TABLE "public"."nt_sys_menu_oper" ADD CONSTRAINT "idx_menu_id" FOREIGN KEY ("menu_id") REFERENCES "public"."nt_sys_menu" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."nt_sys_menu_oper" ADD CONSTRAINT "idx_operation_id" FOREIGN KEY ("operation_id") REFERENCES "public"."nt_sys_operation" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_organization
-- ----------------------------
ALTER TABLE "public"."nt_sys_organization" ADD CONSTRAINT "idx_sys_org_config_id" FOREIGN KEY ("config_id") REFERENCES "public"."nt_data_source_config" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_role_menu
-- ----------------------------
ALTER TABLE "public"."nt_sys_role_menu" ADD CONSTRAINT "idx_role_menu_menu_id" FOREIGN KEY ("menu_id") REFERENCES "public"."nt_sys_menu" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."nt_sys_role_menu" ADD CONSTRAINT "idx_role_menu_menu_type_id" FOREIGN KEY ("menu_type_id") REFERENCES "public"."nt_sys_menu_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."nt_sys_role_menu" ADD CONSTRAINT "idx_role_menu_role_id" FOREIGN KEY ("role_id") REFERENCES "public"."nt_sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_role_menu_oper
-- ----------------------------
ALTER TABLE "public"."nt_sys_role_menu_oper" ADD CONSTRAINT "idx_role_menu_oper_menu_id" FOREIGN KEY ("menu_id") REFERENCES "public"."nt_sys_menu" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."nt_sys_role_menu_oper" ADD CONSTRAINT "idx_role_menu_oper_menu_type_id" FOREIGN KEY ("menu_type_id") REFERENCES "public"."nt_sys_menu_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."nt_sys_role_menu_oper" ADD CONSTRAINT "idx_role_menu_oper_operation_id" FOREIGN KEY ("operation_id") REFERENCES "public"."nt_sys_operation" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."nt_sys_role_menu_oper" ADD CONSTRAINT "idx_role_menu_oper_role_id" FOREIGN KEY ("role_id") REFERENCES "public"."nt_sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_user_job
-- ----------------------------
ALTER TABLE "public"."nt_sys_user_job" ADD CONSTRAINT "idx_user_job_job_id" FOREIGN KEY ("job_id") REFERENCES "public"."nt_sys_job" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."nt_sys_user_job" ADD CONSTRAINT "idx_user_job_user_id" FOREIGN KEY ("user_id") REFERENCES "public"."nt_sys_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_user_org
-- ----------------------------
ALTER TABLE "public"."nt_sys_user_org" ADD CONSTRAINT "idx_user_org_org_id" FOREIGN KEY ("org_id") REFERENCES "public"."nt_sys_organization" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."nt_sys_user_org" ADD CONSTRAINT "idx_user_org_user_id" FOREIGN KEY ("user_id") REFERENCES "public"."nt_sys_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_user_role
-- ----------------------------
ALTER TABLE "public"."nt_sys_user_role" ADD CONSTRAINT "idx_user_role_role_id" FOREIGN KEY ("role_id") REFERENCES "public"."nt_sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."nt_sys_user_role" ADD CONSTRAINT "idx_user_role_user_id" FOREIGN KEY ("user_id") REFERENCES "public"."nt_sys_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table qrtz_blob_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_blob_triggers" ADD CONSTRAINT "qrtz_blob_triggers_ibfk_1" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "public"."qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table qrtz_cron_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_cron_triggers" ADD CONSTRAINT "qrtz_cron_triggers_ibfk_1" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "public"."qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table qrtz_simple_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_simple_triggers" ADD CONSTRAINT "qrtz_simple_triggers_ibfk_1" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "public"."qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table qrtz_simprop_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_simprop_triggers" ADD CONSTRAINT "qrtz_simprop_triggers_ibfk_1" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "public"."qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table qrtz_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_triggers" ADD CONSTRAINT "qrtz_triggers_ibfk_1" FOREIGN KEY ("sched_name", "job_name", "job_group") REFERENCES "public"."qrtz_job_details" ("sched_name", "job_name", "job_group") ON DELETE NO ACTION ON UPDATE NO ACTION;
