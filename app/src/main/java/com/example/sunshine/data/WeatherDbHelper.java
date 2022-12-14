package com.example.sunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sunshine.R;

public class WeatherDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "weather.db";
    public static final int DATABASE_VERSION = 1;

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE "+ WeatherContract.WeatherEntry.TABLE_NAME + " ("+
                WeatherContract.WeatherEntry.COLUMN_WEATHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                WeatherContract.WeatherEntry.COLUMN_DATE+ "INTEGER NOT NULL, "+
                WeatherContract.WeatherEntry.COLUMN_DEGREES+ "REAL NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_HUMIDITY+ "REAL NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_PRESSURE+ "REAL NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_WIND_SPEED+ "REAL NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_MAX_TEMP+ "REAL NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_MIN_TEMP+ "REAL NOT NULL," +
                " UNIQUE (" + WeatherContract.WeatherEntry.COLUMN_DATE + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherContract.WeatherEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
