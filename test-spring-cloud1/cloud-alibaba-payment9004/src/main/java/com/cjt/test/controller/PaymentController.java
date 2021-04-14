package com.cjt.test.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author: chenjintao
 * @Date: 2021/4/11 16:27
 */
@RestController
@RequestMapping("/test/sc/payment")
@Slf4j
public class PaymentController {
    private final static String orderURL = "http://sca-order";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/get/{id}")
    public Object get(@PathVariable("id") String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(orderURL + "/test/sc/order/list", HttpMethod.GET, httpEntity, String.class);
        String orderResult = responseEntity.getBody();
        String result = "paymentId:" + id + "orderResult:" + orderResult;
        return result;
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
}
