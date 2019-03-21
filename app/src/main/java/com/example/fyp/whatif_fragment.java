package com.example.fyp;

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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class whatif_fragment extends Fragment {

    EditText USStock1, USStock2, USStock3, USStock4, INStock1, INStock2, USBond1, USBond2, USBond3, INBond1, INBond2;
    SeekBar RiskLevelSeekBar, ReturnLevelSeekBar;
    TextView RiskLevel, ReturnLevel;
    int x;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_whatif, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        USStock1 = getView().findViewById(R.id.USStockContent_1);
        USStock2 = getView().findViewById(R.id.USStockContent_2);
        USStock3 = getView().findViewById(R.id.USStockContent_3);
        USStock4 = getView().findViewById(R.id.USStockContent_4);

        INStock1 = getView().findViewById(R.id.INStockContent_1);
        INStock2 = getView().findViewById(R.id.INStockContent_2);

        USBond1 = getView().findViewById(R.id.USBondContent_1);
        USBond2 = getView().findViewById(R.id.USBondContent_2);
        USBond3 = getView().findViewById(R.id.USBondContent_3);

        INBond1 = getView().findViewById(R.id.INBondContent_1);
        INBond2 = getView().findViewById(R.id.INBondContent_2);

        RiskLevelSeekBar = getView().findViewById(R.id.OverallRiskSeekBar);
        RiskLevelSeekBar.setProgress(40);
        ReturnLevelSeekBar = getView().findViewById(R.id.OverallReturnSeekBar);
        ReturnLevelSeekBar.setProgress(50);

        RiskLevel = getView().findViewById(R.id.OverallRiskLevel);
        x = RiskLevelSeekBar.getProgress();
        if (x<=33) {
            RiskLevel.setText("Less Risk");
        } else if (x>=66) {
            RiskLevel.setText("More Risk");
        } else {
            RiskLevel.setText("Moderate");
        }
        ReturnLevel = getView().findViewById(R.id.OverallReturnLevel);
        x = ReturnLevelSeekBar.getProgress();
        ReturnLevel.setText(Integer.toString(x));


        RiskLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress<=33) {
                    RiskLevel.setText("Less Risk");
                } else if (progress>=66) {
                    RiskLevel.setText("More Risk");
                } else {
                    RiskLevel.setText("Moderate");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ReturnLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Double s = Double.valueOf(progress);
                ReturnLevel.setText(Double.toString(s/10));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
