package com.cjt.test.clazz.random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @Author: chenjintao
 * @Date: 2019-08-26 15:51
 */

@RunWith(JUnit4.class)
public class TestClass {

    @Test
    public void test1() {
        System.out.println(Code.getIntCode(6));
        System.out.println(Code.getStrCode(6));
    }


}
