package com.example.intern01.driverlicense.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.intern01.driverlicense.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final ProgressBar progressBar=(ProgressBar) findViewById(R.id.progressBarSplash);
        progressBar.setMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(i<5){
                    try {
                        i++;
                        Thread.sleep(1000);
                        progressBar.setProgress(progressBar.getProgress()+10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();

    }
}

