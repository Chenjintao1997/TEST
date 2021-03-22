package com.cjt.test.service;

import java.util.Set;

/**
 * @Author: chenjintao
 * @Date: 2020/9/15 17:45
 */
public interface IRoleSV {

    Set<String> getRolesByUserId(Integer userId);

    Set<String> getPermissionsByUserId(Integer userId);
}
