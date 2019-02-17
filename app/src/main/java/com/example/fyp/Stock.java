package com.example.fyp;

public class Stock {
    String StockFullName, StockShortName, StockPrice, StockPriceChange;

    public Stock(String stockFullName, String stockShortName, String stockPrice, String stockPriceChange) {
        StockFullName = stockFullName;
        StockShortName = stockShortName;
        StockPrice = stockPrice;
        StockPriceChange = stockPriceChange;
    }

    public String getStockFullName() {
        return StockFullName;
    }

    public void setStockFullName(String stockFullName) {
        StockFullName = stockFullName;
    }

    public String getStockShortName() {
        return StockShortName;
    }

    public void setStockShortName(String stockShortName) {
        StockShortName = stockShortName;
    }

    public String getStockPrice() {
        return StockPrice;
    }

    public void setStockPrice(String stockPrice) {
        StockPrice = stockPrice;
    }

    public String getStockPriceChange() {
        return StockPriceChange;
    }

    public void setStockPriceChange(String stockPriceChange) {
        StockPriceChange = stockPriceChange;
    }

}
