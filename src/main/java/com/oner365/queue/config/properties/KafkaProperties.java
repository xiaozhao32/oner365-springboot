package com.oner365.queue.config.properties;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

/**
 * Kafka配置类
 *
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    /**
     * bootstrap-servers
     */
    private @Nullable String bootstrapServers;

    private final Listener listener = new Listener();

    private final Producer producer = new Producer();

    private final Consumer consumer = new Consumer();

    public static class Listener {

        /**
         * ack-mode
         */
        private AckMode ackMode = AckMode.RECORD;

        /**
         * concurrency
         */
        private int concurrency = 3;

        /**
         * poll-timeout
         */
        private int pollTimeout = 3000;

        /**
         * missing-topics-fatal
         */
        private boolean missingTopicsFatal = true;

        public AckMode getAckMode() {
            return ackMode;
        }

        public void setAckMode(AckMode ackMode) {
            this.ackMode = ackMode;
        }

        public int getConcurrency() {
            return concurrency;
        }

        public void setConcurrency(int concurrency) {
            this.concurrency = concurrency;
        }

        public int getPollTimeout() {
            return pollTimeout;
        }

        public void setPollTimeout(int pollTimeout) {
            this.pollTimeout = pollTimeout;
        }

        public boolean getMissingTopicsFatal() {
            return missingTopicsFatal;
        }

        public void setMissingTopicsFatal(boolean missingTopicsFatal) {
            this.missingTopicsFatal = missingTopicsFatal;
        }

    }

    public static class Producer {

        /**
         * Producer acks
         */
        private String acks = "-1";

        /**
         * Producer retries
         */
        private int retries = 3;

        /**
         * Producer batch-size
         */
        private int batchSize = 16384;

        /**
         * Producer buffer-memory
         */
        private int bufferMemory = 33554432;

        /**
         * Producer linger-ms
         */
        private int lingerMs = 5;

        public String getAcks() {
            return acks;
        }

        public void setAcks(String acks) {
            this.acks = acks;
        }

        public int getRetries() {
            return retries;
        }

        public void setRetries(int retries) {
            this.retries = retries;
        }

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }

        public int getBufferMemory() {
            return bufferMemory;
        }

        public void setBufferMemory(int bufferMemory) {
            this.bufferMemory = bufferMemory;
        }

        public int getLingerMs() {
            return lingerMs;
        }

        public void setLingerMs(int lingerMs) {
            this.lingerMs = lingerMs;
        }

    }

    public static class Consumer {

        /**
         * Consumer group-id
         */
        private @Nullable String groupId;

        /**
         * Consumer auto-offset-reset
         */
        private String autoOffsetReset = "latest";

        /**
         * Consumer enable-auto-commit
         */
        private boolean enableAutoCommit = true;

        /**
         * Consumer heartbeat.interval.ms
         */
        private int heartbeatIntervalMs = 3000;

        /**
         * Consumer max-poll-records
         */
        private int maxPollRecords = 100;

        /**
         * Consumer session-timeout-ms
         */
        private int sessionTimeoutMs = 45000;

        /**
         * Consumer request-timeout-ms
         */
        private int requestTimeoutMs = 30000;

        /**
         * Consumer max-poll-interval-ms
         */
        private int maxPollIntervalMs = 600000;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getAutoOffsetReset() {
            return autoOffsetReset;
        }

        public void setAutoOffsetReset(String autoOffsetReset) {
            this.autoOffsetReset = autoOffsetReset;
        }

        public boolean getEnableAutoCommit() {
            return enableAutoCommit;
        }

        public void setEnableAutoCommit(boolean enableAutoCommit) {
            this.enableAutoCommit = enableAutoCommit;
        }

        public int getHeartbeatIntervalMs() {
            return heartbeatIntervalMs;
        }

        public void setHeartbeatIntervalMs(int heartbeatIntervalMs) {
            this.heartbeatIntervalMs = heartbeatIntervalMs;
        }

        public int getMaxPollRecords() {
            return maxPollRecords;
        }

        public void setMaxPollRecords(int maxPollRecords) {
            this.maxPollRecords = maxPollRecords;
        }

        public int getSessionTimeoutMs() {
            return sessionTimeoutMs;
        }

        public void setSessionTimeoutMs(int sessionTimeoutMs) {
            this.sessionTimeoutMs = sessionTimeoutMs;
        }

        public int getRequestTimeoutMs() {
            return requestTimeoutMs;
        }

        public void setRequestTimeoutMs(int requestTimeoutMs) {
            this.requestTimeoutMs = requestTimeoutMs;
        }

        public int getMaxPollIntervalMs() {
            return maxPollIntervalMs;
        }

        public void setMaxPollIntervalMs(int maxPollIntervalMs) {
            this.maxPollIntervalMs = maxPollIntervalMs;
        }

    }

    public KafkaProperties() {
        super();
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public Listener getListener() {
        return listener;
    }

    public Producer getProducer() {
        return producer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

}
