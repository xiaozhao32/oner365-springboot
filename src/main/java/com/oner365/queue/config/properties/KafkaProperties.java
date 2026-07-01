package com.oner365.queue.config.properties;

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
    private String bootstrapServers;

    private Listener listener = new Listener();
    private Producer producer = new Producer();
    private Consumer consumer = new Consumer();

    public static class Listener {
        
        /**
         * ack-mode
         */
        private AckMode ackMode;
        
        /**
         * concurrency
         */
        private int concurrency;
        
        /**
         * poll-timeout
         */
        private int pollTimeout;
        
        /**
         * missing-topics-fatal
         */
        private boolean missingTopicsFatal;

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
        private String acks;

        /**
         * Producer retries
         */
        private int retries;
        
        /**
         * Producer batch-size
         */
        private int batchSize;
        
        /**
         * Producer buffer-memory
         */
        private int bufferMemory;
        
        /**
         * Producer linger-ms
         */
        private int lingerMs;

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
        private String groupId;

        /**
         * Consumer auto-offset-reset
         */
        private String autoOffsetReset;

        /**
         * Consumer enable-auto-commit
         */
        private boolean enableAutoCommit;
        
        /**
         * Consumer heartbeat.interval.ms
         */
        private int heartbeatIntervalMs;
        
        /**
         * Consumer max-poll-records
         */
        private int maxPollRecords;
        
        /**
         * Consumer session-timeout-ms
         */
        private int sessionTimeoutMs;
        
        /**
         * Consumer request-timeout-ms
         */
        private int requestTimeoutMs;
        
        /**
         * Consumer max-poll-interval-ms
         */
        private int maxPollIntervalMs;

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

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

}
