package com.example.laurentiu.demoproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.laurentiu.demoproject.fragments.CameraFragment;
import com.example.laurentiu.demoproject.fragments.DetailFragment;
import com.example.laurentiu.demoproject.fragments.DiscoverFragment;
import com.example.laurentiu.demoproject.fragments.MapFragment;
import com.example.laurentiu.demoproject.fragments.ProfileFragment;

public class HomescreenActivity extends AppCompatActivity implements DiscoverFragment.OnHeadlineSelectedListener {

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

    public void onArticleSelected(String link, String lat, String lon) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        DetailFragment detFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("video_link", link);
        args.putString("gps_lat", lat);
        args.putString("gps_lon", lon);
        detFragment.setArguments(args);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, detFragment).addToBackStack(null).commit();
    }

    protected void getReferences(){
        botButton1 = (ImageButton)findViewById(R.id.bottom1);
        botButton2 = (ImageButton)findViewById(R.id.bottom2);
        botButton3 = (ImageButton)findViewById(R.id.bottom3);
        botButton4 = (ImageButton)findViewById(R.id.bottom4);
    }

    protected void setListeners(){

        botButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new DiscoverFragment()).commit();
                botButton1.setSelected(true);
                botButton2.setSelected(false);
                botButton3.setSelected(false);
                botButton4.setSelected(false);


            }
        });
        botButton2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new CameraFragment()).commit();

                botButton1.setSelected(false);
                botButton2.setSelected(true);
                botButton3.setSelected(false);
                botButton4.setSelected(false);
            }
        });
        botButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new MapFragment()).commit();

                botButton1.setSelected(false);
                botButton2.setSelected(false);
                botButton3.setSelected(true);
                botButton4.setSelected(false);

            }
        });
        botButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ProfileFragment()).commit();
                botButton1.setSelected(false);
                botButton2.setSelected(false);
                botButton3.setSelected(false);
            }
        });




    }
}
