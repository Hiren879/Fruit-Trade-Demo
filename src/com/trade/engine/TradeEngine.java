package com.trade.engine;

import java.util.Scanner;

import com.trade.calculate.TradeManager;
import com.trade.model.TradeType;

public class TradeEngine {
	public static void main(String args[]) {

		System.out.println("Welcome to Fruit Trading Platform.");

		System.out
				.println("You have following commands to operate this application.");
		System.out.println();
		System.out.println("You can buy fruit using following command :: ");
		System.out.println("BUY <FRUIT> <PRICE> <QUANTITY>");

		System.out.println();
		System.out.println("You can sell fruit using following command :: ");
		System.out.println("SELL <FRUIT> <PRICE> <QUANTITY>");

		System.out.println();
		System.out.println("You can check PROFIT using following command :: ");
		System.out.println("PROFIT");

		System.out.println();
		System.out.println("To EXIT the system anytime, kindly press CTRL + C");

		System.out.println();
		System.out.println();
		System.out.println("Kindly start entring input");
		System.out.println();

		// Initialize trade object on startup and store everything here
		TradeManager calculateTrade = new TradeManager();

		while (true) {
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			if (input.startsWith("BUY") || input.startsWith("SELL")) {
				String[] inputArr = input.split(" ");
				if (inputArr.length != 4) {
					System.out
							.println("INVALID INPUT FORMAT !! Kindly retry with correct input.");
				} else {
					try {
						String fruitName = inputArr[1];
						int fruitPrice = Integer.parseInt(inputArr[2]);
						int fruitQuantity = Integer.parseInt(inputArr[3]);
						calculateTrade.calculateBuyOrSellTrade(fruitName,
								fruitPrice, fruitQuantity, input.startsWith("BUY") ? TradeType.BUY : TradeType.SELL);
					} catch (Exception e) {
						System.out
								.println("INVALID INPUT FORMAT !! Kindly retry with correct input.");
					}
				}
			} else if (input.startsWith("PROFIT")) {
				String[] inputArr = input.split(" ");
				calculateTrade.calculateProfit(inputArr[1]);
			} else {
				System.out
						.println("INVALID INPUT FORMAT !! Kindly retry with correct input.");
			}
		}
	}
}
