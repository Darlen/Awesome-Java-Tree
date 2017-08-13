package kafka.service.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/8/13 15:00
 * @Version 1.0
 */
public class KafkaConsumerServiceImpl implements MessageListener<Integer,String> {
    private Logger logger = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);
    @Override
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {
        logger.info("消费者接受消息：value = "+consumerRecord.value()+"; consumerRecord="+ consumerRecord);
    }
}
