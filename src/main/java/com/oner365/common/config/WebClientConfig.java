package com.oner365.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient Config
 * 
 * @author liutao
 * 
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
    	ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
		return WebClient.builder().clientConnector(httpConnector).build();
    }

}
