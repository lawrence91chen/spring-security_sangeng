package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testEncode() {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encode = passwordEncoder.encode("1234");
		String encode2 = passwordEncoder.encode("1234");

		// 每一次加密後的密文會不同，受 鹽(salt) 影響
		System.out.println(encode);
		System.out.println(encode2);

		// $2a$10$JnYC26e4q9ItiONbU78JyOg27uWA3i/S3pty3uJbgAqTHFbTE41j2
		// $2a$10$MDzmHp.HaTIi9EedWt6BmOLaxSPeXxqMpuMPH7fpTRvzdWeemgQr2
	}

	@Test
	public void testMatches() {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean matches = passwordEncoder.matches(
				"1234",
				"$2a$10$JnYC26e4q9ItiONbU78JyOg27uWA3i/S3pty3uJbgAqTHFbTE41j2");
		System.out.println(matches);

		boolean matches2 = passwordEncoder.matches(
				"1234",
				"$2a$10$MDzmHp.HaTIi9EedWt6BmOLaxSPeXxqMpuMPH7fpTRvzdWeemgQr2");
		System.out.println(matches2);

		boolean matches3 = passwordEncoder.matches(
				"12345",
				"$2a$10$MDzmHp.HaTIi9EedWt6BmOLaxSPeXxqMpuMPH7fpTRvzdWeemgQr2");
		System.out.println(matches3);
	}
}
