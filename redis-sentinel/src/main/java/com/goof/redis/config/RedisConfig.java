package com.goof.redis.config;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author goofly
 *
 */

@Configuration
public class RedisConfig {

	@Autowired
	private Sentinelproperty sentinelproperty;

	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		
		System.out.println("...........>:"+sentinelproperty.getNodes());
		
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(new RedisSentinelConfiguration(
				"mymaster", new HashSet<>(sentinelproperty.getNodes())));
		redisConnectionFactory.setUsePool(Boolean.TRUE);
		redisConnectionFactory.setPoolConfig(jedisPoolConfig());
		return redisConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(8);
		jedisPoolConfig.setMinIdle(2);
		jedisPoolConfig.setMaxWaitMillis(2000);
		return jedisPoolConfig;
	}

	@Bean
	public StringRedisSerializer stringRedisSerializer() {
		return new StringRedisSerializer();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public Jackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
		return new Jackson2JsonRedisSerializer(String.class);
	}
}
