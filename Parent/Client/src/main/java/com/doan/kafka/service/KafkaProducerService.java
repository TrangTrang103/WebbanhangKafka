package com.doan.kafka.service;

import com.doan.kafka.constant.ApplicationConstant;
import com.doan.mutual.entity.clickDetailList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, clickDetailList> kafkaTemplate1;
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    public void sendClick(clickDetailList click) {
        logger.info(String.format("Message sent Shipping-> %s", click));
        kafkaTemplate1.send(ApplicationConstant.TOPIC,click);
        logger.info("Published successfully 1");
    }

//    public void sendShip(String message){
//        logger.info(String.format("Message sent Shipping-> %s", message));
//        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(ApplicationConstant.TOPIC_NAME_2, message);
//        System.out.println(send);
//    }
}
