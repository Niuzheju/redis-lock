package com.niuzj.redislock;

import com.niuzj.redislock.util.RedisTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockApplicationTests {


    @Test
    public void contextLoads() {
        Jedis jedis = SpringContextUtil.getBean(JedisPool.class).getResource();
        RedisTool.lock(jedis, "attest", "1", 50000);
        RedisTool.unlock(jedis, "attest", "2");

    }

    @Test
    public void test01() {

    }

}

