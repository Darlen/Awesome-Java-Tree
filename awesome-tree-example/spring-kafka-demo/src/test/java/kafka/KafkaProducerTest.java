package kafka;

import kafka.service.producer.KafkaProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/8/13 15:09
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class KafkaProducerTest {

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * 向kafka里写数据.<br/>
     * @author miaohongbin
     * Date:2016年6月24日下午6:22:58
     */
    @Test
    public void sendDefaultMsgTest() throws InterruptedException {
        kafkaProducerService.sendDefautMessage("hello world");

//        Thread.sleep(10000000);
    }

    /**
     * 向kafka里写数据.<br/>
     * @author miaohongbin
     * Date:2016年6月24日下午6:22:58
     */
    @Test
    public void sendMsgTest() throws InterruptedException {
        kafkaProducerService.sendMessage("hello world-tree");

        Thread.sleep(10000000);
    }
}
