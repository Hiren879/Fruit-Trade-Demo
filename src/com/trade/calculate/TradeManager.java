package com.trade.calculate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trade.model.TradeType;

public class TradeManager {

	private Map<TradeType, Map<String, List<Trade>>> tradeMap = new HashMap<>();

	public TradeManager() {
		this.tradeMap = new HashMap<>();
		Map<String, List<Trade>> buyMapList = new HashMap();
		Map<String, List<Trade>> sellMapList = new HashMap();
		tradeMap.put(TradeType.BUY, buyMapList);
		tradeMap.put(TradeType.SELL, sellMapList);
	}

	/**
	 * 1. Get Buy/Sell Map
	 * 2. Check if fruit exists in Buy/Sell Map or not
	 * 		If YES
	 * 		2.1 : Get fruit specific tradeList
	 * 		2.2 : Add new fruit in same list
	 * 		2.3 : Add modified list again in map
	 * 		2.4 : Add this fruit specific map to masterMap
	 * 
	 * 		If NO
	 * 		2.5 : Create new tradeList for give fruit
	 * 		2.6 : Add into masterMap
	 * @param fruitName
	 * @param fruitPrice
	 * @param fruitQuantity
	 * @param tradeType
	 */
	public void calculateBuyOrSellTrade(String fruitName, int fruitPrice,
			int fruitQuantity, TradeType tradeType) {
		Map<String, List<Trade>> buySellMasterMap = tradeMap.get(tradeType);
		if (buySellMasterMap.containsKey(fruitName)) {
			List<Trade> tradeList = (ArrayList<Trade>) buySellMasterMap
					.get(fruitName);
			tradeList.add(new Trade(fruitPrice, fruitQuantity));
			buySellMasterMap.put(fruitName, tradeList);
			tradeMap.put(tradeType, buySellMasterMap);
		} else {
			List<Trade> tradeList = new ArrayList<>();
			tradeList.add(new Trade(fruitPrice, fruitQuantity));
			buySellMasterMap.put(fruitName, tradeList);
			tradeMap.put(tradeType, buySellMasterMap);
		}
	}

	public void calculateProfit(String fruitName) {
		Map<String, List<Trade>> masterSellMap = tradeMap.get(TradeType.SELL);
		Map<String, List<Trade>> masterBuyMap = tradeMap.get(TradeType.BUY);
		List<Trade> sellTradeList = masterSellMap.get(fruitName);
		List<Trade> buyTradeList = masterBuyMap.get(fruitName);

		// 1. Get total sell quantity & total sell value
		int totalSellQuantity = 0;
		int totalSellValue = 0;
		for (Trade sellTrade : sellTradeList) {
			totalSellQuantity += sellTrade.getFruitQuantity();
			totalSellValue += sellTrade.getFruitPrice()
					* sellTrade.getFruitQuantity();
		}

		// 2. Get buy quantity matching with sell quantity
		int totalBuyValue = 0;
		for (Trade buyTrade : buyTradeList) {
			int buyTradeQuantity = buyTrade.getFruitQuantity();
			if (totalSellQuantity != 0 && buyTradeQuantity < totalSellQuantity) {
				totalBuyValue += buyTrade.getFruitPrice() * buyTradeQuantity;
				totalSellQuantity -= buyTradeQuantity;
			} else {
				totalBuyValue += buyTrade.getFruitPrice() * totalSellQuantity;
				break;
			}
		}

		// 3. Calculate final profit and print
		System.out.println(totalSellValue - totalBuyValue);
	}
}
