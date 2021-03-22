package com.cjt.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: chenjintao
 * @Date: 2019-10-21 18:05
 */
@RestController
public class ReturnValueController {

//    @Value("${123.123}")
//    private String str;

    @GetMapping("/time/test")
    public Object TimeTest() {
        return new Date();
    }



}
