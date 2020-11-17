package com.rathan.zerodha.trades;

import java.text.DecimalFormat;

public class Formula {

	private static final double STT_PRICE = 0.00025;
	private static final double STAMP_CHARGES = 0.00003;
	private static final double SEB_PRICE = 0.0000005;
	
	private static final double BSE_EXCHANGE = 0.00003;
	private static final double NSE_EXCHANGE = 0.0000325;
	
	private static DecimalFormat df;

	static {
		df = new DecimalFormat("#.##");
		df.setMaximumFractionDigits(2);
	}

	public static void main(String[] args) {
		Calculate();

	}
	public static void Calculate () {
	    boolean NSE = true;
	    boolean BSE = true;

	    double bp = 2345.0;
	    double sp = 2347.5;

	    long qty = 12917;
	        

	    double brokerage_buy = zerodhaBrokerage(bp, qty);

	    double brokerage_sell = zerodhaBrokerage(sp, qty);
	    
	    double brokerage = totalBrokerage( brokerage_buy, brokerage_sell);

	    double turnover = calculateTurnOver(bp, sp, qty);

	    double stt_total = sttTotal(sp, qty);

	    double exc_trans_charge = (NSE) ? execCharge(df, turnover, NSE_EXCHANGE) : execCharge(df, turnover, BSE_EXCHANGE) ;
	    double cc = 0 ;
	    double stax = staxCalculation(df, brokerage, exc_trans_charge);
	    double sebi_charges = Double.parseDouble(df.format(turnover * SEB_PRICE));
	    double stamp_charges = Double.parseDouble(df.format((bp * qty) * STAMP_CHARGES));
	    double total_tax = totalTax(df, brokerage, stt_total, exc_trans_charge, cc, stax, sebi_charges, stamp_charges);
	    double breakeven = Double.parseDouble(df.format(total_tax / qty));
	    //breakeven = isNaN(breakeven) ? 0 : breakeven

	    double net_profit = Double.parseDouble(df.format(((sp - bp) * qty) - total_tax));
	    System.out.println(net_profit);
	}

	private static double totalTax(DecimalFormat df, double brokerage, double stt_total, double exc_trans_charge,
			double cc, double stax, double sebi_charges, double stamp_charges) {
		return Double.parseDouble(df.format(brokerage + stt_total + exc_trans_charge + cc + stax + sebi_charges + stamp_charges));
	}

	private static double staxCalculation(DecimalFormat df, double brokerage, double exc_trans_charge) {
		return Double.parseDouble(df.format(0.18 * (brokerage + exc_trans_charge)));
	}
	
	private static double execCharge(DecimalFormat df, double turnover, double exechangePrice) {
		return Double.parseDouble(df.format(exechangePrice * turnover));
	}

	private static long sttTotal(double sp, long qty) {
		return Math.round(Double.parseDouble(df.format((sp * qty) * STT_PRICE)));
	}

	private static double calculateTurnOver(double bp, double sp, long qty) {
		return Double.parseDouble(df.format((bp + sp) * qty));
	}

	private static double totalBrokerage(double brokerage_buy, double brokerage_sell) {
		return Double.parseDouble( df.format(brokerage_buy + brokerage_sell));
	}

	private static double zerodhaBrokerage(double price, long qty) {
		return ((price * qty * 0.0003) > 20) ? 20 : Double.parseDouble(df.format(price * qty * 0.0003));
	}

}
