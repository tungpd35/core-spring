//package com.tungpd.techproject.config;
//
//import com.tungpd.techproject.services.KafkaProducerService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.listener.ContainerProperties;
//import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
//import org.springframework.kafka.listener.DefaultErrorHandler;
//import org.springframework.util.backoff.FixedBackOff;
//
//@Configuration
//public class KafkaConfig {
////    @Bean
////    public DefaultErrorHandler errorHandler() {
////        // retry 3 lần, delay 2s giữa các lần
////        FixedBackOff fixedBackOff = new FixedBackOff(2000L, 3);
////        return new DefaultErrorHandler((record, exception) -> {
////            System.out.println("❌ Failed after retries: " + record.value());
////        }, fixedBackOff);
////    }
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> batchFactory(
//            ConsumerFactory<String, String> consumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//
//        // Bật batch listener
//        factory.setBatchListener(true);
//
//        // Ack mode = commit sau khi xử lý batch
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);
//
//        return factory;
//    }
//
//    @Bean
//    public KafkaProducerService kafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
//        return new KafkaProducerService(kafkaTemplate);
//    }
//    @Bean
//    public DeadLetterPublishingRecoverer recoverer(KafkaTemplate<Object, Object> template) {
//        return new DeadLetterPublishingRecoverer(template);
//    }
//
//    @Bean
//    public DefaultErrorHandler errorHandler(DeadLetterPublishingRecoverer recoverer) {
//        return new DefaultErrorHandler(recoverer);
//    }
//}
