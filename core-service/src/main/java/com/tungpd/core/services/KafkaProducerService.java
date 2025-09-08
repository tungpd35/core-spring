package com.tungpd.core.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    /**
     * Gửi message với partition key (ví dụ: orderId, userId...)
     *
     * @param topic   tên topic
     * @param key     partition key (nếu null thì Kafka sẽ tự phân phối)
     * @param message nội dung message
     */
    public void sendMessage(String topic, String key, String message) {
        if (key != null) {
            // Gửi message với key -> đảm bảo cùng key vào cùng partition
            kafkaTemplate.send(topic, key, message);
            System.out.println("📤 Sent message with key=" + key + " -> " + message);
        } else {
            // Không có key -> Kafka phân phối round-robin/sticky
            kafkaTemplate.send(topic, message);
            System.out.println("📤 Sent message with no key -> " + message);
        }
    }
}
