package com.example.fyp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class profile_page extends AppCompatActivity {

    Float returnValue, riskValue;

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

        getUserPortfolio("http://172.18.9.169/fetch.php");
    }

    public void getCurrentUserName() {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser Current_User = mAuth.getCurrentUser();
//
//        if (Current_User != null) {
//            DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("User").child(Current_User.getUid()).child("First Name");
//
//            user.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    TextView NameView = findViewById(R.id.NameView);
//                    NameView.setText("Hello, " + dataSnapshot.getValue(String.class));
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }

        TextView NameView = findViewById(R.id.NameView);
        NameView.setText("Hello, User");

    }

    public void getUserPortfolio(final String urlWebServices) {

        class getPortfolio extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                /*Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();*/

                try {
                    LoadintoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser CurrentUser = mAuth.getCurrentUser();
                    String UID = CurrentUser.getUid();
                    //URL Method
                    URL url = new URL(urlWebServices);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    //write URL
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("uid", "UTF-8")+"="+URLEncoder.encode(UID, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String JSON;
                    while ((JSON = bufferedReader.readLine()) != null) {
                        stringBuilder.append(JSON +"\n");
                    }
                    return stringBuilder.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        getPortfolio a = new getPortfolio();
        a.execute();
    }

    public  void LoadintoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        final JSONObject obj = jsonArray.getJSONObject(0);

        ListView ProfileStockListView = findViewById(R.id.ProfileStockListView);
        profileAdapter p = new profileAdapter(getApplicationContext(), new ArrayList<Profile>());
        ProfileStockListView.setAdapter(p);

        Profile ListTitle = new Profile("Stock Name", "Percentage", "Return");
        p.add(ListTitle);

        String[] item = new String[14];
        item[0] = "US Total Stock Market";
        item[1] = "US Value Stocks (Large Cap.)";
        item[2] = "US Value Stocks (Mid Cap.)";
        item[3] = "US Value Stocks (Small Cap.)";
        item[4] = "Intl. Developed Market Stocks";
        item[5] = "Intl. Emerging Market Stocks";
        item[6] = "US High Quality Bonds";
        item[7] = "US Municipal Bonds";
        item[8] = "US Inflation-Protected Bonds";
        item[9] = "US High-Yield Corp. Bonds";
        item[10] = "US Short-Term Treasury Bonds";
        item[11] = "US Short-Term Investment-Grade Bonds";
        item[12] = "Intl. Developed Market Bonds";
        item[13] = "Intl. Emerging Market Bonds";

        String[] StockBondReturnRate = new String[14];
        StockBondReturnRate[0] = "15.90%";
        StockBondReturnRate[1] = "14.27%";
        StockBondReturnRate[2] = "13.32%";
        StockBondReturnRate[3] = "15.28%";
        StockBondReturnRate[4] = "10.22%";
        StockBondReturnRate[5] = "14.97%";
        StockBondReturnRate[6] = "1.77%";
        StockBondReturnRate[7] = "1.53%";
        StockBondReturnRate[8] = "1.53%";
        StockBondReturnRate[9] = "2.99%";
        StockBondReturnRate[10] = "0.94%";
        StockBondReturnRate[11] = "1.64%";
        StockBondReturnRate[12] = "3.09%";
        StockBondReturnRate[13] = "6.16%";

        for (int count = 0; count < 14; count++) {
            if (!obj.getString(item[count]).equals("0.0000") && !obj.getString(item[count]).equals("0")) {
                Profile List_item = new Profile(item[count], ChangeToPercentage(obj.getString(item[count])), StockBondReturnRate[count]);
                p.add(List_item);
            }
        }

        String riskLevel = "";
        riskValue = Float.parseFloat(obj.getString("Risk"));

        if ( riskValue < 0.0438 ) {
            riskLevel = "very low";
        } else if ( riskValue >= 0.22 && riskValue < 0.0438 ) {
            riskLevel = "low";
        } else if ( riskValue >= 0.0439 && riskValue < 0.0646 ) {
            riskLevel = "moderate";
        } else if ( riskValue >= 0.0647 && riskValue < 0.0854 ) {
            riskLevel = "high";
        } else if ( riskValue >= 0.0855 && riskValue < 0.1272 ) {
            riskLevel = "very high";
        } else {
            riskLevel = "Extreme";
        }

        String returnLevel = "";
        returnValue = Float.parseFloat(obj.getString("Return rate"));

        if ( returnValue < 0.0401 ) {
            returnLevel = "very low";
        } else if ( returnValue >= 0.0402 && returnValue < 0.0698 ) {
            returnLevel = "low";
        } else if ( returnValue >= 0.0699 && returnValue < 0.0995 ) {
            returnLevel = "moderate";
        } else if ( returnValue >= 0.0966 && returnValue < 0.1293 ) {
            returnLevel = "high";
        } else if ( returnValue >= 0.1294 ) {
            returnLevel = "very high";
        }

        final TextView RiskView = findViewById(R.id.RiskView);
//        RiskView.setText(ChangeToPercentage(obj.getString("Risk")));
        RiskView.setText(riskLevel);
        RiskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RiskView.setText(ChangeToPercentage(obj.getString("Risk")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        final TextView ReturnView = findViewById(R.id.ReturnView);
//        ReturnView.setText(ChangeToPercentage(obj.getString("Return rate")));
        ReturnView.setText(returnLevel);
        ReturnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ReturnView.setText(ChangeToPercentage(obj.getString("Return rate")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        TextView SharpeRatio = findViewById(R.id.SharpeRatioTextView);
        SharpeRatio.setText(obj.getString("Sharpe"));

        //Load Pie Chart
        PieChart pieChart = findViewById(R.id.ProfilePieChart);
        float[] wts = new float[14];

        List<PieEntry> pieEntries = new ArrayList<>();

        for (int count = 0; count < 13; count++) {
            wts[count] = Float.parseFloat(obj.getString(item[count]));
            if (wts[count] != 0) {
                pieEntries.add(new PieEntry(wts[count], item[count]));
            }
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
    }

    public String ChangeToPercentage(String temp) {
        Float d = Float.parseFloat(temp);
        DecimalFormat df = new DecimalFormat("##.##");
        d = d * 100;
        StringBuilder sb = new StringBuilder();
        sb.append(df.format(d)).append("%");
        return sb.toString();
    }
}
