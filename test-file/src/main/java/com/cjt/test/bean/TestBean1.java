package com.cjt.test.bean;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2021/1/10 15:54
 */
public class TestBean1 implements Serializable {
    private String fileId;

    private String batchId;

    private String day;

    private String age;

    private String context;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
