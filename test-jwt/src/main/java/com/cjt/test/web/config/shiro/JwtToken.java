package com.cjt.test.web.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Author: chenjintao
 * @Date: 2020/9/22 16:07
 * @desc: 重写shiro的token，用于realm
 */
public class JwtToken extends UsernamePasswordToken {

    private String token;


    public JwtToken(String token, String username,String password) {
        super(username,password);
        this.token = token;

    }

    public JwtToken(){
        super();
    }



    @Override
    public Object getCredentials() {
        return this.token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
