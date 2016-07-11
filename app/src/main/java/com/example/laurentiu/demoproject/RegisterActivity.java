package com.example.laurentiu.demoproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laurentiu.demoproject.data.PhoneDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText last_name, first_name, email, password1, password2;
    Button importF, importVK, register;
    String phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getReferences();
        importF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Import first name, last name, email from facebook
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateFields();
                Log.i("Phone number", phone_number);
                PhoneDatabase.getInstance(getApplicationContext()).addEntry(getApplicationContext(),
                        phone_number,
                        password1.getText().toString(),
                        first_name.getText().toString(),
                        last_name.getText().toString(),
                        email.getText().toString());
            }
        });
    }

    private boolean validateFields() {
        if(!password1.getText().toString().equals(password2.getText().toString())){
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
            return false;
        }
        if(last_name.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!(String.valueOf(password1.getText())).equals(String.valueOf(password2.getText())))
        {
            Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    protected void getReferences(){

        Intent reg = getIntent();
        phone_number = reg.getStringExtra("phone_number");

        last_name = (EditText)findViewById(R.id.last_name_input);
        first_name = (EditText)findViewById(R.id.first_name_input);
        email = (EditText)findViewById(R.id.email_input);
        password1 = (EditText)findViewById(R.id.registerPassword1);
        password2 = (EditText)findViewById(R.id.registerPassword2);

        importF = (Button)findViewById(R.id.import_F);
        importVK = (Button)findViewById(R.id.import_VK);
        register = (Button)findViewById(R.id.register_button);

    }
}
