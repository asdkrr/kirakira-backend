-- kirakira 数据库结构（MySQL 8）
-- 用法：mysql -uroot -p < schema.sql

CREATE DATABASE IF NOT EXISTS kirakira
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE kirakira;

-- ============================================================
-- 用户表
-- ============================================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL COMMENT '登录名',
    password    VARCHAR(100) NOT NULL COMMENT 'BCrypt 哈希，绝不存明文',
    nickname    VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '昵称',
    avatar      VARCHAR(255) NOT NULL DEFAULT '' COMMENT '头像 URL',
    sign        VARCHAR(255) NOT NULL DEFAULT '' COMMENT '签名',
    exp         INT          NOT NULL DEFAULT 0 COMMENT '经验值',
    coin        DECIMAL(10, 1) NOT NULL DEFAULT 0 COMMENT '硬币',
    fans        INT          NOT NULL DEFAULT 0,
    follows     INT          NOT NULL DEFAULT 0,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_username (username)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='用户';

-- ============================================================
-- 视频表
-- ============================================================
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    uid         INT          NOT NULL COMMENT '作者 uid',
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(100) NOT NULL DEFAULT '' COMMENT '冗余作者名，列表页少一次联表',
    cover       VARCHAR(500) NOT NULL DEFAULT '',
    duration    VARCHAR(20)  NOT NULL DEFAULT '00:00' COMMENT '展示用时长，如 05:44',
    play        VARCHAR(50)  NOT NULL DEFAULT '0' COMMENT '展示用播放量，如 12.3万',
    badge       VARCHAR(20)  NOT NULL DEFAULT '' COMMENT '角标：新/热门',
    danmu       VARCHAR(50)  NOT NULL DEFAULT '0' COMMENT '展示用弹幕数',
    upload_date VARCHAR(30)  NOT NULL DEFAULT '' COMMENT '展示用日期',
    descr       VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '简介',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '1 已发布 0 审核中 2 已删除',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_uid (uid),
    KEY idx_status_created (status, created_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='视频';

-- ============================================================
-- 弹幕表
-- ============================================================
DROP TABLE IF EXISTS `danmu`;
CREATE TABLE `danmu` (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    vid        INT          NOT NULL,
    uid        INT          NOT NULL DEFAULT 0 COMMENT '发送者，0 表示游客',
    content    VARCHAR(100) NOT NULL,
    time_sec   DECIMAL(7, 2) NOT NULL COMMENT '出现在视频第几秒',
    color      VARCHAR(20)  NOT NULL DEFAULT '#ffffff',
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_vid_time (vid, time_sec)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='弹幕';
