package com.cjt.test.web.config;

import com.cjt.test.service.IUserSV;
import com.cjt.test.util.JwtFilter;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

@Configuration
//@ControllerAdvice
public class ShiroSecurityConfig implements WebMvcConfigurer {

    private static Logger log = LoggerFactory.getLogger(ShiroSecurityConfig.class);

    @Autowired
    Realm realm;

    @Autowired
    IUserSV userSV;

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);

        //close shiroDao 关闭subject的state持久化到session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);

        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        return defaultWebSecurityManager;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */

        creator.setUsePrefix(true);
        return creator;
    }

//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//        // use permissive to NOT require authentication, our controller Annotations will decide that
//        chainDefinition.addPathDefinition("/**", "authcBasic[permissive]");
//
//        return chainDefinition;
//    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager, ShiroFilterChainDefinition filterChainDefinition) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        factoryBean.setSecurityManager(securityManager);

        //添加filter，factoryBean.getFilters()获取到的是引用，可直接添加值
        factoryBean.getFilters().put("jwt", new JwtFilter());
        // factoryBean.getFilters().put("customPerms", new CustomPermissionsAuthorizationFilter());

        //默认是size=0的LinkedHashMap
        factoryBean.getFilterChainDefinitionMap();

        Map<String, String> definitionMap = factoryBean.getFilterChainDefinitionMap();

        /*
         * definitionMap是一个LinkedHashMap，是一个链表结构
         * put的顺序很重要，当/open/**匹配到请求url后，将不再检查后面的规则
         * 官方将这种原则称为 FIRST MATCH WINS
         * https://waylau.gitbooks.io/apache-shiro-1-2-x-reference/content/III.%20Web%20Applications/10.%20Web.html
         */
        definitionMap.put("/open/**", "anon");

        /*
         * 由于禁用了session存储，shiro不会存储用户的认证状态，所以在接口授权之前要先认证用户，不然CustomPermissionsAuthorizationFilter不知道用户是谁
         * 实际项目中可以将这些接口权限规则放到数据库中去
         */
//        definitionMap.put("/user/add", "jwt, customPerms["+userService.getUserAdd().getName()+"]");
//        definitionMap.put("/user/delete", "jwt, customPerms["+userService.getUserDelete().getName()+"]");
//        definitionMap.put("/user/edit", "jwt, customPerms["+userService.getUserEdit().getName()+"]");
//        definitionMap.put("/user/view", "jwt, customPerms["+userService.getUserView().getName()+"]");
//        definitionMap.put("/test/other", "jwt, customPerms["+userService.getTestOther().getName()+"]");
//        definitionMap.put("/role/permission/edit", "jwt, customPerms["+userService.getRolePermisssionEdit().getName()+"]");

        // 前面的规则都没匹配到，最后添加一条规则，所有的接口都要经过com.example.shirojwt.filter.JwtFilter这个过滤器验证


        definitionMap.put("/webjars/**", "anon");


        //swagger 放行
        definitionMap.put("/swagger/**", "anon");
        definitionMap.put("/v2/api-docs", "anon");
        definitionMap.put("/swagger-ui.html", "anon");
        definitionMap.put("/swagger-resources/**", "anon");
        definitionMap.put("/csrf", "anon");

        definitionMap.put("/statics/**", "anon");
        definitionMap.put("/login.html", "anon");
        definitionMap.put("/test/login", "anon");
        definitionMap.put("/", "anon");
        //definitionMap.put("/favicon.ico", "anon");


        definitionMap.put("/**", "jwt");

        factoryBean.setFilterChainDefinitionMap(definitionMap);
        //factoryBean.setFilterChainDefinitionMap(filterChainDefinition.getFilterChainMap());

        return factoryBean;
    }

//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//        // use permissive to NOT require authentication, our controller Annotations will decide that
//        chainDefinition.addPathDefinition("/**", "authcBasic[permissive]");
//
//        return chainDefinition;
//    }


    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody
    String handleException(UnauthenticatedException x) {
        log.warn("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody
    String handleException(AuthorizationException x) {
        log.warn("{} was thrown", x.getClass(), x);
        return x.getMessage();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SubjectTypeArgumentResolver(userSV));
    }


}