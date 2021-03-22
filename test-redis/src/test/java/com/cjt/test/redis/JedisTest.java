package com.cjt.test.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjt.test.redis.bean.Student;
import com.cjt.test.redis.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;

import java.io.*;
import java.util.*;

/**
 * @Author: chenjintao
 * @Date: 2021/3/17 23:08
 */
@RunWith(JUnit4.class)
@Slf4j
public class JedisTest {
    private static final JedisPool jedisPool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        jedisPool = new JedisPool(config, "cjtbc.com", 6379, 100, "aAa132132");
    }

    @Test
    public void test1() {
        log.info("空闲连接数：{}",jedisPool.getNumIdle());
        Jedis jedis = jedisPool.getResource();
        log.info("活跃连接数：{}",jedisPool.getNumActive());
        Set<String> set1 = jedis.keys("*");
        System.out.println(JSON.toJSONString(set1));
        log.info("空闲连接数：{}",jedisPool.getNumIdle());
    }

    @Test
    public void test2() throws InterruptedException {
        log.info("空闲连接数：{}",jedisPool.getNumIdle());
        try (  Jedis jedis = jedisPool.getResource();){

            log.info("活跃连接数：{}",jedisPool.getNumActive());
            Set<String> set1 = jedis.keys("*");
            System.out.println(JSON.toJSONString(set1));

        }
        log.info("空闲连接数：{}",jedisPool.getNumIdle());
        Thread.sleep(3000);
    }

    @Test
    public void test3(){
        Jedis jedis = JedisUtil.getJedis();
        String r1 = jedis.set("jedis_test1","jt111");
        System.out.println(r1);
    }

    @Test
    public void testList(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.lpush("test_list1","l1");
        jedis.lpush("test_list1","l2","l3");
        List<String> list1 = new ArrayList<>();
        list1.add("list1");
        list1.add("list2");

        //插入头部
        jedis.lpush("test_list1",list1.toArray(new String[0]));
        jedis.lpushx("test_list1","l0");
        SortingParams sortingParams = new SortingParams();
        sortingParams.alpha();
        sortingParams.desc();
        //排序获取
        List<String> r1 = jedis.sort("test_list1",sortingParams);
        System.out.println(JSON.toJSONString(r1));

        //按索引获取
        long size = jedis.llen("test_list1");
        for (long l = 0; l<size;l++){
            System.out.println(jedis.lindex("test_list1",l));
        }

        //按下标区间获取 -1表示列表的最后一个元素，-2表示倒数第二个元素
        List<String> r2 = jedis.lrange("test_list1",0,-1);
        System.out.println(JSON.toJSONString(r2));
    }

    @Test
    public void testHash(){
        Jedis jedis = JedisUtil.getJedis();
        Map<String,String> map = new HashMap<>();
        map.put("age","20");
        map.put("name","张三");
        jedis.hset("test_hash",map);

        jedis.hset("test_hash","age","21");//值必须用字符串


        Student student = new Student();
        student.setAge("30");
        student.setName("李四");

        jedis.hset("test_hash2", JSON.parseObject(JSON.toJSONBytes(student),Map.class));

        Map<String,String> r1 = jedis.hgetAll("test_hash");
        Map<String,String> r2 = jedis.hgetAll("test_hash2");
        System.out.println(JSON.toJSONString(r1));
        System.out.println(JSON.toJSONString(r2));
    }

    @Test
    public void testObject() throws IOException, ClassNotFoundException {
        Jedis jedis = JedisUtil.getJedis();

        Student student = new Student();
        student.setName("张三");
        student.setAge("30");
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bo);

        os.writeObject(student);

        byte[] barr = bo.toByteArray();

        jedis.set("object_byte_test".getBytes(),barr);

        byte[] rArr = jedis.get("object_byte_test".getBytes());

        ByteArrayInputStream bi = new ByteArrayInputStream(rArr);
        ObjectInputStream oi = new ObjectInputStream(bi);
        Student s = (Student) oi.readObject();
        System.out.println(s.toString());

    }


}
