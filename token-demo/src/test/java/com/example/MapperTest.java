package com.example;

import com.example.domain.User;
import com.example.mapper.MenuMapper;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTest {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Test
	public void testUserMapper() {
		List<User> users = userMapper.selectList(null);
		System.out.println(users);
	}

	@Test
	public void testSelectPermsByUserId() {
		List<String> perms = menuMapper.selectPermsByUserId(1L);
		System.out.println(perms);
	}
}
