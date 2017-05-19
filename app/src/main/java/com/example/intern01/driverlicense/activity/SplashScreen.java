package com.example.intern01.driverlicense.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.intern01.driverlicense.LoginActivity;
import com.example.intern01.driverlicense.R;

public class SplashScreen extends AppCompatActivity {

    Context context;
    Boolean isLogin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        context = this;

                SharedPreferences pref = getSharedPreferences("loginC",MODE_PRIVATE);
                String username = pref.getString("lc_email", null);
                String password = pref.getString("lc_pass", null);
                Log.d("splash","username: "+username+" pass: "+password);
                if((username==null)||(password==null)){
                    Log.d("splash","username: "+username+" pass: "+password);
                    isLogin=true;
                }




        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarSplash);
        progressBar.setMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 2) {
                    try {
                        i++;
                        Thread.sleep(1000);
                        progressBar.setProgress(progressBar.getProgress() + 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                if (isLogin) {
                    Intent login = new Intent(context, LoginActivity.class);
                    startActivity(login);
                    finish();
                }
                else{
                    Intent main = new Intent(context, MainActivity.class);
                    startActivity(main);
                    finish();
                }

            }
        }).start();

    }
}

