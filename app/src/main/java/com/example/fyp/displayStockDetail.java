package com.example.fyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class displayStockDetail extends AppCompatActivity {

    TextView StockFullNameTextView, StockShortNameTextView, StockPriceTextView, StockPriceChangeTextView;
    ListView FinancialDataList;
    Button Backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_stock_detail);

        StockFullNameTextView = findViewById(R.id.StockFullNameTextView);
        StockShortNameTextView = findViewById(R.id.StockShortNameTextView);
        StockPriceTextView = findViewById(R.id.StockPriceTextView);
    }
}
