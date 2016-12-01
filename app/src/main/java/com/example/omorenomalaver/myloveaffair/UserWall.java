package com.example.omorenomalaver.myloveaffair;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.omorenomalaver.myloveaffair.Helpers.Configuration;
import com.example.omorenomalaver.myloveaffair.Helpers.DatabaseContract;
import com.example.omorenomalaver.myloveaffair.Helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class UserWall extends AppCompatActivity {

    private static final String METHOD_NAME = "showAllMessages";
    private static final String NAMES = "showAllMessages";


    private DatabaseHelper mDbHelper;
    private static final String TAG = UserWall.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall);
        mDbHelper = new DatabaseHelper(this);
        showValues();
        addListValues();
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
            final RelativeLayout myRelativeLayout = (RelativeLayout) findViewById(R.id.activity_user_layout);
            Log.i(TAG, "showValues get_background:" + config.get_background());

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

    public void onClickConfig(View v){
        Intent myIntent = new Intent(this, ConfigApp.class);
        startActivity(myIntent);

    }

    public void onClickExit(View v)
    {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    private void addListValues()
    {
        final RelativeLayout myRelativeLayout = (RelativeLayout)findViewById(R.id.activity_user_layout);
        String[] backgroundList = {"luisa 1500 new messages", "anna 2 new messages","candy not messages","Sophie 1 new message","maria 255 new messages"," + ADD NEW CONTACT"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listbackground,backgroundList);
        ListView listView = (ListView)findViewById(R.id.listViewContacts);
        listView.setAdapter(adapter);

    }

}
