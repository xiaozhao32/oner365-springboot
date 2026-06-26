package com.oner365.data.web.sequence.range.impl.redis;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.data.redis.autoconfigure.DataRedisProperties;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.redis.enums.RedisMode;
import com.oner365.data.redis.util.JedisUtils;
import com.oner365.data.web.sequence.range.SeqRange;
import com.oner365.data.web.sequence.range.SeqRangeMgr;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.commands.JedisCommands;

/**
 * sequence redis range
 *
 * @author zhaoyong
 */
public class RedisSeqRangeMgr implements SeqRangeMgr {

    private static final String KEY_PREFIX = "sequence_";

    private int step = 1000;

    private long stepStart = 0L;

    private volatile boolean keyAlreadyExist;

    private DataRedisProperties properties;

    @Override
    public SeqRange nextRange(String name) {
        if (!DataUtils.isEmpty(this.properties.getCluster())) {
            Set<HostAndPort> nodes = new HashSet<>();
            this.properties.getCluster().getNodes().forEach(s -> {
                HostAndPort host = HostAndPort.from(s);
                nodes.add(host);
            });
            try (JedisCluster cluster = new JedisCluster(nodes, null, this.properties.getPassword())) {
                return build(cluster, name);
            }
        } else if (!DataUtils.isEmpty(this.properties.getSentinel())) {
            try (JedisSentinelPool pool = new JedisSentinelPool(this.properties.getSentinel().getMaster(),
                    new HashSet<>(this.properties.getSentinel().getNodes()), this.properties.getPassword(),
                    this.properties.getSentinel().getPassword())) {
                Jedis jedis = pool.getResource();
                return build(jedis, name);
            }
        } else {
            try (Jedis jedis = JedisUtils.getJedis(this.properties, RedisMode.DEFAULT)) {
                return build(jedis, name);
            }
        }
    }

    private SeqRange build(JedisCommands jedis, String name) {
        if (!this.keyAlreadyExist) {
            Boolean isExists = jedis.exists(getRealKey(name));
            if (!isExists.booleanValue()) {
                jedis.setnx(getRealKey(name), String.valueOf(this.stepStart));
            }
            this.keyAlreadyExist = true;
        }
        long max = jedis.incrBy(getRealKey(name), this.step);
        long min = max - this.step + 1L;
        return new SeqRange(min, max);
    }

    @Override
    public void init() {
        // init config
    }

    private String getRealKey(String name) {
        return KEY_PREFIX + name;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public long getStepStart() {
        return this.stepStart;
    }

    public void setStepStart(long stepStart) {
        this.stepStart = stepStart;
    }

    public DataRedisProperties getProperties() {
        return properties;
    }

    public void setProperties(DataRedisProperties properties) {
        this.properties = properties;
    }

}
