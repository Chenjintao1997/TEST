package com.cjt.test.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2021/4/10 16:31
 */
@Data
@ToString
public class Payment implements Serializable {
    private Long id;

    private String serial;
}
