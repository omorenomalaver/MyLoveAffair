package com.example.omorenomalaver.myloveaffair.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.net.ContentHandler;

/**
 * Created by omorenomalaver on 24/05/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    // Defining database variables.
    private static final String DATABASE_NAME = "android_class.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        // This will generate a table if it does not already exists.
        db.execSQL(DatabaseContract.Configuration.CONFIGURATION_TABLE_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        Log.i(TAG, "onUpgrade");
        db.execSQL(DatabaseContract.Configuration.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onDowngrade");
        onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor getConfiguration(){
        Log.i(TAG, "getConfiguration");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from configuration",null);
        if(res.moveToFirst()){
            Configuration myConf = new Configuration();
            myConf.set_id(Integer.parseInt(res.getString(0)));
        }
        return res;
    }

}
