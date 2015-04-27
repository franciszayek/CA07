package CA05.prod.pkg.util;

import java.util.ArrayList;

import CA05.prod.pkg.order.BuyOrder;
import CA05.prod.pkg.order.Order;
import CA05.prod.pkg.order.SellOrder;

// Use these if you want

public class OrderUtility {
	public static boolean isAlreadyPresent(ArrayList<Order> ordersPlaced,
			Order newOrder) {
		for (Order orderPlaced : ordersPlaced) {
			if (((orderPlaced instanceof BuyOrder) && (newOrder instanceof BuyOrder))
					|| ((orderPlaced instanceof SellOrder) && (newOrder instanceof SellOrder))) {
				if (orderPlaced.getStockSymbol().equals(
						newOrder.getStockSymbol())) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean owns(ArrayList<Order> position, String symbol) {
		for (Order stock : position) {
			if (stock.getStockSymbol().equals(symbol)) {
				return true;
			}
		}
		return false;
	}

	public static Order findAndExtractOrder(ArrayList<Order> position,
			String symbol) {
		for (Order stock : position) {
			if (stock.getStockSymbol().equals(symbol)) {
				position.remove(stock);
				return stock;
			}
		}
		return null;
	}

	public static int ownedQuantity(ArrayList<Order> position, String symbol) {
		int ownedQuantity = 0;
		for (Order stock : position) {
			if (stock.getStockSymbol().equals(symbol)) {
				ownedQuantity += stock.getSize();
			}
		}
		return ownedQuantity;
	}

}
