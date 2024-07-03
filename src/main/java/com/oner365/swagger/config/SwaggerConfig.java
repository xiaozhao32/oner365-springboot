package com.oner365.swagger.config;

import java.util.Collections;
import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.swagger.config.properties.SwaggerProperties;

import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Config
 * 
 * @author zhaoyong
 * 
 */
@EnableSwagger2
@Configuration
@EnableConfigurationProperties({ SwaggerProperties.class })
public class SwaggerConfig implements WebMvcConfigurer {

  @Resource
  private SwaggerProperties properties;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Bean
  Docket testApi() {
    return buildApi("Api(对外接口)", "com.oner365.api");
  }

  @Bean
  Docket systemApi() {
    return buildApi("System(系统管理)", "com.oner365.sys");
  }

  @Bean
  Docket monitorApi() {
    return buildApi("Monitor(监控中心)", "com.oner365.monitor");
  }

  @Bean
  Docket elasticsearchApi() {
    return buildApi("Elasticsearch(索引查询)", "com.oner365.elasticsearch");
  }

  @Bean
  Docket gatewayApi() {
    return buildApi("Cloud 专用(网关管理)", "com.oner365.gateway");
  }

  @Bean
  Docket filesApi() {
    return buildApi("Files(文件中心)", "com.oner365.files");
  }

  @Bean
  Docket rabbitmqApi() {
    return buildApi("Queue(队列)", "com.oner365.queue");
  }
  
  @Bean
  Docket statemachineApi() {
    return buildApi("State(状态转换)", "com.oner365.statemachine");
  }

  private Docket buildApi(String groupName, String packageName) {
    return new Docket(DocumentationType.OAS_30).pathMapping(PublicConstants.DELIMITER).groupName(groupName)
        .apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage(packageName)).paths(PathSelectors.any())
        .build().securitySchemes(securitySchemes()).securityContexts(securityContexts());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title(properties.getName()).termsOfServiceUrl(properties.getUrl())
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.txt").description(properties.getDescription())
        .contact(new Contact(properties.getName(), properties.getUrl(), properties.getEmail()))
        .version(properties.getVersion()).build();
  }

  /**
   * 设置授权信息
   */
  private List<SecurityScheme> securitySchemes() {
    ApiKey apiKey = new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, In.HEADER.toValue());
    return Collections.singletonList(apiKey);
  }

  /**
   * 授权信息全局应用
   */
  private List<SecurityContext> securityContexts() {
    return Collections.singletonList(SecurityContext.builder()
        .securityReferences(Collections.singletonList(new SecurityReference(HttpHeaders.AUTHORIZATION,
            new AuthorizationScope[] { new AuthorizationScope("global", "") })))
        .build());
  }

}
