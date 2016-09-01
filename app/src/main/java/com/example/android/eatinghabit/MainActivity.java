package com.example.android.eatinghabit;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.eatinghabit.data.EatingContract;
import com.example.android.eatinghabit.data.EatingDbHelper;

public class MainActivity extends AppCompatActivity {
    private EditText mMenuEdit;
    private EditText mTimeEdit;
    private EditText mDateEdit;
    private EditText mCalroiesEdit;
    private TextView mInfo;
    private Button mAdd;
    private EatingDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMenuEdit = (EditText)findViewById(R.id.menu);
        mTimeEdit = (EditText)findViewById(R.id.when);
        mDateEdit = (EditText)findViewById(R.id.date);
        mCalroiesEdit = (EditText)findViewById(R.id.calories);
        mInfo = (TextView)findViewById(R.id.info);
        mDbHelper = new EatingDbHelper(this);
        mAdd = (Button)findViewById(R.id.add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertEatingHabit();
                displayDatabaseInfo();
            }
        });

    }

    public void insertEatingHabit() {
        String menu = mMenuEdit.getText().toString().trim();
        String time = mTimeEdit.getText().toString().trim();
        int timeInt = Integer.parseInt(time);
        String date = mDateEdit.getText().toString().trim();
        String calories = mCalroiesEdit.getText().toString().trim();
        int caloriesInt = Integer.parseInt(calories);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EatingContract.EatingEntry.COLUMN_EATING_MENU, menu);
        values.put(EatingContract.EatingEntry.COLUMN_EATING_TIME,timeInt);
        values.put(EatingContract.EatingEntry.COLUMN_EATING_CALROIES, caloriesInt);
        values.put(EatingContract.EatingEntry.COLUMN_EATING_DATE,date);
        long newRowId = db.insert(EatingContract.EatingEntry.TABLE_NAME, null, values);


    }

    @Override
    protected void onStart() {
        super.onStart();
       displayDatabaseInfo();
    }

    private void deleteAllEntries(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.execSQL("DELETE FROM " + EatingContract.EatingEntry.TABLE_NAME);

    }

    private Cursor getCursor(){

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                EatingContract.EatingEntry._ID,
                EatingContract.EatingEntry.COLUMN_EATING_MENU,
                EatingContract.EatingEntry.COLUMN_EATING_TIME,
                EatingContract.EatingEntry.COLUMN_EATING_CALROIES,
                EatingContract.EatingEntry.COLUMN_EATING_DATE};
        Cursor cursor = db.query(
                EatingContract.EatingEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        return cursor;
    }

    private void displayDatabaseInfo() throws NullPointerException{

        Cursor cursor = getCursor();
        mInfo.setText("---------DB---------\n");
        try {

            int idColumnIndex = cursor.getColumnIndex(EatingContract.EatingEntry._ID);
            int menuColumnIndex = cursor.getColumnIndex(EatingContract.EatingEntry.COLUMN_EATING_MENU);
            int timeColumnIndex = cursor.getColumnIndex(EatingContract.EatingEntry.COLUMN_EATING_TIME);
            int caloriesColumnIndex = cursor.getColumnIndex(EatingContract.EatingEntry.COLUMN_EATING_CALROIES);
            int dateColumnIndex = cursor.getColumnIndex(EatingContract.EatingEntry.COLUMN_EATING_DATE);
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentMenu = cursor.getString(menuColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);
                int currentCalories = cursor.getInt(caloriesColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);

                mInfo.append(""+currentID+" "+currentMenu+" "+currentTime+ " "+currentCalories+" "+currentDate+"\n");
            }

        } finally {

            cursor.close();
        }
    }
}