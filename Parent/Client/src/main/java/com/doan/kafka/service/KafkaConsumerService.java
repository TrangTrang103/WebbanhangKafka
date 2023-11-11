package com.doan.kafka.service;

import com.doan.kafka.constant.ApplicationConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableScheduling
@EnableAsync
@Service
public class KafkaConsumerService {

//    @Autowired
//    private EmployeeRepo employeeRepo;
public String message;
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
//    @Scheduled(fixedRate = 50000)
    @KafkaListener(topics = ApplicationConstant.TOPIC_NAME_2, groupId = ApplicationConstant.GROUP_ID)
    public void nhan(String message){
        this.message = message;
    }


    @Scheduled(fixedRate = 5*24*60*60*1000)
    public void consume()  {
        logger.info(String.format("Da nhan duoc tin nhan"));
//        List<Employee> e = Arrays.asList(new GsonBuilder().create().fromJson(message, Employee[].class));
//        List<Employee> e = new ArrayList<>(Arrays.asList(new GsonBuilder().create().fromJson(message, Employee[].class)));
//        List<Employee> e = Arrays.asList(new GsonBuilder().create().fromJson(message, Employee[].class));
//        e.forEach(employee ->{
//            employee.setName(employee.getName());
//            employeeRepo.save(employee);
//        });
        Gson gson = new Gson();
        List<String> employeeList = gson.fromJson(message, new TypeToken<List<String>>(){}.getType());

//
//        employeeList.forEach(employee ->{
//            employee.setName(employee.getName());
//            em.setName(employee);
//            employeeRepo.save(em);
//        });
//        for (String s: employeeList) {
//            Employee em = new Employee();
//            em.setName(s);
//            employeeRepo.save(em);
//        }
        logger.info("Da luu thong tin");

    }
}
