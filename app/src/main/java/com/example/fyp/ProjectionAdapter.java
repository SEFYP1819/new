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

public class ProjectionAdapter extends ArrayAdapter<projection>{

    public ProjectionAdapter(@NonNull Context context, List<projection> data) {
        super(context, R.layout.projection_adapter, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.projection_adapter, parent, false);

        TextView NameView = convertView.findViewById(R.id.NameView);
        NameView.setText(getItem(position).getTitle());

        TextView ValueView = convertView.findViewById(R.id.ValueView);
        ValueView.setText(getItem(position).getProject());

        convertView.findViewById(R.id.ProjectionListView);

        return convertView;
    }
}
