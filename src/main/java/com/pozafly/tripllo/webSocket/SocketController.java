package com.pozafly.tripllo.webSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    // endpoint
    @MessageMapping("/receive")
    // send
    @SendTo("/send")

    // SocketHandler
    public SocketVO SocketHandler(SocketVO socketVO) {
        String userName = socketVO.getUserName();
        String content = socketVO.getContent();
        System.out.println("ㅇㅕ기로 들어와서 어디로 나가게 될까?");

        return new SocketVO(userName, content);
    }

}
