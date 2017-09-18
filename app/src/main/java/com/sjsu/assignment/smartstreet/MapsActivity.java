package com.sjsu.assignment.smartstreet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by sindhya on 9/17/17.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    LocationManager locationManager;
    SupportMapFragment mapFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle(getResources().getString(R.string.map_title));
            actionBar.setLogo(R.mipmap.ic_launcher);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                new AlertDialog.Builder(MapsActivity.this).setTitle("Enable Location")
                        .setMessage(getResources().getString(R.string.location_dialog_msg))
                        .setPositiveButton(R.string.location_dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(locationIntent);
                            }
                        }).setNegativeButton(R.string.location_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();

            } else {
                mapFragment.getMapAsync(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            if(!mMap.isMyLocationEnabled())
                mMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (currentLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = locationManager.getBestProvider(criteria, true);
                currentLocation = locationManager.getLastKnownLocation(provider);
            }

            if(currentLocation!=null){
                LatLng location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 9), 1600, null);
            }

            /*int size = DonationData.getInstance().getSize();
            for (int i = 0; i < size; i++) {
                String lat = DonationData.getInstance().get(i).latitude;
                String lng = DonationData.getInstance().get(i).longitude;
                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                mMap.addMarker(new MarkerOptions().position(latLng)).setTag(i);

            }*/


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.getMapAsync(this);
    }
}
