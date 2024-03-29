/*
 Navicat Premium Data Transfer

 Source Server         : car-manage
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 192.168.1.196:3306
 Source Schema         : car-manage

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 27/03/2024 17:58:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for car_parking_pay_rule
-- ----------------------------
DROP TABLE IF EXISTS `car_parking_pay_rule`;
CREATE TABLE `car_parking_pay_rule`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `rule_level` int NULL DEFAULT NULL COMMENT '规则等级',
  `rule_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则信息',
  `available_min_parking` int NULL DEFAULT NULL COMMENT '可用最小时间(最多68年)',
  `available_max_parking` int NULL DEFAULT NULL COMMENT '可用最大时间(最多68年)',
  `cost_money` int NULL DEFAULT NULL COMMENT '消费金额(元整)/小时',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '车辆停放费用规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car_parking_pay_rule
-- ----------------------------
INSERT INTO `car_parking_pay_rule` VALUES (1, 1, '0-2小时', 0, 7200, 0);
INSERT INTO `car_parking_pay_rule` VALUES (2, 2, '2-8小时', 7200, 28800, 2);
INSERT INTO `car_parking_pay_rule` VALUES (3, 3, '8小时以上(不得超过68年)', 28800, 2147480000, 3);

-- ----------------------------
-- Table structure for car_parking_violation_record
-- ----------------------------
DROP TABLE IF EXISTS `car_parking_violation_record`;
CREATE TABLE `car_parking_violation_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `no_parking_area` int NULL DEFAULT NULL COMMENT '禁停区域(1-5)',
  `plate_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车牌',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `penalty_money` int NULL DEFAULT NULL COMMENT '罚款(元整)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `pay_status` int NULL DEFAULT 0 COMMENT '支付状态(0未支付 1已支付)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `appoint_id` bigint NULL DEFAULT NULL COMMENT '预约id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 781913788616245249 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '车辆违停罚款记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car_parking_violation_record
-- ----------------------------
INSERT INTO `car_parking_violation_record` VALUES (1, 2, '京A00006', 5, 17, '2024-03-26 18:29:33', '赵六', '2023-05-03 03:29:02', 'admin', 0, '停在禁停区了', NULL);
INSERT INTO `car_parking_violation_record` VALUES (2, 2, '京A00009', NULL, 128, '2024-03-26 18:29:33', '李四', '2023-09-16 10:15:05', 'admin', 0, '停在禁停区了', 779508805508988928);
INSERT INTO `car_parking_violation_record` VALUES (781528786713391104, 1, '京A00009', 8, 20, '2024-03-26 14:36:23', '超级管理员', '2024-03-26 14:36:23', '超级管理员', 0, '违章原因', NULL);
INSERT INTO `car_parking_violation_record` VALUES (781529484054179840, 1, '京A00009', 8, 20, '2024-03-26 14:39:09', '超级管理员', '2024-03-26 14:40:48', '超级管理员', 1, '违章原因', NULL);
INSERT INTO `car_parking_violation_record` VALUES (781913788616245248, 1, '辽A11111', 1, 12, '2024-03-27 16:06:14', '超级管理员', '2024-03-27 16:14:28', '超级管理员', 0, '', NULL);

-- ----------------------------
-- Table structure for car_stall_appoint
-- ----------------------------
DROP TABLE IF EXISTS `car_stall_appoint`;
CREATE TABLE `car_stall_appoint`  (
  `id` bigint NOT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `ap_order_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预约单号',
  `plate_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号',
  `id_card_no` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `ap_begin_time` datetime(0) NULL DEFAULT NULL COMMENT '预约开始时间',
  `ap_end_time` datetime(0) NULL DEFAULT NULL COMMENT '预约结束时间',
  `create_day` date NULL DEFAULT NULL COMMENT '创建日',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `ap_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '预约状态(0 待审核 1审核通过 2审核拒绝  3已结束)',
  `refuse_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拒绝原因',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_day_plate_number`(`plate_number`, `create_day`) USING BTREE COMMENT '创建日车牌唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '车辆预约记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car_stall_appoint
-- ----------------------------
INSERT INTO `car_stall_appoint` VALUES (777948100208046080, '预约人张三丰', 'AP24031600001', '京A66661', '13145678910', '110000197001016666', '2024-03-18 09:00:00', '2024-03-18 23:00:00', '2024-03-16', '2024-03-16 19:28:01', '张三', '2024-03-18 21:58:56', '张三', '3', NULL, '可能来不了');
INSERT INTO `car_stall_appoint` VALUES (777949742508093440, '预约人张四丰', 'AP24031600002', '京A66662', '13145678910', '110000197001016666', '2024-03-18 09:00:00', '2024-03-18 22:00:00', '2024-03-16', '2024-03-16 17:34:32', '张三', '2024-03-18 22:34:32', '张三', '1', NULL, '可能来不了');
INSERT INTO `car_stall_appoint` VALUES (778760009805545472, '毛利小五郎', 'AP24031800001', '京A66661', '13145678910', '110000197001016666', '2024-03-16 09:00:00', '2024-03-16 16:00:00', '2024-03-18', '2024-03-18 23:14:15', '张三', '2024-03-18 23:14:15', '张三', '0', NULL, '可能来不了');
INSERT INTO `car_stall_appoint` VALUES (779390646124867584, '测试', 'AP24032000001', '11', '11', '111', '2024-03-21 00:00:00', '2024-03-29 00:00:00', '2024-03-20', '2024-03-20 17:00:10', '超级管理员', '2024-03-20 17:00:10', '超级管理员', '0', NULL, '111');
INSERT INTO `car_stall_appoint` VALUES (779508490927800320, '李', 'AP24032100002', '辽A11111', '1892121231', '', '2024-03-21 00:48:11', '2024-03-21 00:48:14', '2024-03-21', '2024-03-21 00:48:27', '超级管理员', '2024-03-21 00:48:27', '超级管理员', '0', NULL, '');
INSERT INTO `car_stall_appoint` VALUES (779508805508988928, '李', 'AP24032100004', '辽A111112', '1281251223', '', '2024-03-21 00:49:28', '2024-03-22 00:00:00', '2024-03-21', '2024-03-21 00:49:42', '超级管理员', '2024-03-21 00:53:01', '超级管理员', '3', NULL, '');

-- ----------------------------
-- Table structure for car_stall_record
-- ----------------------------
DROP TABLE IF EXISTS `car_stall_record`;
CREATE TABLE `car_stall_record`  (
  `id` bigint NOT NULL,
  `plate_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车牌',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id(没有不填)',
  `in_garage_num` int NULL DEFAULT NULL COMMENT '进大门编号',
  `out_garage_num` int NULL DEFAULT NULL COMMENT '出大门编号',
  `access_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进出状态(1进 2出)',
  `ap_order_id` bigint NULL DEFAULT NULL COMMENT '预约id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态(0正常,1已结束未付款 ,2已结束已付款)',
  `total_time` int NULL DEFAULT NULL COMMENT '总时间(秒,最高68年)',
  `pay_money` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '需要缴纳费用',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '车辆进出口记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car_stall_record
-- ----------------------------
INSERT INTO `car_stall_record` VALUES (778665557192814592, '京A66661', NULL, 1, 1, '2', 777948100208046080, '2024-03-18 08:00:00', '张三', '2024-03-18 22:43:06', '张三', '1', 52986, '33', NULL);
INSERT INTO `car_stall_record` VALUES (778753489591812096, '京A00010', 9, 1, 1, '2', NULL, '2024-03-18 22:48:20', '张三', '2024-03-18 22:49:42', '张三', '1', 81, '0', NULL);
INSERT INTO `car_stall_record` VALUES (778753957692915712, '京A00010', 9, 1, 1, '2', NULL, '2024-03-18 06:50:12', '张三', '2024-03-18 23:24:48', '张三', '1', 48876, '30', NULL);
INSERT INTO `car_stall_record` VALUES (778763346298748928, '京A00009', 8, 1, 1, '2', NULL, '2024-03-18 23:27:30', '张三', '2024-03-18 23:28:01', '张三', '1', 30, '0', NULL);
INSERT INTO `car_stall_record` VALUES (779507340800507904, '京A00009', 8, 1, 0, '1', NULL, '2024-03-21 00:43:53', '超级管理员', '2024-03-21 00:43:53', '超级管理员', '0', NULL, NULL, NULL);
INSERT INTO `car_stall_record` VALUES (779509640494252032, '辽A111112', NULL, 1, 0, '1', 779508805508988928, '2024-03-21 00:53:01', '超级管理员', '2024-03-21 00:53:01', '超级管理员', '0', NULL, NULL, NULL);
INSERT INTO `car_stall_record` VALUES (781527571300560896, '京A00009', 8, 1, 1, '2', NULL, '2024-03-26 14:31:33', '超级管理员', '2024-03-26 14:37:00', '超级管理员', '1', 326, '0', NULL);
INSERT INTO `car_stall_record` VALUES (781529164183973888, '京A00009', 8, 1, 1, '2', NULL, '2024-03-26 14:37:53', '超级管理员', '2024-03-26 14:38:24', '超级管理员', '1', 31, '0', NULL);
INSERT INTO `car_stall_record` VALUES (781529455834902528, '京A00009', 8, 1, 1, '2', NULL, '2024-03-26 14:39:02', '超级管理员', '2024-03-26 14:40:48', '超级管理员', '1', 105, '20', NULL);

-- ----------------------------
-- Table structure for car_user_record
-- ----------------------------
DROP TABLE IF EXISTS `car_user_record`;
CREATE TABLE `car_user_record`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `plate_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车牌',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_plate_number`(`plate_number`) USING BTREE COMMENT '车牌唯一',
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户车牌关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of car_user_record
-- ----------------------------
INSERT INTO `car_user_record` VALUES (2, 2, '京A00002');
INSERT INTO `car_user_record` VALUES (3, 3, '京A00003');
INSERT INTO `car_user_record` VALUES (4, 3, '京A00004');
INSERT INTO `car_user_record` VALUES (5, 4, '京A00005');
INSERT INTO `car_user_record` VALUES (6, 5, '京A00006');
INSERT INTO `car_user_record` VALUES (7, 6, '京A00007');
INSERT INTO `car_user_record` VALUES (8, 7, '京A00008');
INSERT INTO `car_user_record` VALUES (9, 8, '京A00009');
INSERT INTO `car_user_record` VALUES (10, 9, '京A00010');
INSERT INTO `car_user_record` VALUES (779464045160120320, 20, '京66666');
INSERT INTO `car_user_record` VALUES (781852658040537088, 1, '辽A11111');
INSERT INTO `car_user_record` VALUES (781853961579888640, 1, '京A11111');

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept`  (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `level` int NULL DEFAULT NULL COMMENT '等级',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `user_id` bigint NULL DEFAULT 1 COMMENT '所属人用户id',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NULL DEFAULT 0 COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_dept
-- ----------------------------
INSERT INTO `system_dept` VALUES (1, 0, '0', 1, '校园管理平台', 0, NULL, NULL, 'admin', '2024-03-10 21:52:13', '', '2024-03-10 21:52:13', 0, '0', NULL);
INSERT INTO `system_dept` VALUES (2, 1, '0,1', 2, '经济管理学院', 1, NULL, NULL, 'admin', '2024-03-10 21:52:13', 'admin', '2024-03-10 21:52:13', 0, '0', NULL);
INSERT INTO `system_dept` VALUES (3, 1, '0,1', 2, '信息学院', 2, NULL, NULL, 'admin', '2024-03-10 21:52:13', '', '2024-03-10 21:52:13', 0, '0', NULL);
INSERT INTO `system_dept` VALUES (4, 1, '0,1', 2, '法律学院', 3, NULL, NULL, 'admin', '2024-03-10 21:52:13', '', '2024-03-10 21:52:13', 0, '0', NULL);
INSERT INTO `system_dept` VALUES (56, 1, '0,1', 2, '测试部门', 1, 1, '王德发', '张三', '2024-03-19 18:52:42', '张三', '2024-03-19 19:09:41', 0, '1', '');
INSERT INTO `system_dept` VALUES (57, 1, '0,1', 2, '测试部门11', 1, 1, '赵四', '超级管理员', '2024-03-20 13:32:54', '超级管理员', '2024-03-20 13:33:11', 0, '1', '');
INSERT INTO `system_dept` VALUES (58, 1, '0,1', 2, '测试部门aaaa', 222, 1, '防守打法胜多负少', '超级管理员', '2024-03-20 22:16:30', '超级管理员', '2024-03-20 22:16:30', 0, '1', '');
INSERT INTO `system_dept` VALUES (59, 1, '0,1', 2, '信息學院', 4, 1, '趙', '张三', '2024-03-24 13:56:20', '张三', '2024-03-24 13:56:20', 0, '0', '');

-- ----------------------------
-- Table structure for system_dict
-- ----------------------------
DROP TABLE IF EXISTS `system_dict`;
CREATE TABLE `system_dict`  (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典详情名(例子: 男女或不详等)',
  `dict_level` int NULL DEFAULT NULL COMMENT '不同类型 用这个等级区分',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典英文名',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典分类',
  `dict_variable` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典变量',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_dict
-- ----------------------------
INSERT INTO `system_dict` VALUES (1, '字典表占位', 0, '0', '', '', '', NULL, NULL, '', NULL, NULL);
INSERT INTO `system_dict` VALUES (50, '男', 1, '0', 'sex', '用户性别', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, '性别男');
INSERT INTO `system_dict` VALUES (51, '女', 1, '1', 'sex', '用户性别', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, '性别女');
INSERT INTO `system_dict` VALUES (52, '未知', 1, '2', 'sex', '用户性别', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, '未知');
INSERT INTO `system_dict` VALUES (100, '默认状态', 2, '0', 'status', '状态', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, NULL);
INSERT INTO `system_dict` VALUES (101, '已完成', 2, '1', 'status', '状态', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, NULL);
INSERT INTO `system_dict` VALUES (102, '已取消', 2, '2', 'status', '状态', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, NULL);
INSERT INTO `system_dict` VALUES (150, '未删除', 3, '0', 'del_flag', '删除状态', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, 'Integer');
INSERT INTO `system_dict` VALUES (151, '已删除', 3, '1', 'del_flag', '删除状态', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, 'char');
INSERT INTO `system_dict` VALUES (200, '1号门', 4, '1', 'garage_num', '校园大门', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, NULL);
INSERT INTO `system_dict` VALUES (201, '2号门', 4, '2', 'garage_num', '校园大门', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, NULL);
INSERT INTO `system_dict` VALUES (202, '3号门', 4, '3', 'garage_num', '校园大门', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, NULL);
INSERT INTO `system_dict` VALUES (203, '4号门', 4, '4', 'garage_num', '校园大门', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, NULL);
INSERT INTO `system_dict` VALUES (204, '5号门', 4, '5', 'garage_num', '校园大门', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, 'Integer');
INSERT INTO `system_dict` VALUES (250, '禁停区1', 5, '1', 'no_parking_area', '禁停区', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, NULL);
INSERT INTO `system_dict` VALUES (251, '禁停区2', 5, '2', 'no_parking_area', '禁停区', 'int', 'admin', '2024-03-19 21:20:14', '', NULL, NULL);
INSERT INTO `system_dict` VALUES (253, '禁停区3', 5, '3', 'no_parking_area', '禁停区', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, NULL);
INSERT INTO `system_dict` VALUES (254, '禁停区4', 5, '4', 'no_parking_area', '禁停区', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, 'char');
INSERT INTO `system_dict` VALUES (255, '禁停区5', 5, '5', 'no_parking_area', '禁停区', 'int', 'admin', '2024-03-09 21:35:30', '', NULL, 'char');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 577 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES (1, '系统管理', 0, 1, 'system', 'M', '0', NULL, NULL, 'admin', '2024-03-06 20:47:46', '', NULL, NULL);
INSERT INTO `system_menu` VALUES (2, '车辆管理', 0, 2, 'car', 'M', '0', NULL, NULL, 'admin', '2024-03-06 20:47:46', '', NULL, NULL);
INSERT INTO `system_menu` VALUES (3, '统计报表', 0, 3, 'analyse', 'M', '0', NULL, NULL, 'admin', '2024-03-06 20:47:46', '', NULL, NULL);
INSERT INTO `system_menu` VALUES (4, '首页', 0, 0, 'index', 'M', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (100, '用户管理', 1, 4, '/user', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (101, '角色管理', 1, 5, '/role', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (102, '菜单管理', 1, 8, '/menu', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (103, '部门管理', 1, 6, '/department', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (104, '岗位管理', 1, 9, '/post', 'C', '1', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (105, '通知管理', 1, 7, '/notice', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (106, '车位配置管理', 2, 10, '/carDisposition', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (107, '出入口管理', 2, 13, '/exim', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (108, '停放费用管理', 2, 11, '/expenses', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (109, '违停车辆管理', 2, 14, '/parking', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (110, '访客预约管理', 2, 12, '/booking', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (111, '违规信息管理', 3, 15, '/rating', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (112, '车辆文明统计', 3, 16, '/carViolate', 'C', '0', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (113, '其他', 3, 0, '', 'C', '1', NULL, '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (500, '新增用户', 100, 0, '', 'F', '0', 'system:user:add', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (501, '删除用户', 100, 0, '', 'F', '0', 'system:user:delete', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (502, '修改用户', 100, 0, '', 'F', '0', 'system:user:edit', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (503, '查询用户1', 100, 0, '', 'F', '0', '', '', 'admin', '2024-03-09 23:06:32', '超级管理员', '2024-03-20 21:40:21', '');
INSERT INTO `system_menu` VALUES (510, '新增角色', 101, 0, '', 'F', '0', 'system:role:add', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (511, '修改角色', 101, 0, '', 'F', '0', 'system:role:edit', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (512, '删除角色', 101, 0, '', 'F', '0', 'system:role:delete', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (513, '查询角色', 101, 0, '', 'F', '0', 'system:role:list', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (514, '分配权限', 101, 0, '', 'F', '0', 'system:role:allot', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (520, '新增菜单', 102, 0, '', 'F', '0', 'system:menu:add', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (521, '删除菜单', 102, 0, '', 'F', '0', 'system:menu:delete', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (522, '修改菜单', 102, 0, '', 'F', '0', 'system:menu:edit', '#', 'admin', '2024-03-09 23:06:32', '', NULL, '');
INSERT INTO `system_menu` VALUES (523, '查询菜单', 102, 0, '', 'F', '0', 'system:menu:list', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (530, '新增部门', 103, 0, '', 'F', '0', 'system:dept:add', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (531, '修改部门', 103, 0, '', 'F', '0', 'system:dept:edit', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (532, '删除部门', 103, 0, '', 'F', '0', 'system:dept:delete', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (533, '查询部门', 103, 0, '', 'F', '0', 'system:dept:list', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (540, '新增岗位', 104, 0, '', 'F', '0', 'system:post:add', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (541, '修改岗位', 104, 0, '', 'F', '0', 'system:post:edit', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (542, '删除岗位', 104, 0, '', 'F', '0', 'system:post:delete', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (543, '查询岗位', 104, 0, '', 'F', '0', 'system:post:list', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (550, '新增通知', 105, 0, '', 'F', '0', 'system:notice:add', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (551, '修改通知', 105, 0, '', 'F', '0', 'system:notice:edit', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (552, '删除通知', 105, 0, '', 'F', '0', 'system:notice:delete', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (553, '查询通知', 105, 0, '', 'F', '0', 'system:notice:list', '#', 'admin', '2024-03-09 23:36:32', NULL, NULL, '');
INSERT INTO `system_menu` VALUES (554, '查询车位配置', 106, 0, '', 'F', '0', 'system:unifySet:list', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (555, '修改车位配置', 106, 0, '', 'F', '0', 'system:unifySet:edit', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (556, '刷新车位配置', 106, 0, '', 'F', '0', 'system:unifySet:refresh', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (557, '查询进出口', 107, 0, '', 'F', '0', 'car:stallRecord:list', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (558, '添加进出口', 107, 0, '', 'F', '0', 'car:stallRecord:add', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (559, '查询停放费用费用', 108, 0, '', 'F', '0', 'car:payRule:list', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (560, '添加停放费用费用', 108, 0, '', 'F', '0', 'car:payRule:add', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (561, '修改停放费用费用', 108, 0, '', 'F', '0', 'car:payRule:edit', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (562, '刷新停放费用配置', 108, 0, '', 'F', '0', 'car:payRule:refresh', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (563, '删除停放费用配置', 108, 0, '', 'F', '0', 'car:payRule:delete', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (564, '查询违规车辆', 109, 0, '', 'F', '0', 'car:violation:list', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (565, '查询车辆预约', 110, 0, '', 'F', '0', 'car:appoint:list', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (566, '添加车辆预约', 110, 0, '', 'F', '0', 'car:appoint:add', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (567, '修改车辆预约', 110, 0, '', 'F', '0', 'car:appoint:edit', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (568, '删除车辆预约', 110, 0, '', 'F', '0', 'car:appoint:delete', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (569, '停罚款数据报表', 111, 0, '', 'F', '0', 'car:violation:report', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (570, '车辆文明评比', 112, 0, '', 'F', '0', 'car:analyse:compare', '#', 'admin', '2024-03-19 21:09:04', '', NULL, '');
INSERT INTO `system_menu` VALUES (571, '新增违规车辆', 109, 0, '', 'F', '0', 'car:violation:add', '#', '', NULL, '', NULL, '');
INSERT INTO `system_menu` VALUES (572, '删除违规车辆', 109, 0, '', 'F', '0', 'car:violation:delete', '#', '', NULL, '', NULL, '');
INSERT INTO `system_menu` VALUES (573, '修改违规车辆', 109, 0, '', 'F', '0', 'car:violation:edit', '#', '', NULL, '', NULL, '');

-- ----------------------------
-- Table structure for system_notice
-- ----------------------------
DROP TABLE IF EXISTS `system_notice`;
CREATE TABLE `system_notice`  (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公告内容',
  `status` int NULL DEFAULT 0 COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除状态(0正常 1删除)',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_notice
-- ----------------------------
INSERT INTO `system_notice` VALUES (10, '公告一', '系统正在升级, 请稍后再用', 0, '张三', '2024-03-10 21:52:13', '张三', '2024-03-13 21:52:13', NULL, 1);
INSERT INTO `system_notice` VALUES (11, '公告二', '系统正在升级, 请稍后再用', 0, '超级管理员', '2024-03-19 21:14:48', '超级管理员', '2024-03-19 21:14:48', NULL, 1);
INSERT INTO `system_notice` VALUES (14, '吗,吗,好的认同感环刀法更大', '第三方师傅胜多负少是否', 0, '超级管理员', '2024-03-20 22:20:41', '超级管理员', '2024-03-20 22:20:41', NULL, 1);
INSERT INTO `system_notice` VALUES (15, 'hggb ', 'yybbjjni', 0, '张三', '2024-03-24 11:22:55', '张三', '2024-03-24 11:22:55', NULL, 0);

-- ----------------------------
-- Table structure for system_post
-- ----------------------------
DROP TABLE IF EXISTS `system_post`;
CREATE TABLE `system_post`  (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_post
-- ----------------------------
INSERT INTO `system_post` VALUES (1, '校长', 1, 0, 'admin', '2024-03-12 01:52:13', 'admin', '2024-03-12 01:52:13', '');
INSERT INTO `system_post` VALUES (2, '导员', 2, 0, 'admin', '2024-03-12 01:52:13', '', '2024-03-12 01:52:13', '');
INSERT INTO `system_post` VALUES (3, '任课老师', 3, 0, 'admin', '2024-03-12 01:52:13', '', '2024-03-12 01:52:13', '');
INSERT INTO `system_post` VALUES (4, '学生会主席', 4, 0, 'admin', '2024-03-12 01:52:13', '', '2024-03-12 01:52:13', '');
INSERT INTO `system_post` VALUES (5, '团支书', 5, 0, '', '2024-03-12 01:52:13', '', '2024-03-12 01:52:13', NULL);
INSERT INTO `system_post` VALUES (6, '班长', 5, 0, '', '2024-03-12 01:52:13', '', '2024-03-12 01:52:13', NULL);
INSERT INTO `system_post` VALUES (8, '测试岗位', 100, 0, '超级管理员', '2024-03-21 01:08:13', '超级管理员', '2024-03-21 01:08:13', NULL);

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES (1, '超级管理员', 1, 'admin', '2024-03-06 20:47:46', 'admin', '2024-03-06 20:47:46', '超级管理员(此角色不可进行任何操作)', '0', '0');
INSERT INTO `system_role` VALUES (2, '管理员', 2, 'admin', '2024-03-06 20:47:46', 'admin', '2024-03-06 20:47:46', '管理员', '0', '0');
INSERT INTO `system_role` VALUES (3, '老师', 3, 'admin', '2024-03-06 20:47:46', '超级管理员', '2024-03-24 13:58:17', '', '0', '0');
INSERT INTO `system_role` VALUES (4, '学生', 4, 'admin', '2024-03-06 20:47:46', 'admin', '2024-03-06 20:47:46', NULL, '0', '0');
INSERT INTO `system_role` VALUES (5, '商户', 5, 'admin', '2024-03-06 20:47:46', 'admin', '2024-03-06 20:47:46', NULL, '0', '0');
INSERT INTO `system_role` VALUES (6, '游客', 100, '张三', '2024-03-10 20:29:05', '张三', '2024-03-10 20:29:05', '测试数据', '0', '1');
INSERT INTO `system_role` VALUES (7, '我是自定义角色', 1, '张三', '2024-03-10 20:29:05', NULL, '2024-03-10 20:29:05', '备注', '0', '1');
INSERT INTO `system_role` VALUES (8, '教师', 1, '张三', '2024-03-21 00:45:49', '张三', '2024-03-19 19:41:15', '教师', '0', '1');
INSERT INTO `system_role` VALUES (9, '保洁', 1, '张三', '2024-03-21 00:45:49', '张三', '2024-03-19 19:41:12', '保洁', '0', '1');
INSERT INTO `system_role` VALUES (10, '保洁', 1, '张三', '2024-03-21 00:45:49', '张三', '2024-03-19 20:54:49', '保洁', '0', '1');
INSERT INTO `system_role` VALUES (11, '测试0001', 0, '超级管理员', '2024-03-20 22:13:18', '超级管理员', '2024-03-20 22:13:18', '2232', '0', '1');

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  INDEX `idx_role_id`(`role_id`) USING BTREE COMMENT '角色id',
  INDEX `idx_menu_id`(`menu_id`) USING BTREE COMMENT '菜单id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
INSERT INTO `system_role_menu` VALUES (1, 1);
INSERT INTO `system_role_menu` VALUES (1, 2);
INSERT INTO `system_role_menu` VALUES (1, 3);
INSERT INTO `system_role_menu` VALUES (1, 4);
INSERT INTO `system_role_menu` VALUES (1, 100);
INSERT INTO `system_role_menu` VALUES (1, 101);
INSERT INTO `system_role_menu` VALUES (1, 102);
INSERT INTO `system_role_menu` VALUES (1, 103);
INSERT INTO `system_role_menu` VALUES (1, 104);
INSERT INTO `system_role_menu` VALUES (1, 105);
INSERT INTO `system_role_menu` VALUES (1, 106);
INSERT INTO `system_role_menu` VALUES (1, 107);
INSERT INTO `system_role_menu` VALUES (1, 108);
INSERT INTO `system_role_menu` VALUES (1, 109);
INSERT INTO `system_role_menu` VALUES (1, 110);
INSERT INTO `system_role_menu` VALUES (1, 111);
INSERT INTO `system_role_menu` VALUES (1, 112);
INSERT INTO `system_role_menu` VALUES (1, 113);
INSERT INTO `system_role_menu` VALUES (1, 500);
INSERT INTO `system_role_menu` VALUES (1, 501);
INSERT INTO `system_role_menu` VALUES (1, 502);
INSERT INTO `system_role_menu` VALUES (1, 503);
INSERT INTO `system_role_menu` VALUES (1, 510);
INSERT INTO `system_role_menu` VALUES (1, 511);
INSERT INTO `system_role_menu` VALUES (1, 512);
INSERT INTO `system_role_menu` VALUES (1, 513);
INSERT INTO `system_role_menu` VALUES (1, 514);
INSERT INTO `system_role_menu` VALUES (1, 520);
INSERT INTO `system_role_menu` VALUES (1, 521);
INSERT INTO `system_role_menu` VALUES (1, 522);
INSERT INTO `system_role_menu` VALUES (1, 523);
INSERT INTO `system_role_menu` VALUES (1, 530);
INSERT INTO `system_role_menu` VALUES (1, 531);
INSERT INTO `system_role_menu` VALUES (1, 532);
INSERT INTO `system_role_menu` VALUES (1, 533);
INSERT INTO `system_role_menu` VALUES (1, 540);
INSERT INTO `system_role_menu` VALUES (1, 541);
INSERT INTO `system_role_menu` VALUES (1, 542);
INSERT INTO `system_role_menu` VALUES (1, 543);
INSERT INTO `system_role_menu` VALUES (1, 550);
INSERT INTO `system_role_menu` VALUES (1, 551);
INSERT INTO `system_role_menu` VALUES (1, 552);
INSERT INTO `system_role_menu` VALUES (1, 553);
INSERT INTO `system_role_menu` VALUES (1, 554);
INSERT INTO `system_role_menu` VALUES (1, 555);
INSERT INTO `system_role_menu` VALUES (1, 556);
INSERT INTO `system_role_menu` VALUES (1, 557);
INSERT INTO `system_role_menu` VALUES (1, 558);
INSERT INTO `system_role_menu` VALUES (1, 559);
INSERT INTO `system_role_menu` VALUES (1, 560);
INSERT INTO `system_role_menu` VALUES (1, 561);
INSERT INTO `system_role_menu` VALUES (1, 562);
INSERT INTO `system_role_menu` VALUES (1, 563);
INSERT INTO `system_role_menu` VALUES (1, 564);
INSERT INTO `system_role_menu` VALUES (1, 565);
INSERT INTO `system_role_menu` VALUES (1, 566);
INSERT INTO `system_role_menu` VALUES (1, 567);
INSERT INTO `system_role_menu` VALUES (1, 568);
INSERT INTO `system_role_menu` VALUES (1, 569);
INSERT INTO `system_role_menu` VALUES (1, 570);
INSERT INTO `system_role_menu` VALUES (1, 572);
INSERT INTO `system_role_menu` VALUES (2, 100);
INSERT INTO `system_role_menu` VALUES (2, 500);
INSERT INTO `system_role_menu` VALUES (2, 501);
INSERT INTO `system_role_menu` VALUES (2, 502);
INSERT INTO `system_role_menu` VALUES (2, 503);
INSERT INTO `system_role_menu` VALUES (2, 101);
INSERT INTO `system_role_menu` VALUES (2, 512);
INSERT INTO `system_role_menu` VALUES (2, 513);
INSERT INTO `system_role_menu` VALUES (2, 514);
INSERT INTO `system_role_menu` VALUES (2, 510);
INSERT INTO `system_role_menu` VALUES (2, 511);
INSERT INTO `system_role_menu` VALUES (2, 102);
INSERT INTO `system_role_menu` VALUES (2, 520);
INSERT INTO `system_role_menu` VALUES (2, 521);
INSERT INTO `system_role_menu` VALUES (2, 522);
INSERT INTO `system_role_menu` VALUES (2, 523);
INSERT INTO `system_role_menu` VALUES (2, 103);
INSERT INTO `system_role_menu` VALUES (2, 530);
INSERT INTO `system_role_menu` VALUES (2, 531);
INSERT INTO `system_role_menu` VALUES (2, 532);
INSERT INTO `system_role_menu` VALUES (2, 533);
INSERT INTO `system_role_menu` VALUES (2, 104);
INSERT INTO `system_role_menu` VALUES (2, 540);
INSERT INTO `system_role_menu` VALUES (2, 541);
INSERT INTO `system_role_menu` VALUES (2, 542);
INSERT INTO `system_role_menu` VALUES (2, 543);
INSERT INTO `system_role_menu` VALUES (2, 550);
INSERT INTO `system_role_menu` VALUES (2, 553);

-- ----------------------------
-- Table structure for system_unify_set
-- ----------------------------
DROP TABLE IF EXISTS `system_unify_set`;
CREATE TABLE `system_unify_set`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `setting_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置名称',
  `common_str` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公共1字段',
  `common_str1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公共2字段',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统统一设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_unify_set
-- ----------------------------
INSERT INTO `system_unify_set` VALUES (1, '开放时间(之间)', '8', '20', '2024-03-15 21:26:40', '可停车区间(早上8点到晚上10点 暂未启用)');
INSERT INTO `system_unify_set` VALUES (2, '最大停车位数量', '0', '80', '2024-03-15 21:26:40', '不可减少(不可删除)');
INSERT INTO `system_unify_set` VALUES (3, '停车费用上限(元)', '200', NULL, '2024-03-15 21:26:40', '停车费用上限200元(不可删除)');

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门id',
  `real_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户真实姓名',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色id(身份唯一)',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0正常 1删除',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE COMMENT '账号唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 169 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES (1, 1, '超级管理员', 'admin', '$2a$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC', 1, '222@163.com', '13045678910', '0', '', '0', '2024-03-27 17:31:20', 'admin', '2024-03-06 20:47:46', '', '2024-03-02 21:19:55', '管理员', '0');
INSERT INTO `system_user` VALUES (2, 1, '张三', 'zhangsan', '$2a$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC', 2, '123@qq.com', '13145678910', '0', '', '0', '2024-03-24 13:55:05', '', '2024-03-06 20:47:46', '超级管理员', '2024-03-23 22:40:15', '', '0');
INSERT INTO `system_user` VALUES (3, 2, '李四', '13312345678', '$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC', 3, '123@qq.com', '13145678910', '0', '', '0', NULL, '', '2024-03-06 20:47:46', '', NULL, '', '1');
INSERT INTO `system_user` VALUES (4, 3, '王五', '13412345678', '$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC', 3, '123@qq.com', '13145678910', '1', '', '0', NULL, '', '2024-03-06 20:47:46', '超级管理员', '2024-03-20 21:55:12', '', '0');
INSERT INTO `system_user` VALUES (5, 3, '赵六', '14012345678', '$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC', 3, '123@qq.com', '13145678910', '1', '', '0', NULL, '张三', '2024-03-06 20:47:46', '超级管理员', '2024-03-20 21:54:44', '', '0');
INSERT INTO `system_user` VALUES (6, 3, '侯七7', '14012345679', '$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC', 3, '', '13000000000', '1', '', '0', NULL, '张三', '2024-03-11 23:46:50', '张三', '2024-03-11 22:58:40', '', '0');
INSERT INTO `system_user` VALUES (7, 3, '马八', '14012345680', '$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC', 3, '', '13000000001', '1', '', '0', NULL, '张三', '2024-03-11 23:46:50', '张三', '2024-03-11 22:58:40', '', '0');
INSERT INTO `system_user` VALUES (8, 2, '冯九', '14012345681', '$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC', 4, '', '13000000002', '0', '', '0', NULL, '', '2024-03-16 20:49:31', '', NULL, NULL, '0');
INSERT INTO `system_user` VALUES (9, 3, '金十', '14012345682', '$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC', 4, '', '13000000003', '0', '', '0', NULL, '', '2024-03-18 23:56:31', '', NULL, NULL, '0');
INSERT INTO `system_user` VALUES (10, 1, '刘十一', '14012345683', '$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC', 5, '2065399217@QQ.COM', '13000000004', '0', '', '0', NULL, '', '2024-03-18 23:56:31', '超级管理员', '2024-03-20 21:56:39', NULL, '0');
INSERT INTO `system_user` VALUES (164, 2, '哈哈', 'user', '$2a$10$IQhBT1lgidFiuvh7tM4ugeELQ5WIUazgDnyNJBk4J7LfOLDpzASbG', 4, '123@qq.com', '123456789', '1', '', '0', NULL, '超级管理员', '2024-03-20 19:11:21', '超级管理员', '2024-03-20 19:33:04', 'haha', '0');
INSERT INTO `system_user` VALUES (165, 2, 'ljc', '2035752978', '$2a$10$ZzI5GacMFkSVQmyQIWcndOmvgmkcxPIiVgrocF/iRxsOgdsOG9sjS', 4, '20357529782@qq.com', '18604447923', '0', '', '0', NULL, '超级管理员', '2024-03-24 00:28:46', '超级管理员', '2024-03-24 00:28:46', '', '0');
INSERT INTO `system_user` VALUES (166, NULL, '1', '1', '$2a$10$GhBHKafE6.BugOb9kVuScuSYNtU2fRPzZNX5Gs.PHeRkbI25naesW', 3, '', '', '', '', '0', '2024-03-24 00:29:55', '超级管理员', '2024-03-24 00:29:30', '超级管理员', '2024-03-24 00:29:30', '', '1');
INSERT INTO `system_user` VALUES (167, 4, 'cy', '111', '$2a$10$VxMDt0SM6nRivW4Qu8EmS.hdc7.IyPeGmd07HzVZS7z5EZsU6lQ.K', 4, '111', '19153806041', '0', '', '0', '2024-03-24 13:59:53', '张三', '2024-03-24 09:29:37', '张三', '2024-03-24 09:29:37', '', '0');
INSERT INTO `system_user` VALUES (168, 4, '123', '123', '$2a$10$4hTWbYz7iHs5U4a32pBu6.699BjgcC4jmJ0ZhhHKpwpsgYPmQ69NG', 4, '123@11.com', '123', '0', '', '0', '2024-03-24 13:54:29', '超级管理员', '2024-03-24 13:53:50', '超级管理员', '2024-03-24 13:53:50', '111', '0');

-- ----------------------------
-- Table structure for system_user_post
-- ----------------------------
DROP TABLE IF EXISTS `system_user_post`;
CREATE TABLE `system_user_post`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户id',
  INDEX `idx_post_id`(`post_id`) USING BTREE COMMENT '岗位id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_user_post
-- ----------------------------
INSERT INTO `system_user_post` VALUES (6, 4);
INSERT INTO `system_user_post` VALUES (6, 5);
INSERT INTO `system_user_post` VALUES (6, 6);
INSERT INTO `system_user_post` VALUES (2, 1);
INSERT INTO `system_user_post` VALUES (165, 6);
INSERT INTO `system_user_post` VALUES (167, 5);
INSERT INTO `system_user_post` VALUES (168, 8);

-- ----------------------------
-- Procedure structure for AddRandomParkingViolation
-- ----------------------------
DROP PROCEDURE IF EXISTS `AddRandomParkingViolation`;
delimiter ;;
CREATE PROCEDURE `AddRandomParkingViolation`()
BEGIN  
  DECLARE i INT DEFAULT 0;  
  DECLARE no_parking_area INT;  
  DECLARE plate_number VARCHAR(50);  
  DECLARE user_id INT;  
  DECLARE penalty_money INT;  
  DECLARE create_time DATETIME;  
  DECLARE create_by VARCHAR(255);  
  DECLARE update_time DATETIME;  
  DECLARE update_by VARCHAR(255);  
  DECLARE pay_status INT;  
  DECLARE remark VARCHAR(255);  
  
  WHILE i < 100 DO  
    SET no_parking_area = FLOOR(1 + (RAND() * 5));  
    SET user_id = FLOOR(3 + (RAND() * 6));  
    SET penalty_money = FLOOR(10 + (RAND() * 191));
    SET create_time = DATE_ADD(  DATE_ADD('2022-01-01 00:00:00',  INTERVAL  FLOOR(1 + (RAND() * 86400))   SECOND ),
INTERVAL  FLOOR(1 + (RAND() * 800))  DAY);  
    SET create_by = (select real_name from system_user where system_user.user_id= user_id);
    SET update_time = DATE_ADD(create_time, INTERVAL FLOOR(0 + (RAND() * 86400)) SECOND);
    SET update_by = 'admin'; 
    SET pay_status = FLOOR(RAND() * 2);  
    SET remark = null;  
    SET plate_number = (select car_user_record.plate_number from car_user_record where car_user_record.user_id = user_id limit 1); 
  
    INSERT INTO `car_parking_violation_record` (  
      `no_parking_area`,  
      `plate_number`,  
      `user_id`,  
      `penalty_money`,  
      `create_time`,  
      `create_by`,  
      `update_time`,  
      `update_by`,  
      `pay_status`,  
      `remark`  
    ) VALUES (  
      no_parking_area,  
      plate_number,  
      user_id,  
      penalty_money,  
      create_time,  
      create_by,  
      update_time,  
      update_by,  
      pay_status,  
      remark  
    );  
  
    SET i = i + 1;  
  END WHILE;  
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
