package com.andrydevelops.cardealership.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.andrydevelops.cardealership.bean.Model;

import java.util.List;

public class ModelSpinnerAdapter extends ArrayAdapter<String> {

    private List<Model> mModels;

    public ModelSpinnerAdapter(@NonNull Context context, List<Model> models) {
        super(context, android.R.layout.simple_spinner_item);
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mModels = models;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mModels.get(position).getName();
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    public int selectModel(int id) {
        for (int i = 0; i < mModels.size(); i++) {
            if (mModels.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
