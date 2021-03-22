package com.cjt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.websocket.Session;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: chenjintao
 * @Date: 2020-06-05 00:09
 */
@RunWith(JUnit4.class)
public class ClassTest {

    @Test
    public void test1(){
         Map<String, Session> sessionPool = new ConcurrentHashMap<>();
        CopyOnWriteArraySet<Integer> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

        System.out.println(        copyOnWriteArraySet.remove(1));

    }

    @Test
    public void test2(){
        int[] longArr = new int[1000000000];

        for (int i = 0;i<1000000000;i++){
            longArr[i] = i;
        }

        long beginTime = System.currentTimeMillis();
        System.out.println(longArr.length);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-beginTime+"ms");
    }
}
