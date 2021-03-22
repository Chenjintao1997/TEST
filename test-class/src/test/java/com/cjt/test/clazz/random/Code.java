package com.cjt.test.clazz.random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @Author: chenjintao
 * @Date: 2020-02-24 15:09
 */
public class Code {
    private final static Logger LOGGER = LoggerFactory.getLogger(Code.class);

    private final static String baseCodeStr = "123456789abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";  //目前不包含0oO
    private final static char[] baseCodeArr = baseCodeStr.toCharArray();

    public static String getIntCode(int length) {
        Random rand = new Random();
        StringBuffer text = new StringBuffer();

        for (int i = 0; i < length; i++) {
            text.append(rand.nextInt(10));
        }
        return text.toString();
    }

    public static String getStrCode(int length) {
        Random rand = new Random();
        StringBuffer text = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            text.append(baseCodeArr[rand.nextInt(baseCodeArr.length)]);
        }

        return text.toString();
    }
}
