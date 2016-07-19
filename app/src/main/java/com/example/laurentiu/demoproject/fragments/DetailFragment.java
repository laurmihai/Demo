package com.example.laurentiu.demoproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.laurentiu.demoproject.R;

public class DetailFragment extends Fragment {

    public DetailFragment() {
        // Required empty public constructor
    }

    ImageButton gps, video;
    Button back;
    String gps_lat, gps_lon, video_link;
    Activity context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        context = getActivity();


        getReferences(rootView);

        setListeners();

        return rootView;
    }

    private void setListeners() {
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri ggp = Uri.parse("geo:+"+gps_lat+","+gps_lon);
                startActivity(new Intent(Intent.ACTION_VIEW, ggp));
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(video_link)));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }


    private void getReferences(View rootView) {
        gps = (ImageButton) rootView.findViewById(R.id.detail_gps);
        video = (ImageButton) rootView.findViewById(R.id.detail_video);
        back = (Button) rootView.findViewById(R.id.detail_back_button);

        video_link = getArguments().getString("video_link");
        gps_lat = getArguments().getString("gps_lat");
        gps_lon = getArguments().getString("gps_lon");

    }

}
