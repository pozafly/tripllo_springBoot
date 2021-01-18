package com.pozafly.tripllo.webSocket;

import com.google.gson.JsonElement;
import com.pozafly.tripllo.pushMessage.dao.PushMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.google.gson.JsonParser;


import java.util.*;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private PushMessageDao pushMessageDao;

    //로그인 한 전체
    List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
    // 1대1
    Map<String, WebSocketSession> userSessionsMap = new HashMap<>();


    // 클라이언트가 서버로 연결시
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        String senderId = getMemberId(session); // 접속한 유저의 http세션을 조회하여 id를 얻는 함수
        if(senderId!=null) {	// 로그인 값이 있는 경우만
            log(senderId + " 연결 됨");
            userSessionsMap.put(senderId, session);   // 로그인중 개별유저 저장
        }
    }
    // 클라이언트가 Data 전송 시
    // endpoint
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String senderId = getMemberId(session);

        // 특정 유저에게 보내기
        String msg = message.getPayload();

        JsonParser parser = new JsonParser();
        JsonElement el = parser.parse(msg);
        String target = el.getAsJsonObject().get("target").getAsString();
        String content = el.getAsJsonObject().get("content").getAsString();
        String boardId = el.getAsJsonObject().get("boardId").getAsString();

        if(!StringUtils.isEmpty(target)) {
            WebSocketSession targetSession = userSessionsMap.get(target);  // 메시지를 받을 세션 조회

            Map<String, Object> map = new HashMap<>();
            map.put("userId", senderId);
            map.put("targetId", target);
            map.put("boardId", boardId);
            map.put("content", content);

            pushMessageDao.createPushMessage(map);

            // 실시간 접속시
            if(targetSession != null) {
                TextMessage tmpMsg = new TextMessage(msg);
                targetSession.sendMessage(tmpMsg);
            } else {
                // 접속중이 아닐때.
                System.out.println("해당 유저가 접속중이 아닙니다.");
            }
        }
    }
    // 연결 해제될 때
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String senderId = getMemberId(session);
        if(senderId!=null) {	// 로그인 값이 있는 경우만
            log(senderId + " 연결 종료됨");
            userSessionsMap.remove(senderId);
            sessions.remove(session);
        }
    }
    // 에러 발생시
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log(session.getId() + " 익셉션 발생: " + exception.getMessage());
    }
    // 로그 메시지
    private void log(String logmsg) {
        System.out.println(new Date() + " : " + logmsg);
    }

    // 웹소켓에 id 가져오기
    // 접속한 유저의 http세션을 조회하여 id를 얻는 함수
    private String getMemberId(WebSocketSession session) {
        Map<String, Object> httpSession = session.getAttributes();

        return (String) httpSession.get("m_id"); // 세션에 저장된 m_id 기준 조회
    }
}