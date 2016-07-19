package com.example.laurentiu.demoproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.laurentiu.demoproject.R;
import com.example.laurentiu.demoproject.data.Offer;

import java.util.ArrayList;
import java.util.List;


public class DiscoverFragment extends Fragment {

    Button search, scroll1, scroll2, scroll3, scroll4, scroll5;
    HorizontalScrollView scroll;
    EditText searchText;
    TextView offer;
    Boolean isSearchOn = false;
    List<List<Offer>> offersListApplication;
    RecyclerView recyclerView;
    myAdapter m;
    GridLayoutManager manager;
    int scrollIndex=0;
    int scrollViewItems = 5;
    Fragment fr;


    public DiscoverFragment() {
        // Required empty public constructor
    }

    OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(String video, String lat, String lon);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        fr=this;

        getReferences(rootView);

        editDataSet();

        setListeners();

        editRecycleView();

        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnHeadlineSelectedListener");
        }


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
        manager = new GridLayoutManager(getActivity(), 2);
        final SwipeDetector sw = new SwipeDetector();

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

        //recyclerView.setOnTouchListener(sw);
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
                    startActivity(new Intent(Intent.ACTION_VIEW, ggp));
                }
            });
            bDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onArticleSelected(link, String.valueOf(lat), String.valueOf(lon));

                }
            });
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if (view.getId() == R.id.offer_item || view.getId() == R.id.small_offer_item)
                Toast.makeText(getActivity().getApplicationContext(), "Item " + pos, Toast.LENGTH_LONG).show();
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
                offerView = LayoutInflater.from(getActivity().getApplicationContext())
                        .inflate(R.layout.offer_layout, parent, false);
            }
            else{
                offerView = LayoutInflater.from(getActivity().getApplicationContext())
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
                                Toast.makeText(getActivity().getApplicationContext(), scrollIndex, Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                        if (deltaX > 0) {
                            if(scrollIndex>0){
                                scrollIndex--;
                                Toast.makeText(getActivity().getApplicationContext(), scrollIndex, Toast.LENGTH_SHORT).show();
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

    protected void getReferences(View rootView){

        search = (Button) rootView.findViewById(R.id.searchImage);
        scroll = (HorizontalScrollView) rootView.findViewById(R.id.scrollHomescreen);
        searchText = (EditText) rootView.findViewById(R.id.searchText);
        offer = (TextView) rootView.findViewById(R.id.offer);


        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        scroll1 = (Button)rootView.findViewById(R.id.scrollHomescreen1);
        scroll2 = (Button)rootView.findViewById(R.id.scrollHomescreen2);
        scroll3 = (Button)rootView.findViewById(R.id.scrollHomescreen3);
        scroll4 = (Button)rootView.findViewById(R.id.scrollHomescreen4);
        scroll5 = (Button)rootView.findViewById(R.id.scrollHomescreen5);


    }

    protected void setListeners() {

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSearchOn == false){
                    offer.setVisibility(View.GONE);
                    searchText.setVisibility(View.VISIBLE);
                    isSearchOn = true;
                    getActivity().findViewById(R.id.searchText).requestFocus();
                    getActivity().findViewById(R.id.searchText).setFocusableInTouchMode(true);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    searchText.setHint("Search");
                }
                else{
                    offer.setVisibility(View.VISIBLE);
                    searchText.setVisibility(View.GONE);
                    isSearchOn = false;
                    searchText.setText("");
                    searchText.setHint("Search");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
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
