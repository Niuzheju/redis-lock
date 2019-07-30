package com.niuzj.redislock;

import com.niuzj.redislock.util.RedisTool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockApplicationTests {


    Jedis jedis;


    @Before
    public void before() {
        JedisPool jedisPool = SpringContextUtil.getBean(JedisPool.class);
        jedis = jedisPool.getResource();

    }


    @Test
    public void contextLoads() throws InterruptedException {
        JedisPool jedisPool = SpringContextUtil.getBean(JedisPool.class);
        new Thread(() -> {

            Jedis jedis = jedisPool.getResource();
            boolean b1 = RedisTool.lock(jedis, "attest", "1", 1000 * 60);
            System.out.println("a" + b1);
        }).start();

        new Thread(() -> {
            Jedis jedis = jedisPool.getResource();
            boolean b1 = RedisTool.lock(jedis, "attest", "1", 1000 * 60);
            System.out.println("b" + b1);
        }).start();

        new CountDownLatch(1).await();

    }

    @Test
    public void test01() {
        //先返回旧值,再返回新值
        String value = jedis.getSet("name", "xxx");
        System.out.println(value);
    }

    @Test
    public void test02(){
        Long result = jedis.setnx("movie", "idea");
        System.out.println(result);
    }

}

