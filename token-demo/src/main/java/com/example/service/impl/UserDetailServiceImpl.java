package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 查詢用戶信息
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getUserName, username);
		User user = userMapper.selectOne(queryWrapper);

		// 如果沒有查詢到用戶，就拋出異常
		if (Objects.isNull(user)) {
			// ExceptionTranslationFilter 會捕獲到例外，即便沒有我們也可以自定義全局異常處理
			throw new RuntimeException("用戶名或密碼錯誤");
		}

		// TODO: 查詢對應的權限信息 (屬於授權部分，後段課程說明)
		List<String> permissions = new ArrayList<>(Arrays.asList("test", "admin"));
		// 把數據封裝成 UserDetails 返回 (UserDetails 是接口，需要對應的實現類)
		return new LoginUser(user, permissions);
	}
}
