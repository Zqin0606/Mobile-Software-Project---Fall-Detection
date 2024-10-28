package com.example.oldersafe.config;

import android.content.Context;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.Locale;

public class ToLocation {

    double latitude;
    double longitude;
    Geocoder geocoder;

    public ToLocation(Context context, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        geocoder = new Geocoder(context, Locale.getDefault());
        // Placeholder for reverse geocoding
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

