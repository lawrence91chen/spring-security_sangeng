CREATE SCHEMA `spring-security_sangeng` DEFAULT CHARACTER SET utf8mb4;

--
-- USER
--
CREATE TABLE sys_user (
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
	user_name VARCHAR(64) NOT NULL COMMENT '名稱',
	nick_name VARCHAR(64) NOT NULL COMMENT '暱稱',
	password VARCHAR(64) NOT NULL COMMENT '密碼',
	status CHAR(1) NOT NULL DEFAULT '0' COMMENT '帳號狀態 (0:正常、1:停用)',
	email VARCHAR(64) COMMENT '信箱',
	phone_number VARCHAR(32) COMMENT '手機',
	sex CHAR(1) COMMENT '性別 (0:男、1:女、2:未知)',
	avatar VARCHAR(128) COMMENT '頭像',
	user_type CHAR(1) NOT NULL DEFAULT '1' NOT NULL COMMENT '用戶類型 (0:管理員、1:普通用戶)',
	create_by BIGINT(20) NOT NULL COMMENT '創建人的用戶 ID',
	create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
	update_by BIGINT(20) NOT NULL COMMENT '更新人',
	update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
	del_flag INT(11) DEFAULT '0' COMMENT '刪除標誌 (0:未刪除、1:已刪除)',

	PRIMARY KEY (id)
)
COMMENT = '用戶表';