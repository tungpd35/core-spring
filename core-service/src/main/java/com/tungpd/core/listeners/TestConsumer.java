//package com.tungpd.techproject.listeners;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.Message;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
////@Component
//public class TestConsumer {
//
//    @KafkaListener(topics = "test-topic", groupId = "my-group", containerFactory = "batchFactory")
//    public void listen(Message<List<String>>  message) { // cơ chế poll batch message
//        List<String> data = message.getPayload();
//        List<Long> offsetList = (List<Long>) message.getHeaders().get(KafkaHeaders.OFFSET);
//        List<Integer> partitionList = (List<Integer>) message.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION);
//        System.out.println("offsetList: " + offsetList);
//        System.out.println("partitionList: " + partitionList);
//        int i = 1;
//        for (String msg : data) {
//            System.out.println("📩 Received message "+ i +": " + msg);
//            i++;
//            throw new RuntimeException("test");
//            //if (i == 3) throw new RuntimeException("test");
//        }
//    }
//
//}
