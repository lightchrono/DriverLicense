package com.example.intern01.driverlicense;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.intern01.driverlicense.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity=this;
        if(init()){
            Log.d("firebase","Connection established");
        }
        else{
            db=null;
            auth=null;
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Could not connect to server").setTitle("Connection Error").setCancelable(false)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LoginActivity.super.finish();
                }
            });
            builder.show();
        }




        TextView registerTV = (TextView) findViewById(R.id.registerTV);
        Button loginB = (Button) findViewById(R.id.loginB);


        final Intent registerI = new Intent(this, RegisterActivity.class);

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerI);
            }
        });


        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent mainAct=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(mainAct);
                    finish();


            }
        });



    }


    private FirebaseAuth auth=null;
    private FirebaseDatabase db=null;

    private boolean init(){
        try{
            auth=FirebaseAuth.getInstance();
        }
        catch (Exception authEx){
            return false;
        }
        try{
            db=FirebaseDatabase.getInstance();
        }
        catch (Exception dbEx){
            return false;
        }
        if((auth==null)||(db==null)){
            return false;
        }
        return true;
    }




}