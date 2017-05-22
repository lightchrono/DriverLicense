package com.example.intern01.driverlicense.Classes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by intern01 on 22/05/2017.
 */

public class carClass {

    String userUID;
    FirebaseDatabase db;
    public carClass(String userUID,FirebaseDatabase db){
        this.userUID=userUID;
        this.db=db;
    }



    public boolean createCar(String carNickname,String producer,String model, String engine, String year, String engineDis,String horsePower,String fuelType,String driverWheel, String bodySeries, String coupeType, String numberDoors,String numberSeats, String color,String kmAtBuy){
        try{
            DatabaseReference root = db.getReference("Users");
            DatabaseReference userRoot = root.child(userUID);
            DatabaseReference car = userRoot.child(carNickname);
            DatabaseReference producerR = car.child("producer");
            producerR.setValue(producer);
            DatabaseReference modelR = car.child("model");
            modelR.setValue(model);
            DatabaseReference engineR = car.child("engine");
            engineR.setValue(engine);
            DatabaseReference yearR = car.child("year");
            yearR.setValue(year);
            DatabaseReference enginedisplacement = car.child("enginedisplacement");
            enginedisplacement.setValue(engineDis);
            DatabaseReference horsepower = car.child("horsepower");
            horsepower.setValue(horsePower);
            DatabaseReference fueltype = car.child("fueltype");
            fueltype.setValue(fuelType);
            DatabaseReference drivewheel = car.child("drivewheel");
            drivewheel.setValue(driverWheel);
            DatabaseReference bodyseries = car.child("bodyseries");
            bodyseries.setValue(bodySeries);
            DatabaseReference coupetype = car.child("coupetype");
            coupetype.setValue(coupeType);
            DatabaseReference numberdoors = car.child("numberdoors");
            numberdoors.setValue(numberDoors);
            DatabaseReference numberseats = car.child("numberseats");
            numberseats.setValue(numberSeats);
            DatabaseReference colorR = car.child("color");
            colorR.setValue(color);
            DatabaseReference kmatbuy = car.child("kmatbuy");
            kmatbuy.setValue(kmAtBuy);
        }
        catch (Exception ex){

            return false;
        }

        return true;
    }
}
