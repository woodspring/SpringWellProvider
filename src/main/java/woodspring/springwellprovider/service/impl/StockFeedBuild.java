package woodspring.springwellprovider.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import woodspring.springwellprovider.entity.StockFeed;
import woodspring.springwellprovider.service.FeedService;

@Service
public class StockFeedBuild implements FeedService{
	private final static Logger logger = LoggerFactory.getLogger(StockFeedBuild.class);
			List<String> symbolList;
			List<Double> priceList;
			List<String> sideList;
	
	public static int StockNumber;
			
	public StockFeedBuild() {
		symbolList = Arrays.asList("IBM", "AAPL", "DAL", "C","A", "B", "XYF", "TTC", "OCC", "Z");
		priceList = Arrays.asList( 116.66, 157.87, 40.12, 76.11, 162.16, 45.43, 4.6900, 104.85, 4.1200, 57.09 );
		sideList = Arrays.asList("BUY", "SELL");
		StockNumber = symbolList.size();
	}
	

	@Override
	public StockFeed generateStock(int index) {
		String sym = symbolList.get(index);
		Double dP = ThreadLocalRandom.current().nextDouble( priceList.get(index ) * 0.12);
		dP = priceList.get(index ) + ((ThreadLocalRandom.current().nextBoolean()) ? dP : (-1.0)*dP);
		String side = ThreadLocalRandom.current().nextBoolean() ? sideList.get(0) :sideList.get(1);
		StockFeed stock = new StockFeed(System.currentTimeMillis(), sym, dP, side );
		return stock;
	}

}
