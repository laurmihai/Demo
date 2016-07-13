package com.example.laurentiu.demoproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.laurentiu.demoproject.data.Offer;

import java.util.ArrayList;
import java.util.List;

public class HomescreenActivity extends AppCompatActivity {

    Button search;
    ImageButton botButton1, botButton2, botButton3, botButton4;
    HorizontalScrollView scroll;
    EditText searchText;
    TextView offer;
    Boolean isSearchOn = false;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        getReferences();

        setListeners();
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

        editRecycleView();
    }

    private void editRecycleView() {

        recyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this, 2);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position % 3 == 0)
                    return 2;
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);
        myAdapter m = new myAdapter();
        recyclerView.setAdapter(m);
    }

    public static class OfferViewHolder extends RecyclerView.ViewHolder {
        protected Button bLink;
        protected ImageButton bGps;
        protected ImageButton bDetail;
        protected float lon, lat;
        protected String link;

        public OfferViewHolder(View v) {
            super(v);
            bLink =  (Button) v.findViewById(R.id.button_offer);
            bGps = (ImageButton)  v.findViewById(R.id.link_button);
            bDetail = (ImageButton)  v.findViewById(R.id.gps_button);
        }
    }

    protected class myAdapter extends RecyclerView.Adapter<OfferViewHolder> {

        private List<Offer> offersList;

        myAdapter(){
            offersList = new ArrayList<Offer>();
            offersList.add(new Offer(0, "1", 0));
            offersList.add(new Offer(0, "1", 0));
            offersList.add(new Offer(0, "1", 0));
            offersList.add(new Offer(0, "1", 0));
            offersList.add(new Offer(0, "1", 0));
            offersList.add(new Offer(0, "1", 0));
        }

        @Override
        public int getItemCount() {
            return offersList.size();
        }

        @Override
        public int getItemViewType(int position) {
            if(position % 3 == 0)
                return 2;
            return 1;
        }

        @Override
        public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View offerView;
            if(viewType == 2){
                offerView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.offer_layout, parent, false);
            }
            else{
                offerView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.offer_small_layout, parent, false);
            }
            return new OfferViewHolder(offerView);
        }

        @Override
        public void onBindViewHolder(OfferViewHolder holder, int position) {
            Offer off = offersList.get(position);
            holder.link = off.link;
            holder.lat = off.lat;
            holder.lon = off.lon;
        }
    }

    protected void getReferences(){
        search = (Button) findViewById(R.id.searchImage);
        scroll = (HorizontalScrollView) findViewById(R.id.scrollHomescreen);
        searchText = (EditText) findViewById(R.id.searchText);
        offer = (TextView) findViewById(R.id.offer);

        botButton1 = (ImageButton) findViewById(R.id.bottom1);
        botButton2 = (ImageButton) findViewById(R.id.bottom2);
        botButton3 = (ImageButton) findViewById(R.id.bottom3);
        botButton4 = (ImageButton) findViewById(R.id.bottom4);

        botButton1.setFocusableInTouchMode(true);
        botButton1.requestFocus();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

    }

    protected void setListeners(){

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

        botButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botButton1.setFocusableInTouchMode(true);
                botButton1.requestFocus();
            }
        });
        botButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botButton2.setFocusableInTouchMode(true);
                botButton2.requestFocus();
            }
        });
        botButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botButton3.setFocusableInTouchMode(true);
                botButton3.requestFocus();
            }
        });
        botButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botButton4.setFocusableInTouchMode(true);
                botButton4.requestFocus();
            }
        });
    }
}
