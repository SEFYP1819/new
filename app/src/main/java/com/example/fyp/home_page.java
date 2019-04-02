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

        Stock stock = new Stock("Dow Jones Industrial Average", "INDU:IND", "25,928.68 USD", "+0.82%");
        stockDataAdapter.add(stock);
    }
}
