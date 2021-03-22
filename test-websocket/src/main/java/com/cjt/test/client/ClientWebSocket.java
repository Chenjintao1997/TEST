package com.cjt.test.client;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;

/**
 * @Author: chenjintao
 * @Date: 2020-06-02 15:24
 */
@ClientEndpoint
public class ClientWebSocket {
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("收到服务端消息：" + message);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    /**
     * 连接关闭调用的方法
     *
     * @throws Exception
     */
    @OnClose
    public void onClose() throws Exception {
    }

    /**
     * 关闭链接方法
     *
     * @throws IOException
     */
    public void closeSocket() throws IOException {
        this.session.close();
    }

    /**
     * 发送消息方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {

        this.session.getBasicRemote().sendText(message);
    }

    public void sendMessage(byte[] message) throws IOException {
        this.session.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
    }

    public void sendMessage(Object message) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(message);
        System.out.println("发送成功");
    }

    public void heartCheck() throws IOException {
        RemoteEndpoint.Basic basic = this.session.getBasicRemote();
        basic.sendPing(ByteBuffer.wrap("heart check".getBytes()));
    }

    //启动客户端并建立链接
    public void start(String uri) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            this.session = container.connectToServer(ClientWebSocket.class, URI.create(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
