package kafka.demo.producer;

import com.alibaba.fastjson.JSON;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Description:
 *
 * 参考：https://github.com/appleappleapple/BigDataLearning
 *
 * @Author tree
 * @Date 2017/8/10 22:45
 * @Version 1.0
 */
public class KafkaProducer {
    private final Producer<String, String> producer;
    public final static String TOPIC = "jdy.application.betaD";

    private KafkaProducer() {
        Properties props = new Properties();
        // 此处配置的是kafka的端口
//        props.put("metadata.broker.list", "127.0.0.1:9092");
        props.put("metadata.broker.list", "192.168.49.206:9092,192.168.49.205:9092,192.168.49.204:9092");
//        props.put("zk.connect", "192.168.49.204:2181");
//        props.put("zookeeper.connect", "192.168.49.204:2181");


        // 配置value的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        // 配置key的序列化类
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");

        props.put("request.required.acks", "1");

        producer = new Producer<String, String>(new ProducerConfig(props));
    }

    void produce() {
        int messageNo = 0;
        final int COUNT = 10;

        while (messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = "INFO JobScheduler: " + key;
            producer.send(new KeyedMessage<String, String>(TOPIC, key, data));
            System.err.println("result = "+JSON.toJSONString(data));
            messageNo++;
        }
    }

    public static void main(String[] args) {
        new KafkaProducer().produce();
    }
}
