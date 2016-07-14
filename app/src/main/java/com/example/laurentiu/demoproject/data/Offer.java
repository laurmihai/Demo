package com.example.laurentiu.demoproject.data;

import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Laurentiu on 7/13/2016.
 */
public class Offer {

    public String link;
    public double lon;
    public double lat;
    public ImageButton bLink;
    public ImageButton bGps;
    public Button bDetail;

    public Offer(double lat, String link, double lon) {
        this.lat = lat;
        this.link = link;
        this.lon = lon;
    }
}
