package com.example.filter;

import com.example.domain.LoginUser;
import com.example.helper.RedisCache;
import com.example.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

// Servlet 原生的 Filter 有可能導致一個請求將過濾器調用多次，
// 使用 Spring 提供的實現類 OncePerRequestFilter 可避免此問題 (保證一個請求只會經過這個過濾器一次)
@Component // 注入到 Spring 容器 (TODO: 可不可以改用 Interceptor ? 看起來不行，因為還要配置到 filter chain)
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private RedisCache redisCache;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 獲取 token
		String token = request.getHeader("token");
		if (!StringUtils.hasText(token)) {
			// 放行 (呼叫 doFilter 方法 -> 放行請求)
			// 連 token 都沒有的話談何解析? 所以放行，讓後面的過濾器去判斷用戶的認證狀態。認證不符自然也就會拋出異常
			filterChain.doFilter(request, response);
			// 如果不 return，filter chain 響應回來的時候就會執行到下方不須執行的 code
			return;
		}

		// 解析 token
		String userId;
		try {
			Claims claims = JwtUtils.parseJWT(token);
			userId = claims.getSubject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("token 非法");
		}

		// 從 redis 中獲取(完整)用戶信息
		String redisKey = "login:" + userId;
		LoginUser loginUser = redisCache.getCacheObject(redisKey); // 方法泛型會自動推測為 LoginUser 型別
		// 有可能 redis 中不存在用戶信息
		if (Objects.isNull(loginUser)) {
			// 因為登入接口已經把用戶信息存到 redis 中了，所以只有當用戶退出登入時才會讓 Cache 失效(找不到)
			// 因此這裡取不到應該拋出用戶未登入異常
			throw new RuntimeException("用戶未登入");
		}

		// 存入 SecurityContextHolder (因為後面的 Filter 都是從 SecurityContextHolder 取得用戶的認證狀態)
		SecurityContext context = SecurityContextHolder.getContext();
		// UsernamePasswordAuthenticationToken 構造函數有兩種， 參數x2 和 參數x3
		// 參數x3: 才會執行 super.setAuthenticated(true);
		// 因為現在已經可從 redis 中獲取到對應的對象，說明該用戶已認證過。
		// 所以將 authenticated 設置為 true 才能讓後面的過濾器知道已認證過
		// 註: 第三個參數 authorities 是權限信息 (目前還沒有) TODO: 獲取權限信息封裝到 Authentication 中
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
		context.setAuthentication(authenticationToken);

		// 放行
		filterChain.doFilter(request, response);
	}
}
