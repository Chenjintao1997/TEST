package com.cjt.test;

/**
 * @Author: chenjintao
 * @Date: 2020-03-09 20:43
 */
public class TestDefaultInterfaceImpl implements TestDefaultInterface,TestDefaultInterface2 {
    @Override
    public void test1() {
        System.out.println("test1");
    }

    @Override
    public void test2() {
        System.out.println("impl:test2");
    }


}
