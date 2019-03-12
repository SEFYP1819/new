package com.example.fyp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class whatif_fragment extends Fragment {

    /*SeekBar ReturnSeekBar, RiskSeekBar, SharpeSeekBar;
    TextView ReturnSeekBarValue, RiskSeekBarValue, SharpeSeekBarValue;*/

    ListView USStockList, InternationalStockList, USBondsList, InternationalBondsList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_whatif, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        USStockList = getView().findViewById(R.id.USStockList);
        InternationalStockList = getView().findViewById(R.id.InternationalStockList);
        USBondsList = getView().findViewById(R.id.USBondsList);
        InternationalBondsList = getView().findViewById(R.id.InternationalBondsList);
        LoadListItem();
    }

    public void LoadListItem() {
        final US_Stock_Adapter s = new US_Stock_Adapter(getContext(), new ArrayList<US_Stock>());
        final US_Bond_Adapter t = new US_Bond_Adapter(getContext(), new ArrayList<US_Bond>());
        final Inter_Stock_Adapter u = new Inter_Stock_Adapter(getContext(), new ArrayList<Inter_Stock>());
        final Inter_Bond_Adapter v = new Inter_Bond_Adapter(getContext(), new ArrayList<Inter_Bond>());

        USStockList.setAdapter(s);
        USBondsList.setAdapter(t);
        InternationalStockList.setAdapter(u);
        InternationalBondsList.setAdapter(v);

        TextView USStockPercentage = getView().findViewById(R.id.USStockPercentage);
        TextView USBondsPercentage = getView().findViewById(R.id.USBondsPercentage);
        TextView InternationalStockPercentage = getView().findViewById(R.id.InternationalStockPercentage);
        TextView InternationalBondsPercentage = getView().findViewById(R.id.InternationalBondsPercentage);


        int[] Percentage = new int[11];

        String[] NameList = new String[11];

        NameList[0] = "US Total Stock Market";
        NameList[1] = "US Large-Cap Value";
        NameList[2] = "US Mid-Cap Value";
        NameList[3] = "US Small-Cap Value";
        NameList[4] = "Intl. Developed Markets";
        NameList[5] = "Intl. Emerging Markets";
        NameList[6] = "US Inflation-Protected Bonds";
        NameList[7] = "US High-Quality Bonds";
        NameList[8] = "US Municipal Bonds";
        NameList[9] = "Intl. Developed Market Bonds";
        NameList[10] = "Intl. Emerging Market Bonds";

        Percentage[0] = 65;
        Percentage[1] = 5;
        Percentage[2] = 5;
        Percentage[3] = 5;
        Percentage[4] = 0;
        Percentage[5] = 0;
        Percentage[6] = 0;
        Percentage[7] = 20;
        Percentage[8] = 16;
        Percentage[9] = 10;
        Percentage[10] = 10;

        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        int sum4 = 0;

        for (int count = 0; count <= 3; count++) {
            sum1 = sum1 + Percentage[count];
            US_Stock us_stock = new US_Stock(NameList[count], Integer.toString(Percentage[count])+"%");
            s.add(us_stock);
        }
        USStockPercentage.setText(Integer.toString(sum1)+"%");

        for (int count = 4; count <= 5; count++) {
            sum2 = sum2 + Percentage[count];
            Inter_Stock inter_stock = new Inter_Stock(NameList[count], Integer.toString(Percentage[count])+"%");
            u.add(inter_stock);
        }
        InternationalStockPercentage.setText(Integer.toString(sum2)+"%");

        for (int count = 6; count <= 8; count++) {
            sum3 = sum3 + Percentage[count];
            US_Bond us_bond = new US_Bond(NameList[count], Integer.toString(Percentage[count])+"%");
            t.add(us_bond);
        }
        USBondsPercentage.setText(Integer.toString(sum3)+"%");

        for (int count = 9; count <= 10; count++) {
            sum4 = sum4 + Percentage[count];
            Inter_Bond inter_bond = new Inter_Bond(NameList[count], Integer.toString(Percentage[count])+"%");
            v.add(inter_bond);
        }
        InternationalBondsPercentage.setText(Integer.toString(sum4)+"%");
    }


}
