package com.cjt.test.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: chenjintao
 * @Date: 2020/9/11 10:54
 */
@Data
@ToString
public class User implements Serializable {

    public final static ConcurrentHashMap<String,User> userCache = new ConcurrentHashMap<>();

    private Integer userId;

    private String username;

    private String password;

    private Integer age;

    private String sex;
}
