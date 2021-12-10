package com.example.project5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class delete extends myconstAndFun implements View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnFocusChangeListener {

    private BottomNavigationView nav;

    SQLiteDatabase db= null;

    private EditText id;

    private Button clear;
    private Button delete_do;

    private static final  String ChannelID= "kmfc";


    //
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inf=getMenuInflater ();
        inf.inflate (R.menu.menu,menu);
        if (menu!=null && menu instanceof MenuBuilder)
            ((MenuBuilder)menu).setOptionalIconsVisible ( true );
        return super.onCreateOptionsMenu ( menu );
    }
    //
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) { return super.onPrepareOptionsMenu ( menu ); }
    //
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) { return super.onMenuOpened ( featureId, menu ); }
    //
    @Override
    public void onOptionsMenuClosed(Menu menu) { super.onOptionsMenuClosed ( menu ); }
    //
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //getSupportActionBar ().setTitle ( item.getTitle ()+ "  is pressed" );
        switch(item.getItemId())
        {
            case R.id.insert_records:
            {
                insertF();
                break;
            }

            case R.id.search_records:
            {
                searchF();
                break;
            }

            case R.id.modify_records:
            {
                modifyF();
                break;
            }

            case R.id.display_records:
            {
                displayF();
                break;
            }
            default:{}
        }
        return super.onOptionsItemSelected ( item );
    }
    //
    @Override
    public boolean onSupportNavigateUp()
    {
        Log.w ("DeleteActivity", "onSupportNavigateUp is calll");
        onBackPressed ();
        return super.onSupportNavigateUp ();
    }
    //
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        id.clearFocus();
        return super.onKeyDown(keyCode, event);
    }
    //
    @Override
    public void onBackPressed()
    {
        //super.onBackPressed ();
        Log.w ("DeleteActivity", "this onbackpress is calll");

        AlertDialog.Builder   x= new AlertDialog.Builder ( this );
        x.setMessage ( "DO YOU WANT TO EXIT?" ).setTitle ( "Exit DeleteActivity" )

                .setPositiveButton ( "YES_EXIT", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.w ("DeleteActivity", "end");
                        Toast.makeText(getApplicationContext(), "Back...", Toast.LENGTH_SHORT).show();
                        db.close();
                        finish();
                    }
                } )

                .setNegativeButton ( "CANCEL", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })

                .setIcon(R.drawable.qus)
                .setPositiveButtonIcon (getDrawable ( R.drawable.yes))
                .setNegativeButtonIcon(getDrawable ( R.drawable.no))
                .show ();
        return;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Log.w ("DeleteActivity", "start");
        Toast.makeText(getApplicationContext(), "DeleteActivity...", Toast.LENGTH_SHORT).show();

        ActionBar bar = getSupportActionBar ();
        bar.setHomeButtonEnabled ( true );
        bar.setDisplayHomeAsUpEnabled ( true );
        bar.setHomeAsUpIndicator ( R.drawable.ex);
        bar.setTitle("delete a records.");

        nav = (BottomNavigationView)findViewById ( R.id.nb );
        nav.setItemIconTintList ( null );
        nav.setOnNavigationItemSelectedListener (this);
        clearSelection(nav);

        clearSelection(nav);
        final BottomNavigationMenuView menuView;
        menuView = (BottomNavigationMenuView) nav.getChildAt(0);
        BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(1);
        item.setChecked(true);

        db=openOrCreateDatabase (DbName,MODE_PRIVATE,null );
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


        id = findViewById (R.id.idDelete); id.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        id.setOnFocusChangeListener(this);

        delete_do = (Button) findViewById (R.id.deleteDelete);
        clear = (Button) findViewById (R.id.clearDelete);

        clear.setOnClickListener (this);
        delete_do.setOnClickListener (this);
    }

    public void onClick(View v) {

        if (v.getId() == R.id.clearDelete) {
            cleaR();
            return;
        }

        if (v.getId() == R.id.deleteDelete) {
            do_();
            return;
        }
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

    void cleaR() { id.setText(""); }
    void do_()
    {
        try {
            String str1 = id.getText().toString();
            if (str1.isEmpty())
            {
                AlertDialog.Builder x = new AlertDialog.Builder(this);
                x.setMessage("Please complete fill the form data.").setTitle("incomplete data")

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

            if(str1.length() >10)
            {
                AlertDialog.Builder x = new AlertDialog.Builder(this);
                x.setMessage("ID must be from 1 to 10 digits.").setTitle("ERROR")

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

            Cursor rs= db.rawQuery("select * from employees where ID="+str1+";",null);
            if(rs.getCount()<1)
            {
                AlertDialog.Builder x = new AlertDialog.Builder(this);
                x.setMessage("This ID does not exist, The ID must be exist.").setTitle("ERROR")

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

         ///

            String sqlInsert=String.format("delete from employees where ID=%d;",
                    Long.parseLong(str1));

            Intent i = new Intent(this,MyIntentService.class);
            i.setAction ( myconstAndFun.bupSer);
            i.putExtra (myconstAndFun.bupKey,sqlInsert);

            AlertDialog.Builder   x= new AlertDialog.Builder ( this );
            x.setMessage ( "Do you really want to delete this row?" ).setTitle ( "delete row" )

                    .setPositiveButton ( "YES_DELETE", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NotificationManager man= (NotificationManager)getSystemService ( NOTIFICATION_SERVICE );
                            NotificationCompat.Builder  note=null;


                            createChannel();

                            NotificationCompat.BigTextStyle bigtext = new NotificationCompat.BigTextStyle ();
                            bigtext.setBigContentTitle ( "Delete Service" );
                            bigtext.bigText ( sqlInsert );
                            bigtext.setSummaryText ( "done delete Employee his ID is:"+str1);

                            note = new NotificationCompat.Builder ( getApplicationContext(),ChannelID )
                                    .setContentTitle ( "Service" )
                                    .setSubText ( "delete Employe " )
                                    .setContentText ("done delete Employee his ID is:"+str1)
                                    .setOngoing ( false )
                                    .setColor ( Color.RED  )
                                    .setColorized ( true )
                                    .setPriority ( NotificationManager.IMPORTANCE_HIGH )
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .setShowWhen ( true )
                                    .setUsesChronometer ( true )
                                    .setSmallIcon ( R.drawable.icof )
                                    .setStyle ( bigtext )
                                    .setLargeIcon ( BitmapFactory.decodeResource ( getResources (),R.drawable.icof ) )
                                    .setAutoCancel ( true )
                            //.setOnlyAlertOnce(true)
                            //.addAction ( R.drawable.no,"Mark Complete", markCompleteIntent);
                            ;
                            man.notify (++ne, note.build ());
                            startService ( i );
                            Toast.makeText ( getApplicationContext(), "Done Successful Delete row.",Toast.LENGTH_SHORT ).show ();

                        }
                    } )

                    .setNegativeButton ( "CANCEL", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {   Toast.makeText ( getApplicationContext(), "delete row canceled.",Toast.LENGTH_SHORT ).show (); }
                    })

                    .setIcon(R.drawable.qus)
                    .setPositiveButtonIcon (getDrawable ( R.drawable.yes))
                    .setNegativeButtonIcon(getDrawable ( R.drawable.no))
                    .show ();
        }
        catch (Exception e)
        {
            AlertDialog.Builder x = new AlertDialog.Builder(this);
            x.setMessage("Unexpected error, please check input.").setTitle("Error")

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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Log.w ("onNavigationItemSelected", "up");

        switch(item.getItemId())
        {
            case R.id.insert_records:
            {
                insertF();
                break;
            }

            case R.id.search_records:
            {
                searchF();
                break;
            }

            case R.id.modify_records:
            {
                modifyF();
                break;
            }

            case R.id.display_records:
            {
                displayF();
                break;
            }
            default:{}
        }

        Log.w ("onNavigationItemSelected", "down");
        return false;
    }


    //
///////////////////////////////////////////////////////////////////
//
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v==id)
        {
            if (!hasFocus) {
                Log.d("focus", "focus lost");
                // Do whatever you want here
            } else {
                Toast.makeText(getApplicationContext(), " Tap outside edittext to lose focus ", Toast.LENGTH_SHORT).show();
                Log.d("focus", "focused");
            }
        }
    }
    //
    // <!-- Clear focus on touch outside for all EditText inputs. "Clear focus input"
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

// "Clear focus input" -->

}