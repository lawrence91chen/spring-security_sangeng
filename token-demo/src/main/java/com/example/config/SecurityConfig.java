package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity 要求 SecurityConfig 這個配置類要繼承 WebSecurityConfigurerAdapter
 * 可以重寫裡面的一些方法來實現相關的功能
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 創建 BCryptPasswordEncoder 注入容器
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
