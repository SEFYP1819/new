package com.example.fyp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity {

    Button start_button, RiskAssess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        start_button = findViewById(R.id.start_button);
        RiskAssess = findViewById(R.id.riskAssessBtn);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == start_button)  {
                    startActivity(new Intent(Start.this, login.class));
                }
            }
        });

        RiskAssess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == RiskAssess) {
                    startActivity(new Intent(Start.this, cal_risk_index.class));
                }
            }
        });
    }
}
