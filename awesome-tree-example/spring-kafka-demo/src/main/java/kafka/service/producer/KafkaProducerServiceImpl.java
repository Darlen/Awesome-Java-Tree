package kafka.service.producer;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/8/13 14:58
 * @Version 1.0
 */
@Service("kafkaProducerService")
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);


    @Value("tree")
    private String topic;

    @Autowired
    private KafkaTemplate<Integer,String> kafkaTemplate;
    @Override
    public void sendDefautMessage(Object object) {
        logger.info("发送消息");
        kafkaTemplate.sendDefault(String.valueOf(object));
    }

    public void sendMessage(Object object){
        logger.info("发送消息, topic = {}, Object = {}", topic, JSON.toJSON(object));
        kafkaTemplate.send(topic,String.valueOf(object));
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
