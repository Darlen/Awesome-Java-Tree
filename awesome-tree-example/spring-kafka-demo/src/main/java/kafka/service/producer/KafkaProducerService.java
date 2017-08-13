package kafka.service.producer;

/**
 * Description:
 *
 * @Author tree
 * @Date 2017/8/13 14:58
 * @Version 1.0
 */
public interface KafkaProducerService {
    void sendDefautMessage(Object object) ;
    void sendMessage(Object object);

}
