//package com.tungpd.techproject.kStream;
//
//import com.tungpd.techproject.dto.FraudAlert;
//import com.tungpd.techproject.dto.Transaction;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.kstream.*;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.kafka.support.serializer.JsonSerde;
//import org.springframework.messaging.handler.annotation.SendTo;
//
//import java.time.Duration;
//
//@EnableBinding(TransactionStream.Processor.class)
//public class TransactionStream {
//
//    @StreamListener
//    @SendTo(Processor.OUTPUT)
//    public KStream<String, FraudAlert> transactionKStream(@Input(Processor.INPUT) KStream<String, Transaction> transactionKStream) {
//        KStream<String, Transaction> transactions = transactionKStream
//                .selectKey((key, value) -> value.getUserId());
//
//        KTable<Windowed<String>, Long> transactionCounts = transactions
//                .groupByKey(Grouped.with(
//                        Serdes.String(),
//                        new JsonSerde<>(Transaction.class) // quan trọng!
//                ))
//                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(1)))
//                .count(Materialized.with(
//                         Serdes.String(),
//                        Serdes.Long()
//                ));
//
//        return transactionCounts.toStream()
//                .filter((windowedUserId, count) -> count > 3)
//                .map((windowedUserId, count) -> {
//                    String alert = "ALERT: user " + windowedUserId.key()
//                            + " có " + count + " giao dịch trong 1 phút!";
//                    FraudAlert fraudAlert = new FraudAlert();
//                    fraudAlert.setUserId(windowedUserId.key());
//                    fraudAlert.setMessage(alert);
//                    return new KeyValue<>(windowedUserId.key(), fraudAlert);
//                });
//    }
//    interface Processor {
//        String INPUT = "transaction"; // NOSONAR
//        String OUTPUT = "fraud-alerts"; // NOSONAR
//
//        @Input(INPUT)
//        KStream<?, ?> input1(); // NOSONAR
//
//        @Output(OUTPUT)
//        KStream<?, ?> output(); // NOSONAR
//    }
//}
