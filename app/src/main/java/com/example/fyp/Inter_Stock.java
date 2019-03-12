package com.example.fyp;

public class Inter_Stock {

    String ItemName, ItemPercentage;

    public Inter_Stock(String itemName, String itemPercentage) {
        ItemName = itemName;
        ItemPercentage = itemPercentage;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getItemPercentage() {
        return ItemPercentage;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setItemPercentage(String itemPercentage) {
        ItemPercentage = itemPercentage;
    }
}
