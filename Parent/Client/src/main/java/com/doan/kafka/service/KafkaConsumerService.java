package com.doan.kafka.service;

import com.doan.click.clickCartRepository;
import com.doan.click.clickProductRepository;
import com.doan.click.clickSearchRepository;
import com.doan.mutual.entity.*;
import com.doan.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@EnableScheduling
@EnableAsync
@Service
public class KafkaConsumerService {

    @Autowired
    private clickProductRepository clickProduct;

    @Autowired
    private clickCartRepository clickCart;

    @Autowired
    private clickSearchRepository clickSearch;
    @Autowired
    private OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
//   @Scheduled(fixedRate = 180000)
//    @KafkaListener(topics = ApplicationConstant.TOPIC_NAME_2, groupId = ApplicationConstant.GROUP_ID)
//    public void nhan(String message){
//        this.message = message;
//    }


//    @Scheduled(fixedRate = 5*24*60*60*1000)
//    @Scheduled(fixedRate = 60000)
//    public void consumeproDetail()  {
//        logger.info(String.format("Da nhan duoc tin nhan xem san pham"));
//        Gson gson = new Gson();
//        try {
//
//            clickDetailList detail = gson.fromJson(message, clickDetailList.class);
//           detail.getLoai();
//           detail.getIdProductDetail();
//            logger.info("da luu thong tin xem san pham ");
//        }catch (NullPointerException exception){
//            exception.printStackTrace();
//        }
//
//    }


    @KafkaListener(topics = "Kafka_Example", groupId = "group_json",
            containerFactory = "clickKafkaListenerFactory")
    public void consumeJson(clickDetailList click) {
        System.out.println("Consumed JSON Message: " + click);
        try {
//            clickDetailList detail = gson.fromJson(message, clickDetailList.class);
//           detail.getLoai();
//           detail.getIdProductDetail();
            if (click.getLoai().equals("productDetail")){
                clickProduct.save(new ClickProduct(click.getIdProductDetail()));
            }else if(click.getLoai().equals("cartDetail")){
                clickCart.save(new ClickCart(click.getIdProductDetail()));
            }else{
                clickSearch.save(new ClickSearch(click.getIdProductDetail()));
            }
            logger.info("da luu thong tin xem san pham ");
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }

    }
}
