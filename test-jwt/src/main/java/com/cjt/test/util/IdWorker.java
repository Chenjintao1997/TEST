package com.cjt.test.util;

import java.util.UUID;

/**
 * @Author: chenjintao
 * @Date: 2019/4/28 15:39
 */
public class IdWorker {
    public static String nextStringId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
