package woodspring.springwellprovider.service;

import java.util.List;

public interface ProduceService<T> {
	
	boolean init(String topicName);
	
	String sendMessage(T message);
	
	String sendMessages(T message, int msgNo);
	
	String sendMessageList(List<T> msgList);

}
