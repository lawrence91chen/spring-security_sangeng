package com.example;

import com.example.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {

	@Test
	public void testCreate() {
		String jwt = JwtUtils.createJWT("2123");
		// eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxZjEwNjk4MDMyNzA0M2E2YTJlOWEwNDQ1ZTYxZmNiZiIsInN1YiI6IjIxMjMiLCJpc3MiOiJzZyIsImlhdCI6MTY0NDczNTk1OCwiZXhwIjoxNjQ0NzM5NTU4fQ.MMc-vM-qpYAfQCHen6jyk3EFwHnZNHRsJyxDP0dWHnI
		System.out.println(jwt);
	}

	@Test
	public void testParse() {
//		String jwt = JwtUtils.createJWT("2123");
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxZjEwNjk4MDMyNzA0M2E2YTJlOWEwNDQ1ZTYxZmNiZiIsInN1YiI6IjIxMjMiLCJpc3MiOiJzZyIsImlhdCI6MTY0NDczNTk1OCwiZXhwIjoxNjQ0NzM5NTU4fQ.MMc-vM-qpYAfQCHen6jyk3EFwHnZNHRsJyxDP0dWHnI";
		Claims claims = JwtUtils.parseJWT(jwt);
		String subject = claims.getSubject();
		System.out.println(subject);
	}
}
