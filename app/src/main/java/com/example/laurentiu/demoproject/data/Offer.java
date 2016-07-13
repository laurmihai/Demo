package com.example.laurentiu.demoproject.data;

import android.widget.ImageButton;

/**
 * Created by Laurentiu on 7/13/2016.
 */
public class Offer {

    public String link;
    public float lon;
    public float lat;
    public ImageButton bLink;
    public ImageButton bGps;
    public ImageButton bDetail;

    public Offer(float lat, String link, float lon) {
        this.lat = lat;
        this.link = link;
        this.lon = lon;
    }
}
