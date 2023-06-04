package com.hk.dispatch.apigateway.config;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.hk.dispatch.apigateway.util.MapperUtils;

@Component
public class RedisHashBean {
	
	private final RedisTemplate<String,Object> redisTemplate;

	public RedisHashBean(RedisTemplate<String, Object> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}
	
	/**
	 * Get values to cache
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void setKeyValues(String key,Object hashKey,Object value)
	{
        Map ruleHash= MapperUtils.objectMapper(value,Map.class);
        redisTemplate.opsForHash().put(key, hashKey, ruleHash);
    }
	/**
	 * Get Service API key against the service id
	 * @param key
	 * @return
	 */
	public List<Object> hValues(String key){
        return  redisTemplate.opsForHash().values(key);
    }
	/**
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hGet(String key,Object hashKey){
	       return redisTemplate.opsForHash().get(key, hashKey);
	}


}
