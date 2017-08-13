package kafka.controller;

import kafka.service.producer.KafkaProducerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/8/13 14:47
 * @Version 1.0
 */
@Controller
@RequestMapping("/kafka")
public class KafkaProducerController {

        @Resource(name = "kafkaProducerService")
        private KafkaProducerService kafkaProducerService;
        @RequestMapping("/sendMessage")
        public void sendMessage(){
            System.out.println("test spring kafka");
            kafkaProducerService.sendDefautMessage("this is a test message");
        }
}
