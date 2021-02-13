package com.pozafly.tripllo.common.interceptor;

import com.pozafly.tripllo.board.dao.BoardDao;
import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.card.dao.CardDao;
import com.pozafly.tripllo.card.model.Card;
import com.pozafly.tripllo.checklist.dao.ChecklistDao;
import com.pozafly.tripllo.checklist.dao.ChecklistItemDao;
import com.pozafly.tripllo.checklist.model.Checklist;
import com.pozafly.tripllo.checklist.model.ChecklistItem;
import com.pozafly.tripllo.checklist.model.response.ChecklistResultMap;
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
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ChecklistItemAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private ChecklistItemDao checklistItemDao;
    @Autowired
    private ChecklistDao checklistDao;
    @Autowired
    private CardDao cardDao;
    @Autowired
    private ListDao listDao;
    @Autowired
    private BoardDao boardDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("---------------ChecklistItem Interceptor---------------");
        String httpMethod = request.getMethod();
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        // token 으로부터 가져온 auth
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        if(httpMethod.equals("POST")) {
            LinkedHashMap<String, Object> requestBody = (LinkedHashMap<String, Object>) request.getAttribute("requestBody");
            long checklistId = (long)Double.parseDouble(String.valueOf(requestBody.get("checklistId")));
            Checklist checklist = checklistDao.readChecklistOne(checklistId); // 여기 잘못됨. cardId를 넣어줘야 함.

            if(ObjectUtils.isEmpty(checklist)) return true;
            Long cardId = checklist.getCardId();
            Card card = cardDao.readCard(cardId);
            Long listId = card.getListId();
            Lists lists = listDao.readList(listId);
            Long boardId = lists.getBoardId();
            Board board = boardDao.readBoardOne(boardId);

            if(!board.getCreatedBy().equals(userId)) {
                String inviteUser = board.getInvitedUser();

                if(StringUtils.isEmpty(inviteUser)) throw new AuthorizationException();  // 제작자가 아닌 사람이 방문했을 때, inviteUser가 없는게 말이 안됨.
                if(!inviteUser.contains(userId)) throw new AuthorizationException();  // 그리고 inviteUser에 현재 사용자의 id가 있어야 함.
            }
        } else if (httpMethod.equals("PUT") || httpMethod.equals("DELETE")) {
            Long checklistItemId = Long.parseLong((String) pathVariables.get("checklistItemId"));
            ChecklistItem checklist = checklistItemDao.readChecklistItem(checklistItemId);
            Long checklistId = checklist.getChecklistId();
            Checklist checklists = checklistDao.readChecklistOne(checklistId);

            if(ObjectUtils.isEmpty(checklists)) return true;
            Long cardId = checklists.getCardId();
            Card card = cardDao.readCard(cardId);
            Long listId = card.getListId();
            Lists lists = listDao.readList(listId);
            Long boardId = lists.getBoardId();
            Board board = boardDao.readBoardOne(boardId);

            if(!board.getCreatedBy().equals(userId)) {
                String inviteUser = board.getInvitedUser();

                if(StringUtils.isEmpty(inviteUser)) throw new AuthorizationException();  // 제작자가 아닌 사람이 방문했을 때, inviteUser가 없는게 말이 안됨.
                if(!inviteUser.contains(userId)) throw new AuthorizationException();  // 그리고 inviteUser에 현재 사용자의 id가 있어야 함.
            }

            if(ObjectUtils.isEmpty(checklist)) return false;
            if(!checklist.getCreatedBy().equals(userId)) throw new AuthorizationException();
        }
        return true;
    }
}
