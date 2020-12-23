package com.pozafly.tripllo.common.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String version;
    private String title;

    @Bean
    public Docket apiV1() {
        version = "V1";
        title = "Tripllo API " + version;

        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder()
                .code(200)
                .message("SUCCESS")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(403)
                .message("인증오류")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(404)
                .message("Page_Not_Found!")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(500)
                .message("Internal_Server_Error!")
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pozafly.tripllo.user.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo(title, version))
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                ;
    }

    private ApiKey apiKey() {
        // authorizations 에 들어가는 jwt 토근정보임. 2번째 파라미터로 keyname은 TOKEN 이라 적어주면 헤더에 붙어서 들어간다.
        return new ApiKey("JWT", "TOKEN", "header");
    }

    private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
        return springfox.documentation.spi.service.contexts.SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }

//    @Bean
//    public Docket apiV2() {
//        version = "Login";
//        title = "victolee API " + version;
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .useDefaultResponseMessages(false)
//                .groupName(version)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.victolee.swaggerexam.api.v2"))
//                .paths(PathSelectors.ant("/v2/api/**"))
//                .build()
//                .apiInfo(apiInfo(title, version));
//
//    }

    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "Tripllo API Docs",
                version,
                "www.example.com",
                new Contact("Contact Me", "www.example.com", "pain103@naver.com"),
                "Licenses",

                "www.example.com",

                new ArrayList<>());
    }
}