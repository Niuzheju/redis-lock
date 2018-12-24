package com.niuzj.redislock.util;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * redis锁工具类
 */
public class RedisTool {

    private static final String LOCK_SUCCESS = "OK";

    private static final Long RELEASE_SUCCESS = 1L;

    //NX参数,如果key已存在则什么操作都不做
    private static final String NX = "NX";

    //是否设置过期时间
    private static final String EXPIRE = "PX";


    public static boolean lock(Jedis jedis, String key, String value, int expire) {
        String set = jedis.set(key, value, NX, EXPIRE, expire);
        return LOCK_SUCCESS.equals(set);
    }

    public static boolean unlock(Jedis jedis, String key, String value) {
        //lua语言,传入redis,这样操作具有原子性,避免多步骤操作中间出现异常
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
        return RELEASE_SUCCESS.equals(result);
    }


}
