package com.cjt.test;

/**
 * @Author: chenjintao
 * @Date: 2020-03-09 20:53
 */
public class Worker {
    private String name;

    private String sex;

    private Integer age;

    public void work(){
        System.out.println("work");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Worker(Integer age) {
        this.age = age;
    }

    public Worker(String name, String sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Worker() {
    }
}
