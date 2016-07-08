package com.example.laurentiu.demoproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        setContentView(R.layout.activity_login);

      getReferences();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateFields())
                {
                    // TODO: go to next screen
                }
            }
        });

    }

    private  void getReferences()
    {
        password1 = (EditText) findViewById(R.id.sendPassword1);
        password2 = (EditText) findViewById(R.id.sendPassword2);

        next = (Button) findViewById(R.id.next_button);
    }

    private boolean validateFields()
    {
        if (password1.getText().toString().length() < 8)
        {
            Toast.makeText(getApplicationContext(), getResources()
                    .getString(R.string.error_invalid_password), Toast.LENGTH_LONG).show();
            return false;
        }

        if ((String.valueOf(password1.getText())).equals(String.valueOf(password2.getText())))
        {
            Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}

