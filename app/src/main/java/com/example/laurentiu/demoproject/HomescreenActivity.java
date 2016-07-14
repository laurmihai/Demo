package com.example.laurentiu.demoproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laurentiu.demoproject.data.Offer;

import java.util.ArrayList;
import java.util.List;

public class HomescreenActivity extends AppCompatActivity {

    int scrollViewItems = 5;

    Button search, scroll1, scroll2, scroll3, scroll4, scroll5;
    ImageButton botButton1, botButton2, botButton3, botButton4;
    HorizontalScrollView scroll;
    EditText searchText;
    TextView offer;
    Boolean isSearchOn = false;
    HorizontalScrollView scrollView;
    List<List<Offer>> offersListApplication;
    RecyclerView recyclerView;
    myAdapter m;
    GridLayoutManager manager;

    int scrollIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        getReferences();
        botButton1.setImageResource(R.drawable.bottom_focused_button1);

        editDataSet();

        setListeners();

        editRecycleView();
    }

    private void editDataSet() {

        offersListApplication = new ArrayList<List<Offer>>(scrollViewItems);

        for(int i=0;i<scrollViewItems;i++){
            List<Offer> offersList = new ArrayList<Offer>();
            offersList.add(new Offer(37.7749, "https://youtu.be/H77fRz1rybs?list=PLn6tBgLF5AeuYDoJtnryCq8cq9IMel1Pb", -122.4194));
            offersList.add(new Offer(47.7749, "https://youtu.be/XxGmgmelZV0?list=PLn6tBgLF5AeuYDoJtnryCq8cq9IMel1Pb", -112.4194));
            offersList.add(new Offer(27.7749, "https://youtu.be/mWRsgZuwf_8?list=PLn6tBgLF5AeuYDoJtnryCq8cq9IMel1Pb", -102.4194));
            offersList.add(new Offer(37.7749, "https://youtu.be/JuM7hObGjWI?list=PLn6tBgLF5AeuYDoJtnryCq8cq9IMel1Pb", -122.4194));
            offersList.add(new Offer(37.7749, "https://youtu.be/CroHhLfAo-0?list=PLn6tBgLF5AeuYDoJtnryCq8cq9IMel1Pb", -122.4194));
            offersList.add(new Offer(37.7749, "https://youtu.be/H77fRz1rybs?list=PLn6tBgLF5AeuYDoJtnryCq8cq9IMel1Pb", -122.4194));
            offersListApplication.add(offersList);
        }


    }

    private void editRecycleView() {

        m = new myAdapter();
        recyclerView.setHasFixedSize(true);
        manager = new GridLayoutManager(this, 2);
        final SwipeDetector sw = new SwipeDetector();
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw.mSwipeDetected.equals("LEFT")){
                    Toast.makeText(getApplicationContext(), "adadsf", Toast.LENGTH_SHORT).show();
                }
            }
        });

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position % 3 == 0)
                    return 2;
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(m);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageButton bLink;
        protected ImageButton bGps;
        protected ImageButton bDetail;
        protected double lon, lat;
        protected String link;

        public OfferViewHolder(View v) {
            super(v);
            bLink =  (ImageButton) v.findViewById(R.id.link_button);
            bGps = (ImageButton)  v.findViewById(R.id.gps_button);
            bDetail = (ImageButton)  v.findViewById(R.id.detail_button);

            bLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                }
            });
            bGps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri ggp = Uri.parse("geo:+"+lat+","+lon);
                    Toast.makeText(getApplicationContext(), lat + ","+lon, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, ggp));
                }
            });
            bDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Detail", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if (view.getId() == R.id.offer_item || view.getId() == R.id.small_offer_item)
                Toast.makeText(getApplicationContext(), "Item " + pos, Toast.LENGTH_LONG).show();
        }
    }

    protected class myAdapter extends RecyclerView.Adapter<OfferViewHolder> {

        protected List<Offer> offersList;

        myAdapter(){
            offersList = new ArrayList<Offer>();
            offersList.addAll(offersListApplication.get(0));
        }

        protected void changeSet(ArrayList<Offer> newList){
            offersList.clear();
            offersList.addAll(newList);
            this.notifyDataSetChanged();
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

    public class SwipeDetector implements View.OnTouchListener {

        private static final int MIN_DISTANCE = 100;
        private float downX, downY, upX, upY;
        String mSwipeDetected;

        public boolean swipeDetected() {
            return mSwipeDetected.equals("NO");
        }

        public String getAction() {
            return mSwipeDetected;
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    downX = event.getX();
                    downY = event.getY();
                    mSwipeDetected = "NO";
                    return false; // allow other events like Click to be processed
                }
                case MotionEvent.ACTION_MOVE: {
                    upX = event.getX();
                    upY = event.getY();

                    float deltaX = downX - upX;
                    float deltaY = downY - upY;

                    // horizontal swipe detection
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        // left or right
                        if (deltaX < 0) {
                            mSwipeDetected = "RIGHT";
                            if(scrollIndex<scrollViewItems-1){
                                scrollIndex++;
                                Toast.makeText(getApplicationContext(), scrollIndex, Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                        if (deltaX > 0) {
                            if(scrollIndex>0){
                                scrollIndex--;
                                Toast.makeText(getApplicationContext(), scrollIndex, Toast.LENGTH_SHORT).show();
                            }
                            mSwipeDetected = "LEFT";
                            return true;
                        }
                    } else

                        // vertical swipe detection
                        if (Math.abs(deltaY) > MIN_DISTANCE) {
                            // top or down
                            if (deltaY < 0) {
                                mSwipeDetected = "DOWN";
                                return false;
                            }
                            if (deltaY > 0) {
                                mSwipeDetected = "TOP";
                                return false;
                            }
                        }
                    return true;
                }
            }
            return false;
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

        scroll1 = (Button)findViewById(R.id.scrollHomescreen1);
        scroll2 = (Button)findViewById(R.id.scrollHomescreen2);
        scroll3 = (Button)findViewById(R.id.scrollHomescreen3);
        scroll4 = (Button)findViewById(R.id.scrollHomescreen4);
        scroll5 = (Button)findViewById(R.id.scrollHomescreen5);


    }

    protected void setListeners(){

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSearchOn == false){
                    offer.setVisibility(View.GONE);
                    searchText.setVisibility(View.VISIBLE);
                    isSearchOn = true;
                    findViewById(R.id.searchText).requestFocus();
                    findViewById(R.id.searchText).setFocusableInTouchMode(true);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    searchText.setHint("Search");
                }
                else{
                    offer.setVisibility(View.VISIBLE);
                    searchText.setVisibility(View.GONE);
                    isSearchOn = false;
                    searchText.setText("");
                    searchText.setHint("Search");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        });

        botButton1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                botButton1.setImageResource(R.drawable.bottom_focused_button1);
                botButton2.setImageResource(R.drawable.bottom_button2);
                botButton3.setImageResource(R.drawable.bottom_button3);;

            }
        });
        botButton2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                botButton1.setImageResource(R.drawable.bottom_button1);
                botButton2.setImageResource(R.drawable.bottom_focused_button2);
                botButton3.setImageResource(R.drawable.bottom_button3);
            }
        });
        botButton3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                botButton1.setImageResource(R.drawable.bottom_button1);
                botButton2.setImageResource(R.drawable.bottom_button2);
                botButton3.setImageResource(R.drawable.bottom_focused_button3);
            }
        });
        botButton4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                botButton1.setImageResource(R.drawable.bottom_button1);
                botButton2.setImageResource(R.drawable.bottom_button2);
                botButton3.setImageResource(R.drawable.bottom_button3);
            }
        });

        scroll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m.changeSet((ArrayList<Offer>) offersListApplication.get(0));
                scroll1.setTextColor(Color.RED);
                scroll2.setTextColor(Color.GRAY);
                scroll3.setTextColor(Color.GRAY);
                scroll4.setTextColor(Color.GRAY);
                scroll5.setTextColor(Color.GRAY);
                manager.scrollToPosition(0);
            }
        });
        scroll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m.changeSet((ArrayList<Offer>) offersListApplication.get(1));
                scroll2.setTextColor(Color.RED);
                scroll1.setTextColor(Color.GRAY);
                scroll3.setTextColor(Color.GRAY);
                scroll4.setTextColor(Color.GRAY);
                scroll5.setTextColor(Color.GRAY);
                manager.scrollToPosition(0);
            }
        });
        scroll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m.changeSet((ArrayList<Offer>) offersListApplication.get(2));
                scroll3.setTextColor(Color.RED);
                scroll2.setTextColor(Color.GRAY);
                scroll1.setTextColor(Color.GRAY);
                scroll4.setTextColor(Color.GRAY);
                scroll5.setTextColor(Color.GRAY);
                manager.scrollToPosition(0);
            }
        });
        scroll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m.changeSet((ArrayList<Offer>) offersListApplication.get(3));
                scroll4.setTextColor(Color.RED);
                scroll2.setTextColor(Color.GRAY);
                scroll3.setTextColor(Color.GRAY);
                scroll1.setTextColor(Color.GRAY);
                scroll5.setTextColor(Color.GRAY);
                manager.scrollToPosition(0);
            }
        });
        scroll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m.changeSet((ArrayList<Offer>) offersListApplication.get(4));
                scroll5.setTextColor(Color.RED);
                scroll2.setTextColor(Color.GRAY);
                scroll3.setTextColor(Color.GRAY);
                scroll4.setTextColor(Color.GRAY);
                scroll1.setTextColor(Color.GRAY);
                manager.scrollToPosition(0);
            }
        });


    }
}
