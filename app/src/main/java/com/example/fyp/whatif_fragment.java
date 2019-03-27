package com.example.fyp;

import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class whatif_fragment extends Fragment {

    EditText USStock1, USStock2, USStock3, USStock4, INStock1, INStock2, USBond1, USBond2, USBond3, INBond1, INBond2;
    SeekBar RiskLevelSeekBar, ReturnLevelSeekBar;
    TextView RiskLevel, ReturnLevel;
    Button SubmitButton;
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

        SubmitButton = getView().findViewById(R.id.SubmitBtn);


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

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRebalance("http://172.20.10.7/doRebalance.php");
            }
        });
    }

    public void doRebalance(final String urlwebservices) {

        class StartRebalance extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                String[] etf_name = new String[11];
                etf_name[0] = "USStock1";
                etf_name[1] = "USStock2";
                etf_name[2] = "USStock3";
                etf_name[3] = "USStock4";
                etf_name[4] = "INStock1";
                etf_name[5] = "INStock2";
                etf_name[6] = "USBond1";
                etf_name[7] = "USBond2";
                etf_name[8] = "USBond3";
                etf_name[9] = "INBond1";
                etf_name[10] = "INBond2";

                String[] etf_Percentage = new String[11];
                etf_Percentage[0] = USStock1.getText().toString();
                etf_Percentage[1] = USStock2.getText().toString();
                etf_Percentage[2] = USStock3.getText().toString();
                etf_Percentage[3] = USStock4.getText().toString();
                etf_Percentage[4] = INStock1.getText().toString();
                etf_Percentage[5] = INStock2.getText().toString();
                etf_Percentage[6] = USBond1.getText().toString();
                etf_Percentage[7] = USBond2.getText().toString();
                etf_Percentage[8] = USBond3.getText().toString();
                etf_Percentage[9] = INBond1.getText().toString();
                etf_Percentage[10] = INBond2.getText().toString();

                try {
                    URL url = new URL(urlwebservices);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                   /* StringBuilder post = new StringBuilder();
                    for (int count = 0; count < 11; count++) {
                        post.append(etf_name[count]).append("=").append(etf_Percentage[count]);
                        if (count != 10) {
                            post.append("&");
                        }
                    }*/

                    String post_data =
                            URLEncoder.encode("USStock1", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[0], "UTF-8")+"&"
                            +URLEncoder.encode("USStock2", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[1], "UTF-8")+"&"
                            +URLEncoder.encode("USStock3", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[2], "UTF-8")+"&"
                            +URLEncoder.encode("USStock4", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[3], "UTF-8")+"&"
                            +URLEncoder.encode("INStock1", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[4], "UTF-8")+"&"
                            +URLEncoder.encode("INStock2", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[5], "UTF-8")+"&"
                            +URLEncoder.encode("USBond1", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[6], "UTF-8")+"&"
                            +URLEncoder.encode("USBond2", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[7], "UTF-8")+"&"
                            +URLEncoder.encode("USBond3", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[8], "UTF-8")+"&"
                            +URLEncoder.encode("INBond1", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[9], "UTF-8")+"&"
                            +URLEncoder.encode("INBond2", "UTF-8")+"="+URLEncoder.encode(etf_Percentage[10], "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();

                    StringBuilder receive = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        receive.append(json+"\n");
                    }

                    return  receive.toString().trim();
                } catch (Exception e) {
                    return null;
                }


            }
        }

        StartRebalance startRebalance = new StartRebalance();
        startRebalance.execute();

    }
}
