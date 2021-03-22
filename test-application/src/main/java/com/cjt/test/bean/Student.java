package com.cjt.test.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: chenjintao
 * @Date: 2020/8/6 15:08
 */
@Data
@ToString
@TableName("student")
public class Student {
    private Long id;

    private String name;

    private String age;

    private String sex;
}
