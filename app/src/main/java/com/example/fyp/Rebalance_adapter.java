package com.example.fyp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class Rebalance_adapter extends BaseAdapter {

    private ViewHolder mViewHolder;
    private LayoutInflater mLayoutInflater;
    private List<String> mList;
    private List<Boolean> mCheckList;
    private String[] itemName = new String[14];


    public Rebalance_adapter(@NonNull Context context, List<String> list, List<Boolean> checkList) {
        mLayoutInflater = LayoutInflater.from(context);
        mList = list;
        mCheckList = checkList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int mTouchItemPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        itemName[0] = "US Total Stock Market";
        itemName[1] = "US Value Stocks (Large Cap.)";
        itemName[2] = "US Value Stocks (Mid Cap.)";
        itemName[3] = "US Value Stocks (Small Cap.)";
        itemName[4] = "Intl. Developed Market Stocks";
        itemName[5] = "Intl. Emerging Market Stocks";
        itemName[6] = "US High Quality Bonds";
        itemName[7] = "US Municipal Bonds";
        itemName[8] = "US Inflation-Protected Bonds";
        itemName[9] = "US High-Yield Corp. Bonds";
        itemName[10] = "US Short-Term Treasury Bonds";
        itemName[11] = "US Short-Term Investment-Grade Bonds";
        itemName[12] = "Intl. Developed Market Bonds";
        itemName[13] = "Intl. Emerging Market Bonds";

        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.what_if_adapter, null);
            mViewHolder.ListItemCheckBox = convertView.findViewById(R.id.ListItemCheckBox);
            mViewHolder.ListItemText = convertView.findViewById(R.id.ListItemText);
            mViewHolder.ListItemPercentage = convertView.findViewById(R.id.ListItemPercentage);

            mViewHolder.ListItemPercentage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    mTouchItemPosition = (Integer) view.getTag();

                    if ((view.getId() == R.id.ListItemPercentage && canVerticalScroll((EditText) view))) {
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                    return false;
                }
            });
            mViewHolder.mTextWatcher = new MyTextWatcher();
            mViewHolder.ListItemPercentage.addTextChangedListener(mViewHolder.mTextWatcher);

            mViewHolder.ListItemCheckBox.setChecked(false);

            mViewHolder.updatePosition(position);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
            mViewHolder.updatePosition(position);
        }

        if (position < 14) {
            mViewHolder.ListItemText.setText(itemName[position]);
        }
        mViewHolder.ListItemPercentage.setText(mList.get(position));
        mViewHolder.ListItemPercentage.setTag(position);

        mViewHolder.ListItemCheckBox.setChecked(mCheckList.get(position));
        mViewHolder.ListItemCheckBox.setTag(position);

        if (mTouchItemPosition == position) {
            mViewHolder.ListItemPercentage.requestFocus();
            mViewHolder.ListItemPercentage.setSelection(mViewHolder.ListItemPercentage.getText().length());
            mViewHolder.ListItemCheckBox.setChecked(true);
            mCheckList.set(mTouchItemPosition, true);
        } else {
            mViewHolder.ListItemPercentage.clearFocus();
        }

        return convertView;
    }

    static final class ViewHolder {
        CheckBox ListItemCheckBox;
        TextView ListItemText;
        EditText ListItemPercentage;
        MyTextWatcher mTextWatcher;

        public void updatePosition(int position) {
            mTextWatcher.updatePosition(position);
        }
    }

    class MyTextWatcher implements TextWatcher {
        private int mPosition;

        public void updatePosition(int position) {
            mPosition = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mList.set(mPosition, s.toString());
        }
    }

    private boolean canVerticalScroll(EditText editText) {
        int scrollY = editText.getScrollY();
        int scrollRange = editText.getLayout().getHeight();
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }
}
