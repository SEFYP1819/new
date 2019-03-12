package com.example.fyp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class Inter_Stock_Adapter extends ArrayAdapter<Inter_Stock> {

    public Inter_Stock_Adapter(@NonNull Context context, List<Inter_Stock> Data) {
        super(context, R.layout.what_if_adapter ,Data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.what_if_adapter, parent, false);

        TextView LisItemName = convertView.findViewById(R.id.ListItemName);
        LisItemName.setText(getItem(position).getItemName());

        EditText ListItemPercentage = convertView.findViewById(R.id.ListItemPercentage);
        ListItemPercentage.setText(getItem(position).getItemPercentage());

        convertView.findViewById(R.id.InternationalStockList);

        ListItemPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return convertView;
    }
}
