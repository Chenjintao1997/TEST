package com.cjt.test.web.controller;

import com.cjt.test.bean.User;
import com.cjt.test.exception.GeneralException;
import com.cjt.test.service.IUserSV;
import com.cjt.test.util.JwtUtil;
import com.cjt.test.web.annotion.LoginUser;
import com.cjt.test.web.config.shiro.JwtToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenjintao
 * @Date: 2020/9/22 18:27
 */
@RestController
@RequestMapping("/test")
public class TestController {

    public static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private IUserSV userSV;


    @GetMapping("/login")
    public Object login(String username, String password, HttpServletResponse response) throws GeneralException {
        Date now = new Date();

        if (StringUtils.isEmpty(username)) {
            throw new GeneralException("请输入用户名");
        }
        if (StringUtils.isEmpty(password)) {
            throw new GeneralException("请输入密码");
        }

        Subject subject = SecurityUtils.getSubject();

        User returnUser = userSV.getUserByUserName(username);
        if (returnUser == null) throw new GeneralException("该账户还未注册");

        String returnUsername = returnUser.getUsername();
        String returnPassword = returnUser.getPassword();
        Integer userId = returnUser.getUserId();

        if (!(returnUsername.equals(username) || returnPassword.equals(password))) {
            throw new GeneralException("账号或密码错误");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        String token = JwtUtil.createJWT(map);

        try {
            if (!subject.isAuthenticated()) {
                JwtToken jwtToken = new JwtToken(token, username, password);
                subject.login(jwtToken);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new GeneralException("系统错误");
        }

        response.setHeader(JwtUtil.JWT_KEY, token);

        return returnUser;
    }

    @GetMapping("/authentication")
    // @RequiresAuthentication
    //@RequiresRoles(value = {"admin", "super_admin"}, logical = Logical.OR)
    @RequiresPermissions(value = {"p1", "p2"}, logical = Logical.OR)
    public Object testAuthentication() {
        System.out.println("success");
        return null;
    }

    @GetMapping("/args")
    public Object testArgs(@LoginUser User user, String param1) {
        System.out.println(user.toString());
        System.out.println(param1);
        return user;
    }

    @PostMapping("/args")
    public Object testPostArgs(@LoginUser User user, String username) {
        System.out.println(user.toString());
        System.out.println(username);
        return user;
    }
}
