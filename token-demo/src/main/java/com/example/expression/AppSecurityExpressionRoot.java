package com.example.expression;

import com.example.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ex") // 指定 Bean 的名稱
public class AppSecurityExpressionRoot {

	public boolean hasAuthority(String authority) {
		System.out.println("自定義校驗方法");

		// 獲取當前用戶的權限  (如果多處重複使用可以考慮封裝成方法，這邊以方便理解為主)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		List<String> permissions = loginUser.getPermissions();
		// 判斷用戶權限集合中是否存在 authority
		return permissions.contains(authority);
	}
}
