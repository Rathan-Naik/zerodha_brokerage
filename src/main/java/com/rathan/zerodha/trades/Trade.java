package com.rathan.zerodha.trades;

public class Trade {
	String type;
	String productType;
	String quantity;
	String companyName;
	double price;
	
	public Trade(String type, String companyName, String productType, String quantity, double price) {
		this.type = type;
		this.productType = productType;
		this.quantity = quantity;
		this.companyName = companyName;
		this.price = price;
	}

}
