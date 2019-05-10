package com.example.fyp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        RelativeLayout BottomMenu = findViewById(R.id.BottomMenu);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(BottomMenu);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        TextView DateView = findViewById(R.id.DateView);
        DateView.setText(getCurrentDate());

        getStockData();

       ImageView HomePageBtn, ProfilePageBtn, RebalancePageBtn, ChatBotPageBtn, LogoutBtn;
       HomePageBtn = findViewById(R.id.HomePageBtn);
       ProfilePageBtn = findViewById(R.id.ProfilePageBtn);
       RebalancePageBtn = findViewById(R.id.RebalancePageBtn);
       ChatBotPageBtn = findViewById(R.id.ChatBotPageBtn);
       LogoutBtn = findViewById(R.id.LogOutBtn);

       ProfilePageBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(home_page.this, profile_page.class));
           }
       });

       RebalancePageBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(home_page.this, what_if_page.class));
           }
       });

       ChatBotPageBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(home_page.this, chat_bot_page.class));
           }
       });

       LogoutBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseAuth mAuth = FirebaseAuth.getInstance();
               mAuth.signOut();
               startActivity(new Intent(home_page.this, Start.class));
           }
       });

    }

    private String getCurrentDate() {
        String day, month;
        Calendar calendar;
        SimpleDateFormat dayFormat, monthFormat;
        StringBuilder currentDate = new StringBuilder();

        calendar = Calendar.getInstance();
        dayFormat = new SimpleDateFormat("dd");
        monthFormat = new SimpleDateFormat("MMMM");
        day = dayFormat.format(calendar.getTime());
        month = monthFormat.format(calendar.getTime());

        currentDate.append(day).append(" ").append(month);
        return currentDate.toString().trim();
    }

    private void getStockData() {
        ListView StockDataListView = findViewById(R.id.StockDataListView);
        final StockDataAdapter stockDataAdapter = new StockDataAdapter(getApplicationContext(), new ArrayList<Stock>());
        StockDataListView.setAdapter(stockDataAdapter);

        Stock stock1 = new Stock("Vanguard Total Stock Market ETF", "VTI:US", "149.4800 USD", "-0.08%");
        stockDataAdapter.add(stock1);
        Stock stock2 = new Stock("Xtrackers USD High Yield Corporate Bond ETF", "HYLB:US", "49.9864 USD", "+0.11%");
        stockDataAdapter.add(stock2);
        Stock stock3 = new Stock("Vanguard Total International Bond ETF", "BNDX:US", "55.7150 USD", "+0.03%");
        stockDataAdapter.add(stock3);
        Stock stock4 = new Stock("Vanguard FTSE Emerging Markets ETF", "VWO:US", "43.1801 USD", "-0.37%");
        stockDataAdapter.add(stock4);
    }
}
