package com.example.fyp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StockDataAdapter extends ArrayAdapter<Stock> {

    public StockDataAdapter(@NonNull Context context, List<Stock> Data) {
        super(context, R.layout.listviewstyle, Data);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.stock_data_list_style, parent, false);

        TextView StockShortName = convertView.findViewById(R.id.StockShortName);
        StockShortName.setText(getItem(position).getStockShortName());

        TextView StockFullName = convertView.findViewById(R.id.StockFullName);
        StockFullName.setText(getItem(position).getStockFullName());

        TextView StockPrice = convertView.findViewById(R.id.StockPrice);
        StockPrice.setText(getItem(position).getStockPrice());

        TextView StockPriceChange = convertView.findViewById(R.id.StockPriceChange);
        double change = Double.parseDouble(getItem(position).getStockPriceChange().trim().replace("%",""));

        if (change < 0) {
            StockPriceChange.setBackgroundResource(R.drawable.redstockpricebackground);
        } else if (change >= 0) {
            StockPriceChange.setBackgroundResource(R.drawable.greenstockpricebackground);
        }
        StockPriceChange.setText(getItem(position).getStockPriceChange());

        convertView.findViewById(R.id.StockDataList);

        return convertView;
    }
}
