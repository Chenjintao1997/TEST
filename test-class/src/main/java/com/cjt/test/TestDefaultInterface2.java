package com.cjt.test;

/**
 * @Author: chenjintao
 * @Date: 2020/7/17 17:11
 */
public interface TestDefaultInterface2 {
    default void test2(){
        System.out.println("interface2:test2");
    }

    default void test6(){
        System.out.println("interface2:test6");
    }
}
