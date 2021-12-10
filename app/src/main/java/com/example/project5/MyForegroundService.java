package com.example.project5;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MyForegroundService extends Service {

    private  static final String ChannelID="yci";

    public MyForegroundService() {
    }

    @Override
    public void onCreate() {
        System.out.println("\nForeGroundService t the oncreate is been called...");
        super.onCreate ();
    }

    @Override
    public void onDestroy() {

        System.out.println("\nForeGroundService the OnDestroy  is been called...");
        super.onDestroy ();

    }

    private void createChannel()
    {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel x;
            x=new NotificationChannel (ChannelID,"My  Hi Channel with you"  , NotificationManager.IMPORTANCE_HIGH);

            NotificationManager  man= (NotificationManager)getSystemService ( NOTIFICATION_SERVICE );

            man.createNotificationChannel ( x );


        }

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("\nForeGroundService t the OnStartCommand() is been called...");

        createChannel();
        NotificationCompat.Builder notefor=null;

        notefor = new NotificationCompat.Builder ( getApplicationContext (),ChannelID )
                .setAutoCancel ( true )
                .setContentTitle ( "My first encounter with Notific" )
                .setContentText ( "hello you . i am testinth enotification...." )
                .setSmallIcon (R.drawable.iii )
                .setOngoing ( true )
                .setColor ( Color.BLUE )
                .setUsesChronometer ( true );

        startForeground ( 100, notefor.build () );
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



}