package com.cjt.test.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2020-04-22 16:42
 */
@Data
@ToString
public class TenantInfoData implements Serializable {
    private String companyid;

    private String companyname;

}
