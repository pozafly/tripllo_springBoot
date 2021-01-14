package com.pozafly.tripllo.common.interceptor;

import com.pozafly.tripllo.card.dao.CardDao;
import com.pozafly.tripllo.card.model.Card;
import com.pozafly.tripllo.list.dao.ListDao;
import com.pozafly.tripllo.list.model.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class CardAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private CardDao cardDao;
    @Autowired
    private ListDao listDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("---------------Card Interceptor---------------");
        String httpMethod = request.getMethod();
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        // token 으로부터 가져온 auth
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        if(httpMethod.equals("GET") || httpMethod.equals("PUT") || httpMethod.equals("DELETE")) {
            Long cardId = Long.parseLong((String) pathVariables.get("cardId"));
            Card card = cardDao.readCard(cardId);

            if(ObjectUtils.isEmpty(card)) return true;
            if(!userId.equals(card.getCreatedBy())) throw new AuthorizationException();
        } else if (httpMethod.equals("POST")) {
            LinkedHashMap<String, Object> requestBody = (LinkedHashMap<String, Object>) request.getAttribute("requestBody");
            Long listId = Long.parseLong(String.valueOf(requestBody.get("listId")));

            Lists list = listDao.readList(listId);
            if(ObjectUtils.isEmpty(list)) return true;
            if(!list.getCreatedBy().equals(userId)) throw new AuthorizationException();
        }
        return true;
    }
}
