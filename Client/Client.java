package CA05.prod.pkg.client;

import CA05.prod.pkg.exception.StockMarketExpection;
import CA05.prod.pkg.market.Market;
import CA05.prod.pkg.market.api.IPO;
import CA05.prod.pkg.order.OrderType;
import CA05.prod.pkg.trader.Trader;

public class Client {
	public static void main(String[] args) {
		Market m = new Market("NASDAQ");
		IPO.enterNewStock(m, "SBUX", "Starbucks Corp.", 92.86);
		IPO.enterNewStock(m, "TWTR", "Twitter Inc.", 47.88);
		IPO.enterNewStock(m, "VSLR", "Vivint Solar", 16.44);
		IPO.enterNewStock(m, "GILD", "Gilead Sciences", 93.33);

		m.printStocks();

		Market market = new Market("Nikkei");
		IPO.enterNewStock(market, "BABA", "Alibaba", 84.88);
		IPO.enterNewStock(market, "BDU", "Baidu", 253.66);

		market.printStocks();

		// Create 16 traders
		Trader neda = new Trader("Neda", 200000.00);
		Trader scott = new Trader("Scott", 100000.00);
		Trader luke = new Trader("Luke", 100000.00);
		Trader thomas = new Trader("Thomas", 100000.00);
		Trader sritika = new Trader("Sritika", 100000.00);
		Trader meg = new Trader("Meg", 100000.00);
		Trader jen = new Trader("Jen", 100000.00);
		Trader emory = new Trader("Emory", 100000.00);
		Trader justin = new Trader("Justin", 100000.00);
		Trader zach = new Trader("Zach", 100000.00);
		Trader matt = new Trader("Matt", 100000.00);
		Trader angelea = new Trader("Angela", 100000.00);
		Trader hamza = new Trader("Hamza", 100000.00);
		Trader ethan = new Trader("Ethan", 100000.00);

		// Two traders with market orders
		Trader t1 = new Trader("T1", 300000.00);
		Trader t2 = new Trader("T2", 300000.00);

		try {
			neda.buyFromBank(m, "SBUX", 1600);
			scott.buyFromBank(m, "SBUX", 300);
			luke.buyFromBank(m, "SBUX", 300);
			thomas.buyFromBank(m, "SBUX", 300);
			sritika.buyFromBank(m, "SBUX", 600);
			meg.buyFromBank(m, "SBUX", 700);
			jen.buyFromBank(m, "SBUX", 500);
			t1.buyFromBank(m, "SBUX", 1500);
			// Exception
			emory.buyFromBank(m, "SBUX", 5000);
		} catch (StockMarketExpection e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		neda.printTrader();
		scott.printTrader();
		luke.printTrader();
		thomas.printTrader();
		sritika.printTrader();
		meg.printTrader();
		jen.printTrader();
		emory.printTrader();
		t1.printTrader();

		// Place sell orders
		try {
			neda.placeNewOrder(m, "SBUX", 100, 97.0, OrderType.SELL);
			scott.placeNewOrder(m, "SBUX", 300, 97.5, OrderType.SELL);
			luke.placeNewOrder(m, "SBUX", 300, 98.0, OrderType.SELL);
			thomas.placeNewOrder(m, "SBUX", 300, 98.5, OrderType.SELL);
			sritika.placeNewOrder(m, "SBUX", 500, 99.0, OrderType.SELL);
			meg.placeNewOrder(m, "SBUX", 700, 99.5, OrderType.SELL);
			jen.placeNewOrder(m, "SBUX", 500, 100.0, OrderType.SELL);
			t1.placeNewMarketOrder(m, "SBUX", 1500, 0, OrderType.SELL);

		} catch (StockMarketExpection e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Printing after the sell orders are placed");
		System.out.println("&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^");
		neda.printTrader();
		scott.printTrader();
		luke.printTrader();
		thomas.printTrader();
		sritika.printTrader();
		meg.printTrader();
		jen.printTrader();
		t1.printTrader();

		// Place buy orders
		try {
			emory.placeNewOrder(m, "SBUX", 200, 101.0, OrderType.BUY);
			justin.placeNewOrder(m, "SBUX", 300, 100.5, OrderType.BUY);
			zach.placeNewOrder(m, "SBUX", 400, 100.0, OrderType.BUY);
			matt.placeNewOrder(m, "SBUX", 500, 99.5, OrderType.BUY);
			angelea.placeNewOrder(m, "SBUX", 900, 99.0, OrderType.BUY);
			hamza.placeNewOrder(m, "SBUX", 1000, 98.5, OrderType.BUY);
			ethan.placeNewOrder(m, "SBUX", 900, 98.0, OrderType.BUY);
			t2.placeNewMarketOrder(m, "SBUX", 700, 0, OrderType.BUY);
		} catch (StockMarketExpection e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Printing after the buy orders are placed");
		System.out.println("&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^");
		emory.printTrader();
		justin.printTrader();
		zach.printTrader();
		matt.printTrader();
		angelea.printTrader();
		hamza.printTrader();
		ethan.printTrader();
		t2.printTrader();

		m.triggerTrade();

		System.out.println("Printing after the tradings are done");
		System.out.println("&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^");
		neda.printTrader();
		scott.printTrader();
		luke.printTrader();
		thomas.printTrader();
		sritika.printTrader();
		meg.printTrader();
		jen.printTrader();
		emory.printTrader();
		justin.printTrader();
		zach.printTrader();
		matt.printTrader();
		angelea.printTrader();
		hamza.printTrader();
		ethan.printTrader();
		t1.printTrader();
		t2.printTrader();

		m.printHistoryFor("SBUX");

	}
}
