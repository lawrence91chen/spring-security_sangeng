package com.example.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.example.helper.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	/**
	 * 重寫Redis序列化方式，使用Json方式:
	 * 當我們的數據存儲到Redis的時候，我們的鍵（key）和值（value）都是通過Spring提供的Serializer序列化到數據庫的。RedisTemplate默認使用的是JdkSerializationRedisSerializer，StringRedisTemplate默認使用的是StringRedisSerializer。
	 * Spring Data JPA為我們提供了下面的Serializer：
	 * GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
	 * 在此我們將自己配置RedisTemplate並定義Serializer。
	 *
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
		// 全局開啟AutoType，不建議使用
		// ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
		// 建議使用這種方式，小範圍指定白名單
		ParserConfig.getGlobalInstance().addAccept("com.example.");

		// 設置值（value）的序列化採用FastJsonRedisSerializer。
		redisTemplate.setValueSerializer(fastJsonRedisSerializer);
		redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
		// 設置鍵（key）的序列化採用StringRedisSerializer。
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());

		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}