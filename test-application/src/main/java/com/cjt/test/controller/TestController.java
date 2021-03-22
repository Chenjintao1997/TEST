package com.cjt.test.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chenjintao
 * @Date: 2019-10-31 11:21
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/post/json")
    public Object testPostJson(@RequestBody JSONObject params){
        return params;

    }
}
