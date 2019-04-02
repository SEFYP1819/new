package com.example.fyp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class profile_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        RelativeLayout BottomMenu = findViewById(R.id.BottomMenu);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(BottomMenu);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        getCurrentUserName();

        ImageView HomePageBtn, ProfilePageBtn, RebalancePageBtn, ChatBotPageBtn, LogoutBtn;
        HomePageBtn = findViewById(R.id.HomePageBtn);
        ProfilePageBtn = findViewById(R.id.ProfilePageBtn);
        RebalancePageBtn = findViewById(R.id.RebalancePageBtn);
        ChatBotPageBtn = findViewById(R.id.ChatBotPageBtn);
        LogoutBtn = findViewById(R.id.LogOutBtn);

        HomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile_page.this, home_page.class));
            }
        });

        RebalancePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile_page.this, what_if_page.class));
            }
        });

        ChatBotPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile_page.this, chat_bot_page.class));
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(profile_page.this, Start.class));
            }
        });

        getUserPortfolio();
    }

    public void getCurrentUserName() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser Current_User = mAuth.getCurrentUser();

        if (Current_User != null) {
            DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("User").child(Current_User.getUid()).child("First Name");

            user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TextView NameView = findViewById(R.id.NameView);
                    NameView.setText("Hello, " + dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void getUserPortfolio() {
        ListView ProfileStockListView = findViewById(R.id.ProfileStockListView);
        final profileAdapter p = new profileAdapter(getApplicationContext(), new ArrayList<Profile>());
        ProfileStockListView.setAdapter(p);

        Profile listTitle = new Profile("Stock Name", "Percentage", "Return");
        p.add(listTitle);

        Profile etf1 = new Profile("ETF 1", "20%", "5%");
        p.add(etf1);

        Profile etf2 = new Profile("ETF 2", "50%", "5.5%");
        p.add(etf2);

        Profile etf3 = new Profile("ETF 3", "30%", "4.5%");
        p.add(etf3);

        PieChart pieChart = findViewById(R.id.ProfilePieChart);
        String Label[] = {"xxx", "yyy", "zzz"};
        float wts[] = {0,0,0};

        Label[0] = etf1.getStockFullName();
        Label[1] = etf2.getStockFullName();
        Label[2] = etf3.getStockFullName();

        wts[0] = Float.parseFloat(etf1.getPercentage().replace("%", ""));
        wts[1] = Float.parseFloat(etf2.getPercentage().replace("%", ""));
        wts[2] = Float.parseFloat(etf3.getPercentage().replace("%", ""));

        List<PieEntry> pieEntries = new ArrayList<>();

        for (int count = 0; count < Label.length; count++) {
            pieEntries.add(new PieEntry(wts[count], Label[count]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Portfolio Weightings");

        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);

        dataSet.setValueTextColor(ColorTemplate.rgb("#000000"));

        PieData pieData = new PieData(dataSet);

        pieChart.setData(pieData);

        pieChart.animateY(1000);

        pieChart.setHoleRadius(50);

        pieChart.setHoleColor(121212);

        Legend legend = pieChart.getLegend();

        legend.setEnabled(false);

        pieChart.invalidate();

        ListView ProjectionListView = findViewById(R.id.ProjectionListView);
        final ProjectionAdapter pro = new ProjectionAdapter(getApplicationContext(), new ArrayList<projection>());
        ProjectionListView.setAdapter(pro);

        projection item1 = new projection("Test Value 1", "5%");
        pro.add(item1);

        projection item2 = new projection("Test Value 2", "4%");
        pro.add(item2);
    }
}
