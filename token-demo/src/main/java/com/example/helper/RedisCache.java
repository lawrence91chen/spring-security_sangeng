package com.example.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 緩存基本的對象。 (Integer、String、實體類等)
	 *
	 * @param key 緩存的鍵值
	 * @param value 緩存的值
	 */
	public <T> void setCacheObject(final String key, final T value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 緩存基本的對象。 (Integer、String、實體類等)
	 *
	 * @param key 緩存的鍵值
	 * @param value 緩存的值
	 * @param timeout 時間
	 * @param timeUnit 時間顆粒度
	 */
	public <T> void setCacheObject(final String key, final T value,
								   final Integer timeout, final TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
	}

	/**
	 * 設置有效時間
	 * @param key Redis鍵
	 * @param timeout 超時時間
	 * @param timeUnit 時間單位
	 * @return ture=設置成功；false=設置失敗
	 */
	public boolean expire(final String key, final long timeout, final TimeUnit timeUnit) {
		return redisTemplate.expire(key, timeout, timeUnit);
	}

	/**
	 * 獲得緩存的基本對象
	 *
	 * @param key 緩存鍵值
	 * @return 緩存鍵值對應的數據
	 */
	public <T> T getCacheObject(final String key) {
		ValueOperations<String, T> operations = redisTemplate.opsForValue();
		return operations.get(key);
	}

	/**
	 * 刪除單個對象
	 *
	 * @param key
	 */
	public boolean deleteObject(final String key) {
		return redisTemplate.delete(key);
	}

	/**
	 * 刪除集合對象
	 *
	 * @param collection 多個對象
	 * @return
	 */
	public long deleteObject(final Collection collection) {
		return redisTemplate.delete(collection);
	}

	/**
	 * 緩存 List 數據
	 *
	 * @param key 緩存的鍵值
	 * @param dataList 待緩存的 List 數據
	 * @return 緩存的對象
	 */
	public <T> long setCacheList(final String key, final List<T> dataList) {
		Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
		return count == null ? 0 : count;
	}

	/**
	 * 獲得緩存 List 對象
	 *
	 * @param key 緩存的鍵值
	 * @return 緩存鍵值對應的數據
	 */
	public <T> List<T> getCacheList(final String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * 緩存 List 數據
	 *
	 * @param key 緩存的鍵值
	 * @param dataSet 待緩存的數據
	 * @return 緩存數據的對象
	 */
	public <T> BoundSetOperations<String, T> setCacheList(final String key, final Set<T> dataSet) {
		BoundSetOperations operations = redisTemplate.boundSetOps(key);

		Iterator<T> it = dataSet.iterator();
		while (it.hasNext()) {
			operations.add(it.next());
		}

		return operations;
	}

	/**
	 * 獲得緩存 Set
	 *
	 * @param key 緩存的鍵值
	 * @return 緩存鍵值對應的數據
	 */
	public <T> Set<T> getCacheSet(final String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * 緩存 Map
	 *
	 * @param key 緩存的鍵值
	 * @param dataMap 待緩存的 Map 數據
	 * @return 緩存的對象
	 */
	public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
		if (dataMap != null) {
			redisTemplate.opsForHash().putAll(key, dataMap);
		}
	}

	/**
	 * 獲得緩存 Map
	 *
	 * @param key 緩存的鍵值
	 * @return 緩存鍵值對應的數據
	 */
	public <T> Map<String, T> getCacheMap(final String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 往 Hash 中存入數據
	 *
	 * @param key Redis鍵
	 * @param hKey Hash鍵
	 * @return 緩存的對象
	 */
	public <T> void setCacheMap(final String key, final String hKey, final T value) {
		redisTemplate.opsForHash().put(key, hKey, value);
	}

	/**
	 * 獲取 Hash 中的數據
	 *
	 * @param key Redis鍵
	 * @param hKey Hash鍵
	 * @return Hash 中的對象
	 */
	public <T> T getCacheMap(final String key, final String hKey) {
		HashOperations<String, String, T> operations = redisTemplate.opsForHash();
		return operations.get(key, hKey);
	}

	/**
	 * 刪除 Hash 中的數據
	 *
	 * @param key Redis鍵
	 * @param hKey Hash鍵
	 */
	public void deleteCacheMapValue(final String key, final String hKey) {
		HashOperations<String, String, ?> operations = redisTemplate.opsForHash();
		operations.delete(key, hKey);
	}

	/**
	 * 獲得多個 Hash 中的數據
	 *
	 * @param key Redis鍵
	 * @param hKeys Hash鍵集合
	 * @return Hash對象集合
	 */
	public <T> List<T> getCacheMap(final String key, final Collection<Objects> hKeys) {
		return redisTemplate.opsForHash().multiGet(key, hKeys);
	}

	/**
	 * 獲取緩存的基本對象列表
	 *
	 * @param pattern 字串前綴
	 * @return 對象列表
	 */
	public Collection<String> keys(final String pattern) {
		return redisTemplate.keys(pattern);
	}
}
