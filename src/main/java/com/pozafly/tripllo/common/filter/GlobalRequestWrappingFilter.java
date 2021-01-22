package com.pozafly.tripllo.common.filter;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;

@Component
@WebFilter(urlPatterns = "/api/**")
public class GlobalRequestWrappingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            System.out.println("--------------------filter--------------------");
            HttpServletRequest wrapper = new ReadableRequestWrapper((HttpServletRequest) request);

            Gson gson = new Gson();
            LinkedHashMap requestBody = gson.fromJson(((ReadableRequestWrapper) wrapper).getRequestBody(), LinkedHashMap.class);

            wrapper.setAttribute("requestBody", requestBody);
            chain.doFilter(wrapper, response);
        } catch (Exception e) {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {}
}
