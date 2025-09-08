package com.tungpd.core.config;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class KafkaConsumerInterceptor implements ConsumerInterceptor {
    private static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerInterceptor.class);

    @Override
    public ConsumerRecords onConsume(ConsumerRecords consumerRecords) {
        LOGGER.info("Consumer Records size: {}", consumerRecords.count());
        return consumerRecords;
    }

    @Override
    public void close() {
    }

    @Override
    public void onCommit(Map map) {
        LOGGER.info("on Commit: {}", map);
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
