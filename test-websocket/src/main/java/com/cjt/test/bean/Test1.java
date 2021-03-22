package com.cjt.test.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2020-06-03 11:05
 */
@Component
@Data
@ToString
public class Test1 implements Serializable {
    private static final long serialVersionUID = 4504464726248150572L;

    private Integer age;

    private String name;

    public Test1() {
        System.out.println("222");
    }
}
