package com.example.sunshine.data;

import android.provider.BaseColumns;

import java.util.prefs.BackingStoreException;

public class WeatherContract {

    public static final String CONTENT_AUTHORITY = "com.example.sunshine";
    public static final String PATH_WEATHER = "weather";

    public static class WeatherEntry implements BaseColumns {

        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_WEATHER_ID = "weather_id";
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_WIND_SPEED = "wind_speed";
        public static final String COLUMN_DEGREES = "degrees";

    }
}
