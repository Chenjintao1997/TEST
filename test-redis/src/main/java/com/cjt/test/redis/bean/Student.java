package com.cjt.test.redis.bean;

import lombok.Data;
import lombok.ToString;

import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2021/3/18 00:18
 */
@Data
@ToString
public class Student implements Serializable {
    private String age;

    private String name;


}
