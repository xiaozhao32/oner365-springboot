package com.oner365.monitor.config;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.oner365.monitor.config.properties.WebClientProperties;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import jakarta.annotation.Resource;
import reactor.netty.http.client.HttpClient;

/**
 * WebClient Config
 *
 * @author liutao
 *
 */
@Configuration
@EnableConfigurationProperties({ WebClientProperties.class })
public class WebClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);

    @Resource
    private WebClientProperties properties;

    @Bean
    WebClient webClient() {
        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        if (!properties.getSsl().isEnable()) {
            httpConnector = new ReactorClientHttpConnector(HttpClient.create().secure(sslSpec -> {
                try {
                    sslSpec.sslContext(
                            SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build());
                }
                catch (SSLException e) {
                    LOGGER.error("webClient error:", e);
                }
            }));
        }
        return WebClient.builder()
            .clientConnector(httpConnector)
            .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(Integer.parseInt(properties.getMaxInMemorySize().toBytes() + "")))
                .build())
            .build();
    }

}
