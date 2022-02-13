package com.example.config;

import com.example.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity 要求 SecurityConfig 這個配置類要繼承 WebSecurityConfigurerAdapter
 * 可以重寫裡面的一些方法來實現相關的功能
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

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
				// 配置請求認證規則
				.authorizeRequests()
				// 對於登入接口，允許匿名訪問
				/**
				 * anonymous: 匿名訪問 -> 未登入可訪問； 登入不可訪問
				 * permitAll: 登入/未登入 接可訪問
				 */
				.antMatchers("/user/login").anonymous()
				// 除上面外的所有請求，全部都需要鑒權(authentication)認證
				.anyRequest().authenticated();

		// 配置 JwtAuthenticationTokenFilter 到 UsernamePasswordAuthenticationFilter 之前
		http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
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
