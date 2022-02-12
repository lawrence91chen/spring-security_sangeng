package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT 工具類。
 */
public class JwtUtils {
	/** 有效期: 60 * 60 * 1000 = 1 小時 */
	public static final Long JWT_TTL = 60 * 60 * 1000L;
	/** 設置密鑰明文 */
	public static final String JWT_KEY = "sangeng";

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成 JWT
	 * @param subject token 中要存放的數據 (JSON 格式)
	 * @return
	 */
	public static String createJWT(String subject) {
		// 設置過期時間
		JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
		return builder.compact();
	}

	/**
	 * 生成 JWT
	 * @param subject token 中要存放的數據 (JSON 格式)
	 * @param ttlMillis token 超過時間
	 * @return
	 */
	public static String createJWT(String subject, Long ttlMillis) {
		// 設置過期時間
		JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
		return builder.compact();
	}

	public static String createJWT(String id, String subject, Long ttlMillis) {
		// 設置過期時間
		JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
		return builder.compact();
	}

	private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		SecretKey secretKey = generateSecretKey();
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		if (ttlMillis == null) {
			ttlMillis = JWT_TTL;
		}

		long expMillis = nowMillis + ttlMillis;
		Date expDate = new Date(expMillis);

		return Jwts.builder()
				.setId(uuid)
				.setSubject(subject)
				.setIssuer("sg")
				.setIssuedAt(now)
				.signWith(signatureAlgorithm, secretKey)
				.setExpiration(expDate);
	}

	/**
	 * 生成加密後的密鑰 SecretKey
	 */
	private static SecretKey generateSecretKey() {
		byte[] encodeKey = Base64.getDecoder().decode(JWT_KEY);
		SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
		return key;
	}

	/**
	 * 解析 JWT
	 * @param jwt
	 */
	public static Claims parseJWT(String jwt) {
		SecretKey secretKey = generateSecretKey();
		return Jwts.parser().setSigningKey(secretKey)
				.parseClaimsJws(jwt)
				.getBody();
	}
}
