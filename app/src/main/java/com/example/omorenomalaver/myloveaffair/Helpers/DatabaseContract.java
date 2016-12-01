package com.example.omorenomalaver.myloveaffair.Helpers;

import android.provider.BaseColumns;

/**
 * Created by omorenomalaver on 24/05/2016.
 */
public class DatabaseContract {

    public DatabaseContract() {}

    public static abstract class Configuration implements BaseColumns {
        public static final String TABLE_NAME = "configuration";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_BACKGROUND = "background";
        public static final String COLUMN_PASSWORD = "password";
        public static final String IF_NOT_EXISTS = "IF NOT EXISTS";

        public static final String CONFIGURATION_TABLE_CREATE =
                "CREATE TABLE " + IF_NOT_EXISTS + " " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_BACKGROUND + " TEXT NOT NULL, " +
                        COLUMN_PASSWORD + " TEXT NOT NULL);";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;


    }

}
