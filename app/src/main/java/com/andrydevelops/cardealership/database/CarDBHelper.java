package com.andrydevelops.cardealership.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.andrydevelops.cardealership.bean.CarBodyType;

import static com.andrydevelops.cardealership.database.CarDataBase.*;

public class CarDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "carDB.db";
    private static final String PRODUCER_TABLE_CREATE = "create table "
            + ProducerTable.NAME + " ("
            + ProducerTable.Cols.PRODUCER_ID
            + " integer primary key autoincrement, "
            + ProducerTable.Cols.PRODUCER + ")";
    private static final String MODEL_TABLE_CREATE = "create table "
            + ModelTable.NAME + " ("
            + ModelTable.Cols.MODEL_ID
            + " integer primary key autoincrement, "
            + ModelTable.Cols.PRODUCER_ID + ", "
            + ModelTable.Cols.MODEL + ", "
            + "foreign key ("
            + ModelTable.Cols.PRODUCER_ID + ")"
            + " references " + ProducerTable.NAME + "("
            + ProducerTable.Cols.PRODUCER_ID + ")" + ")";
    private static final String CAR_TABLE_CREATE = "create table "
            + CarTable.NAME + " ("
            + CarTable.Cols.ID
            + " integer primary key autoincrement, "
            + CarTable.Cols.MODEL_ID + ", "
            + CarTable.Cols.BODY_TYPE + ", "
            + CarTable.Cols.COLOR + ", "
            + CarTable.Cols.POWER + ", "
            + CarTable.Cols.PRICE + ", "
            + CarTable.Cols.YEAR_OF_ISSUE + ", "
            + CarTable.Cols.TRANSMISSION + ", "
            + CarTable.Cols.IMAGE_NAME + ", "
            + "foreign key (" +
            CarTable.Cols.MODEL_ID + ")" +
            " references " + ModelTable.NAME + "(" +
            ModelTable.Cols.MODEL_ID + ")" + ")";

    private static final String[] PRODUCERS = {"BMW", "Audi", "Mercedes"};
    private static final long[] PRODUCER_IDS = new long[3];
    private static final String[] BMW_MODELS = {"X5", "X6", "i8"};
    private static final long[] BMW_MODELS_IDS = new long[3];
    private static final String[] AUDI_MODELS = {"A3", "A4"};
    private static final long[] AUDI_MODELS_IDS = new long[2];
    private static final String[] MERCEDES_MODELS = {"A180", "E140", "C200"};
    private static final long[] MERCEDES_MODELS_IDS = new long[3];

    public CarDBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PRODUCER_TABLE_CREATE);
        db.execSQL(MODEL_TABLE_CREATE);
        db.execSQL(CAR_TABLE_CREATE);
        insertProducers(db);
        insertModels(db, PRODUCER_IDS[0], BMW_MODELS, BMW_MODELS_IDS);
        insertModels(db, PRODUCER_IDS[1], MERCEDES_MODELS, MERCEDES_MODELS_IDS);
        insertModels(db, PRODUCER_IDS[2], AUDI_MODELS, AUDI_MODELS_IDS);
        insertCars(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insertProducers(SQLiteDatabase db) {
        for (int i = 0; i < PRODUCERS.length; i++) {
            ContentValues contentValues = getProducerContentValues(PRODUCERS[i]);
            PRODUCER_IDS[i] = db.insert(ProducerTable.NAME, null, contentValues);
        }
    }

    private void insertModels(SQLiteDatabase db, long sourceId,  String[] modelValues, long[] resultIds) {
        for (int i = 0; i < modelValues.length; i++) {
            ContentValues contentValues = getModelContentValues(sourceId, modelValues[i]);
            resultIds[i] = db.insert(ModelTable.NAME, null, contentValues);
        }
    }

    private long insertCar(SQLiteDatabase db, ContentValues values) {
        return db.insert(CarTable.NAME, null, values);
    }

    private ContentValues getProducerContentValues(String value) {
        ContentValues values = new ContentValues();
        values.put(ProducerTable.Cols.PRODUCER, value);
        return values;
    }

    private ContentValues getModelContentValues(long id, String model) {
        ContentValues values = new ContentValues();
        values.put(ModelTable.Cols.PRODUCER_ID, id);
        values.put(ModelTable.Cols.MODEL, model);
        return values;
    }

    private ContentValues getCarContentValues(long id, CarBodyType carBodyType, int year, int price, int power, String color, boolean transmission) {
        ContentValues values = new ContentValues();
        values.put(CarTable.Cols.MODEL_ID, id);
        values.put(CarTable.Cols.BODY_TYPE, carBodyType.toString());
        values.put(CarTable.Cols.YEAR_OF_ISSUE, year);
        values.put(CarTable.Cols.PRICE, price);
        values.put(CarTable.Cols.POWER, power);
        values.put(CarTable.Cols.COLOR, color);
        values.put(CarTable.Cols.TRANSMISSION, transmission ? 1 : 0);
        return values;
    }

    private void insertCars(SQLiteDatabase db) {
        ContentValues contentValues1 = getCarContentValues(BMW_MODELS_IDS[0], CarBodyType.SUV, 2005, 13000, 167, "серый", false);
        insertCar(db, contentValues1);
        ContentValues contentValues2 = getCarContentValues(AUDI_MODELS_IDS[1], CarBodyType.PICKUP, 2003, 17900, 150, "крсаный", false);
        insertCar(db, contentValues2);
        ContentValues contentValues3 = getCarContentValues(BMW_MODELS_IDS[2], CarBodyType.SEDAN, 2014, 38300, 198, "золотой", true);
        insertCar(db, contentValues3);
        ContentValues contentValues4 = getCarContentValues(AUDI_MODELS_IDS[0], CarBodyType.SEDAN, 2006, 21200, 195, "голубой", true);
        insertCar(db, contentValues4);
        ContentValues contentValues5 = getCarContentValues(AUDI_MODELS_IDS[0], CarBodyType.WAGON, 2005, 29500, 210, "синий", false);
        insertCar(db, contentValues5);
        ContentValues contentValues6 = getCarContentValues(BMW_MODELS_IDS[1], CarBodyType.HATCHBACK, 2008, 35000, 190, "черный", true);
        insertCar(db, contentValues6);
        ContentValues contentValues7 = getCarContentValues(MERCEDES_MODELS_IDS[1], CarBodyType.SEDAN, 2011, 24000, 276, "черный", false);
        insertCar(db, contentValues7);
        ContentValues contentValues8 = getCarContentValues(BMW_MODELS_IDS[0], CarBodyType.HATCHBACK, 2007, 15500, 205, "белый", false);
        insertCar(db, contentValues8);
        ContentValues contentValues9 = getCarContentValues(AUDI_MODELS_IDS[1], CarBodyType.SEDAN, 1990, 9000, 223, "серый", false);
        insertCar(db, contentValues9);
        ContentValues contentValues10 = getCarContentValues(MERCEDES_MODELS_IDS[0], CarBodyType.SUV, 2014, 21000, 295, "зеленый", true);
        insertCar(db, contentValues10);
        ContentValues contentValues11 = getCarContentValues(BMW_MODELS_IDS[0], CarBodyType.HATCHBACK, 1998, 16800, 170, "красный", false);
        insertCar(db, contentValues11);
        ContentValues contentValues12 = getCarContentValues(MERCEDES_MODELS_IDS[2], CarBodyType.SEDAN, 2000, 19000, 210, "зеленый", false);
        insertCar(db, contentValues12);
    }
}
