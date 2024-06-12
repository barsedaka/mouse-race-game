package com.bar.games.mouseracegame.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisClient {
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private Jedis jedis;

    public RedisClient() {
        try {
            jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        }
    }

    public void set(String key, String value) {
        jedis.set(key, value);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    /**
     * Closes the Redis connection.
     */
    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
