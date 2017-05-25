package com.example.intern01.driverlicense;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.intern01.driverlicense.Classes.carClass;
import com.example.intern01.driverlicense.Service.NotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;

    String user;
    String carS = "v";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        if (getIntent().getExtras() == null) user = "not";
        else {
            user = getIntent().getExtras().getString("USER");
        }

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        Button b = (Button) findViewById(R.id.testButton);
        b.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Log.d("username", user);
                carClass car=new carClass(user,db);
                //car.createCar();
                Intent serviceI=new Intent(TestActivity.this,NotificationService.class);
                startService(serviceI);




            }
        });
    }
}


    /*DatabaseReference root = db.getReference("Users");
    DatabaseReference userRoot = root.child(user);
    DatabaseReference car = userRoot.child(carS);
    DatabaseReference producer = car.child("producer");
                producer.setValue("");
                        DatabaseReference model = car.child("model");
                        model.setValue("");
                        DatabaseReference engine = car.child("engine");
                        engine.setValue("");
                        DatabaseReference year = car.child("year");
                        year.setValue("");
                        DatabaseReference enginedisplacement = car.child("enginedisplacement");
                        enginedisplacement.setValue("");
                        DatabaseReference horsepower = car.child("horsepower");
                        horsepower.setValue("");
                        DatabaseReference fueltype = car.child("fueltype");
                        fueltype.setValue("");
                        DatabaseReference drivewheel = car.child("drivewheel");
                        drivewheel.setValue("");
                        DatabaseReference bodyseries = car.child("bodyseries");
                        bodyseries.setValue("");
                        DatabaseReference coupetype = car.child("coupetype");
                        coupetype.setValue("");
                        DatabaseReference numberdoors = car.child("numberdoors");
                        numberdoors.setValue("");
                        DatabaseReference numberseats = car.child("numberseats");
                        numberseats.setValue("");
                        DatabaseReference color = car.child("color");
                        color.setValue("");
                        DatabaseReference kmatbuy = car.child("kmatbuy");
                        kmatbuy.setValue("");*/