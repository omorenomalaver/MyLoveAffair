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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omorenomalaver.myloveaffair.Helpers.Configuration;
import com.example.omorenomalaver.myloveaffair.Helpers.DatabaseContract;
import com.example.omorenomalaver.myloveaffair.Helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ConfigApp extends AppCompatActivity {

    private DatabaseHelper mDbHelper;
    private static final String TAG = ConfigApp.class.getSimpleName();
    private String wallPaperToUpdate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_app);

        // Instantiating the helper.
        mDbHelper = new DatabaseHelper(this);
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();
        //mDbHelper.onUpgrade(db,1,2);
        showValues();
        addListValues();

    }

    private void addListValues()
    {
        final RelativeLayout myRelativeLayout = (RelativeLayout)findViewById(R.id.configLayout);
        String[] backgroundList = {"birds background","city background","clouds background","faces background"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listbackground,backgroundList);
        ListView listView = (ListView)findViewById(R.id.listViewBackground);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        myRelativeLayout.setBackgroundResource(R.drawable.birds);
                        wallPaperToUpdate = "birds";
                        break;
                    case 1:
                        myRelativeLayout.setBackgroundResource(R.drawable.city);
                        wallPaperToUpdate = "city";
                        break;
                    case 2:
                        myRelativeLayout.setBackgroundResource(R.drawable.clouds);
                        wallPaperToUpdate = "clouds";
                        break;
                    case 3:
                        myRelativeLayout.setBackgroundResource(R.drawable.faces);
                        wallPaperToUpdate = "faces";
                        break;

                    default:
                        Log.i(TAG, "addListValues default something break ");
                        break;
                }

            }
        });
    }

    public void onClickUpdate(View v){
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        String myPassword = txtPassword.getText().toString();

        Log.i(TAG, "onClickUpdate myPassword:" + myPassword);

        if(myPassword.equals("")){
            Toast.makeText(this, "You Must Add a Password", Toast.LENGTH_SHORT).show();
        }
        else if(wallPaperToUpdate==""){
            Toast.makeText(this, "You Must Modify Your Wallpaper", Toast.LENGTH_SHORT).show();
        }
        else if(wallPaperToUpdate==null){
            Toast.makeText(this, "You Must Update Your Wallpaper", Toast.LENGTH_SHORT).show();
        }
        else {
            updateData(myPassword,wallPaperToUpdate);
            Intent myIntent = new Intent(this, UserWall.class);
            startActivity(myIntent);
        }

    }

    private void updateData(String password, String background)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int idInteger = 1;
        try{
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.Configuration.COLUMN_BACKGROUND, background);
            values.put(DatabaseContract.Configuration.COLUMN_PASSWORD, password);
            String selection = DatabaseContract.Configuration.COLUMN_NAME_ID + " LIKE ?";
            String[] selectionArgs = { String.valueOf(idInteger) };
            int count = db.update(
                    DatabaseContract.Configuration.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);

            db.setTransactionSuccessful();
            Toast.makeText(this, "Row " + idInteger + " successfully updated.", Toast.LENGTH_SHORT)
                    .show();
        }
        catch (SQLException e) {
            Toast.makeText(this, "Unable to update row, error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } finally {
            db.endTransaction();
            db.close();
        }

    }

    /**
     *
     */
    public void showValues() {

        Log.i(TAG, "showValues: start");
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selectQuery = "Select * from " + DatabaseContract.Configuration.TABLE_NAME;
        Log.i(TAG, "showValues: selectQuery:" + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            Configuration config = new Configuration();
            config.set_id(Integer.parseInt(cursor.getString(0)));
            config.set_background(cursor.getString(1));
            config.set_password(cursor.getString(2));
            final RelativeLayout myRelativeLayout = (RelativeLayout) findViewById(R.id.configLayout);

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
                    break;
            }

            EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
            txtPassword.setText(config.get_password());
            Log.i(TAG, "showValues get_background:" + config.get_background());
            Log.i(TAG, "showValues get_password:" +config.get_password() );
        }
    }



    public void onClickInsert(View v) {
        showValues();
    }



}
