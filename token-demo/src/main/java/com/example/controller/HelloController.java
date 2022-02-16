package com.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	// SpringSecurity 運行時將屬性值視為表達式去調用 SecurityExpressionRoot#hasAuthority (返回值為 boolean)
	// 判斷用戶是否具有 test 權限，有就返回 true
	// 實務上可以自行定義實現類去實作權限校驗相關方法，這樣會更加靈活
	@PreAuthorize("hasAuthority('system:dept:list')")
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
}