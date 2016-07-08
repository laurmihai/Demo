package com.example.laurentiu.demoproject.data;

import android.provider.BaseColumns;

/**
 * Created by Laurentiu on 7/8/2016.
 */
public class DatabaseContract {

    public static final class PhoneEntry implements BaseColumns {
        public static final String TABLE_NAME = "phoneNumbers";

        public static final String _ID = "id";

        public static final String COLUMN_PHONE_NUMBER = "phone_number";

        public static final String COLUMN_PASSWORD = "password";

    }
}
