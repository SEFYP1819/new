package com.example.fyp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
import java.util.ArrayList;
import java.util.List;

public class what_if_page extends AppCompatActivity {

    Button SubmitButton;
    ListView StockList;                     //Weight Part
    Rebalance_adapter re;                   //Weight Part
    TextView RiskLevelTextView, ReturnLevelTextView;    //RiskReturn Part
    SeekBar  RiskSeekBar, ReturnSeekBar;                //RiskReturn Part
    CheckBox UseRiskReturn, UseWeight, UseRisk, UseReturn;
    List<String> mList = new ArrayList<String>(14);
    List<Boolean> mCheckList = new ArrayList<Boolean>(14);
    String[] itemName = new String[14];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_if_page);

        //Item Name
        itemName[0] = "US Total Stock Market";
        itemName[1] = "US Value Stocks (Large Cap.)";
        itemName[2] = "US Value Stocks (Mid Cap.)";
        itemName[3] = "US Value Stocks (Small Cap.)";
        itemName[4] = "Intl. Developed Market Stocks";
        itemName[5] = "Intl. Emerging Market Stocks";
        itemName[6] = "US High Quality Bonds";
        itemName[7] = "US Municipal Bonds";
        itemName[8] = "US Inflation-Protected Bonds";
        itemName[9] = "US High-Yield Corp. Bonds";
        itemName[10] = "US Short-Term Treasury Bonds";
        itemName[11] = "US Short-Term Investment-Grade Bonds";
        itemName[12] = "Intl. Developed Market Bonds";
        itemName[13] = "Intl. Emerging Market Bonds";

        //Bottom Menu
        RelativeLayout BottomMenu = findViewById(R.id.BottomMenu);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(BottomMenu);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        ImageView HomePageBtn, ProfilePageBtn, RebalancePageBtn, ChatBotPageBtn, LogoutBtn;
        HomePageBtn = findViewById(R.id.HomePageBtn);
        ProfilePageBtn = findViewById(R.id.ProfilePageBtn);
        RebalancePageBtn = findViewById(R.id.RebalancePageBtn);
        ChatBotPageBtn = findViewById(R.id.ChatBotPageBtn);
        LogoutBtn = findViewById(R.id.LogOutBtn);

        ProfilePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(what_if_page.this, profile_page.class));
            }
        });

        HomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(what_if_page.this, home_page.class));
            }
        });

        ChatBotPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(what_if_page.this, chat_bot_page.class));
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(what_if_page.this, Start.class));
            }
        });

        //ListView
        StockList = findViewById(R.id.StockList);

        //CheckBox
        UseRiskReturn = findViewById(R.id.RebalanceUsingRiskReturn);
        UseWeight = findViewById(R.id.RebalanceUsingWeight);
        UseRisk = findViewById(R.id.RiskCheckBox);
        UseReturn = findViewById(R.id.ReturnCheckBox);
        UseRiskReturn.setChecked(true);

        UseRiskReturn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UseWeight.setChecked(false);
                }
            }
        });

        UseWeight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UseRiskReturn.setChecked(false);
                }
            }
        });

        UseRisk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UseReturn.setChecked(false);
                }
            }
        });

        UseReturn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UseRisk.setChecked(false);
                }
            }
        });

        //SeekBar
        //Risk Part
        RiskSeekBar = findViewById(R.id.RiskSeekBar);
        RiskLevelTextView = findViewById(R.id.RiskLevelTextView);
        RiskSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int p = progress/100;
                RiskLevelTextView.setText(Integer.toString(p));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (RiskSeekBar.getProgress() < 22) {
                    RiskSeekBar.setProgress(22);
                }
            }
        });

        //Return Part
        ReturnSeekBar = findViewById(R.id.ReturnSeekBar);
        ReturnLevelTextView = findViewById(R.id.ReturnLevelTextView);
        ReturnSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int p = progress/10;
                ReturnLevelTextView.setText(Integer.toString(p));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        getCurrentUserPortfolio("http://172.18.9.169/fetch.php");   //Fetch from XAMPP


        //Button
        SubmitButton = findViewById(R.id.SubmitBtn);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UseRiskReturn.isChecked()) {
                    if (UseReturn.isChecked()) {
                        doRebalanceUsingReturn("http://172.18.9.169/RebalanceUsingReturn.php");
                    }
                    if (UseRisk.isChecked()) {
                        doRebalanceUsingRisk("http://172.18.9.169/RebalanceUsingRisk.php");
                    }
                }

                if (UseWeight.isChecked()) {
                    doRebalanceUsingWeight("http://172.18.9.169/RebalanceUsingWeight.php");
                }

                /*startActivity(new Intent(what_if_page.this, profile_page.class));*/
            }
        });
    }

    //Fetch from XAMPP
    public void getCurrentUserPortfolio(final String urlWebServices) {
        class getPortfolio extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
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
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);

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
        getPortfolio get = new getPortfolio();
        get.execute();
    }

    //Load into listview after fetch
    public void LoadintoListView(String json) throws JSONException {

        //Load JSON to listview

        JSONArray jsonArray = new JSONArray(json);
        JSONObject obj = jsonArray.getJSONObject(0);

        for (int count = 0; count < 14; count++) {
            mList.add(obj.getString(itemName[count]));
            mCheckList.add(false);
        }

        re = new Rebalance_adapter(this, mList, mCheckList);
        StockList.setAdapter(re);

    }

    //Do Re-balance Using Risk
    public void doRebalanceUsingRisk(final String urlWebServices) {
        class startProgress extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Double d2 = Double.valueOf(RiskSeekBar.getProgress());
                    d2 = d2 / 10000;

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser CurrentUser = mAuth.getCurrentUser();
                    String UID = CurrentUser.getUid();

                    //Do Output
                    URL url = new URL(urlWebServices);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("uid", "UTF-8")+"="+URLEncoder.encode(UID, "UTF-8")+"&"+URLEncoder.encode("Risk", "UTF-8")+"="+URLEncoder.encode(d2.toString(), "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();

                    //Do Input
                    StringBuilder receive = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        receive.append(json+"\n");
                    }
                    return  receive.toString().trim();

                } catch (Exception e) {
                    return null;

                }
            }
        }
        startProgress start = new startProgress();
        start.execute();
    }

    //Do Re-balance Using Return
    public void doRebalanceUsingReturn(final String urlWebServices) {
        class startProgress extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Double d1 = Double.valueOf(ReturnSeekBar.getProgress());
                    d1 = (d1 * 10 + 105) /10000;

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser CurrentUser = mAuth.getCurrentUser();
                    String UID = CurrentUser.getUid();

                    //Do Output
                    URL url = new URL(urlWebServices);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("uid", "UTF-8")+"="+URLEncoder.encode(UID, "UTF-8")+"&"+URLEncoder.encode("Return_rate", "UTF-8")+"="+URLEncoder.encode(d1.toString(), "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();

                    //Do Input
                    StringBuilder receive = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        receive.append(json+"\n");
                    }
                    return  receive.toString().trim();

                } catch (Exception e) {
                    return null;

                }
            }
        }
        startProgress start = new startProgress();
        start.execute();
    }

    public void test() {
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < 14; count++) {
            if (mCheckList.get(count)) {
                sb.append(mList.get(count)).append(" ");
            }
        }
        Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();

    }

    //Do Re-balance Using Weighting
    public void doRebalanceUsingWeight(final String urlWebServices) {
        class startProgress extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser CurrentUser = mAuth.getCurrentUser();
                    String UID = CurrentUser.getUid();

                    //Do Output
                    URL url = new URL(urlWebServices);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("uid", "UTF-8")+"="+URLEncoder.encode(UID, "UTF-8")+"&"+URLEncoder.encode("US_Total_Stock_Market", "UTF-8")+"="+URLEncoder.encode(mList.get(0), "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();

                    //Do Input
                    StringBuilder receive = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        receive.append(json+"\n");
                    }
                    return  receive.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }
        }
        startProgress start = new startProgress();
        start.execute();
    }
}
