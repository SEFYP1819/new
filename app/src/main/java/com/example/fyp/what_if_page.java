package com.example.fyp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class what_if_page extends AppCompatActivity {

    EditText USStock1, USStock2, USStock3, USStock4, INStock1, INStock2, USBond1, USBond2, USBond3, INBond1, INBond2;
    SeekBar RiskLevelSeekBar, ReturnLevelSeekBar;
    TextView RiskLevel, ReturnLevel;
    Button SubmitButton;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_if_page);

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

        USStock1 = findViewById(R.id.USStockContent_1);
        USStock2 = findViewById(R.id.USStockContent_2);
        USStock3 = findViewById(R.id.USStockContent_3);
        USStock4 = findViewById(R.id.USStockContent_4);

        INStock1 = findViewById(R.id.INStockContent_1);
        INStock2 = findViewById(R.id.INStockContent_2);

        USBond1 = findViewById(R.id.USBondContent_1);
        USBond2 = findViewById(R.id.USBondContent_2);
        USBond3 = findViewById(R.id.USBondContent_3);

        INBond1 = findViewById(R.id.INBondContent_1);
        INBond2 = findViewById(R.id.INBondContent_2);

        RiskLevelSeekBar = findViewById(R.id.OverallRiskSeekBar);
        RiskLevelSeekBar.setProgress(40);
        ReturnLevelSeekBar = findViewById(R.id.OverallReturnSeekBar);
        ReturnLevelSeekBar.setProgress(50);

        RiskLevel = findViewById(R.id.OverallRiskLevel);
        x = RiskLevelSeekBar.getProgress();
        if (x<=33) {
            RiskLevel.setText("Less Risk");
        } else if (x>=66) {
            RiskLevel.setText("More Risk");
        } else {
            RiskLevel.setText("Moderate");
        }
        ReturnLevel = findViewById(R.id.OverallReturnLevel);
        x = ReturnLevelSeekBar.getProgress();
        ReturnLevel.setText(Integer.toString(x));

        SubmitButton = findViewById(R.id.SubmitBtn);

        RiskLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress<=33) {
                    RiskLevel.setText("Less Risk");
                } else if (progress>=66) {
                    RiskLevel.setText("More Risk");
                } else {
                    RiskLevel.setText("Moderate");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ReturnLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Double s = Double.valueOf(progress);
                ReturnLevel.setText(Double.toString(s/10));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRebalance("http://172.18.9.169/doRebalance.php");
            }
        });

    }

    public void doRebalance(final String urlwebservices) {

        class StartRebalance extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                String[] etf_name = new String[11];
                etf_name[0] = "USStock1";
                etf_name[1] = "USStock2";
                etf_name[2] = "USStock3";
                etf_name[3] = "USStock4";
                etf_name[4] = "INStock1";
                etf_name[5] = "INStock2";
                etf_name[6] = "USBond1";
                etf_name[7] = "USBond2";
                etf_name[8] = "USBond3";
                etf_name[9] = "INBond1";
                etf_name[10] = "INBond2";

                String[] etf_Percentage = new String[11];
                etf_Percentage[0] = USStock1.getText().toString();
                etf_Percentage[1] = USStock2.getText().toString();
                etf_Percentage[2] = USStock3.getText().toString();
                etf_Percentage[3] = USStock4.getText().toString();
                etf_Percentage[4] = INStock1.getText().toString();
                etf_Percentage[5] = INStock2.getText().toString();
                etf_Percentage[6] = USBond1.getText().toString();
                etf_Percentage[7] = USBond2.getText().toString();
                etf_Percentage[8] = USBond3.getText().toString();
                etf_Percentage[9] = INBond1.getText().toString();
                etf_Percentage[10] = INBond2.getText().toString();

                try {
                    URL url = new URL(urlwebservices);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data =
                            URLEncoder.encode("USStock1", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[0], "UTF-8")+"&"
                                    +URLEncoder.encode("USStock2", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[1], "UTF-8")+"&"
                                    +URLEncoder.encode("USStock3", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[2], "UTF-8")+"&"
                                    +URLEncoder.encode("USStock4", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[3], "UTF-8")+"&"
                                    +URLEncoder.encode("INStock1", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[4], "UTF-8")+"&"
                                    +URLEncoder.encode("INStock2", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[5], "UTF-8")+"&"
                                    +URLEncoder.encode("USBond1", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[6], "UTF-8")+"&"
                                    +URLEncoder.encode("USBond2", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[7], "UTF-8")+"&"
                                    +URLEncoder.encode("USBond3", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[8], "UTF-8")+"&"
                                    +URLEncoder.encode("INBond1", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[9], "UTF-8")+"&"
                                    +URLEncoder.encode("INBond2", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[10], "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();

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
        StartRebalance startRebalance = new StartRebalance();
        startRebalance.execute();
    }

}
