package com.cjt.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chenjintao
 * @Date: 2021/4/10 17:43
 */
@RestController
@RequestMapping("/test/sc/order")
@Slf4j
public class OrderController {
    @GetMapping("/list")
    public Object list() {
        String result = "success:" + "order2";
        log.info(result);
        return result;
    }

    @GetMapping("/timeout ")
    public Object timeout() throws InterruptedException {
        Thread.sleep(3000);
        String result = "success:" + "order2";
        log.info(result);
        return result;
    }
}
