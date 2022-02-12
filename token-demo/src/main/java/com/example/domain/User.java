package com.example.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class User implements Serializable {

	/**
	 * 主鍵
	 */
	@TableId
	private Long id;

	/**
	 * 用戶名
	 */
	private String userName;

	/**
	 * 暱稱
	 */
	private String nickName;

	/**
	 * 密碼
	 */
	private String password;

	/**
	 * 帳號狀態 (0: 正常、1: 停用)
	 */
	private String status;

	/**
	 * 信箱
	 */
	private String email;

	/**
	 * 手機
	 */
	private String phoneNumber;

	/**
	 * 性別 (0:男、1:女、2:未知)
	 */
	private String sex;

	/**
	 * 頭像
	 */
	private String avatar;

	/**
	 * 用戶類型 (0:管理員、1:普通用戶)
	 */
	private String userType;

	/**
	 * 創建人的用戶 ID
	 */
	private Long createBy;

	/**
	 * 創建時間
	 */
	private Date createTime;

	/**
	 * 更新人
	 */
	private Long updateBy;

	/**
	 * 更新時間
	 */
	private Date updateTime;

	/**
	 * 刪除標誌 (0:未刪除、1:已刪除)
	 */
	private Integer delFlag;
}
