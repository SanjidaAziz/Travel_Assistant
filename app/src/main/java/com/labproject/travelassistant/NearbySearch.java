package com.labproject.travelassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NearbySearch extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton placeCatagory;
    RadioButton hotel;
    RadioButton restaurant;
    RadioButton shop;
    RadioButton touristSpot;
    Button button;
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_search);

        //assign variable
        radioGroup = (RadioGroup) findViewById(R.id.nearbyCategoryId);
        button = (Button) findViewById(R.id.nearby_place_button);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.nearbyMap);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                getMyLocation();

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedId= radioGroup.getCheckedRadioButtonId();
                        placeCatagory= (RadioButton) findViewById(selectedId);
                        hotel= (RadioButton) findViewById(R.id.nearbyHotelId);
                        restaurant= (RadioButton) findViewById(R.id.nearbyRestaurantId);
                        shop= (RadioButton) findViewById(R.id.nearbyShopId);
                        touristSpot  = (RadioButton) findViewById(R.id.nearbySpotId);
                        if(placeCatagory==hotel) {
                            findNearbyHotels();
                        }
                        else if(placeCatagory==restaurant) {
                            findNearbyRestaurants();
                        }
                        else if(placeCatagory==shop) {
                            findNearbyShops();
                        }
                        else if(placeCatagory==touristSpot) {
                            findNearbyPlaces();
                        }
                    }
                });
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();


    }

    public void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng= new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions markerOptions=new MarkerOptions().position(latLng);

                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                    }
                });
            }
        });

    }

    public  void findNearbyHotels(){
        Uri gmmIntentUri = Uri.parse("geo:0,0?z=17&q=nearby+hotels");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    public  void findNearbyRestaurants(){
        Uri gmmIntentUri = Uri.parse("geo:0,0?z=17&q=nearby+restaurants");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    public  void findNearbyShops(){
        Uri gmmIntentUri = Uri.parse("geo:0,0??z=17&q=nearby+shopping mall");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    public  void findNearbyPlaces(){
        Uri gmmIntentUri = Uri.parse("geo:0,0??q=nearby+Beautiful Places");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }



}