package com.tungpd.core.controllers;

import com.tungpd.core.services.KafkaProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProducerController {
    private final KafkaProducerService producerService;

    public ProducerController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/send")
    public String send(@RequestParam String orderId, @RequestParam String message) {
        producerService.sendMessage("test-topic", orderId, message);
        return "✅ Message sent with orderId=" + orderId;
    }

//    @GetMapping("/kafka-stream")
//    public String ksql() {
//        Properties props = new Properties();
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.115:9093");
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-streams-app"); // 🔥 bắt buộc
//
//// Security config
//        props.put("security.protocol", "SASL_PLAINTEXT");
//        props.put("sasl.mechanism", "PLAIN");
//        props.put("sasl.jaas.config",
//                "org.apache.kafka.common.security.plain.PlainLoginModule required " +
//                        "username=\"admin\" password=\"admin-secret\";");
//// Serde default
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, org.apache.kafka.common.serialization.Serdes.String().getClass());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, org.apache.kafka.common.serialization.Serdes.String().getClass());
//        StreamsBuilder builder = new StreamsBuilder();
//
//        // Đọc dữ liệu từ topic
//        KStream<String, String> orders = builder.stream("test-topic");
//
//        KStream<String, String> bigOrders = orders
//                .filter((key, value) -> {
//                    try {
//                        System.out.println(value);
//                        if (value.equals("error"))  return true;
//                        return false;
//                    } catch (Exception e) {
//                        return false;
//                    }
//                });
//
//        // Ghi vào topic "big-orders"
//        bigOrders.to("error-test");
//
//        // Build topology và chạy Streams
//        KafkaStreams streams = new KafkaStreams(builder.build(), props);
//        // Bắt đầu chạy
//        streams.start();
//
//        // Shutdown hook để dừng app khi Ctrl+C
//        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
//        return "ok";
//    }
}
