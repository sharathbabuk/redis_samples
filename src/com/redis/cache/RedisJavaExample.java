package com.redis.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisJavaExample { 
   public static void main(String[] args) { 
      //Connect to Redis server using localhost
      //Jedis jedis = new Jedis("redis://localhost:6379");
      Jedis jedis = new Jedis();
      System.out.println("Connection successful"); 
	  
      //Checking server
      System.out.println("** REDIS: Sending Ping to REDIS: "+jedis.ping());
      
      // Strings
      System.out.println("** REDIS: Setting string key/value pair test");
      jedis.set("events/city/rome", "32,15,223,828");
      String cachedResponse = jedis.get("events/city/rome");
      
      System.out.println("Getting cached response from the server: "+cachedResponse);
      
      //Lists
      System.out.println("** REDIS: Setting 'List' test");
      jedis.lpush("queue#newtasks", "firstTask");
      jedis.lpush("queue#newtasks", "secondTask");

      String task1 = jedis.rpop("queue#newtasks");      
      System.out.println("Getting cached task1 from the server: "+task1);
      
      String task2 = jedis.rpop("queue#newtasks");      
      System.out.println("Getting cached task2 from the server: "+task2);
      
      // Sets
      System.out.println("** REDIS: Setting 'Set' test");
      jedis.sadd("nicknames", "nickname#1");
      jedis.sadd("nicknames", "nickname#2");
      jedis.sadd("nicknames", "nickname#1");
      jedis.sadd("nicknames", "nickname#2");
      jedis.sadd("nicknames", "nickname#3");

      Set<String> nicknames = jedis.smembers("nicknames");
      for(String nickname : nicknames){
    	  if (jedis.sismember("nicknames", nickname)) {
    		  System.out.println(nickname + " is member of nicknames");
    	  }
      }
      
      // Redis Hash functions
      System.out.println("** REDIS: Setting 'Hash' test");
      jedis.hset("user#1", "name", "Peter");
      		
      String name = jedis.hget("user#1", "name");
      System.out.println("Name of user#1: " + name);
      		
      jedis.hset("user#1", "job", "politician");
      Map<String, String> fields = jedis.hgetAll("user#1");
      String job = fields.get("job");
      System.out.println("Name & Job of user#1: " + name + " " + job);
      
      // Redis Sorted Set
      System.out.println("** REDIS: Setting 'Sorted Set' test");
      Map<String, Double> scores = new HashMap<>();
      String key = "ranking";

      scores.put("PlayerOne", 3000.0);
      scores.put("PlayerTwo", 1500.0);
      scores.put("PlayerThree", 8200.0);
      scores.put("PlayerThree", 9200.0);
      scores.put("PlayerTwo", 10200.0);
      scores.put("PlayerThree", 11200.0);

      scores.entrySet().forEach(playerScore -> {
          jedis.zadd(key, playerScore.getValue(), playerScore.getKey());
      });
      
      String player;
      List<String> list=new ArrayList<String>();
      list = jedis.zrevrange(key, 0, -1);
      
      System.out.println("Player\tRanking");
      Iterator<String> iterator = list.iterator();
      while(iterator.hasNext()) {
    	  player = iterator.next();
         System.out.println(player + "\t" + jedis.zrevrank(key, player));
      }
    
      // close connection
      jedis.close();
   }
}
