package com.example.oldersafe.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.oldersafe.R;

public class HomeFragment extends Fragment implements SensorEventListener {

    TextView state, name, phone;
    Button help, cancal;
    SensorManager sensorManager;
    Sensor accelerometer;
    double latitude, longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isStart();
        initSensor();
    }

    private void initView() {
        state = getActivity().findViewById(R.id.state);
        name = getActivity().findViewById(R.id.name);
        phone = getActivity().findViewById(R.id.phone);
        help = getActivity().findViewById(R.id.help);
        cancal = getActivity().findViewById(R.id.cancal);

        String state_ = "Normal";  // Placeholder
        state.setText(state_);
        name.setText("User Name");
        phone.setText("1234567890");

        Log.d("HomeFragment", "Initialized view with state: " + state_);
    }

    private void isStart() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGpsEnabled && !isNetworkEnabled) {
            // gps and network are not enabled
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        Log.d("HomeFragment", "GPS enabled: " + isGpsEnabled + ", Network enabled: " + isNetworkEnabled); 
    }

    private void initSensor() {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        // Log sensor initialization
        Log.d("HomeFragment", "Accelerometer initialized.");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            Log.d("SensorEvent", "x: " + x + ", y: " + y + ", z: " + z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not implemented
    }
}

