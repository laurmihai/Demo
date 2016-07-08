package com.example.laurentiu.demoproject;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText password1;
    EditText password2;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        password1 = (EditText) findViewById(R.id.sendPassword1);
        password2 = (EditText) findViewById(R.id.sendPassword2);

        next = (Button) findViewById(R.id.next_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((String.valueOf(password1.getText())).equals(String.valueOf(password2.getText()))
                        && (String.valueOf(password1.getText()).length() !=0) ){
                    Toast.makeText(getApplicationContext(), "Sunt egale", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Nu unt egale", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}

