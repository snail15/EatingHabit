package com.example.android.eatinghabit.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.eatinghabit.data.EatingContract.EatingEntry;

/**
 * Created by OWNER on 8/31/2016.
 */
public class EatingDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = EatingDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "eating.db";
    private static final int DATABASE_VERSION = 1;

    public EatingDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_EATING_TABLE =  "CREATE TABLE " + EatingEntry.TABLE_NAME + " ("
                + EatingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EatingEntry.COLUMN_EATING_MENU + " TEXT NOT NULL, "
                + EatingEntry.COLUMN_EATING_DATE + " TEXT NOT NULL, "
                + EatingEntry.COLUMN_EATING_TIME + " INTEGER NOT NULL, " //user should put time in 24:00 format (e.g. 1923 = 7:23pm)
                + EatingEntry.COLUMN_EATING_CALROIES + " INTEGER NOT NULL);";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_EATING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EatingEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteDatabase(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DELETE FROM " + EatingEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
