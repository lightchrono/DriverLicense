package com.example.intern01.driverlicense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    FirebaseClass firebase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        new Thread(new Runnable() {
            @Override
            public void run() {
                firebase = new FirebaseClass();
                switch (firebase.init()) {
                    case -2: {
                        Log.d("FIREBASE", "Already Connected");
                        break;
                    }
                    case -1: {
                        Log.d("FIREBASE", "Error! not conncted");
                        //popup Connection Error => Exit
                        break;
                    }
                    case 0: {
                        Log.d("FIREBASE", "Connected ON");

                        break;
                    }
                }
            }
        }).start();


        Button registerB = (Button) findViewById(R.id.confirmRegisterB);
        Button cancelB = (Button) findViewById(R.id.cancelRegisterB);
        final EditText email = (EditText) findViewById(R.id.registerETEmail);
        final EditText password = (EditText) findViewById(R.id.registerETPassword);

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        firebase.createAccount(String.valueOf(email.getText()), String.valueOf(password.getText()));
                    }
                }).start();
            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
