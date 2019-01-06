package com.andrydevelops.cardealership.callback;

import com.andrydevelops.cardealership.bean.Car;

public interface ListCallBack {

    void onCarSelected(Car car);
    void onCarCreated();
}
