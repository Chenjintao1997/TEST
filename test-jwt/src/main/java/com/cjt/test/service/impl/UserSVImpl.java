package com.cjt.test.service.impl;

import com.cjt.test.bean.User;
import com.cjt.test.service.IUserSV;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenjintao
 * @Date: 2020/9/15 17:50
 */
@Service
public class UserSVImpl implements IUserSV {
    private final static Map<String, User> users = new HashMap<>();

    static {
        User user = new User();
        user.setUserId(1);
        user.setUsername("张三");
        user.setPassword("123456");
        users.put("张三", user);
        users.put("1", user);

        User user1 = new User();
        user1.setUserId(2);
        user1.setUsername("李四");
        user1.setPassword("123123");
        users.put("李四", user1);
        users.put("2", user1);

        User user2 = new User();
        user2.setUserId(3);
        user2.setUsername("王五");
        user2.setPassword("456456");
        users.put("王五", user2);
        users.put("3", user2);
    }

    @Override
    public User getUserByUserName(String userName) {
        return users.get(userName);
    }

    @Override
    public User getUserByUserId(Integer userId) {
        return users.get(String.valueOf(userId));
    }
}
