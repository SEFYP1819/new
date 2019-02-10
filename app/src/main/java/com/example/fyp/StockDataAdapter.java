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

public class StockDataAdapter extends ArrayAdapter<Stock> {

    public StockDataAdapter(@NonNull Context context, List<Stock> Data) {
        super(context, R.layout.listviewstyle, Data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.listviewstyle,parent, false);

        TextView StockNameView = convertView.findViewById(R.id.StockNameView);
        StockNameView.setText(getItem(position).getStockName());
        TextView StockPriceView = convertView.findViewById(R.id.StockPriceView);
        StockPriceView.setText(getItem(position).getStockPrice());

        convertView.findViewById(R.id.StockList);
        return convertView;
    }
}
