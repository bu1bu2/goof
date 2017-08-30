package com.goof.redis.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
	
	private final RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	public RedisService(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void addValue(String key, String value) {
		this.redisTemplate.opsForValue().set(key, value, 360, TimeUnit.SECONDS);
	}

	public String getValue(String key) {
		return this.redisTemplate.opsForValue().get(key);
	}
}
