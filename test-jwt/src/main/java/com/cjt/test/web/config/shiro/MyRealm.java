package com.cjt.test.web.config.shiro;

import com.auth0.jwt.interfaces.Claim;
import com.cjt.test.bean.User;
import com.cjt.test.service.IRoleSV;
import com.cjt.test.service.IUserSV;
import com.cjt.test.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @Author: chenjintao
 * @Date: 2020/9/15 17:42
 */
@Component
public class MyRealm extends AuthorizingRealm {


    @Autowired
    private IUserSV userSV;
    @Autowired
    private IRoleSV roleSV;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = principals.getPrimaryPrincipal().toString();
        User baseUser = userSV.getUserByUserName(username);

        Integer userId = baseUser.getUserId();
        Set<String> roles = roleSV.getRolesByUserId(userId);
        Set<String> permissions = roleSV.getPermissionsByUserId(userId);

        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String username = (String) jwtToken.getPrincipal();
        String password = String.valueOf(jwtToken.getPassword());
        String token = (String) jwtToken.getCredentials();


        User currentUser = null;

        if (StringUtils.isBlank(token)) {
            throw new UnsupportedTokenException();
        }

        if (JwtUtil.verifierToken(token)) {
            Map<String, Claim> tokenInfo = JwtUtil.parseClaimsJWT(token);
            Claim userIdClaim = tokenInfo.get("userId");
            Integer userId = userIdClaim.asInt();
            currentUser = userSV.getUserByUserId(userId);
        }
        if (currentUser == null) throw new UnsupportedTokenException();

        Map<String, User> userCache = User.userCache;
        userCache.put(String.valueOf(currentUser.getUserId()), currentUser);
        username = currentUser.getUsername();    //不能为空，为空会导致PrincipalCollection取不到username
        return new SimpleAuthenticationInfo(username, token, this.getName());


    }

//    protected AuthenticationInfo doOldGetAuthenticationInfo(AuthenticationToken token) {
//        String username = (String) token.getPrincipal();
//        String password = new String((char[]) ((char[]) token.getCredentials()));
//        User currentUser = userSV.getUserByUserName(username);
//        if (currentUser == null) {
//            throw new UnknownAccountException();
//        } else if (!currentUser.getPassword().equals(password)) {
//            throw new IncorrectCredentialsException();
//        } else {
//            Session session = SecurityUtils.getSubject().getSession();
//            session.setTimeout(30 * 60 * 1000);
//            //session.setAttribute(User.SESSION_ATTR_NAME, currentUser);
//            return new SimpleAuthenticationInfo(username, password, this.getName());
//        }
//    }
}
