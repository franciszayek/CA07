package CA05.prod.pkg.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CA05.prod.pkg.exception.StockMarketExpection;
import CA05.prod.pkg.market.api.IObserver;
import CA05.prod.pkg.market.api.ISubject;
import CA05.prod.pkg.stock.Stock;

public class MarketHistory implements IObserver {
	private ISubject subject;
	private Market market;
	private Map<String, List<Double>> history;

	public MarketHistory(Market market) {
		super();
		this.market = market;
		history = new HashMap<String, List<Double>>();
	}

	@Override
	public void setSubject(ISubject priceSetter) {
		this.subject = priceSetter;
	}

	public void startHistoryWithPrice(String symbol, Double newPrice)
			throws StockMarketExpection {
		if (!history.containsKey(symbol)) {
			List<Double> priceList = new ArrayList<Double>();
			priceList.add(newPrice);
			history.put(symbol, priceList);
		}
	}

	@Override
	public void update() {
		Stock updatedStock = (Stock) subject.getUpdate();
		if (market.getStockForSymbol(updatedStock.getSymbol()) == null) {
			return;
		}
		if (history.containsKey(updatedStock.getSymbol())) {
			List<Double> priceList = history.get(updatedStock.getSymbol());
			priceList.add(updatedStock.getPrice());
			history.put(updatedStock.getSymbol(), priceList);
		} else {
			// New entry to history
			List<Double> priceList = new ArrayList<Double>();
			priceList.add(updatedStock.getPrice());
			history.put(updatedStock.getSymbol(), priceList);
		}
	}

	public ArrayList<Double> getPriceFor(String symbol) {
		if (history.containsKey(symbol)) {
			return (ArrayList<Double>) history.get(symbol);
		} else {
			return new ArrayList<Double>();
		}
	}
}
