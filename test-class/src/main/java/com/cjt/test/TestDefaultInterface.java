package com.cjt.test;

/**
 * @Author: chenjintao
 * @Date: 2020-03-09 20:42
 */
public interface TestDefaultInterface {
    void test1();

    default void test2() {
        System.out.println("test2");
    }

    int i1 = 100;

    static void test3() {
        System.out.println("test3");
    }

    default void test4() {
        System.out.println("---test4");
        test1();
        test2();
        test3();
        System.out.println("---test4");

    }

    static void test5() {
        System.out.println("---test5");
        test3();
        System.out.println("---test5");
    }
}
