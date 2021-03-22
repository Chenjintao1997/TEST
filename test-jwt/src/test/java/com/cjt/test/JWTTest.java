package com.cjt.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.cjt.test.bean.User;
import com.cjt.test.util.IdWorker;
import com.cjt.test.util.JwtUtil;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Map;

/**
 * @Author: chenjintao
 * @Date: 2020/9/15 16:48
 */
@RunWith(JUnit4.class)
public class JWTTest {

    @Test
    public void test1() {

        User user = new User();
        user.setUsername("张三");
        user.setAge(20);
        user.setSex("男");
        String jsonStr = JSON.toJSONString(user);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Map<String, Object> map = jsonObject.getInnerMap();
        String token = JwtUtil.createJWT(map);
        System.out.println(token);

        //token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZXNzaW9uX2luZm8iLCJzZXgiOiLnlLciLCJpc3MiOiJuZXdfY2NhcyIsImV4cCI6MTYwMDE2MzcwMCwiaWF0IjoxNjAwMTYxOTAwLCJqdGkiOiI0NDUxOGJhNDVkYjM0ZGUwYjkwZTU5YjNhNTdmZDI5NCIsImFnZSI6MjAsInVzZXJuYW1lIjoi5byg5LiJIn0.FAIvqnPzQOtP1Kpe6e3lQ29pVt1cLmXibClfZFIiB";

        boolean verf = JwtUtil.verifierToken(token);
        System.out.println("verf:" + verf);

        Map<String, Claim> result = JwtUtil.parseClaimsJWT(token);

        Claim username = result.get("username");
        Claim age = result.get("age");
        Claim sex = result.get("sex");
        System.out.println(username.asString());
        System.out.println(age.asInt());
        System.out.println(sex.asString());
        System.out.println(JSON.toJSONString(result));


    }

    @Test
    public void testExpire() throws InterruptedException {
        User user = new User();
        user.setUsername("张三");
        user.setAge(20);
        user.setSex("男");
        String jsonStr = JSON.toJSONString(user);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Map<String, Object> map = jsonObject.getInnerMap();
        String token = JwtUtil.createJWT(IdWorker.nextStringId(),JwtUtil.JWT_ISSUER,JwtUtil.JWT_SUBJECT,10*1000,map);
        System.out.println(token);

        Thread.sleep(5*1000);

        boolean verf = JwtUtil.verifierToken(token);
        System.out.println("verf:" + verf);

        Map<String, Claim> result = JwtUtil.parseClaimsJWT(token);

        Claim username = result.get("username");
        Claim age = result.get("age");
        Claim sex = result.get("sex");
        System.out.println(username.asString());
        System.out.println(age.asInt());
        System.out.println(sex.asString());
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void test(){
        System.out.println(!(true || false));
    }

    @Test
    public void test2(){
        ShiroHttpServletRequest shiroRequest = new ShiroHttpServletRequest(null,null,false);

    }
}
