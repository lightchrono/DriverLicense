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
import android.widget.Toast;

import com.example.intern01.driverlicense.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    Activity activity;
    boolean isOn=false;
    Button loginB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity=this;
        loginB= (Button) findViewById(R.id.loginB);
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


                login("t1@t.com","123123");
                loginB.setEnabled(false);
            }
        });



    }


    private FirebaseAuth auth=null;
    private FirebaseDatabase db=null;

    private boolean init(){

            auth=FirebaseAuth.getInstance();
            db=FirebaseDatabase.getInstance();


        return true;
    }
    private void login(String email,String password){
        Log.d("firebase","firebase");
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("firebase2", "signInWithEmail:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){

                            Intent main=new Intent(getBaseContext(),MainActivity.class);
                            startActivity(main);
                            finish();
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("firebase", "signInWithEmail:failed", task.getException());
                            Toast.makeText(activity, "Auth failed", Toast.LENGTH_SHORT).show();
                            loginB.setEnabled(true);
                        }

                        // ...
                    }
                });
    }



}