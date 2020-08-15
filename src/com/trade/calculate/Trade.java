package com.trade.calculate;


public class Trade {

	private int fruitPrice;
	private int fruitQuantity;

	public Trade(int fruitPrice, int fruitQuantity) {
		this.fruitPrice = fruitPrice;
		this.fruitQuantity = fruitQuantity;
	}

	public int getFruitPrice() {
		return fruitPrice;
	}

	public void setFruitPrice(int fruitPrice) {
		this.fruitPrice = fruitPrice;
	}

	public int getFruitQuantity() {
		return fruitQuantity;
	}

	public void setFruitQuantity(int fruitQuantity) {
		this.fruitQuantity = fruitQuantity;
	}
}
