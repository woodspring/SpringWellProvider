package woodspring.springwellprovider.produce;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import woodspring.springwellprovider.config.KafkaProviderConfig;


@Component
public class MessageProduce {
	private static Logger logger = LoggerFactory.getLogger(MessageProduce.class);
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTmp;	

	private String topicName;
	private int msgPerRequest;
	private CountDownLatch latch;
	
	public MessageProduce(@Value("${tpd.topic-name}") final String topicName_,
			@Value("${tpd.message-per-request}") final int messagePerRequest_) {
		this.topicName = topicName_;
		this.msgPerRequest = messagePerRequest_;

	}	
	
	public boolean init(String topicName_, int msgPerRequest_) {
		this.topicName = topicName_;
	this.msgPerRequest = msgPerRequest_;
		return true;
	}
	
	public String sendMsg(int msgInd, String message) {
		latch = new CountDownLatch( msgPerRequest);
		StringBuffer strBuf = new StringBuffer();
		
		try {	
			String msgSent = String.format("%s-->%d", message, msgInd);
			logger.info("topicName:"+topicName+"   -------------------------theMsg:"+ msgSent);
			this.kafkaTmp.send(this.topicName, msgSent );			
			latch.await(100, TimeUnit.MICROSECONDS );
			strBuf.append("send message:"+ message +" on "+ msgInd);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			strBuf.append("InterruptedException,  on "+ msgInd +"  "+ message);
		}
		return strBuf.toString();		
	}
	
	public String sendMsg(String message) {
		latch = new CountDownLatch( msgPerRequest);
		StringBuffer strBuf = new StringBuffer();
		int msgInd = ThreadLocalRandom.current().nextInt(1000);
		try {	
			this.kafkaTmp.send(this.topicName, String.format("%s%d", message, msgInd), String.format("%d message: %s", message, msgInd) );			
			latch.await(60, TimeUnit.SECONDS);
			strBuf.append("send message:"+ message +" on "+ msgInd);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			strBuf.append("InterruptedException,  on "+ msgInd +"  "+ message);
		}
		return strBuf.toString();		
	}

}
