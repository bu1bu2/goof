package com.goof.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void test() {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set("cluster", "cluster123");
		System.out.println(opsForValue.get("cluster"));
	}
}

