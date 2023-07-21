package com.labproject.travelassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerviewRes extends AppCompatActivity {

    RecyclerView recviewR;
    ResAdapter resAdapter;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_res);

        Bundle bundle=getIntent().getExtras();
        value= bundle.getString("tag");

        recviewR=(RecyclerView)findViewById(R.id.recviewRes);
        recviewR.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ResModel> options =
             //   new FirebaseRecyclerOptions.Builder<ResModel>()
                      //  .setQuery(FirebaseDatabase.getInstance().getReference().child("Restaurant").child("ctg"), ResModel.class)
                     //   .build();
                new FirebaseRecyclerOptions.Builder<ResModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Restaurant").child(value), ResModel.class)
                        .build();

        resAdapter=new ResAdapter(options);
        recviewR.setAdapter(resAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        resAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        resAdapter.stopListening();
    }

}