package com.example.service.impl;

import com.example.domain.LoginUser;
import com.example.domain.ResponseResult;
import com.example.domain.User;
import com.example.helper.RedisCache;
import com.example.service.LoginService;
import com.example.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private RedisCache redisCache;

	@Override
	public ResponseResult login(User user) {
		// AuthenticationManager#authenticate 進行用戶認證

		// 參數是 Authentication 接口，需要創建實現類
		// Idea 按 ctrl + alt 找到我們要的實現類 UsernamePasswordAuthenticationToken
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				user.getUserName(), user.getPassword()
		);
		// ProviderManager 會調用 UserDetailServiceImpl#loadUserByUsername 去進行用戶校驗
		Authentication authenticate = authenticationManager.authenticate(authenticationToken);

		// 如果認證沒通過，給出對應的提示
		if (Objects.isNull(authenticate)) {
			throw new RuntimeException("登入失敗");
		}

		// 如果認證通過了，使用 userid 生成一個 JWT。 JWT 存入 ResponseResult 返回
		// 可使用斷點調試(debug)來查看 userid 在 Authentication 的哪一個屬性當中，再使用 getter 方法取得即可
		LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
		Long userId = loginUser.getUser().getId();
		String jwt = JwtUtils.createJWT(userId.toString());
		// 將 ResponseResult#data 做成 key-value 的形式
		HashMap<String, String> map = new HashMap<>();
		map.put("token", jwt);

		// 把完整的用戶信息存入 redis (userid 作為 key)
		// 加上 login: 當作 key 的前綴
		redisCache.setCacheObject("login:" + userId, loginUser);

		return new ResponseResult(200, "登入成功", map);
	}

	@Override
	public ResponseResult logout() {
		// 獲取 SecurityContextHolder 中的用戶 id
		// (實際上 SecurityContextHolder 中的資料不用刪，因為 redis 已刪。
		// JwtAuthenticationTokenFilter 發現 redis 中找不到 userid 就會未登入拋異常)
		UsernamePasswordAuthenticationToken authentication =
				(UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		// 理論上不會出現 loginUser 無值的情況，因為在前面的 filter 就會被擋下來。所以可以直接取用 loginUser
		Long userId = loginUser.getUser().getId();

		// 刪除 redis 中的值
		redisCache.deleteObject("login:" + userId);

		return new ResponseResult(200, "註銷成功");
	}
}
