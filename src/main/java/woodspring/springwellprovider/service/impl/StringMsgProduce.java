package woodspring.springwellprovider.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import woodspring.springwellprovider.entity.StockFeed;
import woodspring.springwellprovider.produce.MessageProduce;
import woodspring.springwellprovider.service.FeedService;
import woodspring.springwellprovider.service.ProduceService;


@Service
public class StringMsgProduce implements ProduceService<String> {
	private static final Logger logger = LoggerFactory.getLogger(StringMsgProduce.class);
	
	@Autowired
	MessageProduce msgProducer;
	
	@Autowired 
	FeedService stockFeed;

	@Override
	public java.lang.String sendMessage(String message) {
		String thisMsg = message;
		StringBuffer strBuf = new StringBuffer();
		String retStr = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(20))
				.limit(10)
				.distinct()
				.mapToObj(ind -> {
					String aStr = msgProducer.sendMsg(ind, message);
					return aStr;
				})
			    .collect(Collectors.joining(", ", "[", "]"));
		return retStr;
	}

	@Override
	public java.lang.String sendMessages(String message, int msgNo) {
		StringBuffer strBuf = new StringBuffer();
		String retStr= IntStream.range(0, msgNo)
			.mapToObj(ind -> {
				String aStr = msgProducer.sendMsg(ind, message);
				return aStr;
			})
			.collect(Collectors.joining(", ", "[", "]"));
		return retStr;
	}

	@Override
	public String sendMessageList(List<String> msgList) {
		String retStr = msgList.stream()
						.map( msg -> msgProducer.sendMsg(msg) )
						.collect(Collectors.joining(", ", "{", "}"));
		return retStr;
	}
	
	@Override
	public String sendStockFeed(int stockNo) {
		List<StockFeed> retFeeds = new ArrayList<>();
		//List<StockFeed> retFeeds = 
				IntStream.generate( () -> ThreadLocalRandom.current().nextInt(StockFeedBuild.StockNumber))
										.limit(stockNo)
										.mapToObj( item -> stockFeed.generateStock( item))

										.forEach( stock -> retFeeds.add( msgProducer.sendFeed(stock)));
				StringBuffer strBuf = new StringBuffer();
				for ( StockFeed item : retFeeds) strBuf.append( item.toString()+ "||");
		//String retStr = retFeeds.stream().collect(Collectors.joining(", ", "{", "}"));
		return strBuf.toString();
	}

	@Override
	public boolean init(String topicName) {
		msgProducer.init(topicName, 10);
		return false;
	}

}
