package com.cjt.test.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2020/10/27 11:17
 */
@Data
@ToString
public class Test3 implements Serializable {

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("年龄")
    private Integer age;

    private String pass;
}
