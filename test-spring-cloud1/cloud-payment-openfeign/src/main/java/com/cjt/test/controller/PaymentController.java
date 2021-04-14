package com.cjt.test.controller;

import com.alibaba.fastjson.JSON;
import com.cjt.test.service.OrderFeignSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: chenjintao
 * @Date: 2021/4/11 16:27
 */
@RestController
@RequestMapping("/test/sc/payment")
@Slf4j
public class PaymentController {
    private final static String orderURL = "http://sc-order";


    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private OrderFeignSV orderFeignSV;

    @GetMapping("/get/{id}")
    public Object get(@PathVariable("id") String id) {
        return orderFeignSV.list();
    }

    @GetMapping("/discovery")
    public Object getDiscovery() {
        List<String> services = discoveryClient.getServices();
        log.info(JSON.toJSONString(services));
        List<ServiceInstance> instances = discoveryClient.getInstances("sc-payment");
        for (ServiceInstance serviceInstance : instances) {
            log.info("serviceId:{},host:{},port:{}", serviceInstance.getInstanceId(), serviceInstance.getHost(), serviceInstance.getPort());
        }
        List<ServiceInstance> instances1 = discoveryClient.getInstances("sc-order");
        for (ServiceInstance serviceInstance : instances1) {
            log.info("serviceId:{},host:{},port:{}", serviceInstance.getInstanceId(), serviceInstance.getHost(), serviceInstance.getPort());
        }
        return services;
    }

    @GetMapping("/get/order/timeout")
    public Object getOrderTimeout() {
        return orderFeignSV.timeout();
    }
}
