CREATE TABLE IF NOT EXISTS `user`
(
    `id`
                int       NOT NULL AUTO_INCREMENT COMMENT '标识符',
    `username`
                varchar(32)    DEFAULT NULL COMMENT '用户名',
    `password`  char(32)       DEFAULT NULL COMMENT '密码',
    `role`      tinyint        DEFAULT NULL COMMENT '用户级别, 0为管理员',
    `gender`    tinyint        DEFAULT NULL COMMENT '性别: 1:男, 0:女',
    `nickname`  varchar(500)   DEFAULT NULL COMMENT '网络名',
    `phone`     char(11)       DEFAULT NULL COMMENT '电话号码',
    `avatar`    varchar(64)    DEFAULT 'default.jpg' COMMENT '用户头像',
    `email`     varchar(100)   DEFAULT NULL COMMENT '邮箱',
    `height`    int            DEFAULT NULL COMMENT '身高',
    `weight`    int            DEFAULT NULL COMMENT '体重',
    `birthday`  timestamp NULL DEFAULT NULL COMMENT '出生日期',
    `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY
        (
         `id`
            )
);
CREATE TABLE IF NOT EXISTS `test`
(
    `id`
                    int       NOT NULL AUTO_INCREMENT COMMENT '标识符',
    `number`
                    varchar(32)    DEFAULT NULL COMMENT '产品编号',
    `name`          char(32)       DEFAULT NULL COMMENT '产品名称',
    `age`           varchar(64)    DEFAULT NULL COMMENT '适合的年龄',
    `effect`        text           DEFAULT NULL COMMENT '产品功效',
    `status`        int            DEFAULT NULL COMMENT '状态',
    `researcher_id` int       NOT NULL COMMENT '研发员id',
    `testor_id`     int       NOT NULL COMMENT '测试员id',
    `create_at`     timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at`     timestamp NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY
        (
         `id`
            )
);
CREATE TABLE IF NOT EXISTS `solution`
(
    `id`
                  int       NOT NULL AUTO_INCREMENT COMMENT '标识符',
    `test_Id`
                  int(32)        DEFAULT NULL COMMENT '所属的测试需求id',
    `fail_reason` text           DEFAULT NULL COMMENT '不通过的原因',
    `content`     text           DEFAULT NULL COMMENT '测试方案内容',
    `report`      text           DEFAULT NULL COMMENT '测试报告内容',
    `status`      int            DEFAULT 0 COMMENT '状态',
    `accessory`   char(64)       DEFAULT '' COMMENT '附件路径',
    `photo`   char(64)       DEFAULT '' COMMENT '现场照片路径',
    `create_at`   timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at`   timestamp NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY
        (
         `id`
            )
);
CREATE TABLE IF NOT EXISTS `volunteer`
(
    `id`
                int       NOT NULL AUTO_INCREMENT COMMENT '标识符',
    `name`     char(20)       DEFAULT NULL COMMENT '姓名',
    `solution_id` int       NOT NULL COMMENT '所属的方案id',
    `sex`       char(2)        DEFAULT NULL COMMENT '性别: 1:男, 0:女',
    `age`       int            DEFAULT NULL COMMENT '年龄',
    `phone`     char(11)       DEFAULT NULL COMMENT '电话号码',
    `skin`      varchar(512)   DEFAULT NULL COMMENT '肤质',
    `sensibility` char(2)        DEFAULT NULL COMMENT '是否敏感肌',
    `spot`      char(2)        DEFAULT NULL COMMENT '是否长斑',
    `acne`      char(2)        DEFAULT NULL COMMENT '是否痤疮',
    `other`      text           DEFAULT NULL COMMENT '其他说明',
    `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY
        (
         `id`
            )
);