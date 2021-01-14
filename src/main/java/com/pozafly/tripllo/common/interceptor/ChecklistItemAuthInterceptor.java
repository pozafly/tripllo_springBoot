package com.pozafly.tripllo.common.interceptor;

import com.pozafly.tripllo.checklist.dao.ChecklistDao;
import com.pozafly.tripllo.checklist.dao.ChecklistItemDao;
import com.pozafly.tripllo.checklist.model.ChecklistItem;
import com.pozafly.tripllo.checklist.model.response.ChecklistResultMap;
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
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ChecklistItemAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private ChecklistItemDao checklistItemDao;
    @Autowired
    private ChecklistDao checklistDao;

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
            Long checklistId = Long.parseLong(String.valueOf(requestBody.get("checklistId")));
            List<ChecklistResultMap> checklists = checklistDao.readChecklist(checklistId);

            if(ObjectUtils.isEmpty(checklists)) return true;
            if(!checklists.get(0).getCreatedBy().equals(userId)) throw new AuthorizationException();

        } else if (httpMethod.equals("PUT") || httpMethod.equals("DELETE")) {
            Long checklistItemId = Long.parseLong((String) pathVariables.get("checklistItemId"));
            System.out.println("~~~~~~~~~~~~~~~~~~");
            System.out.println(checklistItemId);
            ChecklistItem checklist = checklistItemDao.readChecklistItem(checklistItemId);

            if(ObjectUtils.isEmpty(checklist)) return true;
            if(!checklist.getCreatedBy().equals(userId)) throw new AuthorizationException();
        }
        return true;
    }
}
