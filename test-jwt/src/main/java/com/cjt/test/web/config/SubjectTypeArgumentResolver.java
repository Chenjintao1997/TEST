package com.cjt.test.web.config;

import com.auth0.jwt.interfaces.Claim;
import com.cjt.test.bean.User;
import com.cjt.test.service.IUserSV;
import com.cjt.test.util.JwtUtil;
import com.cjt.test.web.annotion.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component
public class SubjectTypeArgumentResolver implements HandlerMethodArgumentResolver {
    private IUserSV userSV;

    public SubjectTypeArgumentResolver(IUserSV userSV) {
        this.userSV = userSV;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class) && methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {

        String token = nativeWebRequest.getHeader(JwtUtil.JWT_KEY);
        if (StringUtils.isBlank(token)) return null;
        Map<String, Claim> tokenInfo = JwtUtil.parseClaimsJWT(token);
        Claim userIdClaim = tokenInfo.get("userId");
        Integer userId = userIdClaim.asInt();
        return  userSV.getUserByUserId(userId);
    }

}