package com.example.project5;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class myconstAndFun  extends AppCompatActivity {
    protected static final String DbName="myDB";
    protected static final int perSD=999;

    static    int ne=1;
    //protected static final String rowAll="rowAll";
   // protected static final String rowKey="102";
   // protected static final String rowAllB="rowNumber";
    //protected static final String rowKeyB="101";

    protected static final String bupSer ="serBup";
    protected static final String bupKey ="123";

    //protected static final String ds ="serBup";
    //protected static final String dsKey ="123";

    protected void insertF()
    {
        Intent i  = new Intent( this, insert.class);
        startActivity ( i );
    }

    protected void deleteF()
    {
        Intent i  = new Intent( this, delete.class);
        startActivity ( i );
    }

    protected void searchF()
    {
        Intent i  = new Intent( this, search.class);
        startActivity ( i );
    }

    protected void modifyF()
    {
        Intent i  = new Intent( this, modify.class);
        startActivity ( i );
    }

    protected void displayF()
    {
        Intent i  = new Intent( this, display.class);
        startActivity ( i );
    }



    //
    @SuppressLint("RestrictedApi")
    public static void clearSelection(BottomNavigationView view)
    {
        final BottomNavigationMenuView menuView;
        menuView = (BottomNavigationMenuView) view.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++)
        {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            item.setChecked(false);
        }
    }
    //
}
