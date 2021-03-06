package com.example.fyp;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class m_fragment extends Fragment {

    Calendar calendar;
    SimpleDateFormat dateFormat, monthFormat;
    String date, month;
    TextView date_view;
    ListView StockDataList;
    RelativeLayout BottomSheet;
    ImageView BottomSheetCloseBtn;
    BottomSheetBehavior bottomSheetBehavior;
    String[] url;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_m, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomSheet = getView().findViewById(R.id.BottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(BottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        StockDataList = getView().findViewById(R.id.StockDataList);
        show_time();

        url = new String[5];

        url[0] = "https://www.bloomberg.com/quote/HSI:IND";
        url[1] = "https://www.bloomberg.com/quote/INDU:IND";
        url[2] = "https://www.bloomberg.com/quote/SPX:IND";
        url[3] = "https://www.bloomberg.com/quote/NYA:IND";

        /*GetJsoupContent(url);*/

        BottomSheetCloseBtn = getView().findViewById(R.id.BottomSheetCloseBtn);
        BottomSheetCloseBtn.setVisibility(View.INVISIBLE);
        BottomSheetCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                BottomSheetCloseBtn.setVisibility(View.INVISIBLE);
            }
        });
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

    public void GetJsoupContent(final String[] url) {
        final StockDataAdapter s = new StockDataAdapter(getContext(), new ArrayList<Stock>());
        StockDataList.setAdapter(s);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int count = 0; count < 4; count++) {
                    final StringBuilder StockFullNameBuilder = new StringBuilder();
                    final StringBuilder StockShortNameBuilder = new StringBuilder();
                    final StringBuilder StockPriceBuilder = new StringBuilder();
                    final StringBuilder StockPriceChangeBuilder = new StringBuilder();

                    try {
                        Document doc = Jsoup.connect(url[count]).get();
                        Element element1 = doc.select("div.overviewRow__0956421f")
                                .select("span.priceText__1853e8a5").first();
                        Element c = doc.select("div.overviewRow__0956421f")
                                .select("span.currency__defc7184").first();
                        String StockPrice = element1.text();
                        String Currency = c.text();
                        StockPriceBuilder.append(StockPrice).append(" ").append(Currency);

                        Element element2 = doc.select("section.company__c1979f17")
                                .select("span.companyId__87e50d5a")
                                .first();
                        String StockShortName = element2.text();
                        StockShortNameBuilder.append(StockShortName);

                        Element element3 = doc.select("section.company__c1979f17")
                                .select("div")
                                .select("h1.companyName__99a4824b")
                                .first();
                        String StockFullName = element3.text();
                        StockFullNameBuilder.append(StockFullName);

                        Element element4 = doc.select("div.overviewRow__0956421f")
                                .get(1)
                                .select("span")
                                .get(1);
                        String StockPriceChange = element4.text();
                        StockPriceChangeBuilder.append(StockPriceChange);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Stock stock = new Stock(StockFullNameBuilder.toString(), StockShortNameBuilder.toString(), StockPriceBuilder.toString(), StockPriceChangeBuilder.toString());
                            s.add(stock);

                            StockDataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    BottomSheetCloseBtn.setVisibility(View.VISIBLE);

                                    TextView BottomSheetTitle =getView().findViewById(R.id.BottomSheetTitle);
                                    BottomSheetTitle.setText(s.getItem(position).getStockShortName());

                                    TextView BottomSheetStockFullName = getView().findViewById(R.id.BottomSheetStockFullName);
                                    BottomSheetStockFullName.setText(s.getItem(position).getStockFullName());

                                    TextView BottomSheetStockPrice = getView().findViewById(R.id.BottomSheetStockPrice);
                                    BottomSheetStockPrice.setText(s.getItem(position).getStockPrice()+"   ");

                                    TextView BottomSheetStockPriceChange = getView().findViewById(R.id.BottomSheetStockPriceChange);
                                    double change = Double.parseDouble(s.getItem(position).getStockPriceChange().trim().replace("%",""));
                                    if (change < 0) {
                                        BottomSheetStockPriceChange.setTextColor(Color.parseColor("#ff0000"));
                                    } else if (change >= 0) {
                                        BottomSheetStockPriceChange.setTextColor(Color.parseColor("#00ff00"));
                                    }
                                    BottomSheetStockPriceChange.setText(s.getItem(position).getStockPriceChange());

                                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }
                            });
                        }
                    });
                }
            }
        }).start();
    }


}
