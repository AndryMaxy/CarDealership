package com.andrydevelops.cardealership.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.andrydevelops.cardealership.R;
import com.andrydevelops.cardealership.callback.CarCallBack;
import com.andrydevelops.cardealership.fragment.EditCarFragment;
import com.andrydevelops.cardealership.fragment.NewCarFragment;
import com.andrydevelops.cardealership.fragment.CarFragment;
import com.andrydevelops.cardealership.fragment.CarListFragment;

public class CarActivity extends AppCompatActivity implements CarCallBack {

    public static final String ARG_CAR_ID = "arg_car_id";
    public static final int FRAGMENT_CAR = 1;
    private static final String EXTRA_CAR_ID = "extra_car_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        int id = getIntent().getIntExtra(EXTRA_CAR_ID, 0);
        Log.i(CarListFragment.TAG, "id in CA: " + id);
        Fragment fragment;

        if (id == 0) {
            fragment = new NewCarFragment();
        } else {
            fragment = CarFragment.newInstance(id);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();

    }

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, CarActivity.class);
        intent.putExtra(EXTRA_CAR_ID, id);
        return intent;
    }

    @Override
    public void replaceFragment(int fragmentId, int carId) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment;
        if (fragmentId == FRAGMENT_CAR) {
            fragment = CarFragment.newInstance(carId);
        } else {
            fragment = EditCarFragment.newInstance(carId);
        }
        manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }


}
