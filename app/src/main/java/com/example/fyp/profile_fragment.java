package com.example.fyp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class profile_fragment extends Fragment {

    Calendar calendar;
    SimpleDateFormat dateFormat, monthFormat;
    String date, month;
    String current_user_id;
    TextView date_view, name_view;
    FirebaseAuth mAuth;
    FirebaseUser current_user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        show_time();

        show_name();
    }

    public void show_time() {
        date_view = getView().findViewById(R.id.date_view);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd");
        monthFormat = new SimpleDateFormat("MMMM");
        date = dateFormat.format(calendar.getTime());
        month = monthFormat.format(calendar.getTime());
        date_view.setText(date+" "+month);
    }

    public void show_name() {
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser();
        name_view = getView().findViewById(R.id.name_view);

        if (current_user != null) {
            current_user_id = current_user.getUid();
            DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("User").child(current_user_id).child("First Name");

            user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name_view.setText("Hello, "+dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
