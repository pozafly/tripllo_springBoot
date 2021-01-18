package com.pozafly.tripllo.common.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                .message("Not_Found!")
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pozafly.tripllo"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiInfo(title, version))
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages)
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                // controller에 @AuthenticationPrincipal SecurityUser securityUser 이렇게 유저 정보 조회 건 swagger에서 파라미터 표시안함
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                ;
    }

    private ApiKey apiKey() {
        // authorizations 에 들어가는 jwt 토근정보임. 2번째 파라미터로 keyname은 Authorization 이라 적어주면 헤더에 붙어서 들어간다.
        return new ApiKey("JWT", "Authorization", "header");
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
                "Tripllo API 문서입니다 😁",
                version,
                "www.example.com",
                new Contact("여기로 연락주세요!", "www.example.com", "pain103@naver.com"),
                "Licenses",

                "www.example.com",

                new ArrayList<>());
    }
}