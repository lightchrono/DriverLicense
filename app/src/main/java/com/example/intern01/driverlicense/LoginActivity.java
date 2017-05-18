package com.example.intern01.driverlicense;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    FirebaseClass firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new Thread(new Runnable() {
            @Override
            public void run() {
                firebase=new FirebaseClass();
                switch (firebase.init()){
                    case -2:{
                        Log.d("FIREBASE","Already Connected");
                        break;
                    }
                    case -1:{
                        Log.d("FIREBASE","Error! not conncted");
                        //popup Connection Error => Exit
                        break;
                    }
                    case 0:{
                        Log.d("FIREBASE","Connected ON");

                        break;
                    }
                }
            }
        }).start();





        TextView registerTV=(TextView) findViewById(R.id.registerTV);
        Button loginB=(Button) findViewById(R.id.loginB);

        final Intent registerI=new Intent(this, RegisterActivity.class);

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerI);
            }
        });


        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}