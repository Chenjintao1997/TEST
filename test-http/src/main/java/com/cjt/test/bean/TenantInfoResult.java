package com.cjt.test.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: chenjintao
 * @Date: 2020-04-22 16:41
 */
@Data
@ToString
public class TenantInfoResult implements Serializable {
    private String code;

    private String resMsg;

    private List<TenantInfoData> data;
}
