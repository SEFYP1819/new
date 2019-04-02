package com.example.fyp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class news_fragment extends Fragment {

    ListView MessageListView;
    EditText MessageInputBox;
    Button SubmitBtn;
    MessageAdapter messageAdapter;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        MessageListView = getView().findViewById(R.id.MessageListView);
        MessageInputBox = getView().findViewById(R.id.MessageInputBox);
        SubmitBtn = getView().findViewById(R.id.SubmitBtn);

        /*BotReply("http://192.168.0.186/chatbot.php", getCurrentTime(), "Opening");*/

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == SubmitBtn) {
                    String temp = MessageInputBox.getText().toString().trim();
                    if (temp.length() > 0) {
                        Message message = new Message(true, temp, "xxxx");
                        messageAdapter.add(message);
                        MessageInputBox.getText().clear();

                        /*BotReply("http://192.168.0.186/chatbot.php", getCurrentTime(), temp);*/
                    }
                }
            }
        });
    }

    public String getCurrentTime() {
        String time;
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        time = simpleDateFormat.format(calendar.getTime());
        return time;
    }

    public void BotReply(final String urlWebServices, final String ReplyTime, final String Content) {
        class Reply extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                Message message = new Message(false, s, ReplyTime);
                messageAdapter.add(message);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebServices);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoOutput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("input", "UTF-8")+"="+URLEncoder.encode(Content, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String BotMessageContent;
                    while ((BotMessageContent = bufferedReader.readLine()) != null) {
                        stringBuilder.append(BotMessageContent);
                    }

                    return stringBuilder.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }
        }
        Reply re = new Reply();
        re.execute();
    }
}
