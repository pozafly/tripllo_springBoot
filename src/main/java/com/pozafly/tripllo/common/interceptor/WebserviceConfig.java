package com.pozafly.tripllo.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebserviceConfig implements WebMvcConfigurer {

    @Autowired
    private UserAuthInterceptor userAuthInterceptor;
    @Autowired
    private BoardAuthInterceptor boardAuthInterceptor;
    @Autowired
    private ListAuthInterceptor listAuthInterceptor;
    @Autowired
    private CardAuthInterceptor cardAuthInterceptor;
    @Autowired
    private ChecklistAuthInterceptor checklistAuthInterceptor;
    @Autowired
    private ChecklistItemAuthInterceptor checklistItemAuthInterceptor;
    @Autowired
    private CommentAuthInterceptor commentAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthInterceptor)
                .excludePathPatterns("/api/user/valid/**")
                .addPathPatterns("/api/user/**");
        registry.addInterceptor(boardAuthInterceptor)
                .addPathPatterns("/api/board/**");
        registry.addInterceptor(listAuthInterceptor)
                .addPathPatterns("/api/list/**");
        registry.addInterceptor(cardAuthInterceptor)
                .addPathPatterns("/api/card/**");
        registry.addInterceptor(checklistAuthInterceptor)
                .addPathPatterns("/api/checklist/**");
        registry.addInterceptor(checklistItemAuthInterceptor)
                .addPathPatterns("/api/checklistItem/**");
        registry.addInterceptor(commentAuthInterceptor)
                .addPathPatterns("/api/comment/**");
    }
}
