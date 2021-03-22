package com.cjt.test.service.impl;

import com.cjt.test.service.IRoleSV;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: chenjintao
 * @Date: 2020/9/15 17:48
 */
@Service
public class RoleSVImpl implements IRoleSV {
    private final static Set<String> roles = new HashSet<>();
    private final static Set<String> roles1 = new HashSet<>();
    private final static Set<String> roles2 = new HashSet<>();

    private final static Set<String> permissions1 = new HashSet<>();
    private final static Set<String> permissions2 = new HashSet<>();
    private final static Set<String> permissions3 = new HashSet<>();

    private final static Map<Integer, Set<String>> userPermissions = new HashMap<>();

    private final static Map<Integer, Set<String>> userRole = new HashMap<>();

    static {
        roles.add("admin");
        roles.add("super_admin");
        roles.add("guest");
        userRole.put(1, roles);

        permissions1.add("p1");
        permissions1.add("p2");
        permissions1.add("p3");
        userPermissions.put(1, permissions1);

        roles1.add("admin");
        userRole.put(2, roles1);

        permissions2.add("p1");
        permissions2.add("p2");
        userPermissions.put(2, permissions2);

        roles2.add("guest");
        userRole.put(3, roles2);

        permissions3.add("p3");
        userPermissions.put(3, permissions3);
    }

    @Override
    public Set<String> getRolesByUserId(Integer userId) {
        return userRole.get(userId);
    }

    @Override
    public Set<String> getPermissionsByUserId(Integer userId) {
        return userPermissions.get(userId);
    }
}
