package com.example.intern01.driverlicense;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    FirebaseClass firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebase=new FirebaseClass();
        switch (firebase.dbConnect()){
            case -2:{
                Log.d("FIREBASE","Already Connected");
                break;
            }
            case -1:{
                Log.d("FIREBASE","Error! not conncted");
                break;
            }
            case 0:{
                Log.d("FIREBASE","Connected ON");
                firebase.test();
                break;
            }
        }



        TextView registerTV=(TextView) findViewById(R.id.registerTV);
        Button loginB=(Button) findViewById(R.id.loginB);


        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.layout_register);
            }
        });


        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
