package com.cjt.test.clazz;

/**
 * @Author: chenjintao
 * @Date: 2019-08-29 11:44
 */
public class Item {

    private String str;

    private static int i = 10;


    static {
        int j = 20;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Item{" +
                "str='" + str + '\'' +
                '}';
    }

    public Item(){}

    public Item(Item item){
        this.str = item.str;
    }

}
