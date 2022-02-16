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
	del_flag INT(1) DEFAULT '0' COMMENT '刪除標誌 (0:未刪除、1:已刪除)',

	PRIMARY KEY (id)
)
COMMENT = '用戶表';


--
-- MENU
--
CREATE TABLE sys_menu (
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
	menu_name VARCHAR(64) NOT NULL COMMENT '功能名稱',
	path VARCHAR(200) COMMENT '路由地址',
	component VARCHAR(255) COMMENT '組件路徑',
	visible CHAR(1) DEFAULT '0' COMMENT '狀態 (0:顯示、1:隱藏)',
	status CHAR(1) DEFAULT '0' COMMENT '狀態 (0:正常、1:停用)',
	perms VARCHAR(100) COMMENT '權限標示(key)',
	icon VARCHAR(100) DEFAULT '#' COMMENT '功能圖示',
	create_by BIGINT(20) NOT NULL COMMENT '創建人的用戶 ID',
	create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
	update_by BIGINT(20) NOT NULL COMMENT '更新人',
	update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
	del_flag INT(1) DEFAULT '0' COMMENT '刪除標誌 (0:未刪除、1:已刪除)',
	remark VARCHAR(500) COMMENT '備註',

	PRIMARY KEY (id)
)
COMMENT = '權限表';


--
-- ROLE
--
CREATE TABLE sys_role (
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
	name VARCHAR(128) NOT NULL COMMENT '角色名稱',
	role_key VARCHAR(100) COMMENT '角色權限',
	status CHAR(1) DEFAULT '0' COMMENT '狀態 (0:正常、1:停用)',
	create_by BIGINT(20) NOT NULL COMMENT '創建人的用戶 ID',
	create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
	update_by BIGINT(20) NOT NULL COMMENT '更新人',
	update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
	del_flag INT(1) DEFAULT '0' COMMENT '刪除標誌 (0:未刪除、1:已刪除)',
	remark VARCHAR(500) COMMENT '備註',

	PRIMARY KEY (id)
)
COMMENT = '角色表';


--
-- ROLE_MENU
--
CREATE TABLE sys_role_menu (
	role_id BIGINT(20) NOT NULL COMMENT 'sys_role.id',
	menu_id BIGINT(20) NOT NULL COMMENT 'sys_menu.id',

	PRIMARY KEY (role_id, menu_id),
	FOREIGN KEY (role_id) REFERENCES sys_role(id),
    FOREIGN KEY (menu_id) REFERENCES sys_menu(id)
)
COMMENT = '角色權限對應表';


--
-- USER_ROLE
--
CREATE TABLE sys_user_role (
	user_id BIGINT(20) NOT NULL COMMENT 'sys_user.id',
	role_id BIGINT(20) NOT NULL COMMENT 'sys_role.id',

	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY (user_id) REFERENCES sys_user(id),
	FOREIGN KEY (role_id) REFERENCES sys_role(id)
)
COMMENT = '用戶角色對應表';

