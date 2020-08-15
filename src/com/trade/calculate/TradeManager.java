package com.trade.calculate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trade.exception.InvalidPriceQuantityFormatException;
import com.trade.exception.InvalidTradeException;
import com.trade.exception.SellQuantityException;
import com.trade.model.TradeType;

/**
 * This class is responsible to manage all trade activities.
 * 
 * @author Hiren Savalia
 * 
 */
public class TradeManager {
	
	private static final String KINDLY_ENTER_BUY_OR_SELL_KEYWORD = "Kindly enter BUY or SELL keyword.";
	private static final String KINDLY_BUY_SOMETHING_IN_ORDER_TO_SELL_IT = "Kindly BUY something in order to SELL it.";
	private static final String KINDLY_CHECK_PRICE_QUANTITY_FORMAT_ONLY_POSITIVE_NUMBERS_ARE_ALLOWED = "Kindly check price/quantity format. Only positive numbers are allowed.";
	// local DB to store all trade history
	private Map<TradeType, Map<String, List<Trade>>> tradeMap = new HashMap<>();

	public TradeManager() {
		this.tradeMap = new HashMap<>();
		Map<String, List<Trade>> buyMapList = new HashMap();
		Map<String, List<Trade>> sellMapList = new HashMap();
		tradeMap.put(TradeType.BUY, buyMapList);
		tradeMap.put(TradeType.SELL, sellMapList);
	}

	/**
	 * This method will validate incoming trade before adding to local DB
	 * Currently handling following scenarios :
	 * 	1. Only BUY and SELL keywords are allowed.
	 * 	2. Only positive numbers are allowed for price & quantity.
	 * 	3. User can not enter SELL trade before BUY trade
	 * 	4. User can not SELL quantity more than his buy quantity for given fruit
	 * @param  
	 * @param input 
	 * @param fruitName
	 * @param tradeType
	 * @param fruitQuantity 
	 * @param fruitPrice 
	 * @throws InvalidTradeException 
	 * @throws InvalidPriceQuantityFormatException 
	 * @throws SellQuantityException 
	 */
	public void validateAndInsertTrade(String[] tradeArr)
			throws InvalidTradeException, InvalidPriceQuantityFormatException, SellQuantityException {
		// 1.
		if (!tradeArr[0].equals("BUY") && !tradeArr[0].equals("SELL")) {
			throw new InvalidTradeException(KINDLY_ENTER_BUY_OR_SELL_KEYWORD);
		}

		TradeType tradeType = tradeArr[0].equals("BUY") ? TradeType.BUY
				: TradeType.SELL;

		// 2.
		String fruitName = tradeArr[1];
		int fruitPrice;
		int fruitQuantity;
		try {
			fruitPrice = Integer.parseInt(tradeArr[2]);
			fruitQuantity = Integer.parseInt(tradeArr[3]);
		} catch (NumberFormatException e) {
			throw new InvalidPriceQuantityFormatException(
					KINDLY_CHECK_PRICE_QUANTITY_FORMAT_ONLY_POSITIVE_NUMBERS_ARE_ALLOWED);
		}

		if (fruitPrice < 0 || fruitQuantity < 0) {
			throw new InvalidPriceQuantityFormatException(
					KINDLY_CHECK_PRICE_QUANTITY_FORMAT_ONLY_POSITIVE_NUMBERS_ARE_ALLOWED);
		}

		// 3.
		if (tradeType == TradeType.SELL
				&& tradeMap.get(TradeType.BUY).get(fruitName) == null
				&& tradeMap.get(tradeType).get(fruitName) == null) {
			throw new InvalidTradeException(
					KINDLY_BUY_SOMETHING_IN_ORDER_TO_SELL_IT);
		}

		// 4. Sell quantity must be lesser than buy quantity for given fruit
		if (tradeType == TradeType.SELL) {
			List<Trade> buyList = tradeMap.get(TradeType.BUY).get(fruitName);
			int totalBuyQuantity = 0;
			int totalSellQuantity = 0;
			for (Trade buyTrade : buyList) {
				totalBuyQuantity += buyTrade.getFruitQuantity();
			}
			if (tradeMap.get(TradeType.SELL).get(fruitName) != null) {
				List<Trade> sellList = tradeMap.get(TradeType.SELL).get(
						fruitName);
				for (Trade sellTrade : sellList) {
					totalSellQuantity += sellTrade.getFruitQuantity();
				}
				totalSellQuantity += fruitQuantity;
			} else {
				totalSellQuantity = fruitQuantity;
			}
			if (totalSellQuantity > totalBuyQuantity) {
				throw new SellQuantityException(
						"You can not sell more than what you own.");
			}
		}
		
		// if all validations are passed then add trade into local DB
		insertTrade(fruitName, fruitPrice, fruitQuantity, tradeType);
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
	public void insertTrade(String fruitName, int fruitPrice,
			int fruitQuantity, TradeType tradeType) {
		Map<String, List<Trade>> buySellMasterMap = tradeMap.get(tradeType);
		if (buySellMasterMap.containsKey(fruitName)) {
			List<Trade> tradeList = (ArrayList<Trade>) buySellMasterMap
					.get(fruitName);
			tradeList.add(new Trade(fruitPrice, fruitQuantity));
			setUpTradeMap(fruitName, tradeType, buySellMasterMap, tradeList);
		} else {
			List<Trade> tradeList = new ArrayList<>();
			tradeList.add(new Trade(fruitPrice, fruitQuantity));
			setUpTradeMap(fruitName, tradeType, buySellMasterMap, tradeList);
		}
		if (tradeType == TradeType.BUY) {
			System.out.println("Bought " + fruitQuantity + " KG of "
					+ fruitName + " at " + fruitPrice + " rs.");
		} else {
			System.out.println("Sold " + fruitQuantity + " KG of "
					+ fruitName + " at " + fruitPrice + " rs.");
		}
	}

	private void setUpTradeMap(String fruitName, TradeType tradeType,
			Map<String, List<Trade>> buySellMasterMap, List<Trade> tradeList) {
		buySellMasterMap.put(fruitName, tradeList);
		tradeMap.put(tradeType, buySellMasterMap);
	}

	/**
	 * This method will calculate profit for given fruit
	 * @param fruitName
	 */
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
		getProfit(fruitName, totalSellValue, totalBuyValue);
	}

	/**
	 * This method will derive profit and print it.
	 * @param fruitName
	 * @param totalSellValue
	 * @param totalBuyValue
	 */
	private void getProfit(String fruitName, int totalSellValue,
			int totalBuyValue) {
		// 3. Calculate final profit and print
		int profit = totalSellValue - totalBuyValue;
		System.out.println("Net P/L on trading of fruit " + fruitName
				+ " is :: " + profit);
	}
}
