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
    String city;

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
                // Fetching country and city
                country = address.getCountryName();
                city = address.getLocality();
                Log.d("AddressInfo", "countryName: " + country);
                Log.d("AddressInfo", "locality: " + city);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}


