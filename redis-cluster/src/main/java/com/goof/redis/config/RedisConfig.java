package com.goof.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
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
	private ClusterProperty clusterProperty;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
    	
    	System.out.println(">>>>>>>>>>>>>>>>:"+clusterProperty.getNodes().size());
    	clusterProperty.getNodes().forEach(System.out::printf);
    	
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(
                new RedisClusterConfiguration(clusterProperty.getNodes()));
        redisConnectionFactory.setUsePool(Boolean.TRUE);
        redisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return redisConnectionFactory;
    }

	@Bean
	public RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String,String>();
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
