package com.andrydevelops.cardealership.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.andrydevelops.cardealership.bean.CarBodyType;

import java.util.ArrayList;
import java.util.List;

public class BodyTypeAdapter extends ArrayAdapter<String> {

    private CarBodyType[] mBodies;

    public BodyTypeAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_spinner_item);
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBodies = CarBodyType.values();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mBodies[position].toString();
    }

    @Override
    public int getCount() {
        return mBodies.length;
    }
}
