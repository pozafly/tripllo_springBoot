package com.pozafly.tripllo.common.filter;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/api/user/**")
public class GlobalRequestWrappingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            System.out.println("--------------------filter--------------------");
            HttpServletRequest wrapper = new ReadableRequestWrapper((HttpServletRequest) request);

            JSONParser parser = new JSONParser(((ReadableRequestWrapper) wrapper).getRequestBody());
            wrapper.setAttribute("requestBody", parser.object());
            chain.doFilter(wrapper, response);
        } catch (Exception e) {
            chain.doFilter(request, response);
        }
    }
}
