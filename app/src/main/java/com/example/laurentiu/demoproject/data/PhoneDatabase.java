package com.example.laurentiu.demoproject.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.laurentiu.demoproject.data.DatabaseContract.PhoneEntry;

/**
 * Created by Laurentiu on 7/8/2016.
 */
public final class PhoneDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "demo.db";

    private static volatile PhoneDatabase instance;

    public static synchronized PhoneDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new PhoneDatabase(context.getApplicationContext());
        }
        return instance;
    }

    public PhoneDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PHONE_TABLE = "CREATE TABLE " + PhoneEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                PhoneEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                PhoneEntry.COLUMN_PHONE_NUMBER + " TEXT NOT NULL, " +
                PhoneEntry.COLUMN_PASSWORD + " TEXT NOT NULL); ";

        sqLiteDatabase.execSQL(SQL_CREATE_PHONE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getData(String phoneNumber, Context context){
        SQLiteDatabase db = PhoneDatabase.getInstance(context).getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from phoneNumbers where phone_number="+phoneNumber+";", null );
        return res;
    }
}
