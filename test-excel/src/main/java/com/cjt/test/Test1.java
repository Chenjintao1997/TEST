package com.cjt.test;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2020-02-26 19:03
 */
@Data
@ToString
public class Test1 implements Serializable {

    @Excel(name = "名称")
    private String name;

    @Excel(name = "性别", replace = {"男_1", "女_2", "未知_0", "null_0"})
    private Integer sex;
}
