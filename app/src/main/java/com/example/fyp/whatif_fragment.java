package com.example.fyp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class whatif_fragment extends Fragment {

    SeekBar ReturnSeekBar, RiskSeekBar, SharpeSeekBar;
    TextView ReturnSeekBarValue, RiskSeekBarValue, SharpeSeekBarValue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_whatif, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SeekBarStatus();
    }

    private void SeekBarStatus() {
        ReturnSeekBar = getView().findViewById(R.id.ReturnSeekBar);
        RiskSeekBar = getView().findViewById(R.id.RiskSeekBar);
        SharpeSeekBar = getView().findViewById(R.id.ShapeRatioSeekBar);

        ReturnSeekBarValue = getView().findViewById(R.id.ReturnSeekBarValue);
        RiskSeekBarValue = getView().findViewById(R.id.RiskSeekBarValue);
        SharpeSeekBarValue = getView().findViewById(R.id.ShapeRatioSeekBarValue);

        ReturnSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ReturnSeekBarValue.setText(progress+"/100");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        RiskSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RiskSeekBarValue.setText(progress+"/100");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SharpeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharpeSeekBarValue.setText(progress+"/100");
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
