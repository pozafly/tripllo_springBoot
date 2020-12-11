package com.pozafly.tripllo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
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
                .apis(RequestHandlerSelectors.basePackage("com.pozafly.tripllo.controller"))
                .paths(PathSelectors.ant("/user/**"))
                .build()
                .apiInfo(apiInfo(title, version))
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                ;
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