package com.example.laurentiu.demoproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laurentiu.demoproject.data.PhoneDatabase;

public class MainLoginActivity extends AppCompatActivity {

    Button sendButton;
    Button skipButton;
    EditText sendText;
    PhoneDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        db = new PhoneDatabase(this);

        sendButton = (Button) findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendText = (EditText)findViewById(R.id.sendText);
                Log.i("Click", String.valueOf(sendText.getText()));

                if( String.valueOf(sendText.getText()).length() != 10 ){
                    Context context = getApplicationContext();
                    CharSequence text = "Filed wrongly field ";
                    sendText.setError("10 digits");
                    int duration = Toast.LENGTH_LONG;

                    Toast.makeText(context, text, duration).show();
                }
                else{
                    Cursor cursorNumber = db.getData(String.valueOf(sendText.getText()), getApplicationContext()) ;
                    if( cursorNumber.getCount() < 1 ){
                        //Go to screen 3
                        Log.i("Cursor", "Number not in Database");
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class ));
                    }
                    else{
                        cursorNumber.moveToFirst();
                        String phoneNumber = cursorNumber.getString( cursorNumber.getColumnIndex("COLUMN_PHONE_NUMBER"));
                        Log.i("Cursor", "Password is "+phoneNumber);
                        //Go so screen 2
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class ));
                    }

                }
            }
        });

        skipButton = (Button) findViewById(R.id.skip_button);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Click", "skip");
                //Go to screen 4
            }
        });


    }

}
