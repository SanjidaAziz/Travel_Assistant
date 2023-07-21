package com.labproject.travelassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddSpot extends AppCompatActivity {

    private Spinner division;
    private Button addNewSpot;
    private TextInputLayout spotName;
    private TextInputLayout spotLoc;

    private FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference root= db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spot);

        addNewSpot=findViewById(R.id.newSpotAdd);
        spotName= findViewById(R.id.newSpotName);
        spotLoc= findViewById(R.id.newSpotLoc);

        division=findViewById(R.id.divisionChooseSpot);

        String[] DistrictNames= new String[]{"Dhaka", "Barisal", "Chittagong","Khulna","Mymensingh","Rajshahi","Rangpur","Sylhet"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.division_view,R.id.textViewsampleId,DistrictNames);

        division.setAdapter(adapter);

        addNewSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String div= division.getSelectedItem().toString();
                if(valid()){
                    HashMap<String,Object> map= new HashMap<>();
                    map.put("tname",spotName.getEditText().getText().toString());
                    map.put("loc",spotLoc.getEditText().getText().toString());

                    //root.child("Spots").child(div).push().setValue(map);

                    //Dhaka bsl ctg khulna mym raj rang syl
                    if(div.equals("Dhaka")) root.child("Spots").child("Dhaka").push().setValue(map);
                    else if(div.equals("Barisal")) root.child("Spots").child("bsl").push().setValue(map);
                    else if(div.equals("Chittagong")) root.child("Spots").child("ctg").push().setValue(map);
                    else if(div.equals("Khulna")) root.child("Spots").child("khulna").push().setValue(map);
                    else if(div.equals("Mymensingh")) root.child("Spots").child("mym").push().setValue(map);
                    else if(div.equals("Rajshahi")) root.child("Spots").child("raj").push().setValue(map);
                    else if(div.equals("Rangpur")) root.child("Spots").child("rang").push().setValue(map);
                    else if(div.equals("Sylhet")) root.child("Spots").child("syl").push().setValue(map);
                    spotName.getEditText().setText("");
                    spotLoc.getEditText().setText("");
                    Toast.makeText(getApplicationContext(), "Place added successfully.", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "You can't leave any field empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    boolean valid(){
        String sptName= spotName.getEditText().getText().toString();
        String sptLoc= spotLoc.getEditText().getText().toString();

        if(sptName.isEmpty()){
            spotName.setError("Please enter Place name");
            spotName.requestFocus();
            return false;
        }
        else if(sptLoc.isEmpty()){
            spotLoc.setError("Please enter location");
            spotLoc.requestFocus();
            return false;
        }
        else return true;
    }
}