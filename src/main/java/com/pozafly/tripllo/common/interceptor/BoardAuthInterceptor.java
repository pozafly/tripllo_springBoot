package com.pozafly.tripllo.common.interceptor;

import com.pozafly.tripllo.board.dao.BoardDao;
import com.pozafly.tripllo.board.model.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BoardAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private BoardDao boardDao;

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
            if(methodName.equals("readBoardList")) {
                // pathVariable로 들어온 userId
                String id = (String) pathVariables.get("userId");
                List<Board> boardList = boardDao.readBoardList(id);
                if(boardList.size() == 0) return true;

                if(!userId.equals(boardList.get(0).getCreatedBy())) throw new AuthorizationException();
            } else if(methodName.equals("readBoardDetail")) {
                Long boardId = Long.parseLong((String) pathVariables.get("boardId"));
                boardDao.readBoardDetail(boardId);

                Board board = boardDao.readBoardOne(boardId);
                if(ObjectUtils.isEmpty(board)) return true;
                if(!userId.equals(board.getCreatedBy())) throw new AuthorizationException();
            }
        } else if (httpMethod.equals("PUT") || httpMethod.equals("DELETE")) {
            Long boardId = Long.parseLong((String) pathVariables.get("boardId"));
            boardDao.readBoardDetail(boardId);

            Board board = boardDao.readBoardOne(boardId);
            if(ObjectUtils.isEmpty(board)) return true;
            if(!userId.equals(board.getCreatedBy())) throw new AuthorizationException();
        }
        return true;
    }
}
