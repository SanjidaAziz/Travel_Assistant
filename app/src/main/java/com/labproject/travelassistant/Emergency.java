package com.labproject.travelassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;


public class Emergency extends AppCompatActivity {
    private static  final int REQUEST_LOCATION=1;
    private static  final int REQUEST_CALL=1;
    private static final int RESULT_PICK_CONTACT =1;
    EditText phone;
    ImageView select;
    ImageView nearbyHospital;
    ImageView nearbyPoliceStation;
    ImageView call999;
    Button getlocationBtn;

    //ImageView getlocationBtn;
    TextView showLocationTxt;
    String phoneNumber;
    LocationManager locationManager;
    String latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        showLocationTxt=findViewById(R.id.show_location);
        getlocationBtn=findViewById(R.id.getLocation);
        getlocationBtn.setBackgroundColor(getResources().getColor(R.color.light_white));
        phone= (EditText) findViewById(R.id.emergencyPhoneNo);
        select=(ImageView) findViewById(R.id.chooseContact);
        phoneNumber=phone.getText().toString();
        nearbyHospital=(ImageView) findViewById(R.id.nearbyHospitalID);
        nearbyPoliceStation=(ImageView) findViewById(R.id.nearbyPoliceID);
        call999=(ImageView)findViewById(R.id.call999ID);
        getlocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                }
                else {
                    getLocation();
                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent (Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult (in, RESULT_PICK_CONTACT);
            }
        });

        nearbyHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNearbyHospitals();
            }
        });
        nearbyPoliceStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNearbyPoliceStation();
            }
        });
        call999.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //makePhoneCall();
                String no="999";
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+no));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Failed To pick contact", Toast.LENGTH_SHORT).show();
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;

        try {
            String phoneNo = null;
            Uri uri = data.getData ();
            cursor = getContentResolver ().query (uri, null, null,null,null);
            cursor.moveToFirst ();
            int phoneIndex = cursor.getColumnIndex (ContactsContract.CommonDataKinds.Phone.NUMBER);

            phoneNo = cursor.getString (phoneIndex);

            phone.setText (phoneNo);


        } catch (Exception e) {
            e.printStackTrace ();
        }
    }


    private void getLocation(){
        //Check Permissions again
        if (ActivityCompat.checkSelfPermission(Emergency.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Emergency.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
                        sendSMS(phoneNumber,latitude,longitude);

                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
                        sendSMS(phoneNumber,latitude,longitude);

                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);
                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
                        sendSMS(phoneNumber,latitude,longitude);

                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public void sendSMS(String phoneNumber,String latitude,String longitude){
        try{
            String number=phone.getText().toString();
            SmsManager smsManager= SmsManager.getDefault();
            StringBuffer smsBody = new StringBuffer();
            smsBody.append("http://maps.google.com?q=");
            smsBody.append(latitude);
            smsBody.append(",");
            smsBody.append(longitude);
            if(number.trim().length() <= 0) Toast.makeText(Emergency.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
            else {
                smsManager.sendTextMessage(number, null, smsBody.toString(), null, null);
                Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Failed to send message.",Toast.LENGTH_SHORT).show();
        }
    }

    public  void findNearbyHospitals(){
        Uri gmmIntentUri = Uri.parse("geo:0,0?z=17&q=nearby+hospital&rankBy=distance");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);
    }
    public  void findNearbyPoliceStation(){
        Uri gmmIntentUri = Uri.parse("geo:0,0?z=17&q=police station near me&rankBy=distance");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
   
}