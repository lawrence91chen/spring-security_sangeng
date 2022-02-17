package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler successHandler;

	@Autowired
	private AuthenticationFailureHandler failureHandler;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				// 配置認證成功處理器
				.successHandler(successHandler)
				// 配置認證失敗處理器
				.failureHandler(failureHandler);

		http.logout()
				// 配置註銷成功處理器
				.logoutSuccessHandler(logoutSuccessHandler);

		// 重寫了 configure 後，相關接口要重新配置
		http.authorizeRequests().anyRequest().authenticated();
	}
}
