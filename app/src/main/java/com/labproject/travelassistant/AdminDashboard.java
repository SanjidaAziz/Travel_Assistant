package com.labproject.travelassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {

    ImageView hotel,restaurant,shop,spot,userhome,logout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        hotel=findViewById(R.id.addHotel);
        restaurant=findViewById(R.id.addRestaurant);
        shop=findViewById(R.id.addShop);
        spot=findViewById(R.id.addSpot);
        userhome=findViewById(R.id.goUserDashboard);
        logout=findViewById(R.id.logoutAdmin);
        firebaseAuth=FirebaseAuth.getInstance();

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminDashboard.this,AddHotel.class);
                startActivity(intent);
            }
        });

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminDashboard.this,AddRestaurant.class);
                startActivity(intent);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminDashboard.this,AddShop.class);
                startActivity(intent);
            }
        });

        spot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminDashboard.this,AddSpot.class);
                startActivity(intent);
            }
        });

        userhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminDashboard.this,UserDashboard.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent= new Intent(AdminDashboard.this,Login_form.class);
                startActivity(intent);
                finish();
            }
        });


    }
}