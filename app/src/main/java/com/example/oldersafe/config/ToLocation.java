package com.example.oldersafe.config;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ToLocation {

    double latitude;
    double longitude;
    Geocoder geocoder;
    String country;
    String adminarea;
    String city;
    String area;
    String through;

    public ToLocation(Context context, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        geocoder = new Geocoder(context, Locale.getDefault());
        deal();
    }

    public void deal(){
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                country = address.getCountryName();
                adminarea = address.getAdminArea();
                city = address.getLocality();
                area = address.getSubLocality();
                through = address.getFeatureName();

                Log.d("AddressInfo", "Retrieved Address: " + country + ", " + adminarea + ", " + city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCountry() {
        return country;
    }

    public String getAdminarea() {
        return adminarea;
    }

    public String getCity() {
        return city;
    }

    public String getThrough() {
        return through;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}



