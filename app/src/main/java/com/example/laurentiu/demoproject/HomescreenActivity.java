package com.example.laurentiu.demoproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class HomescreenActivity extends AppCompatActivity {

    Button search;
    ScrollView scroll;
    EditText searchText;
    TextView offer;
    Boolean isSearchOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        getReferences();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSearchOn == false){
                    offer.setVisibility(View.GONE);
                    searchText.setVisibility(View.VISIBLE);
                    isSearchOn = true;
                }
                else{
                    offer.setVisibility(View.VISIBLE);
                    searchText.setVisibility(View.GONE);
                    isSearchOn = false;
                    searchText.setText("");
                }
            }
        });
    }

    protected void getReferences(){
        search = (Button) findViewById(R.id.searchImage);
        scroll = (ScrollView) findViewById(R.id.scrollHomescreen);
        searchText = (EditText) findViewById(R.id.searchText);
        offer = (TextView) findViewById(R.id.offer);
    }
}
