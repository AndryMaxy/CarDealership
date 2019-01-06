package com.andrydevelops.cardealership.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.andrydevelops.cardealership.bean.Producer;

import java.util.List;

public class ProducerSpinnerAdapter extends ArrayAdapter<String> {

    private List<Producer> mProducers;

    public ProducerSpinnerAdapter(@NonNull Context context, List<Producer> producers) {
        super(context, android.R.layout.simple_spinner_item);
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProducers = producers;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mProducers.get(position).getName();
    }

    @Override
    public int getCount() {
        return mProducers.size();
    }

}
