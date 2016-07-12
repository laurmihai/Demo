package com.example.laurentiu.demoproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.laurentiu.demoproject.data.DatabaseContract.PhoneEntry;

/**
 * Created by Laurentiu on 7/8/2016.
 */
public final class PhoneDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "demo.db";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PhoneEntry.TABLE_NAME;

    public PhoneDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static PhoneDatabase instance;

    public static synchronized PhoneDatabase getInstance(Context context)
    {
        if (instance == null)
            instance = new PhoneDatabase(context);

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PHONE_TABLE = "CREATE TABLE " + PhoneEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.

                // the ID of the location entry associated with this weather data
                PhoneEntry.COLUMN_PHONE_NUMBER + " TEXT NOT NULL, " +
                PhoneEntry.COLUMN_PASSWORD + " TEXT NOT NULL, " +
                PhoneEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                PhoneEntry.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                PhoneEntry.COLUMN_EMAIL + " TEXT NOT NULL);";
                Log.i("Queryul de creeare", SQL_CREATE_PHONE_TABLE);

        sqLiteDatabase.execSQL(SQL_CREATE_PHONE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public Cursor getData(String phoneNumber){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from phoneNumbers where phone_number = " + phoneNumber + ";", null );
        return res;
    }

    public void addEntry(Context context, String phone, String password, String first_name, String last_name, String email){
        ContentValues values = new ContentValues();
        values.put(PhoneEntry.COLUMN_PHONE_NUMBER, phone);
        values.put(PhoneEntry.COLUMN_PASSWORD, password);
        values.put(PhoneEntry.COLUMN_FIRST_NAME, first_name);
        values.put(PhoneEntry.COLUMN_LAST_NAME, last_name);
        values.put(PhoneEntry.COLUMN_EMAIL, email);
        getWritableDatabase().insert(
                PhoneEntry.TABLE_NAME,
                null,
                values);
    };
}
