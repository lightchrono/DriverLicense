package com.example.intern01.driverlicense.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.intern01.driverlicense.LoginActivity;
import com.example.intern01.driverlicense.R;

public class SplashScreen extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = this;

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        })

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
                if (true) {
                    Intent login = new Intent(context, LoginActivity.class);
                    startActivity(login);
                    finish();
                }
            }
        }).start();

    }
}

