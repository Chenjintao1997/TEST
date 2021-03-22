package com.cjt.test.controller;

import com.alibaba.fastjson.JSON;
import com.cjt.test.bean.MessageVO;
import com.cjt.test.bean.Test1;
import com.cjt.test.client.ClientWebSocket;
import com.cjt.test.websocket.WebSocket;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * @Author: chenjintao
 * @Date: 2019-10-31 11:21
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/server/send")
    public Object serverSend() throws IOException {
        WebSocket.sendOnMessage("cjt", "测试服务转发数据");
        return null;
    }

    @PostMapping("/client/send")
    public Object clientSend() throws IOException {
        ClientWebSocket clientWebSocket = new ClientWebSocket();
        clientWebSocket.start("ws://127.0.0.1:8006/websocket/cjt");
        clientWebSocket.sendMessage("测试客户端发送数据");
        return null;
    }

    @PostMapping("/client/send/byte")
    public Object clientSendByte() throws IOException {
        ClientWebSocket clientWebSocket = new ClientWebSocket();
        clientWebSocket.start("ws://127.0.0.1:8006/websocket/cjt");
        clientWebSocket.sendMessage("测试客户端发送数据".getBytes());
        return null;
    }

    @PostMapping("/client/send/object")
    public Object clientSendObject() throws IOException, EncodeException {
        Test1 test1 = new Test1();
        test1.setAge(20);
        test1.setName("name1");
        ClientWebSocket clientWebSocket = new ClientWebSocket();
        clientWebSocket.start("ws://127.0.0.1:8006/websocket/cjt");
        clientWebSocket.sendMessage(test1);
        return null;
    }

    @PostMapping
    public Object heartCheck() throws IOException, EncodeException {

        ClientWebSocket clientWebSocket = new ClientWebSocket();
        clientWebSocket.start("ws://127.0.0.1:8006/websocket/cjt");
        clientWebSocket.heartCheck();
        return null;
    }

    @PostMapping("/client/send/other/ws1")
    public Object clientSendOtherWs1() throws IOException {
        ClientWebSocket clientWebSocket = new ClientWebSocket();
        clientWebSocket.start("ws://127.0.0.1:8003/im/ws/1/1");
        MessageVO messageVO = new MessageVO();
        messageVO.setData("测试客户端发送数据1");
        messageVO.setDataType(1);
        messageVO.setFrom("1");
        messageVO.setTo("2");
        messageVO.setType(1);
        clientWebSocket.sendMessage(JSON.toJSONString(messageVO));
        return null;
    }

    @PostMapping("/client/send/other/ws2")
    public Object clientSendOtherWs2() throws IOException {
        ClientWebSocket clientWebSocket = new ClientWebSocket();
        clientWebSocket.start("ws://127.0.0.1:8003/im/ws/1/2");
        MessageVO messageVO = new MessageVO();
        messageVO.setData("测试客户端发送数据2");
        messageVO.setDataType(1);
        messageVO.setFrom("2");
        messageVO.setTo("1");
        messageVO.setType(1);
        clientWebSocket.sendMessage(JSON.toJSONString(messageVO));
        return null;
    }

    @PostMapping("/client/send/other/ws3")
    public Object clientSendOtherWs3() throws IOException {
        ClientWebSocket clientWebSocket = new ClientWebSocket();
        clientWebSocket.start("ws://127.0.0.1:8003/ws/im");
        MessageVO messageVO = new MessageVO();
        messageVO.setData("测试客户端发送数据3");
        messageVO.setDataType(1);
        messageVO.setFrom("2");
        messageVO.setTo("1");
        messageVO.setType(1);
        clientWebSocket.sendMessage(JSON.toJSONString(messageVO));
        return null;
    }
}
