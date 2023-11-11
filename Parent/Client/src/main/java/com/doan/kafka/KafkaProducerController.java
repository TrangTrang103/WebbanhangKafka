package com.doan.kafka;

import com.doan.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaProducerController {

    @Autowired
    HttpSession session;
    private final KafkaProducerService kafkaProducerService;

    public KafkaProducerController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("add")
    public ResponseEntity<?> addEmployee(){
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
        kafkaProducerService.sendMessage(messages);
       messages.removeAll(messages);
        return ResponseEntity.ok("Message sent to kafka topic");
    }
}
