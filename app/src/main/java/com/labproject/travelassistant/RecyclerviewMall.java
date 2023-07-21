package com.labproject.travelassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerviewMall extends AppCompatActivity {

    RecyclerView recviewM;
    MallAdapter mAdapter;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_mall);

        Bundle bundle=getIntent().getExtras();
        value= bundle.getString("tag");

        recviewM=(RecyclerView)findViewById(R.id.recviewMall);
        recviewM.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MallModel> options =
                new FirebaseRecyclerOptions.Builder<MallModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Shops").child(value), MallModel.class)
                        .build();

        mAdapter=new MallAdapter(options);
        recviewM.setAdapter(mAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}