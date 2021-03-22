package com.cjt.test.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2020-03-04 18:22
 */
@Data
public class TestMessageParam implements Serializable {
    private TestMessageParamData params;
}
