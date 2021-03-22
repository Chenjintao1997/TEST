package com.cjt.test;

/**
 * @Author: chenjintao
 * @Date: 2020-03-09 21:12
 */
public class MotorVehicle {
    private String brand;

    private String prince;

    public void action(){
        System.out.println("run it");
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrince() {
        return prince;
    }

    public void setPrince(String prince) {
        this.prince = prince;
    }
}
