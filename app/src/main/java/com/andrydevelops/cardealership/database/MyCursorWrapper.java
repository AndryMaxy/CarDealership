package com.andrydevelops.cardealership.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.andrydevelops.cardealership.bean.Car;
import com.andrydevelops.cardealership.bean.CarBodyType;
import com.andrydevelops.cardealership.bean.Model;
import com.andrydevelops.cardealership.bean.Producer;

public class MyCursorWrapper extends CursorWrapper {

    public MyCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Car getCar(){
        int carId = getInt(getColumnIndex(CarDataBase.CarTable.Cols.ID));
        int producerId = getInt(getColumnIndex(CarDataBase.ProducerTable.Cols.PRODUCER_ID));
        int modelId = getInt(getColumnIndex(CarDataBase.CarTable.Cols.MODEL_ID));
        int yearOfIssue = getInt(getColumnIndex(CarDataBase.CarTable.Cols.YEAR_OF_ISSUE));
        String bodyType = getString(getColumnIndex(CarDataBase.CarTable.Cols.BODY_TYPE));
        String color = getString(getColumnIndex(CarDataBase.CarTable.Cols.COLOR));
        boolean autoTransmission = getInt(getColumnIndex(CarDataBase.CarTable.Cols.TRANSMISSION)) == 1;
        int price = getInt(getColumnIndex(CarDataBase.CarTable.Cols.PRICE));
        int power = getInt(getColumnIndex(CarDataBase.CarTable.Cols.POWER));
        String imageName = getString(getColumnIndex(CarDataBase.CarTable.Cols.IMAGE_NAME));
        String producer = getString(getColumnIndex(CarDataBase.ProducerTable.Cols.PRODUCER));
        String model = getString(getColumnIndex(CarDataBase.ModelTable.Cols.MODEL));

        Car car = new Car();
        car.setId(carId);
        car.setProducer(new Producer(producerId, producer));
        car.setModel(new Model(modelId, model));
        car.setYearOfIssue(yearOfIssue);
        car.setBodyType(CarBodyType.valueOf(bodyType));
        car.setColor(color);
        car.setAutoTransmission(autoTransmission);
        car.setPrice(price);
        car.setPower(power);
        car.setImageFileName(imageName);
        return car;
    }

    public Producer getProducer() {
        int id = getInt(getColumnIndex(CarDataBase.ProducerTable.Cols.PRODUCER_ID));
        String name = getString(getColumnIndex(CarDataBase.ProducerTable.Cols.PRODUCER));
        return new Producer(id, name);
    }

    public Model getModel() {
        int id = getInt(getColumnIndex(CarDataBase.ModelTable.Cols.MODEL_ID));
        String name = getString(getColumnIndex(CarDataBase.ModelTable.Cols.MODEL));
        return new Model(id, name);
    }
}
