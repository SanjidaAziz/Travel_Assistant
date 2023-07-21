package com.labproject.travelassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerviewSpot extends AppCompatActivity {

    RecyclerView recviewT;
    SpotAdapter tAdapter;
     String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_spot);

        Bundle bundle=getIntent().getExtras();

        value= bundle.getString("tag");


        recviewT=(RecyclerView)findViewById(R.id.recviewSpt);
        recviewT.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<SpotModel> options =
               // new FirebaseRecyclerOptions.Builder<SpotModel>()
                      //  .setQuery(FirebaseDatabase.getInstance().getReference().child("Spots").child("Dhaka"), SpotModel.class)
                      //  .build();
                new FirebaseRecyclerOptions.Builder<SpotModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Spots").child(value), SpotModel.class)
                .build();

        tAdapter=new SpotAdapter(options);
        recviewT.setAdapter(tAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tAdapter.stopListening();
    }
}