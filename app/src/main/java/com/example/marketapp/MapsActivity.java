package com.example.marketapp;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;

import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.marketapp.ViewModels.MapsViewModel;
import com.example.marketapp.api.models.LocationPost;
import com.example.marketapp.api.clients.APIClient;
import com.example.marketapp.api.models.Market;
import com.example.marketapp.api.services.LocationAPIInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.example.marketapp.managers.UserManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    LocationAPIInterface locationAPIInterface;
    UserManager userManager;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker marker;
    FusedLocationProviderClient mFusedLocationClient;
    Button sendSms;
    Double lon;
    Double lat;
    String curr_city;
    String curr_desc;
    TextView longitude;
    TextView latitude;
    TextView address;
    TextView address_city;
    EditText additional_desc;

    MapsViewModel viewModel;

    private boolean mLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Select Map");
        locationAPIInterface = APIClient.getClient().create(LocationAPIInterface.class);

        viewModel = ViewModelProviders.of(this).get(MapsViewModel.class);


        setContentView(R.layout.activity_maps);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        longitude = (TextView) findViewById(R.id.longitude);
        latitude = (TextView) findViewById(R.id.latitude);
        address = (TextView) findViewById(R.id.location_res);
        address_city = (TextView) findViewById(R.id.location_city);
        additional_desc = (EditText) findViewById(R.id.additional_desc);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        //Location Permission already granted

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                    Looper.myLooper());
            }
        else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                // You can directly ask for the permission.
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        1);
            }


        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                //remove previously placed Marker
                if (marker != null) {
                    marker.remove();
                }
                //place marker where user just clicked
                mGoogleMap.clear();

                marker = mGoogleMap.addMarker(new MarkerOptions().position(point).title("Choose this Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).draggable(true));

                getMarkets();


                UpdateTextViews(marker);


            }
        });
        mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()
        {
            @Override
            public void onMarkerDragStart(Marker marker)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void onMarkerDragEnd(Marker marker)
            {
                // TODO Auto-generated method stub
                UpdateTextViews(marker);

            }

            @Override
            public void onMarkerDrag(Marker marker)
            {
                // TODO Auto-generated method stub
            }
        });


    }

    public void UpdateTextViews(Marker marker){
        lon = marker.getPosition().longitude;
        lat = marker.getPosition().latitude;

        longitude.setText(String.valueOf(lon));
        latitude.setText(String.valueOf(lat));

        mGoogleMap.addCircle(new CircleOptions()
                .center(marker.getPosition())
                .radius(6000)
                .strokeWidth(0f)
                .fillColor(0x550000FF));



        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            android.location.Address address_res = geocoder.getFromLocation(lat, lon, 1).get(0);
            String admin_area = address_res.getAdminArea();
            String feature_name = address_res.getFeatureName();
            address.setText(admin_area);
            address_city.setText(feature_name);
            curr_city = admin_area + "," + feature_name;
        } catch (IOException e) {
//            e.printStackTrace();
        }




    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                mLastLocation = location;
                if (marker != null) {
                    marker.remove();
                }
                //move map camera
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    };


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        mGoogleMap.setMyLocationEnabled(true);
                        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                                Looper.myLooper());
                }  else {
                }
        }
    }

    public void getMarkets(){
        viewModel.getMarkets().observe(this, new Observer<List<Market>>() {
            @Override
            public void onChanged(List<Market> markets) {
                for (Market m : markets){
                    Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(m.getLatitude(), m.getLongitude())).title(m.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).draggable(false));
                }


            }
        });

    }

    public void AddUserLocation(View view){
        LocationPost locationPost = new LocationPost(lon, lat, curr_city, additional_desc.getText().toString());
        viewModel.getResponse(locationPost).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    goToListofLocations();
                }else{
                    showAPIError();
                }
            }
        });
    }

    public void showAPIError(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.ConstraintLayout), "Failed to add new location", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }


    public void goToListofLocations(){
        Intent intent = new Intent(getApplicationContext(), LocationSelection.class);
        startActivity(intent);
        finish();
    }

    public void CancelAdding(View view){
        goToListofLocations();
    }

}







