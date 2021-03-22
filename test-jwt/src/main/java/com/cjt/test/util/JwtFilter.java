package com.cjt.test.util;

import com.cjt.test.web.config.shiro.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: chenjintao
 * @Date: 2020/9/11 16:08
 * @Desc: 重写并声明实现用户身份验证的过滤器
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String token = this.getAuthzHeader(request);
        return new JwtToken(token, "", "");
    }

    @Override
    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(JwtUtil.JWT_KEY);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = this.createToken(request, response);
        if (token == null) {
            String msg = "create Token method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            LOGGER.error(msg);
            throw new IllegalStateException(msg);
        } else {
            try {
                Subject subject = this.getSubject(request, response);
                subject.login(token);
                return this.onLoginSuccess(token, subject, request, response);
            } catch (AuthenticationException var5) {
                LOGGER.error(var5.getMessage(), var5);
                return this.onLoginFailure(token, var5, request, response);
            }
        }
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String url = WebUtils.toHttp(request).getRequestURI();
        LOGGER.debug("isAccessAllowed url:{}", url);
        if (this.isLoginRequest(request, response)) {
            return true;
        }
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch (Exception e) {
            LOGGER.error("访问错误", e);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        // 返回401
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 设置响应码为401或者直接输出消息
        String url = httpServletRequest.getRequestURI();
        LOGGER.error("onAccessDenied url：{}", url);
        //throw new UnauthenticatedException();
        return false;
    }


}
