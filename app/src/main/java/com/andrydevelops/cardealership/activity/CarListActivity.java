package com.andrydevelops.cardealership.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.andrydevelops.cardealership.R;
import com.andrydevelops.cardealership.bean.Car;
import com.andrydevelops.cardealership.callback.ListCallBack;
import com.andrydevelops.cardealership.fragment.CarListFragment;

public class CarListActivity extends AppCompatActivity implements ListCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new CarListFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment)
                    .commit();
        }

    }

    @Override
    public void onCarSelected(Car car) {
        Intent intent = CarActivity.newIntent(this, car.getId());
        startActivity(intent);
    }

    @Override
    public void onCarCreated() {
        Intent intent = CarActivity.newIntent(this, 0);
        startActivity(intent);
    }
}
