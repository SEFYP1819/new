package com.example.fyp;

public class Profile {
    String StockFullName, Percentage, ExpectedReturn;

    public Profile(String stockFullName, String percentage, String expectedReturn) {
        StockFullName = stockFullName;
        Percentage = percentage;
        ExpectedReturn = expectedReturn;
    }

    public String getStockFullName() {
        return StockFullName;
    }

    public void setStockFullName(String stockFullName) {
        StockFullName = stockFullName;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public String getExpectedReturn() {
        return ExpectedReturn;
    }

    public void setExpectedReturn(String expectedReturn) {
        ExpectedReturn = expectedReturn;
    }
}
