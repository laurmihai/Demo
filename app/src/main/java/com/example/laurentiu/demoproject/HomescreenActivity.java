package com.example.laurentiu.demoproject;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.laurentiu.demoproject.fragments.CameraFragment;
import com.example.laurentiu.demoproject.fragments.DiscoverFragment;
import com.example.laurentiu.demoproject.fragments.MapFragment;

public class HomescreenActivity extends AppCompatActivity {

    ImageButton botButton1, botButton2, botButton3, botButton4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DiscoverFragment())
                    .commit();
        }

        getReferences();

        setListeners();

        botButton1.setSelected(true);

    }

    protected void getReferences(){
        botButton1 = (ImageButton)findViewById(R.id.bottom1);
        botButton2 = (ImageButton)findViewById(R.id.bottom2);
        botButton3 = (ImageButton)findViewById(R.id.bottom3);
        botButton4 = (ImageButton)findViewById(R.id.bottom4);
    }

    protected void setListeners(){



        botButton1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new DiscoverFragment()).commit();
                botButton1.setSelected(true);


            }
        });
        botButton2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                botButton1.setSelected(false);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new CameraFragment()).commit();
            }
        });
        botButton3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                botButton1.setSelected(false);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new MapFragment()).commit();
            }
        });
        botButton4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                botButton1.setSelected(false);
                botButton1.setImageResource(R.drawable.bottom_button1);
                botButton2.setImageResource(R.drawable.bottom_button2);
                botButton3.setImageResource(R.drawable.bottom_button3);
            }
        });




    }
}
