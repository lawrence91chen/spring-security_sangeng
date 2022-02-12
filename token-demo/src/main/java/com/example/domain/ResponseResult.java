package com.example.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {
	/** 狀態碼。 */
	private Integer code;
	/** 提示信息。 如果有錯誤，前端可以獲取該字段進行提示 */
	private String msg;
	/** 查詢到的結果數據。 */
	private T data;

	public ResponseResult(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResponseResult(Integer code, T data) {
		this.code = code;
		this.data = data;
	}
}
