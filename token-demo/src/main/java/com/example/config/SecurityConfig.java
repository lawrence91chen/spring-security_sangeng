package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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

	/**
	 * 前後端分離架構下 放行登入接口 的 配置
	 *
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// 關閉 CSRF
				.csrf().disable()
				// 不通過 Session 獲取 SecurityContext
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				// 對於登入接口，允許匿名訪問
				.antMatchers("/user/login").anonymous()
				// 除上面外的所有請求，全部都需要鑒權(authentication)認證
				.anyRequest().authenticated();
	}

	// IDE generate override methods, then choose `authenticationManagerBean`
	// Idea alt + insert
	// 透過繼承此方法並加上 @Bean 可暴露(expose) 到容器當中，就可以獲取到 AuthenticationManager 了 (其他 Class 可以 @Autowired)
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
