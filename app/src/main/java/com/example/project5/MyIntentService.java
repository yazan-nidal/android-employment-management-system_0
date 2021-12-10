package com.example.project5;

import android.app.IntentService;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class MyIntentService extends IntentService {

    SQLiteDatabase db= null;

    public MyIntentService() {
        super ( "MyIntentService" );
        System.out.println("IntentService--> calls COntructor");
    }


    @Override
    public void onCreate() {

        System.out.println("IntentService--> calls OnCreate");

        db=openOrCreateDatabase (myconstAndFun.DbName,MODE_PRIVATE,null );
        if(db==null)
        {
            AlertDialog.Builder x = new AlertDialog.Builder(this);
            x.setMessage("No DataBase.").setTitle("Error")

                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })

                    .setIcon(R.drawable.goo)
                    .setPositiveButtonIcon(getDrawable(R.drawable.yes))

                    .show();
            return;
        }
        super.onCreate ();
    }

    @Override
    public void onDestroy() {

        System.out.println("IntentService--> calls onDestroy");
        super.onDestroy ();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        System.out.println("IntentService--> calls Onstart command()");
        return super.onStartCommand ( intent, flags, startId );
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent.getAction ().equals ( myconstAndFun.bupSer)) {
            String S= intent.getStringExtra (myconstAndFun.bupKey);
            db.execSQL(S);

            return;
        }

    }
}