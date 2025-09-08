package com.tungpd.core.processors;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.cloud.stream.annotation.StreamListener;

@RequiredArgsConstructor
//@EnableBinding(TestTopicSink.Processor.class)
public class TestTopicSink {
    static final Logger LOGGER = LoggerFactory.getLogger(TestTopicSink.class);

    @StreamListener(Processor.INPUT)
    public void receive(Message<String> message) {
        LOGGER.info("TEST TOPIC RECEIVE PAYLOAD:" + message.getPayload());

    }

    public interface Processor {
        String INPUT = "TestTopicSink"; // NOSONAR

        @Input(INPUT)
        SubscribableChannel input(); // NOSONAR
    }
}
