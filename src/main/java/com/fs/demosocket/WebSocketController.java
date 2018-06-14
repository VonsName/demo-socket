package com.fs.demosocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author Administrator
 */
@Controller
public class WebSocketController {


    /**
     * 通过simpMessagingTemplate向浏览器发送消息
     */
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    /**
     * 在springmvc中,可以直接在参数中获得principal,pinciple中包含当前用户信息
     */

    public void handleChat(Principal principal, String msg){
        /**
         * 硬编码,对用户姓名进行判断
         *  向用户发送消息,第一个参数:接收消息的用户,第二个参数:浏览器订阅地址,第三个参数:消息
         */
        if ("james".equals(principal.getName())) {
            simpMessagingTemplate.convertAndSendToUser("curry",
                    "/queue/notifications", principal.getName() + "-send: " + msg);
        } else {
            simpMessagingTemplate.convertAndSendToUser("james",
                    "/queue/notifications", principal.getName() + "-send: " + msg);
        }
    }

    /**
     * 当浏览器向服务端发送请求时,通过@MessageMapping映射/welcome这个地址,类似于@RequestMapping
     */
    @MessageMapping(value = "/welcome")
    @SendTo(value = "/topic/response")
    public AricResponse test(AricMessage message){

        return new AricResponse("welcome"+message.getName()+"!");
    }
}
