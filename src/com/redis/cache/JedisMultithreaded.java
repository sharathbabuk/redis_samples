package com.redis.cache;

import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JedisMultithreaded {

    private static final String YOUR_CONNECTION_STRING = "redis://localhost:6379";

    public static void main(String[] args) {
        JedisPool pool = new JedisPool(YOUR_CONNECTION_STRING);

        List<String> allResults = IntStream.rangeClosed(1, 5)
                .parallel()
                .mapToObj(n -> {
                    Jedis jedis = pool.getResource();

                    jedis.set("foo" + n, "bar" + n);
                    String result = jedis.get("foo" + n);

                    jedis.close();

                    return result;
                })
                .collect(Collectors.toList());

        pool.close();

        System.out.println(allResults); // "bar1, bar2, bar3, bar4, bar5"
    }
}
