package com.pozafly.tripllo.webSocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    /**
     * Before handshake boolean.
     *
     * @param request    the request
     * @param response   the response
     * @param wsHandler  the ws handler
     * @param attributes the attributes
     * @return the boolean
     * @throws Exception the exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession();

            String uri = servletRequest.getURI().toString();
            String userId = uri.substring(uri.lastIndexOf("m_id") + 5);
            System.out.println("beforeHandshake - m_id : " + userId + "  ||  session_id : " + session.getId());

            attributes.put("m_id", userId);
            attributes.put("sessionId", session.getId());
        }

        return true;
    }

    /**
     * After handshake.
     *
     * @param request   the request
     * @param response  the response
     * @param wsHandler the ws handler
     * @param exception the exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // nothing to do.
    }
}