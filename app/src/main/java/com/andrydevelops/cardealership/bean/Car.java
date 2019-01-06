package com.andrydevelops.cardealership.bean;

public class Car {

    private int mId;
    private Model mModel;
    private Producer mProducer;
    private int mYearOfIssue;
    private CarBodyType mBodyType;
    private String mColor;
    private boolean mIsAutoTransmission;
    private int mPrice;
    private int mPower;
    private String mPhotoFileName;

    public Model getModel() {
        return mModel;
    }

    public void setModel(Model model) {
        mModel = model;
    }

    public Producer getProducer() {
        return mProducer;
    }

    public void setProducer(Producer producer) {
        mProducer = producer;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getYearOfIssue() {
        return mYearOfIssue;
    }

    public void setYearOfIssue(int yearOfIssue) {
        mYearOfIssue = yearOfIssue;
    }

    public CarBodyType getBodyType() {
        return mBodyType;
    }

    public void setBodyType(CarBodyType bodyType) {
        mBodyType = bodyType;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public boolean isAutoTransmission() {
        return mIsAutoTransmission;
    }

    public void setAutoTransmission(boolean autoTransmission) {
        mIsAutoTransmission = autoTransmission;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public int getPower() {
        return mPower;
    }

    public void setPower(int power) {
        mPower = power;
    }

    public String getImageFileName() {
         return mPhotoFileName;
    }

    public void setImageFileName(String photoFileName) {
        mPhotoFileName = photoFileName;
    }

    public String createImageFileName() {
        if (mPhotoFileName != null) {
            return mPhotoFileName;
        }
        mPhotoFileName = "IMG_" + getId() + ".jpg";
        return mPhotoFileName;
    }
}
