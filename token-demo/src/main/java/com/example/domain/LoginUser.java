package com.example.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

	private User user;

	/**
	 * 存儲權限信息
	 */
	private List<String> permissions;

	public LoginUser(User user, List<String> permissions) {
		this.user = user;
		this.permissions = permissions;
	}

	/**
	 * 存儲 SpringSecurity 所需要權限信息的集合
	 */
	@JSONField(serialize = false) // 不需要序列化到 redis (且基於安全問題 SimpleGrantedAuthority 序列化到 redis 也會報錯)
	List<GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 把 permissions 中 String 類型的權限信息封裝成 SimpleGrantedAuthority 對象 (GrantedAuthority 的實現類)

		if (authorities != null) {
			// 小優化，不希望每次調用都重新遍歷 permissions
			return authorities;
		}

		// 傳統寫法
//		authorities = new ArrayList<>();
//		for (String permission : permissions) {
//			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
//			authorities.add(authority);
//		}

		// 函數式編程寫法(目前較主流)
		authorities = permissions.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	// TODO: 下面都先暫設成 true 避免相關認證失敗無法登入

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
