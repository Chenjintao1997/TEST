package com.cjt.test.websocket;

import com.cjt.test.bean.Test1;
import com.cjt.test.utils.SpringBeanUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: chenjintao
 * @Date: 2020-06-02 10:57
 */
@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocket {

    //解决无法依赖注入的问题
    private Test1 test1 = SpringBeanUtils.getBean(Test1.class);

    public Session session;

    public static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    public static Map<String, Session> sessionPool = new HashMap<>();

    public WebSocket() {
        System.out.println("1111");
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        webSockets.add(this);
        sessionPool.put(username, session);
        System.out.println(username + "---【websocket消息】有新的连接，总数为：" + webSockets.size());
        System.out.println("Test1:" + test1);
    }

    @OnClose
    public void OnClose() {
        webSockets.remove(this);
        System.out.println("【websocket消息】连接断开，总数为：" + webSockets.size());

    }


    @OnMessage
    public void onMessage(String message) {
        System.out.println("【websocket消息】收到客户端消息:" + message);
    }

    @OnMessage
    public void onMessage(byte[] message) {
        System.out.println("【websocket消息】收到二进制客户端消息:" + Arrays.toString(message));
    }

    @OnError
    public void OnError(Session session, Throwable e) {
        System.out.println(e.toString());
    }

    public static void sendOnMessage(String username, String message) throws IOException {
        Session session = sessionPool.get(username);
        if (session != null) {
            session.getBasicRemote().sendText("123");
        }
    }

    public static void sendAllMessage(String message) {
        for (WebSocket webSocket : webSockets) {
            System.out.println("【websocket消息】广播消息:" + message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
