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
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

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

public class chat_bot_page extends AppCompatActivity {

    ListView MessageListView;
    MessageAdapter messageAdapter;
    EditText MessageInputBox;

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

        getBotMessage("http://172.18.9.169/chatbot.php", "Opening", getCurrentTime());

        Button SubmitBtn = findViewById(R.id.SubmitBtn);
        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message UserEntries = new Message(true, MessageInputBox.getText().toString(), getCurrentTime());
                messageAdapter.add(UserEntries);
                getBotMessage("http://172.18.9.169/chatbot.php", MessageInputBox.getText().toString(), getCurrentTime());
                MessageInputBox.getText().clear();
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

    public void getBotMessage(final String urlWebServices, final String UserEntries, final String EntriesTime) {
        class BotReply extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                Message message = new Message(false, s, EntriesTime);
                messageAdapter.add(message);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebServices);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);

                    //Do the output
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String PostData = URLEncoder.encode("input", "UTF-8")+"="+URLEncoder.encode(UserEntries, "UTF-8");
                    bufferedWriter.write(PostData);
                    bufferedWriter.flush();

                    //Get the input
                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String RelpyContent;
                    while ((RelpyContent = bufferedReader.readLine()) != null) {
                        stringBuilder.append(RelpyContent);
                    }

                    return stringBuilder.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }
        }
        BotReply botReply = new BotReply();
        botReply.execute();
    }
}
