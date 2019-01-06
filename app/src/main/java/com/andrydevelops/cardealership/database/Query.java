package com.andrydevelops.cardealership.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.andrydevelops.cardealership.bean.Car;
import com.andrydevelops.cardealership.bean.Model;
import com.andrydevelops.cardealership.bean.Producer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.andrydevelops.cardealership.database.CarDataBase.*;

public class Query {

    private static final String QUERY_CARS = "select " +
                    CarTable.NAME + ".*, " +
                    ProducerTable.NAME + "." + ProducerTable.Cols.PRODUCER_ID + ", " +
                    ProducerTable.NAME + "." + ProducerTable.Cols.PRODUCER + ", " +
                    ModelTable.NAME + "." + ModelTable.Cols.MODEL + " " +
                    "from " + CarTable.NAME + " " +
                    "join " + ModelTable.NAME + " " +
                    "on " +
                    CarTable.NAME + "." + CarTable.Cols.MODEL_ID + " = " +
                    ModelTable.NAME + "." + ModelTable.Cols.MODEL_ID + " " +
                    "join " + ProducerTable.NAME + " " +
                    "on " +
                    ModelTable.NAME + "." + ModelTable.Cols.PRODUCER_ID + " = " +
                    ProducerTable.NAME + "." + ProducerTable.Cols.PRODUCER_ID;
    private static Query sQuery;
    private SQLiteDatabase mSQLiteDatabase;

    private Query(Context context) {
        mSQLiteDatabase = new CarDBHelper(context).getWritableDatabase();
    }

    public static Query getQuery(Context context) {
        if (sQuery == null) {
            sQuery = new Query(context);
        }
        return sQuery;
    }

    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        try (MyCursorWrapper cursor = queryCars()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Car car = cursor.getCar();
                cursor.moveToNext();
                cars.add(car);
            }
        }
        return cars;
    }

    public List<Car> getCarsByPrice(boolean isDownSorted) {
        List<Car> cars = new ArrayList<>();
        MyCursorWrapper cursor;
        if (!isDownSorted) {
            cursor = queryCarDownSortByPrice();
        } else {
            cursor = queryCarUpSortByPrice();
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Car car = cursor.getCar();
            cursor.moveToNext();
            cars.add(car);
        }
        return cars;
    }

    private MyCursorWrapper queryCars() {
        return new MyCursorWrapper(mSQLiteDatabase.rawQuery(QUERY_CARS, null));
    }

    public List<Car> getCarsByProducerId(int id) {
        List<Car> cars = new ArrayList<>();
        try (MyCursorWrapper cursor = queryCarByProducerId(id)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Car car = cursor.getCar();
                cursor.moveToNext();
                cars.add(car);
            }
        }
        return cars;
    }

    private MyCursorWrapper queryCarByProducerId(int id) {
        return new MyCursorWrapper(mSQLiteDatabase.rawQuery(
                QUERY_CARS + " " +
                        "where " +
                        ProducerTable.NAME + "." + ProducerTable.Cols.PRODUCER_ID +
                        " = ?",
                new String[]{String.valueOf(id)}));
    }

    public List<Car> getCarsByModelId(int id) {
        List<Car> cars = new ArrayList<>();
        try (MyCursorWrapper cursor = queryCarByModelId(id)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Car car = cursor.getCar();
                cursor.moveToNext();
                cars.add(car);
            }
        }
        return cars;
    }

    private MyCursorWrapper queryCarByModelId(int id) {
        return new MyCursorWrapper(mSQLiteDatabase.rawQuery(
                QUERY_CARS +
                        " where " +
                        CarTable.NAME + "." + CarTable.Cols.MODEL_ID +
                        " = " +
                        String.valueOf(id),
                null));
    }

    public List<Producer> getProducers(){
        List<Producer> producers = new ArrayList<>();
        MyCursorWrapper cursor = queryProducers();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Producer producer = cursor.getProducer();
            producers.add(producer);
            cursor.moveToNext();
        }
        cursor.close();
        return producers;
    }

    private MyCursorWrapper queryProducers() {
        return new MyCursorWrapper(mSQLiteDatabase.rawQuery(
                "select * from " + ProducerTable.NAME,
                null));
    }

    public List<Model> getModelsByProducerId(int id){
        List<Model> models = new ArrayList<>();
        MyCursorWrapper cursor = queryModels(id);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Model model = cursor.getModel();
            models.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return models;
    }

    private MyCursorWrapper queryModels(int id) {
        return new MyCursorWrapper(mSQLiteDatabase.rawQuery(
                "select " +
                        ModelTable.Cols.MODEL_ID + ", " +
                        ModelTable.Cols.MODEL + " " +
                        "from " +
                        ModelTable.NAME + " " +
                        "where " +
                        ModelTable.Cols.PRODUCER_ID + " = " +
                        String.valueOf(id),
                null));
    }

    public Car getCarById(int id) {
        Car car;
        try (MyCursorWrapper myCursorWrapper = queryCarById(id)) {
            myCursorWrapper.moveToFirst();
            car = myCursorWrapper.getCar();
        }
        return car;
    }

    private MyCursorWrapper queryCarById(int id) {
        return new MyCursorWrapper(mSQLiteDatabase.rawQuery(
                QUERY_CARS + " " +
                        "where " +
                        CarTable.NAME + "." + CarTable.Cols.ID + " = ?",
                new String[]{String.valueOf(id)}));
    }

    private MyCursorWrapper queryCarDownSortByPrice(){
        return new MyCursorWrapper(mSQLiteDatabase.rawQuery(
                QUERY_CARS + " " +
                        "order by " +
                        CarTable.NAME + "." + CarTable.Cols.PRICE,
                null));
    }

    private MyCursorWrapper queryCarUpSortByPrice(){
        return new MyCursorWrapper(mSQLiteDatabase.rawQuery(
                QUERY_CARS + " " +
                        "order by " +
                        CarTable.NAME + "." + CarTable.Cols.PRICE + " " +
                        "desc",
                null));
    }

    public int addCar(Car car) {
        ContentValues contentValues = getCarContentValues(car);
        return (int) mSQLiteDatabase.insert(CarTable.NAME, null, contentValues);
    }

    public void updateCar(Car car) {
        ContentValues values = getCarContentValues(car);
        mSQLiteDatabase.update(
                CarTable.NAME,
                values,
                CarTable.Cols.ID + " = ?",
                new String[]{String.valueOf(car.getId())});
    }

    private ContentValues getCarContentValues(Car car) {
        ContentValues values = new ContentValues();
        values.put(CarTable.Cols.MODEL_ID, car.getModel().getId());
        values.put(CarTable.Cols.YEAR_OF_ISSUE, car.getYearOfIssue());
        values.put(CarTable.Cols.BODY_TYPE, car.getBodyType().toString());
        values.put(CarTable.Cols.COLOR, car.getColor());
        values.put(CarTable.Cols.TRANSMISSION, car.isAutoTransmission() ? 1 : 0);
        values.put(CarTable.Cols.PRICE, car.getPrice());
        values.put(CarTable.Cols.POWER, car.getPower());
        values.put(CarTable.Cols.IMAGE_NAME, car.getImageFileName());
        return values;
    }

    public File getImageFile(Car car, Context context) {
        File fileDir = context.getFilesDir();
        return new File(fileDir, car.createImageFileName());
    }
}
