package com.example.fyp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class cal_risk_index extends AppCompatActivity {

    Button YesBtn, NoBtn;
    ListView ContentListView;
    riskAssessMessageAdapter my_adapter;
    String Opening;
    String[] Content = new String[6];
    int count = 0;
    FirebaseAuth mAuth;
    FirebaseUser CurrentUser;
    String index;
    int mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_risk_index);

        //Get intent
        mark = getIntent().getExtras().getInt("riskIndex");

        Opening = "The following question assesses risk aversion index." +System.lineSeparator() +
                "A varying payoff of a specified value or a gamble of fair coin with a certain payoff of a specified value";

        ContentListView = findViewById(R.id.GameContentListView);
        my_adapter = new riskAssessMessageAdapter(getApplicationContext(), new ArrayList<riskAssessMessage>());
        ContentListView.setAdapter(my_adapter);

        YesBtn = findViewById(R.id.YesBtn);
        NoBtn = findViewById(R.id.NoBtn);

        //Check Mark
        getMessage(false, "Your marks is "+Integer.toString(mark), getCurrentTime());

        getMessage(false, Opening, getCurrentTime());

        Content[0] = "$50 for sure or a fair coin flip in which you get $200 if it is head and $0 if it is tails." + System.lineSeparator() +"Will you play the coin flip game?";
        Content[1] = "$60 for sure or a fair coin flip in which you get $200 if it is head and $0 if it is tails." + System.lineSeparator() +"Will you play the coin flip game?";
        Content[2] = "$70 for sure or a fair coin flip in which you get $200 if it is head and $0 if it is tails." + System.lineSeparator() +"Will you play the coin flip game?";
        Content[3] = "$80 for sure or a fair coin flip in which you get $200 if it is head and $0 if it is tails." + System.lineSeparator() +"Will you play the coin flip game?";
        Content[4] = "$90 for sure or a fair coin flip in which you get $200 if it is head and $0 if it is tails." + System.lineSeparator() +"Will you play the coin flip game?";
        Content[5] = "$100 for sure or a fair coin flip in which you get $200 if it is head and $0 if it is tails." + System.lineSeparator() +"Will you play the coin flip game?";
        getMessage(false, Content[0], getCurrentTime());
        YesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMessage(true, "Yes", getCurrentTime());
                count++;
                if (count < 6) {
                    getMessage(false,Content[count], getCurrentTime());
                }

                if (count == 6) {
                    mark = mark + 7;
                    calculateTotalMark();
                    getFirstPortfolio("http://172.18.9.169/newProfolio.php");
                    startActivity(new Intent(cal_risk_index.this, home_page.class));
                }
            }
        });
        NoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMessage(true, "No", getCurrentTime());
                switch (count) {
                    case 0:

                        mark = mark + 1;
                        break;

                    case 1:

                        mark = mark + 2;
                        break;

                    case 2:

                        mark = mark + 3;
                        break;

                    case 3:

                        mark = mark + 4;
                        break;

                    case 4:

                        mark = mark + 5;
                        break;

                    case 5:

                        mark = mark + 6;
                        break;
                }
                calculateTotalMark();
                getFirstPortfolio("http://172.18.9.169/newProfolio.php");
                startActivity(new Intent(cal_risk_index.this, home_page.class));

            }
        });
    }

    public void calculateTotalMark() {
        String temp;
        if (mark < 10) {
            temp = "Very low";
            index = "4.0";
        } else if (mark >= 10 && mark <= 14) {
            temp = "Low";
            index = "3.0";
        } else if (mark >= 15 && mark <= 19) {
            temp = "Medium";
            index = "2.5";
        } else if (mark >= 20 && mark <= 24) {
            temp = "High";
            index = "2.0";
        } else {
            temp = "Very High";
            index = "1.5";
        }
        getMessage(false, "Your risk acceptance level: "+temp, getCurrentTime());
    }

    public String getCurrentTime() {
        String time;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        time = simpleDateFormat.format(calendar.getTime());
        return time;
    }

    public void getMessage(final Boolean isMine, final String MessageContent, final String MessageTime) {
        riskAssessMessage message = new riskAssessMessage(MessageContent, MessageTime, isMine);
        my_adapter.add(message);
    }

    public void getFirstPortfolio(final String urlWebServices) {
        class startprogress extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
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
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("uid", "UTF-8")+"="+URLEncoder.encode(UID, "UTF-8")+"&"+URLEncoder.encode("index", "UTF-8")+"="+URLEncoder.encode(index, "UTF-8");
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
        startprogress s = new startprogress();
        s.execute();
    }
}
