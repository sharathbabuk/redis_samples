package com.redis.cache;

import java.time.Duration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPoolJavaExample {
	
	private static JedisPoolConfig buildPoolConfig() {
	    final JedisPoolConfig poolConfig = new JedisPoolConfig();
	    poolConfig.setMaxTotal(128);
	    poolConfig.setMaxIdle(128);
	    poolConfig.setMinIdle(16);
	    poolConfig.setTestOnBorrow(true);
	    poolConfig.setTestOnReturn(true);
	    poolConfig.setTestWhileIdle(true);
	    poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
	    poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
	    poolConfig.setNumTestsPerEvictionRun(3);
	    poolConfig.setBlockWhenExhausted(true);
	    return poolConfig;
	}		
	
	public static void main(String[] args) { 

		final JedisPoolConfig poolConfig = buildPoolConfig();
		JedisPool jedisPool = new JedisPool(poolConfig, "redis://localhost:6379");
		Jedis jedis = null;

		for (int i = 0; i< 150; i++) {
		try {
			jedis = jedisPool.getResource();
		    // do operations with jedis resource
			//System.out.println("** REDIS: resource: "+ jedis.toString());
			System.out.println("** REDIS: Sending Ping to REDIS (" + i + "): "+jedis.ping());
		} catch (Exception e) {
			System.out.println ("Exception: " + e.getMessage());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		}
	}
}
