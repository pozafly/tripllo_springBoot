package com.pozafly.tripllo.common.config;

import com.pozafly.tripllo.webSocket.WebSocketHandler;
import com.pozafly.tripllo.webSocket.HttpHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    // 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/send");
//    }
//
//    @Override
//    // connection을 맺을때 CORS 허용합니다.
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/").setAllowedOrigins("http://localhost:8080").withSockJS();
//    }
//}


@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/websocket")
                .setAllowedOrigins("https://tripllo.tech")
//                .setAllowedOrigins("*")
                .addInterceptors(new HttpHandshakeInterceptor())
                .withSockJS(); // sockjs 지원
    }
}

//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.setApplicationDestinationPrefixes("/app");
//        // topic과 단일로 연결된 대상에게 통신을 전달할 /queue 를 구독할 수 있도록 설정함.
//        config.enableSimpleBroker("/topic", "/queue");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        // 클라이언트에서 End point를 /websocket으로 해서 구독할 수 있도록 함.
//        registry.addEndpoint("/websocket")
//                .setAllowedOrigins("http://localhost:8080")
//                .addInterceptors(new HttpSessionHandshakeInterceptor());
//        registry.addEndpoint("/websocket")
//                .setAllowedOrigins("http://localhost:8080")
//                .addInterceptors(new HttpSessionHandshakeInterceptor())
//                .withSockJS();
//    }
//
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.setMessageSizeLimit(160 * 64 * 1024);
//        registration.setSendTimeLimit(20 * 10000);
//        registration.setSendBufferSizeLimit(10 * 512 * 1024);
//    }
//}