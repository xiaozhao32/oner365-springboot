package com.oner365.data.redis.config;

import java.time.Duration;
import java.util.List;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.ConstantDelay;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.data.redis.autoconfigure.DataRedisProperties;
import org.springframework.boot.data.redis.autoconfigure.DataRedisProperties.Pool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.redis.enums.RedisMode;

import jakarta.annotation.Resource;

/**
 * Redission Config
 *
 * @author zhaoyong
 */
@Configuration
public class RedissionConfig {

    @Resource
    private DataRedisProperties redisProperties;

    @Resource
    private RedisCacheProperties redisCacheProperties;

    private static final String REDIS_PROFIX = "redis://";

    @Bean
    RedissonClient redissonClient() {
        Config config = new Config();

        if (RedisMode.CLUSTER.equals(redisCacheProperties.getMode())) {
            ClusterServersConfig clusterConfig = config.useClusterServers();
            buildClusterConfig(config, clusterConfig);
        }
        else if (RedisMode.SENTINEL.equals(redisCacheProperties.getMode())) {
            SentinelServersConfig sentinelConfig = config.useSentinelServers();
            buildSentinelConfig(config, sentinelConfig);
        }
        else {
            SingleServerConfig singleConfig = config.useSingleServer();
            buildSingleConfig(config, singleConfig);
        }

        return Redisson.create(config);
    }

    private void buildClusterConfig(Config config, ClusterServersConfig clusterConfig) {
        List<String> addresses = redisProperties.getCluster()
            .getNodes()
            .stream()
            .map(addr -> addr.startsWith(REDIS_PROFIX) ? addr : REDIS_PROFIX + addr)
            .toList();
        clusterConfig.setNodeAddresses(addresses);
        if (!DataUtils.isEmpty(redisProperties.getPassword())) {
            config.setPassword(redisProperties.getPassword());
        }
        // 设置连接池、超时等参数
        Pool pool = redisProperties.getLettuce().getPool();
        clusterConfig.setSubscriptionConnectionPoolSize(pool.getMaxIdle());
        clusterConfig.setMasterConnectionPoolSize(pool.getMaxActive());
        clusterConfig.setMasterConnectionMinimumIdleSize(pool.getMinIdle());
        clusterConfig.setSlaveConnectionPoolSize(pool.getMaxActive());
        clusterConfig.setSlaveConnectionMinimumIdleSize(pool.getMinIdle());
        clusterConfig.setRetryAttempts(3);
        clusterConfig.setRetryDelay(new ConstantDelay(Duration.ofMillis(1500)));
    }

    private void buildSentinelConfig(Config config, SentinelServersConfig sentinelConfig) {
        sentinelConfig.setMasterName(redisProperties.getSentinel().getMaster());
        List<String> addresses = redisProperties.getSentinel()
            .getNodes()
            .stream()
            .map(addr -> addr.startsWith(REDIS_PROFIX) ? addr : REDIS_PROFIX + addr)
            .toList();
        sentinelConfig.setSentinelAddresses(addresses);

        if (!DataUtils.isEmpty(redisProperties.getSentinel().getPassword())) {
            sentinelConfig.setSentinelPassword(redisProperties.getSentinel().getPassword());
        }
        if (!DataUtils.isEmpty(redisProperties.getPassword())) {
            config.setPassword(redisProperties.getPassword());
        }
        if (redisProperties.getDatabase() != 0) {
            sentinelConfig.setDatabase(redisProperties.getDatabase());
        }
        // 设置连接池、超时等参数
        Pool pool = redisProperties.getLettuce().getPool();
        sentinelConfig.setSubscriptionConnectionPoolSize(pool.getMaxIdle());
        sentinelConfig.setMasterConnectionPoolSize(pool.getMaxActive());
        sentinelConfig.setMasterConnectionMinimumIdleSize(pool.getMinIdle());
        sentinelConfig.setSlaveConnectionPoolSize(pool.getMaxActive());
        sentinelConfig.setSlaveConnectionMinimumIdleSize(pool.getMinIdle());
        sentinelConfig.setRetryAttempts(3);
        sentinelConfig.setRetryDelay(new ConstantDelay(Duration.ofMillis(1500)));
    }

    private void buildSingleConfig(Config config, SingleServerConfig singleConfig) {
        singleConfig.setAddress(REDIS_PROFIX + redisProperties.getHost() + ":" + redisProperties.getPort())
            .setDatabase(redisProperties.getDatabase());
        if (!DataUtils.isEmpty(redisProperties.getPassword())) {
            config.setPassword(redisProperties.getPassword());
        }
    }

}
