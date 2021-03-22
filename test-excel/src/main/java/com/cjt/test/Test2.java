package com.cjt.test;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2020-02-28 20:53
 */
@Data
@ToString
public class Test2 implements Serializable {
    @Excel(name = "1",orderNum = "0")
    private String str1;

    @Excel(name = "2",orderNum = "1")
    private String str2a;

    @Excel(name = "2",orderNum = "2",mergeRely = {1})
    private String str2b;

    @Excel(name = "2",orderNum = "3",mergeRely = {1,2})
    private String str2c;
}
