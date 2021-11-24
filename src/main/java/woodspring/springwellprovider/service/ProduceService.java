package woodspring.springwellprovider.service;

import java.util.ArrayList;
import java.util.List;

import woodspring.springwellprovider.entity.StockFeed;

public interface ProduceService<T> {
	
	boolean init(String topicName);
	
	String sendMessage(T message);
	
	String sendMessages(T message, int msgNo);
	
	String sendMessageList(List<T> msgList);
	
	String sendStockFeed(int stockNo);
	//ArrayList<StockFeed> sendStockFeed(int stockNo);

}
