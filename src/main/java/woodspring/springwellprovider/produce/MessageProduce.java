package woodspring.springwellprovider.produce;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class MessageProduce {
	private static Logger logger = LoggerFactory.getLogger(MessageProduce.class);
	
	private KafkaTemplate<String, Object> kafkaTemp;
	private final String topicName;
	private final int msgPerRequest;
	private CountDownLatch latch;
	
	public MessageProduce(@Value("${tpd.topic-name}") final String topicName_,
			@Value("${tpd.message-per-request}") final int messagePerRequest_) {
		this.topicName = topicName_;
		this.msgPerRequest = messagePerRequest_;		
		
	}
	

}
