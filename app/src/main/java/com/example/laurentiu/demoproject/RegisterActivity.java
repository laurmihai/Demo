package com.example.laurentiu.demoproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.laurentiu.demoproject.data.PhoneDatabase;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {

    EditText last_name, first_name, email, password1, password2;
    Button importF, importVK, register;
    String phone_number;
    CallbackManager mCallbackManager;
    ImageButton button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
         mCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_profile);

        getReferences();
        importF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Import first name, last name, email from facebook
                facebookLogin();


            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateFields()){
                    PhoneDatabase.getInstance(getApplicationContext()).addEntry(getApplicationContext(),
                            phone_number,
                            password1.getText().toString(),
                            first_name.getText().toString(),
                            last_name.getText().toString(),
                            email.getText().toString());
                    startActivity(new Intent(getApplicationContext(), HomescreenActivity.class));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void facebookLogin(){
        // Other app specific specialization

        // Callback registration
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()  {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.d("Register", "FB: login success");

                final String token = loginResult.getAccessToken().getToken();

                //prepare fields with email
                String[] requiredFields = new String[]{"email", "last_name", "first_name"};
                Bundle parameters = new Bundle();
                parameters.putString("fields", TextUtils.join(",", requiredFields));

                GraphRequest requestGraph = new GraphRequest(loginResult.getAccessToken(), "me", parameters, null, new GraphRequest.Callback()
                {
                    @Override
                    public void onCompleted (GraphResponse response)
                    {
                        if (response != null)
                        {
                            GraphRequest.GraphJSONObjectCallback callbackEmail = new GraphRequest.GraphJSONObjectCallback()
                            {
                                @Override
                                public void onCompleted (JSONObject me, GraphResponse response)
                                {
                                    if (response.getError() != null)
                                    {
                                        Log.d("Register", "FB: cannot parse email");
                                    }
                                    else
                                    {
                                        String mail = me.optString("email");
                                        String lastName= me.optString("last_name");
                                        String firstName= me.optString("first_name");

                                        /*try {
                                            URL image_value = new URL("http://graph.facebook.com/"+ loginResult.getAccessToken().getUserId()+ "/picture?type=small");
                                            Bitmap bmp = null;
                                            try {
                                                bmp = BitmapFactory.decodeStream(image_value.openConnection().getInputStream());
                                                button4.setImageBitmap(bmp);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }*/


                                        last_name.setText(lastName);
                                        first_name.setText(firstName);
                                        email.setText(mail);
                                    }
                                }
                            };

                            callbackEmail.onCompleted(response.getJSONObject(), response);
                        }
                    }
                });

                requestGraph.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("Register", "FB: login cancel");
                //showDialog(getString(R.string.dialog_message_unknown_error));
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Register", "FB: login error ");
                //showDialog(getString(R.string.dialog_message_unknown_error));
            }
        });

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
    }

    private boolean validateFields() {
        if(password1.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
            return false;
        }
        if(last_name.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
            return false;
        }
        if(first_name.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
            return false;
        }
        if(email.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!email.getText().toString().contains("@") ||
            !email.getText().toString().contains(".")){
            Toast.makeText(getApplicationContext(), "Mail format is incorrect", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!(String.valueOf(password1.getText())).equals(String.valueOf(password2.getText())))
        {
            Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_LONG).show();
            return false;
        }
        if(String.valueOf(password1.getText()).length()<8){
            Toast.makeText(getApplicationContext(), "Passwords must have minimum 8 characters", Toast.LENGTH_LONG).show();
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

        button4 = (ImageButton)findViewById(R.id.bottom4);

    }
}
