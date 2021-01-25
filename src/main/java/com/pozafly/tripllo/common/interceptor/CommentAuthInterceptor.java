package com.pozafly.tripllo.common.interceptor;

import com.pozafly.tripllo.comment.dao.CommentDao;
import com.pozafly.tripllo.comment.model.Comment;
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
public class CommentAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private CommentDao commentDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("---------------Comment Interceptor---------------");
        String httpMethod = request.getMethod();
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        // token 으로부터 가져온 auth
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        if(httpMethod.equals("PUT")) {
            LinkedHashMap<String, Object> requestBody = (LinkedHashMap<String, Object>) request.getAttribute("requestBody");
            Double doubleValue = (Double)(requestBody.get("id"));
            Long commentId = doubleValue.longValue();
            Comment comment = commentDao.readCommentByCommentId(commentId);

            if(ObjectUtils.isEmpty(comment)) return true;
            if(!userId.equals(comment.getCreatedBy())) throw new AuthorizationException();
        } else if (httpMethod.equals("DELETE")) {
            Long commentId = Long.parseLong((String) pathVariables.get("id"));
            Comment comment = commentDao.readCommentByCommentId(commentId);

            if(ObjectUtils.isEmpty(comment)) return true;
            if(!userId.equals(comment.getCreatedBy())) throw new AuthorizationException();
        }
        return true;
    }
}
