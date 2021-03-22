package com.cjt.test.encrypt;

/**
 * @Author: chenjintao
 * @Date: 2019-11-21 18:02
 */
public enum OpMode {
    ENCRYPT(1),
    DECRYPT(2);

    int value;

    OpMode(int i) {
        value = i;
    }
}
