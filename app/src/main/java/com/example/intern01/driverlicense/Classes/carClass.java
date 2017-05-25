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
            DatabaseReference generalInfo=car.child("generalInfo");
            
            DatabaseReference producerR = generalInfo.child("producer");
            producerR.setValue(producer);
            DatabaseReference modelR = generalInfo.child("model");
            modelR.setValue(model);
            DatabaseReference engineR = generalInfo.child("engine");
            engineR.setValue(engine);
            DatabaseReference yearR = generalInfo.child("year");
            yearR.setValue(year);
            DatabaseReference enginedisplacement = generalInfo.child("enginedisplacement");
            enginedisplacement.setValue(engineDis);
            DatabaseReference horsepower = generalInfo.child("horsepower");
            horsepower.setValue(horsePower);
            DatabaseReference fueltype = generalInfo.child("fueltype");
            fueltype.setValue(fuelType);
            DatabaseReference drivewheel = generalInfo.child("drivewheel");
            drivewheel.setValue(driverWheel);
            DatabaseReference bodyseries = generalInfo.child("bodyseries");
            bodyseries.setValue(bodySeries);
            DatabaseReference coupetype = generalInfo.child("coupetype");
            coupetype.setValue(coupeType);
            DatabaseReference numberdoors = generalInfo.child("numberdoors");
            numberdoors.setValue(numberDoors);
            DatabaseReference numberseats = generalInfo.child("numberseats");
            numberseats.setValue(numberSeats);
            DatabaseReference colorR = generalInfo.child("color");
            colorR.setValue(color);
            DatabaseReference kmatbuy = generalInfo.child("kmatbuy");
            kmatbuy.setValue(kmAtBuy);
        }
        catch (Exception ex){

            return false;
        }

        return true;
    }
}
