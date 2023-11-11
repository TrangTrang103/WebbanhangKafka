package com.doan.kafka.service;

import com.doan.kafka.constant.ApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(List<String> message) {
        logger.info(String.format("Message sent -> %s", message));
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(ApplicationConstant.TOPIC_NAME_2, String.valueOf(message));
        System.out.println(send);
    }
}
