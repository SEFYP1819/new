package com.example.fyp;

public class Stock {

    String StockName, StockPrice;

    public Stock(String stockName, String stockPrice) {
        StockName = stockName;
        StockPrice = stockPrice;
    }

    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public String getStockPrice() {
        return StockPrice;
    }

    public void setStockPrice(String stockPrice) {
        StockPrice = stockPrice;
    }
}
