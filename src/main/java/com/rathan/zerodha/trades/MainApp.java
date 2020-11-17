package com.rathan.zerodha.trades;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class MainApp {

	private static final String MIS = "MIS";
	private static final String FILE_PATH = "/home/rathannaik/Downloads/trades.csv";

	public static void main(String args[]) {
		createTradeBook(FILE_PATH);
	}

	public static void createTradeBook(String file) 
	{ 

		List <Trade> misTrades = new ArrayList<>();
		List <Trade> otherTrades = new ArrayList<>();

		try { 

			// Create an object of filereader 
			// class with CSV file as a parameter. 
			FileReader filereader = new FileReader(file); 

			// create csvReader object passing 
			// file reader as a parameter 
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
			// CSVReader csvReader = new CSVReader(filereader,); 
			String[] nextRecord; 

			while ((nextRecord = csvReader.readNext()) != null) { 
				seggregateTrade(misTrades, otherTrades, nextRecord);

				System.out.println(); 
			} 
		} 
		catch (Exception e) {
			System.out.print("Error Occured while reading csv"+e);
		} 
	}

	private static void seggregateTrade(List<Trade> misTrades, List<Trade> otherTrades, String[] nextRecord) {
		Trade entry = extractFields(nextRecord);

		if(entry.productType == MIS) {
			misTrades.add(entry);
		} else {
			otherTrades.add(entry);
		}
	}

	private static Trade extractFields(String[] nextRecord) {
		String type = nextRecord[2];
		String companyName = nextRecord[3];
		String productType = nextRecord[4];
		String quantity = nextRecord[5];
		double price = Double.parseDouble(nextRecord[6]);

		Trade entry = new Trade(type, companyName, productType, quantity, price);
		return entry;
	} 

}
