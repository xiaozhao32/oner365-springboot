package com.oner365.data.redis.config;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.TimeZone;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.boot.data.redis.autoconfigure.DataRedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oner365.data.commons.config.properties.AccessTokenProperties;
import com.oner365.data.commons.jackson.JavaTimeModule;
import com.oner365.data.commons.util.DataUtils;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import redis.clients.jedis.Jedis;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * Redis Cache Config
 *
 * @author zhaoyong
 *
 */
@Configuration
@EnableCaching
@ConditionalOnClass({ JedisConnection.class, RedisOperations.class, Jedis.class })
@EnableConfigurationProperties(DataRedisProperties.class)
@AutoConfigureAfter(DataRedisAutoConfiguration.class)
public class RedisCacheConfig {

    private final AccessTokenProperties accessTokenProperties;

    public RedisCacheConfig(AccessTokenProperties accessTokenProperties) {
        this.accessTokenProperties = accessTokenProperties;
    }

    @Bean
    CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(accessTokenProperties.getExpireTime()))
            .transactionAware()
            .build();
    }

    @Bean
    RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        
        JsonMapper jsonMapper = JsonMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .changeDefaultPropertyInclusion(
                        old -> JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL))
                .defaultTimeZone(TimeZone.getDefault())
                .addModules(new JavaTimeModule())
                .build();
        
        JacksonJsonRedisSerializer<Object> serializer = new JacksonJsonRedisSerializer<>(jsonMapper, Object.class);
        
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    GenericObjectPoolConfig<LettuceClientConfiguration> poolConfig(DataRedisProperties redisProperties) {
        GenericObjectPoolConfig<LettuceClientConfiguration> config = new GenericObjectPoolConfig<>();
        config.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        config.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        config.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        config.setMaxWait(redisProperties.getLettuce().getPool().getMaxWait());
        return config;
    }

    @Bean
    @ConditionalOnProperty(value = { "spring.data.redis.mode" }, havingValue = "cluster")
    RedisConnectionFactory redisClusterConnectionFactory(DataRedisProperties redisProperties) {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(
                redisProperties.getCluster().getNodes());
        if (!DataUtils.isEmpty(redisProperties.getPassword())) {
            redisClusterConfiguration.setPassword(redisProperties.getPassword());
        }

        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
            .enablePeriodicRefresh()
            .refreshPeriod(Duration.ofSeconds(5L))
            .build();
        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
            .topologyRefreshOptions(clusterTopologyRefreshOptions)
            .build();
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
            .readFrom(ReadFrom.REPLICA_PREFERRED)
            .clientOptions(clusterClientOptions)
            .build();
        return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
    }

    @Bean
    @ConditionalOnProperty(value = { "spring.data.redis.mode" }, havingValue = "sentinel")
    RedisConnectionFactory redisSentinelConnectionFactory(DataRedisProperties redisProperties,
            @Qualifier("poolConfig") GenericObjectPoolConfig<LettuceClientConfiguration> poolConfig) {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration(
                redisProperties.getSentinel().getMaster(), new HashSet<>(redisProperties.getSentinel().getNodes()));
        if (!DataUtils.isEmpty(redisProperties.getSentinel().getPassword())) {
            redisSentinelConfiguration.setSentinelPassword(redisProperties.getSentinel().getPassword());
        }
        if (!DataUtils.isEmpty(redisProperties.getPassword())) {
            redisSentinelConfiguration.setPassword(redisProperties.getPassword());
        }
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration
            .defaultConfiguration();
        return new LettuceConnectionFactory(redisSentinelConfiguration, lettuceClientConfiguration);
    }

}
