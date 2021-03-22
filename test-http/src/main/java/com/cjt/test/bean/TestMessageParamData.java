package com.cjt.test.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: chenjintao
 * @Date: 2020-03-04 18:24
 */
@Data
@ToString
public class TestMessageParamData implements Serializable {
    private String rspId;

    private String billId;

    private String userName;

    private String apiKey;

    private Map<String,Object> infoMap;

    private String smsSeq;
}
