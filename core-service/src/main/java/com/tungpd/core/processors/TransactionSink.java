package com.tungpd.core.processors;

import com.tungpd.core.dto.Transaction;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;

@RequiredArgsConstructor
//@EnableBinding(TransactionSink.Processor.class)
public class TransactionSink {
    static final Logger LOGGER = LoggerFactory.getLogger(TransactionSink.class);

    @StreamListener(TransactionSink.Processor.INPUT)
    public void receive(Message<Transaction> message) {
        LOGGER.info("Transaction high value:" + message.getPayload());
    }

    public interface Processor {
        String INPUT = "HighValueTransaction"; // NOSONAR

        @Input(INPUT)
        SubscribableChannel input(); // NOSONAR
    }
}
