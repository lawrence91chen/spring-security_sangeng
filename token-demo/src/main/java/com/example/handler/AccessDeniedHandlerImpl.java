package com.example.handler;

import com.alibaba.fastjson.JSON;
import com.example.domain.ResponseResult;
import com.example.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 處理異常
		ResponseResult<Object> result = new ResponseResult<>(HttpStatus.FORBIDDEN.value(), "您的權限不足");
		String json = JSON.toJSONString(result);

		WebUtils.renderString(response, json);
	}
}
