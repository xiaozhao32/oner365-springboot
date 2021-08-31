package com.oner365.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import com.oner365.common.constants.PublicConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger Config
 * 
 * @author zhaoyong
 * 
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.oner365")).paths(PathSelectors.any())
                .build().globalRequestParameters(setRequestParameter())
                .pathMapping(PublicConstants.DELIMITER);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Springboot Swagger3").description("springboot | swagger")
                .contact(new Contact("oner365", "https://www.oner365.com", "service@oner365.com")).version("1.0.0")
                .build();
    }
    
    private List<RequestParameter> setRequestParameter() {
        List<RequestParameter> result = new ArrayList<>();

        RequestParameterBuilder builder = new RequestParameterBuilder();
        builder.name(HttpHeaders.AUTHORIZATION).description("token").required(false).in(ParameterType.HEADER).build();
        result.add(builder.build());
        return result;
    }

}
