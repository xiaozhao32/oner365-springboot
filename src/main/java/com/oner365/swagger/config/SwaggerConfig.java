package com.oner365.swagger.config;

import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import com.oner365.swagger.config.properties.SwaggerProperties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;

/**
 * Swagger Config
 * 
 * @author zhaoyong
 * 
 */
@Configuration
@EnableConfigurationProperties({ SwaggerProperties.class })
public class SwaggerConfig {

  @Resource
  private SwaggerProperties properties;

  @Bean
  OpenAPI customOpenAPI() {
    Contact contact = new Contact();
    contact.setEmail(properties.getEmail());
    contact.setName(properties.getName());
    contact.setUrl(properties.getUrl());
    return new OpenAPI()
        // 增加swagger授权请求头配置
        .components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION,
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme(HttpHeaders.AUTHORIZATION)))
        .info(new Info().title(properties.getName()).version(properties.getVersion()).contact(contact)
            .description(properties.getDescription()).termsOfService(properties.getUrl())
            .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html")));
  }

  @Bean
  GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
    return openApi -> {
      // 全局添加鉴权参数
      if (openApi.getPaths() != null) {
        openApi.getPaths().forEach((s, pathItem) -> {
          pathItem.readOperations().forEach(operation -> {
            operation.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
          });
        });
      }
    };
  }

}
