package com.oner365.common.config;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

/**
 * WebClient Config
 * 
 * @author liutao
 * 
 */
@Configuration
public class WebClientConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);
	
    /**
     * 是否ssl验证开关
     */
    @Value("${webclient.ssl.vaild}")
    private boolean sslVaild;

    @Bean
    public WebClient webClient() {
    	ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
    	if(!sslVaild) {
			SslContext sslContext = null;
		    try {
		        sslContext = SslContextBuilder
		                .forClient()
		                .trustManager(InsecureTrustManagerFactory.INSTANCE)
		                .build();
			} catch (SSLException e) {
				LOGGER.error("ssl exception:{}",e);
			}
		
		    SslContext finalSslContext = sslContext;
		    HttpClient httpClient = HttpClient.create()
		            .secure(sslSpec -> sslSpec.sslContext(finalSslContext));
			httpConnector = new ReactorClientHttpConnector(httpClient);
    	}
		return WebClient.builder().clientConnector(httpConnector).build();
    }

}
