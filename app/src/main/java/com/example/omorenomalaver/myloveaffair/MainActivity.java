package com.example.omorenomalaver.myloveaffair;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omorenomalaver.myloveaffair.Helpers.Configuration;
import com.example.omorenomalaver.myloveaffair.Helpers.DatabaseContract;
import com.example.omorenomalaver.myloveaffair.Helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String myResult;
    private String myPassword;
    private DatabaseHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new DatabaseHelper(this);
        //insertValues();
        showValues();
    }

    public void showValues() {
        Log.i(TAG, "showValues: start");
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selectQuery = "Select * from " + DatabaseContract.Configuration.TABLE_NAME;
        Log.i(TAG, "showValues: selectQuery:" + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Cursor cursor = mDbHelper.getConfiguration();
        if (cursor.moveToFirst()){
            List<Configuration> configurationList = new ArrayList<Configuration>();
            Log.i(TAG, "showValues: working access database");
            Configuration config = new Configuration();
            config.set_id(Integer.parseInt(cursor.getString(0)));
            config.set_password(cursor.getString(2));
            config.set_background(cursor.getString(1));
            final RelativeLayout myRelativeLayout = (RelativeLayout) findViewById(R.id.activity_main_layout);
            Log.i(TAG, "showValues get_background:" + config.get_background());
            myPassword = config.get_password();
            switch (config.get_background()) {
                case "birds":
                    myRelativeLayout.setBackgroundResource(R.drawable.birds);
                    break;
                case "city":
                    myRelativeLayout.setBackgroundResource(R.drawable.city);
                    break;
                case "clouds":
                    myRelativeLayout.setBackgroundResource(R.drawable.clouds);
                    break;
                case "faces":
                    myRelativeLayout.setBackgroundResource(R.drawable.faces);
                    break;
                default:
                    Log.i(TAG, "showValues: case working but something is wrong");
                    break;
            }
        }
        else{
            Log.i(TAG, "showValues: no access database");
        }
    }

    public void onClickButton(View v){
        Log.i(TAG, "You Clicked Something!" + v.getId());
        TextView textView = (TextView) findViewById(R.id.tvResult);
        switch (v.getId()){
            case R.id.btn01:
                myResult = "1";
                break;
            case R.id.btn02:
                myResult = "2";
                break;
            case R.id.btn03:
                myResult = "3";
                break;
            case R.id.btn04:
                myResult = "4";
                break;
            case R.id.btn05:
                myResult = "5";
                break;
            case R.id.btn06:
                myResult = "6";
                break;
            case R.id.btn07:
                myResult = "7";
                break;
            case R.id.btn08:
                myResult = "8";
                break;
            case R.id.btn09:
                myResult = "9";
                break;
            case R.id.btnDel:
                myResult = "";
                break;
            default:
                break;
        }
        if(myResult=="")
            textView.setText("");

        myResult =  textView.getText().toString() +myResult;

        if(myResult.equals(myPassword)){
            Intent myIntent = new Intent(this, UserWall.class);
            startActivity(myIntent);
        }

        textView.setText(myResult);

    }

    private void insertValues(){
        Log.i(TAG, "start insertValues ");
        // Retrieving the Writable database with which we are going to add the data.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.Configuration.COLUMN_BACKGROUND, "cells");
            values.put(DatabaseContract.Configuration.COLUMN_PASSWORD, "666");
            long newRowId;
            newRowId = db.insert(DatabaseContract.Configuration.TABLE_NAME,
                    DatabaseContract.Configuration.COLUMN_NAME_ID, values);

            db.setTransactionSuccessful();

            // Notify the user of the successful action.
            Toast.makeText(this, "Added row: " + newRowId, Toast.LENGTH_SHORT).show();

        } catch (SQLException e) {
            Log.i(TAG, "error insertValues " + e.getMessage());
            Toast.makeText(this, "Unable to add row, error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
