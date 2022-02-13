package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.User;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/user/login")
	public ResponseResult login(@RequestBody User user) {
		// 登入
		return loginService.login(user);
	}

	@GetMapping("/user/logout")
	public ResponseResult logout() {
		return loginService.logout();
	}
}
