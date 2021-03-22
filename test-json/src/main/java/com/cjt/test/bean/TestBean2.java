package com.cjt.test.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2020/10/20 16:40
 */
@Data
@ToString
public class TestBean2 implements Serializable {
    private String name;

    private String phone;

    private String password;
}
