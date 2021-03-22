package com.cjt.test.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: chenjintao
 * @Date: 2020-06-02 10:55
 */
@Configuration
public class WebSocketConfig {

    @Bean
    /*
     *     这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，
     *     就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理。
     *     以上是网上的说法，
     *     我个人通过打断点的个人理解是，注册此bean的作用为了在spring容器中检测被@ServerEndpoint注解标识的对象，并将其注入ServerContainer容器；
     *     然后在客户端请求连接服务端websocket时在根据path从serverContainer容器中找到指定@serverEndpoint修饰的类并实例化
     */
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
