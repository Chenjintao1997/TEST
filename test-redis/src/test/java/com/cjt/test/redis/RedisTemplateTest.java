package com.cjt.test.redis;

import com.alibaba.fastjson.JSON;
import com.cjt.test.redis.bean.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chenjintao
 * @Date: 2021/3/20 17:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class RedisTemplateTest {
    @Autowired
    @Qualifier("defaultRedisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate1;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test1() {
        stringRedisTemplate.opsForValue().set("srt-test1", "111");
        System.out.println(stringRedisTemplate.opsForValue().get("srt-test1"));
        System.out.println(redisTemplate.opsForValue().get("srt-test1"));
    }


    @Test
    public void test2() {
        redisTemplate.opsForValue().set("rt-test1", "111");
        System.out.println(stringRedisTemplate.opsForValue().get("srt-test1"));
        System.out.println(redisTemplate.opsForValue().get("srt-test1"));
        System.out.println(stringRedisTemplate.opsForValue().get("rt-test1"));
        System.out.println(redisTemplate.opsForValue().get("rt-test1"));

        // redisTemplate和stringRedisTemplate的key和value的序列化方式不同
    }

    @Test
    public void testKey() {
        redisTemplate.opsForValue().set("rt-test2", 222);
        Set<Object> rt_keys = redisTemplate.keys("*");
        log.info("rt_keys:{}", JSON.toJSONString(rt_keys));
        Set<String> srt_keys = stringRedisTemplate.keys("*");
        System.out.println(srt_keys);

        //todo redisTemplate.randomKey会报反序列化错误
        log.info("rt_randomKey:{}", stringRedisTemplate.randomKey().toString());

        long rt_ttl1 = redisTemplate.getExpire("rt-test2", TimeUnit.MILLISECONDS);
        long rt_ttl2 = redisTemplate.getExpire("ffmdlkasfjlkad");
        log.info("rt_ttl1:{}", rt_ttl1);
        log.info("rt_ttl2:{}", rt_ttl2);

        boolean rt_exists1 = redisTemplate.hasKey("rt-test2");
        boolean rt_exists2 = redisTemplate.hasKey("srt-test1");
        log.info("rt_exists1:{},rt_exists2:{}", rt_exists1, rt_exists2);
    }

    @Test
    public void testSet() {
        Set<String> set1 = new HashSet<>();
        set1.add("set1");
        set1.add("set2");
        stringRedisTemplate.opsForSet().add("srt-set1", set1.toArray(new String[0]));
        Set<String> srt_r1 = stringRedisTemplate.opsForSet().members("srt-set1");
        log.info("srt_r1:{}", JSON.toJSONString(srt_r1));

        stringRedisTemplate.opsForSet().remove("srt-set1", set1.toArray(new String[0]));
        Set<String> srt_r2 = stringRedisTemplate.opsForSet().members("srt-set1");
        log.info("rm_later:srt_r2:{}", JSON.toJSONString(srt_r2));

        redisTemplate.opsForSet().add("rt-set1", set1.toArray(new String[0]));
        Set<Object> rt_r1 = redisTemplate.opsForSet().members("rt-set1");
        log.info("rt_r1:{}", JSON.toJSONString(rt_r1));

        redisTemplate.opsForSet().remove("rt-set1", set1.toArray(new String[0]));
        Set<Object> rt_r2 = redisTemplate.opsForSet().members("rt-set1");
        log.info("rm_later:rt_r2:{}", JSON.toJSONString(rt_r2));
    }

    @Test
    public void testHash() {
        Map<String, String> map = new HashMap<>();
        map.put("age", "20");
        map.put("name", "张三");
        redisTemplate.opsForHash().putAll("rt_hash1", map);
        redisTemplate.opsForHash().put("rt_hash1", "sex", 1);

        Student student = new Student();
        student.setAge("30");
        student.setName("李四");
        stringRedisTemplate.opsForHash().putAll("srt_hash1", JSON.parseObject(JSON.toJSONString(student), Map.class));
        stringRedisTemplate.opsForHash().put("srt_hash1", "sex", "1"); //值必须用字符串

        log.info("rt_hash1:name:{},age:{},sex:{}",
                redisTemplate.opsForHash().get("rt_hash1", "name"),
                redisTemplate.opsForHash().get("rt_hash1", "age"),
                redisTemplate.opsForHash().get("rt_hash1", "sex"));

        log.info("srt_hash1:name:{},age:{},sex:{}",
                stringRedisTemplate.opsForHash().get("srt_hash1", "name"),
                stringRedisTemplate.opsForHash().get("srt_hash1", "age"),
                stringRedisTemplate.opsForHash().get("srt_hash1", "sex"));
    }

    @Test
    public void testZSet() {
        stringRedisTemplate.opsForZSet().add("srt_sort-set", "0", 0);
        stringRedisTemplate.opsForZSet().add("srt_sort-set", "1", 1);
        stringRedisTemplate.opsForZSet().add("srt_sort-set", "1a", 1);

        Set<String> srt_r1 = stringRedisTemplate.opsForZSet().range("srt_sort-set", 0, -1);
        Set<ZSetOperations.TypedTuple<String>> srt_r2 = stringRedisTemplate.opsForZSet().rangeWithScores("srt_sort-set", 0, -1);
        Set<String> srt_r3 = stringRedisTemplate.opsForZSet().rangeByScore("srt_sort-set", 0, 2);

        log.info("srt_r1-range-byIndex:{}\n" +
                        "srt_r1-range-byIndex-withScore:{}\n" +
                        "srt_r1-range-byScore:{}",
                srt_r1,
                JSON.toJSONString(srt_r2),
                srt_r3);
    }

    @Test
    public void testObject(){
        Student student = new Student();
        student.setAge("30");
        student.setName("李四");

        redisTemplate.opsForValue().set("rt_testObj-va",student);
        Student r1 = (Student) redisTemplate.opsForValue().get("rt_testObj-va");
        System.out.println(JSON.toJSONString(r1));
    }

    @Test
    public void testSerializableKeyValue(){
        Student student = new Student();
        student.setAge("23");
        student.setName("王五");

        redisTemplate1.opsForValue().set("rt_testObj-sr",student);
        Student r1 = (Student) redisTemplate1.opsForValue().get("rt_testObj-sr");
        System.out.println(JSON.toJSONString(r1));
    }

    @Test
    public void testSerializableKeyValue_Hash(){
        Map<String, String> map = new HashMap<>();
        map.put("age", "20");
        map.put("name", "张三");
        redisTemplate1.opsForHash().putAll("rt-sr_hash1", map);
        redisTemplate1.opsForHash().put("rt-sr_hash1", "sex", 1);

        log.info("rt-sr_hash1:name:{},age:{},sex:{}",
                redisTemplate.opsForHash().get("rt-sr_hash1", "name"),
                redisTemplate.opsForHash().get("rt-sr_hash1", "age"),
                redisTemplate.opsForHash().get("rt-sr_hash1", "sex"));
    }


}
