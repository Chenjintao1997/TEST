package com.cjt.test.clazz;

import com.cjt.test.clinit.InterfaceClinitTest;

/**
 * @Author: chenjintao
 * @Date: 2019-08-29 11:48
 */
public class ItemOne extends Item {
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ItemOne{" +
                "Item"+super.toString()+
                "age=" + age +
                '}';
    }

    public ItemOne(){}

    public ItemOne(Item item){
        super(item);
    }
}
