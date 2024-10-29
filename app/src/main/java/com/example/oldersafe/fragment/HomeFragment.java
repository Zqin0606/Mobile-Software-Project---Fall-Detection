package compackage com.example.oldersafe.fragment;

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

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.oldersafe.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class HomeFragment extends Fragment implements SensorEventListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView state, name, phone;
    Button help, cancal;
    SensorManager sensorManager;
    Sensor accelerometer;
    GoogleApiClient mGoogleApiClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isStart();
        initSensor();
        initLocation();
        smsManager = SmsManager.getDefault();
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
    private void initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(2000);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        lat.setText(String.valueOf(location.getLatitude()));
                        longg.setText(String.valueOf(location.getLongitude()));
                        Log.d("LocationUpdate", "Lat: " + location.getLatitude() + ", Long: " + location.getLongitude());
                    }
                }
            }
        }, null);
    }

    private void initMap() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(com.google.android.gms.location.LocationServices.API)
                .build();

        mGoogleApiClient.connect();
        Log.d("HomeFragment", "GoogleApiClient initialized.");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("HomeFragment", "Google API Connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("HomeFragment", "Google API Connection Suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("HomeFragment", "Google API Connection Failed: " + connectionResult.getErrorMessage());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            Log.d("SensorEvent", "x: " + x + ", y: " + y + ", z: " + z);
            if (acceleration > 15) {  // Simple threshold for SMS trigger
                String message = "Emergency detected! Location: Lat=" + lat.getText() + ", Long=" + longg.getText();
                smsManager.sendTextMessage("1234567890", null, message, null, null);
                Log.d("SmsTrigger", "Sending SMS: " + message);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}


