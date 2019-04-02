package com.example.fyp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {

    private static final int MY_MESSAGE = 0;
    private static final int BOT_MESSAGE = 1;


    public MessageAdapter(@NonNull Context context, List<Message> data) {
        super(context, R.layout.user_message, data);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Message item = getItem(position);

        if (item.isMine() == true) {
            return MY_MESSAGE;
        } else {
            return BOT_MESSAGE;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        int ViewType = getItemViewType(position);

        if (ViewType == MY_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_message, parent, false);

            TextView ContentView = convertView.findViewById(R.id.ContentView);
            ContentView.setText(getItem(position).getContent());

            TextView TimeView = convertView.findViewById(R.id.TimeView);
            TimeView.setText(getItem(position).getTime());
        } else if (ViewType == BOT_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bot_message, parent, false);

            TextView ContentView = convertView.findViewById(R.id.ContentView);
            ContentView.setText(getItem(position).getContent());

            TextView TimeView = convertView.findViewById(R.id.TimeView);
            TimeView.setText(getItem(position).getTime());
        }

        convertView.findViewById(R.id.MessageListView);
        return convertView;
    }
}
