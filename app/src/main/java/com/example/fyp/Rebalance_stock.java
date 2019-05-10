package com.example.fyp;

public class Rebalance_stock {

    String StockName, Percentage;

    public Rebalance_stock(String stockName, String percentage) {
        StockName = stockName;
        Percentage = percentage;
    }

    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }
}
