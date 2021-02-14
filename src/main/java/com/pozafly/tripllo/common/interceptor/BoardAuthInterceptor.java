package com.pozafly.tripllo.common.interceptor;

import com.pozafly.tripllo.board.dao.BoardDao;
import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.pushMessage.dao.PushMessageDao;
import com.pozafly.tripllo.pushMessage.model.PushMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BoardAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private BoardDao boardDao;
    @Autowired
    private PushMessageDao pushMessageDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("---------------Board Interceptor---------------");
        String httpMethod = request.getMethod();
        HandlerMethod method = (HandlerMethod) handler;
        String methodName = method.getMethod().getName();
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        // token 으로부터 가져온 auth
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        if(httpMethod.equals("GET")) {
            if (methodName.equals("readPersonalBoardList")) {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);

                // pathVariable로 들어온 userId
                List<Board> boardList = boardDao.readPersonalBoardList(map);
                if (boardList.size() == 0) return true;

                if (!userId.equals(boardList.get(0).getCreatedBy())) throw new AuthorizationException();
            } else if (methodName.equals("readBoardDetail")) {
                // 1. public 에 공개 되었을 때, 모두 접근 가능해야함.
                // 2. 자기 자신 접근 가능.
                // 3. 초대된 사람 접근 가능.
                // 4. 그 외 접근 불가능.
                Long boardId = Long.parseLong((String) pathVariables.get("boardId"));

                Board board = boardDao.readBoardOne(boardId);
                if (ObjectUtils.isEmpty(board)) return true;

                if(!board.getPublicYn().equals("Y")) {
                    if (!userId.equals(board.getCreatedBy())) {
                        String inviteUser = board.getInvitedUser();

                        if(StringUtils.isEmpty(inviteUser)) throw new AuthorizationException();  // 제작자가 아닌 사람이 방문했을 때, inviteUser가 없는게 말이 안됨.
                        if(!inviteUser.contains(userId)) throw new AuthorizationException();  // 그리고 inviteUser에 현재 사용자의 id가 있어야 함.
                    }
                } else {
                    return true;
                }
            }
        } else if(httpMethod.equals("PUT")) {
            // 초대 푸시메세지 기능 넣었을 경우.
            Long boardId = Long.parseLong((String) pathVariables.get("boardId"));

            // 첫번째 inviteUser를 board에 등록할 때 푸시메세지로 함.
            List<PushMessage> pushMessageList = pushMessageDao.readPushMessage(userId);
            if(!ObjectUtils.isEmpty(pushMessageList)) {
                for(PushMessage el : pushMessageList) {
                    if(el.getBoardId().equals(boardId)) {
                        Long targetBardId = el.getBoardId();
                        Board targetBoard = boardDao.readBoardOne(targetBardId);
                        if(targetBoard.getCreatedBy().equals(el.getSenderId())) {
                            return true;
                        }
                    }
                }
            }

            // board에 invite User 체크 후 권한 획득
            Board board = boardDao.readBoardOne(boardId);
            if(userId.equals(board.getCreatedBy())) return true;
            String inviteUser = board.getInvitedUser();

            LinkedHashMap<String, Object> requestBody = (LinkedHashMap<String, Object>) request.getAttribute("requestBody");
            String publicYn = String.valueOf(requestBody.get("publicYn"));
            if(!StringUtils.isEmpty(publicYn)) {
                if(!board.getCreatedBy().equals(userId)) throw new AuthorizationException();
            }

            if(StringUtils.isEmpty(inviteUser)) throw new AuthorizationException();  // 제작자가 아닌 사람이 방문했을 때, inviteUser가 없는게 말이 안됨.
            if(!inviteUser.contains(userId)) throw new AuthorizationException();  // 그리고 inviteUser에 현재 사용자의 id가 있어야 함.

            return true;

        } else if (httpMethod.equals("DELETE")) {
            // 제작자만 Board 삭제가 가능함.
            Long boardId = Long.parseLong((String) pathVariables.get("boardId"));

            Board board = boardDao.readBoardOne(boardId);
            if(ObjectUtils.isEmpty(board)) return false;
            if(!userId.equals(board.getCreatedBy())) throw new AuthorizationException();
        }
        return true;
    }
}
