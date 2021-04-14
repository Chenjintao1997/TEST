package com.cjt.test.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: chenjintao
 * @Date: 2021/4/12 17:32
 */
@FeignClient(name = "SC-ORDER")
@RequestMapping("/test/sc/order")
public interface OrderFeignSV {
    @GetMapping("/list")
    String list();

    @GetMapping("/timeout ")
    String timeout();
}
