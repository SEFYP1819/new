package com.example.fyp;

import android.content.Intent;
import android.graphics.Path;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class chat_bot_page extends AppCompatActivity {

    ListView MessageListView;
    MessageAdapter messageAdapter;
    EditText MessageInputBox;
    Button SubmitBtn;
    String flag = "0";
    Float returnValue, riskValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot_page);

        RelativeLayout BottomMenu = findViewById(R.id.BottomMenu);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(BottomMenu);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        //Bottom Menu Buttons
        ImageView HomePageBtn, ProfilePageBtn, RebalancePageBtn, ChatBotPageBtn, LogoutBtn;
        HomePageBtn = findViewById(R.id.HomePageBtn);
        ProfilePageBtn = findViewById(R.id.ProfilePageBtn);
        RebalancePageBtn = findViewById(R.id.RebalancePageBtn);
        ChatBotPageBtn = findViewById(R.id.ChatBotPageBtn);
        LogoutBtn = findViewById(R.id.LogOutBtn);

        HomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chat_bot_page.this, home_page.class));
            }
        });

        ProfilePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chat_bot_page.this, profile_page.class));
            }
        });

        RebalancePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chat_bot_page.this, what_if_page.class));
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(chat_bot_page.this, Start.class));
            }
        });

        MessageListView = findViewById(R.id.MessageListView);
        messageAdapter = new MessageAdapter(getApplicationContext(), new ArrayList<Message>());
        MessageListView.setAdapter(messageAdapter);

        MessageInputBox = findViewById(R.id.MessageInputBox);

        String Opening = "Hello! Welcome to chat bot v1.0" +System.lineSeparator() +"You can ask whatever you want to know.";

        getMessage(false, Opening, getCurrentTime());

        SubmitBtn = findViewById(R.id.SubmitBtn);

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = MessageInputBox.getText().toString();
                String reply = "";
                getMessage(true, temp, getCurrentTime());
                MessageInputBox.getText().clear();

                if (temp.contains("risk") && flag.equals("0")) {
                    reply = "Risk is a chance that the combination of assets or units, within the investments that you own, fail to meet financial objectives."+System.lineSeparator()+
                            "Each investment within a portfolio carries its own risk, with higher potential return typically meaning higher risk.";
                    getMessage(false, reply, getCurrentTime());
                } else if (temp.contains("return") && flag.equals("0")) {
                    reply = "Portfolio return refers to the gain or loss realized by an investment portfolio containing several types of investments."+System.lineSeparator()+
                            "You can adjust your portfolio return and risk in the What-if page.";
                    getMessage(false, reply, getCurrentTime());
                } else if (temp.contains("sharpe")) {
                    reply = "Sharp Ratio is to help investors understand the return of an investment compared to its risk."+System.lineSeparator()+
                            "The ratio is the average return earned in excess of the risk-free rate per unit of volatility or total risk.";
                    getMessage(false, reply, getCurrentTime());
                } else if (temp.contains("portfolio") && (temp.contains("rebalance") || temp.contains("adjust"))) {
                    getUserPortfolio("http://172.18.9.169/fetch.php");
                    flag = "1";
                } else if (temp.contains("risk") && flag.equals("1")) {
                    if (temp.contains("high")) {
                        getMessage(false, "Adjust risk to a higher class", getCurrentTime());
                        doRebalanceUsingRiskHigher("http://172.18.9.169/RebalanceUsingRisk.php");
                        getUserPortfolio("http://172.18.9.169/fetch.php");
                    }
                    if (temp.contains("low")) {
                        getMessage(false, "Adjust risk to a lower class", getCurrentTime());
                        doRebalanceUsingRiskLower("http://172.18.9.169/RebalanceUsingRisk.php");
                        getUserPortfolio("http://172.18.9.169/fetch.php");
                    }
                } else if (temp.contains("return") && flag.equals("1")) {
                    getMessage(false, "Rebalance using return", getCurrentTime());
                    doRebalanceUsingReturn("http://172.18.9.169/RebalanceUsingReturn.php");
                    getUserPortfolio("http://172.18.9.169/fetch.php");
                }
                else {
                    reply = "Sorry I do not understand. Please ask another question";
                    getMessage(false, reply, getCurrentTime());
                }
            }
        });

    }

    public String getCurrentTime() {
        String time;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        time = simpleDateFormat.format(calendar.getTime());
        return time;
    }

    public void getMessage(final Boolean isMine, final String MessageContent, final String MessageTime)  {
        Message message = new Message(isMine, MessageContent, MessageTime);
        messageAdapter.add(message);
    }

    public void getUserPortfolio(final String urlWebServices) {
        class getPortfolio extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                try {
                    helpReBalance(s);
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

                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String JSON;
                    while ((JSON = bufferedReader.readLine()) != null) {
                        sb.append(JSON +"\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        getPortfolio a = new getPortfolio();
        a.execute();
    }

    public void helpReBalance(String JSON) throws JSONException {
        JSONArray jsonArray = new JSONArray(JSON);
        final JSONObject obj = jsonArray.getJSONObject(0);

        String temp = "*** Portfolio Rebalancing Helper ***"
                +System.lineSeparator()+"Your portfolio are as follow:";
        getMessage(false, temp, getCurrentTime());

        String dialog;

        String[] item = new String[17];
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
        item[14] = "Risk";
        item[15] = "Return rate";
        item[16] = "Sharpe";

        StringBuilder sb = new StringBuilder();

        for (int count = 0; count < 16; count++) {
            if (!obj.getString(item[count]).equals("0.0000")) {
                sb.append(item[count]).append("   ").append(ChangeToPercentage(obj.getString(item[count]))).append("\n");
            }
        }
        sb.append(item[16]).append("   ").append(obj.getString(item[16])).append("\n");

        getMessage(false, sb.toString(), getCurrentTime());

        String riskLevel = "";
        riskValue = Float.parseFloat(obj.getString(item[14]));

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
        returnValue = Float.parseFloat(obj.getString(item[15]));

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

        if (riskLevel.contains("high")) {
            temp =  "Your risk is "+ riskLevel+ System.lineSeparator()+"Your return is "+returnLevel+System.lineSeparator()+"Maybe you can lower the risk to have a more stable income. You can adjust the risk to lower than 6.5% in What-if Page or perhaps I can help you now";
        } else {
            temp = "Your risk is "+ riskLevel+ System.lineSeparator()+"Your return is "+returnLevel+System.lineSeparator()+"You can be more risk-seeking to have a better income. You can adjust the risk to higher than 10% in What-if Page or perhaps I can help you now";
        }

//        temp =  "Your risk is "+ riskLevel+ System.lineSeparator()+"Your return is "+returnLevel+System.lineSeparator()+
//                "Please enter which aspect you would like to adjust?"+ System.lineSeparator()+
//                "A. Adjust risk to higher or lower"+ System.lineSeparator()+
//                "B. Adjust return to higher"+ System.lineSeparator()+System.lineSeparator()+
//                "!!Note: Having a higher return might result in a higher risk!!";

        getMessage(false, temp, getCurrentTime());

    }

    public String ChangeToPercentage(String temp) {
        Float d = Float.parseFloat(temp);
        DecimalFormat df = new DecimalFormat("##.##");
        d = d * 100;
        StringBuilder sb = new StringBuilder();
        sb.append(df.format(d)).append("%");
        return sb.toString();
    }

    public void doRebalanceUsingRiskHigher(final String urlWebServices) {

        class startProgress extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                getMessage(false,"Success", getCurrentTime());
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    if (riskValue < 0.1100) {
                        riskValue = riskValue + 0.0150f;
                    }

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser CurrentUser = mAuth.getCurrentUser();
                    String UID = CurrentUser.getUid();

                    URL url = new URL(urlWebServices);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                    String post_data = URLEncoder.encode("Risk", "UTF-8")+"="+URLEncoder.encode(riskValue.toString(), "UTF-8");
                    String post_data = URLEncoder.encode("uid", "UTF-8")+"="+URLEncoder.encode(UID, "UTF-8")+"&"+URLEncoder.encode("Risk", "UTF-8")+"="+URLEncoder.encode(riskValue.toString(), "UTF-8");
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

    public void doRebalanceUsingRiskLower(final String urlWebServices) {

        class startProgress extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                getMessage(false,"Success", getCurrentTime());
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    if (riskValue > 0.0200) {
                        riskValue = riskValue - 0.0150f;
                    }

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser CurrentUser = mAuth.getCurrentUser();
                    String UID = CurrentUser.getUid();

                    URL url = new URL(urlWebServices);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                    String post_data = URLEncoder.encode("Risk", "UTF-8")+"="+URLEncoder.encode(riskValue.toString(), "UTF-8");
                    String post_data = URLEncoder.encode("uid", "UTF-8")+"="+URLEncoder.encode(UID, "UTF-8")+"&"+URLEncoder.encode("Risk", "UTF-8")+"="+URLEncoder.encode(riskValue.toString(), "UTF-8");
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

    public void doRebalanceUsingReturn(final String urlWebServices) {
        class startProgress extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                getMessage(false,"Success", getCurrentTime());
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    if (returnValue < 0.1500) {
                        returnValue = returnValue + 0.0200f;
                    }

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
//                    String post_data = URLEncoder.encode("Return_rate", "UTF-8")+"="+URLEncoder.encode(returnValue.toString(), "UTF-8");
                    String post_data = URLEncoder.encode("uid", "UTF-8")+"="+URLEncoder.encode(UID, "UTF-8")+"&"+URLEncoder.encode("Return_rate", "UTF-8")+"="+URLEncoder.encode(riskValue.toString(), "UTF-8");
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
