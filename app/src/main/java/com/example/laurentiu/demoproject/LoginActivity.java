package com.example.laurentiu.demoproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laurentiu.demoproject.data.DatabaseContract;
import com.example.laurentiu.demoproject.data.PhoneDatabase;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText password1;
    EditText password2;
    Button next;
    String phone_number;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getReferences();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateFields(phone_number)) {
                    //TODO:Go to screen 4
                }
            }
        });

    }

    private  void getReferences()
    {
        password1 = (EditText) findViewById(R.id.sendPassword1);
        password2 = (EditText) findViewById(R.id.sendPassword2);

        next = (Button) findViewById(R.id.next_button);

        Intent myIntent = getIntent();
        phone_number = myIntent.getStringExtra("phone_number");
    }

    protected boolean validateFields(String phone_number)
    {
        Cursor cursorNumber = PhoneDatabase.getInstance(getApplicationContext()).getData(phone_number, getApplicationContext());
        password = cursorNumber.getString( cursorNumber.getColumnIndex(DatabaseContract.PhoneEntry.COLUMN_PASSWORD));

        if(!password.equals(password1.getText().toString())){
            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG);
            return false;
        }

        if (password1.getText().toString().length() < 8)
        {
            Toast.makeText(getApplicationContext(), getResources()
                    .getString(R.string.error_invalid_password), Toast.LENGTH_LONG).show();
            return false;
        }

        if (!(String.valueOf(password1.getText())).equals(String.valueOf(password2.getText())))
        {
            Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}

