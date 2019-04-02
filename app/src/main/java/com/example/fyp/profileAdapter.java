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

public class profileAdapter extends ArrayAdapter<Profile> {

    public profileAdapter(@NonNull Context context, List<Profile> data) {
        super(context, R.layout.profile_adapter, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.profile_adapter, parent, false);

        TextView StockFullName = convertView.findViewById(R.id.StockFullName);
        StockFullName.setText(getItem(position).getStockFullName());

        TextView Percentage = convertView.findViewById(R.id.Percentage);
        Percentage.setText(getItem(position).getPercentage());

        TextView Return = convertView.findViewById(R.id.Return);
        Return.setText(getItem(position).getExpectedReturn());

        convertView.findViewById(R.id.ProfileStockListView);

        return convertView;
    }
}
