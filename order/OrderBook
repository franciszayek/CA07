package CA05.prod.pkg.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import CA05.prod.pkg.exception.StockMarketExpection;
import CA05.prod.pkg.market.Market;
import CA05.prod.pkg.market.api.PriceSetter;
import CA05.prod.pkg.stock.Stock;

public class OrderBook {
	Market m;
	HashMap<String, ArrayList<Order>> buyOrders;
	HashMap<String, ArrayList<Order>> sellOrders;

	public OrderBook(Market m) {
		this.m = m;
		buyOrders = new HashMap<String, ArrayList<Order>>();
		sellOrders = new HashMap<String, ArrayList<Order>>();
	}

	public void addToOrderBook(Order order) {
		// Populate the buyOrders and sellOrders data structures, whichever
		// appropriate
		if (order.getType() == OrderType.BUY) {
			if (buyOrders.get(order.getStockSymbol()) == null) {
				ArrayList<Order> newOrderArray = new ArrayList<Order>();
				newOrderArray.add(order);
				buyOrders.put(order.getStockSymbol(), newOrderArray);
			} else
				buyOrders.get(order.getStockSymbol()).add(order);
		} else if (sellOrders.get(order.getStockSymbol()) == null) {
			ArrayList<Order> newOrderArray = new ArrayList<Order>();
			newOrderArray.add(order);
			sellOrders.put(order.getStockSymbol(), newOrderArray);
		} else
			sellOrders.get(order.getStockSymbol()).add(order);
	}

	public void trade() {
		// Complete the trading.
		// 1. Follow and create the orderbook data representation (see spec)
		// 2. Find the matching price
		// 3. Update the stocks price in the market using the PriceSetter.
		// Note that PriceSetter follows the Observer pattern. Use the pattern.
		// 4. Remove the traded orders from the orderbook
		// 5. Delegate to trader that the trade has been made, so that the
		// trader's orders can be placed to his possession (a trader's position
		// is the stocks he owns)
		// (Add other methods as necessary)

		BuyOrder marketBuyOrder = null;
		SellOrder marketSellOrder = null;

		// buyOrder and sellOrder same price
		for (ArrayList<Order> buyOrder : buyOrders.values()) {
			for (Order currentBuyOrder : buyOrder) {
				if (currentBuyOrder.isMarketOrder()) {
					marketBuyOrder = (BuyOrder) currentBuyOrder;
					break;
				}

			}
		}

		for (ArrayList<Order> sellOrder : sellOrders.values()) {
			for (Order currentSellOrder : sellOrder) {
				if (currentSellOrder.isMarketOrder()) {
					marketSellOrder = (SellOrder) currentSellOrder;
					break;
				}
			}
		}

		int marketBuyOrderSize = marketBuyOrder.getSize();
		int marketSellOrderSize = marketSellOrder.getSize();

		List<ArrayList<Order>> buyOrdersFakeList = new ArrayList(
				buyOrders.values());
		List<Order> buyOrdersList = new ArrayList(buyOrdersFakeList.get(0));
		Collections.sort(buyOrdersList, new OrderPrices());
		Collections.reverse(buyOrdersList);
		for (int i = 0; i < buyOrdersList.size(); i++) {
			if (buyOrdersList.get(i).isMarketOrder()) {
				marketBuyOrder = (BuyOrder) buyOrdersList.get(i);
				buyOrdersList.remove(i);
				break;
			}
		}

		List<ArrayList<Order>> sellOrdersFakeList = new ArrayList(
				sellOrders.values());
		List<Order> sellOrdersList = new ArrayList(sellOrdersFakeList.get(0));
		Collections.sort(sellOrdersList, new OrderPrices());
		sellOrdersList.remove(marketSellOrder);
		for (int i = 0; i < sellOrdersList.size(); i++) {
			if (sellOrdersList.get(i).isMarketOrder()) {
				marketSellOrder = (SellOrder) sellOrdersList.get(i);
				sellOrdersList.remove(i);
				break;
			}
		}

		double matchedPrice = 0.0;
		Order lastBuyOrder = null;
		int i;
		for (i = 0; i < buyOrdersList.size(); i++) {

			marketBuyOrderSize += buyOrdersList.get(i).getSize();
			marketSellOrderSize += sellOrdersList.get(i).getSize();
			
			if (marketBuyOrderSize >= marketSellOrderSize && sellOrdersList.get(i).getPrice() > buyOrdersList.get(i).getPrice() && lastBuyOrder != null)
			{
				matchedPrice = lastBuyOrder.getPrice();
				PriceSetter ps = new PriceSetter();
				ps.registerObserver(m.getMarketHistory());
				m.getMarketHistory().setSubject(ps);
				ps.setNewPrice(m, lastBuyOrder.getStockSymbol(),
						lastBuyOrder.getPrice());
				int size = buyOrdersList.size();
				try {
					while (i < size) {
						buyOrdersList.remove(i);
						sellOrdersList.remove(i);
					}
				} catch (Exception e) {
				}
				break;
			}
			else
			{
				lastBuyOrder = buyOrdersList.get(i);
			}
			

			if (marketBuyOrderSize >= marketSellOrderSize) {
				matchedPrice = buyOrdersList.get(i).getPrice();
				PriceSetter ps = new PriceSetter();
				ps.registerObserver(m.getMarketHistory());
				m.getMarketHistory().setSubject(ps);
				ps.setNewPrice(m, buyOrdersList.get(i).getStockSymbol(),
						buyOrdersList.get(i).getPrice());
				int size = buyOrdersList.size();
				i++;
				try {
					while (i < size) {
						buyOrdersList.remove(i);
						sellOrdersList.remove(i);
					}
				} catch (Exception e) {
				}
				break;
			}
			
			

		}

		buyOrdersList.add(marketBuyOrder);
		sellOrdersList.add(marketSellOrder);

		Collections.sort(buyOrdersList, new OrderPrices());
		Collections.reverse(buyOrdersList);

		Collections.sort(sellOrdersList, new OrderPrices());

		for (Order o : buyOrdersList) {
			try {
				o.getTrader().getPosition().add(m.getStockForSymbol(sellOrdersList.get(i).getStockSymbol()));
				o.getTrader().tradePerformed(o, matchedPrice);
			} catch (StockMarketExpection e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Stock stockSymbolToRemove = m.getStockForSymbol(sellOrdersList.get(i).getStockSymbol());
		for (Order o : sellOrdersList) {
			try {
				o.getTrader().getPosition().remove(stockSymbolToRemove);
				o.getTrader().tradePerformed(o, matchedPrice);
			} catch (StockMarketExpection e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		buyOrders.clear();
		sellOrders.clear();
	}

	public Market getM() {
		return m;
	}

	public void setM(Market m) {
		this.m = m;
	}

	public HashMap<String, ArrayList<Order>> getBuyOrders() {
		return buyOrders;
	}

	public void setBuyOrders(HashMap<String, ArrayList<Order>> buyOrders) {
		this.buyOrders = buyOrders;
	}

	public HashMap<String, ArrayList<Order>> getSellOrders() {
		return sellOrders;
	}

	public void setSellOrders(HashMap<String, ArrayList<Order>> sellOrders) {
		this.sellOrders = sellOrders;
	}

	class OrderPrices implements Comparator<Order> {

		@Override
		public int compare(Order o1, Order o2) {
			// TODO Auto-generated method stub
			if (o1.getPrice() > o2.getPrice())
				return 1;
			else if (o2.getPrice() > o1.getPrice())
				return -1;
			else
				return 0;
		}
	}

}
