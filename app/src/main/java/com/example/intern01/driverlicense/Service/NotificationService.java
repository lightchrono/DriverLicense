package com.example.intern01.driverlicense.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.intern01.driverlicense.R;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(i<10) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    NotificationCompat.Builder notf = (NotificationCompat.Builder) new NotificationCompat.Builder(getBaseContext())
                            .setContentTitle("Notf")
                            .setContentText("Random Text")
                            .setSmallIcon(R.mipmap.ic_launcher);
                    NotificationManager notfMang = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notfMang.notify(001, notf.build());
                    i++;
                }
            }
        }).start();
        stopSelf();
    }
}
