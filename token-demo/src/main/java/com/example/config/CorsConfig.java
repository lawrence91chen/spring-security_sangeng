package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 設置允許跨域的路徑
		registry.addMapping("/**")
				// 設置允許跨域請求的域名
				.allowedOriginPatterns("*")
				// 是否允許 cookie
				.allowCredentials(true)
				// 設置允許的請求方式
				.allowedMethods("GET", "POST", "DELETE", "PUT")
				// 設置允許的 header 屬性
				.allowedHeaders("*")
				// 跨域允許時間
				.maxAge(3600);
	}
}
