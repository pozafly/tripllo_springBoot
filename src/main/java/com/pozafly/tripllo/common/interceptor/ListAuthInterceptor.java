package com.pozafly.tripllo.common.interceptor;

import com.pozafly.tripllo.board.dao.BoardDao;
import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.list.dao.ListDao;
import com.pozafly.tripllo.list.model.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class ListAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private ListDao listDao;
    @Autowired
    private BoardDao boardDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("---------------List Interceptor---------------");
        String httpMethod = request.getMethod();
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        // token 으로부터 가져온 auth
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        if (httpMethod.equals("POST")) {
            LinkedHashMap<String, Object> requestBody = (LinkedHashMap<String, Object>) request.getAttribute("requestBody");
            long boardId = (long)Double.parseDouble(String.valueOf(requestBody.get("boardId")));

            Board board = boardDao.readBoardOne(boardId);
            if(!board.getCreatedBy().equals(userId)) {
                String inviteUser = board.getInvitedUser();
                if(StringUtils.isEmpty(inviteUser)) throw new AuthorizationException();  // 제작자가 아닌 사람이 방문했을 때, inviteUser가 없는게 말이 안됨.
                if(!inviteUser.contains(userId)) throw new AuthorizationException();  // 그리고 inviteUser에 현재 사용자의 id가 있어야 함.
            }

        } else if (httpMethod.equals("PUT") || httpMethod.equals("DELETE")) {
            Long listId = Long.parseLong((String) pathVariables.get("listId"));
            Lists list = listDao.readList(listId);
            Long boardId = list.getBoardId();

            Board board = boardDao.readBoardOne(boardId);
            if(!board.getCreatedBy().equals(userId)) {
                String inviteUser = board.getInvitedUser();
                if(StringUtils.isEmpty(inviteUser)) throw new AuthorizationException();  // 제작자가 아닌 사람이 방문했을 때, inviteUser가 없는게 말이 안됨.
                if(!inviteUser.contains(userId)) throw new AuthorizationException();  // 그리고 inviteUser에 현재 사용자의 id가 있어야 함.
            }

//            if(!ObjectUtils.isEmpty(list)) {
//                if(!list.getCreatedBy().equals(userId)) throw new AuthorizationException();
//            }
        }
        return true;
    }
}
