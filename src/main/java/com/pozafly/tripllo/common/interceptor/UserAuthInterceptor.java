package com.pozafly.tripllo.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

// https://semtax.tistory.com/76
@Component
@Slf4j
public class UserAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("---------------interceptor---------------");
        String httpMethod = request.getMethod();

        // 유저를 수정할 때 만든이가 맞는지 판단하는 인터셉터.
        if(httpMethod.equals("PUT")) {
            // requestBody로 들어온 id
            LinkedHashMap<String, Object> requestBody = (LinkedHashMap<String, Object>) request.getAttribute("requestBody");
            String inputId = (String) requestBody.get("id");
            // token 으로부터 가져온 auth
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userId = auth.getName();

            if(!inputId.equals(userId)) throw new AuthorizationException();
        }
        return true;
    }
}
