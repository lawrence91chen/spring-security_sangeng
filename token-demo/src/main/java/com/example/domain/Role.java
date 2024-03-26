package com.example.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("sys_role")
public class Role implements Serializable {
	@TableId
	private Long id;

	private String name;

	private String roleKey;

	private String status;

	private Long createBy;

	private Date createTime;

	private Long updateBy;

	private Date updateTime;

	private Integer delFlag;

	private String remark;
}