package com.cjt.test.exception;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GeneralException extends Exception {
    private String code;
    private String info;

    public GeneralException() {
        this.code = "-1";
        this.info = "系统错误";
    }

    public GeneralException(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public GeneralException(String errinfo) {
        this.code = "-1";
        this.info = errinfo;
    }

}
