package com.cjt.test.service;

import com.cjt.test.bean.User;

/**
 * @Author: chenjintao
 * @Date: 2020/9/15 17:45
 */
public interface IUserSV {
    User getUserByUserName(String userName);

    User getUserByUserId(Integer userId);
}
