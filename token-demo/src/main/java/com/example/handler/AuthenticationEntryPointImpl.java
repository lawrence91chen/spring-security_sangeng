package com.example.handler;

import com.alibaba.fastjson.JSON;
import com.example.domain.ResponseResult;
import com.example.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		// 處理異常
		ResponseResult<Object> result = new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "用戶認證失敗，請重新登入");
		String json = JSON.toJSONString(result);

		WebUtils.renderString(response, json);
	}
}
