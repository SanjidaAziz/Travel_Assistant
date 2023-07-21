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

public class AddShop extends AppCompatActivity {

    private Spinner division;
    private Button addNewShop;
    private TextInputLayout shopName;
    private TextInputLayout shopLoc;

    private FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference root= db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        addNewShop=findViewById(R.id.newShopAdd);
        shopName= findViewById(R.id.newShopName);
        shopLoc= findViewById(R.id.newShopLoc);

        division=findViewById(R.id.divisionChooseShop);

        String[] DistrictNames= new String[]{"Dhaka", "Barisal", "Chittagong","Khulna","Mymensingh","Rajshahi","Rangpur","Sylhet"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.division_view,R.id.textViewsampleId,DistrictNames);

        division.setAdapter(adapter);

        addNewShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String div= division.getSelectedItem().toString();
                if(valid()){
                    HashMap<String,Object> map= new HashMap<>();
                    map.put("sname",shopName.getEditText().getText().toString());
                    map.put("loc",shopLoc.getEditText().getText().toString());

                    //root.child("Shops").child(div).push().setValue(map);

                    //Dhaka bsl ctg khulna mym raj rang syl
                    if(div.equals("Dhaka")) root.child("Shops").child("Dhaka").push().setValue(map);
                    else if(div.equals("Barisal")) root.child("Shops").child("bsl").push().setValue(map);
                    else if(div.equals("Chittagong")) root.child("Shops").child("ctg").push().setValue(map);
                    else if(div.equals("Khulna")) root.child("Shops").child("khulna").push().setValue(map);
                    else if(div.equals("Mymensingh")) root.child("Shops").child("mym").push().setValue(map);
                    else if(div.equals("Rajshahi")) root.child("Shops").child("raj").push().setValue(map);
                    else if(div.equals("Rangpur")) root.child("Shops").child("rang").push().setValue(map);
                    else if(div.equals("Sylhet")) root.child("Shops").child("syl").push().setValue(map);

                    shopName.getEditText().setText("");
                    shopLoc.getEditText().setText("");
                    Toast.makeText(getApplicationContext(), "Place added successfully.", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(AddShop.this,"You can't leave any field empty",Toast.LENGTH_SHORT).show();

            }
        });

    }

    boolean valid(){
        String shpName= shopName.getEditText().getText().toString();
        String shpLoc= shopLoc.getEditText().getText().toString();

        if(shpName.isEmpty()){
            shopName.setError("Please enter Shop name");
            shopName.requestFocus();
            return false;
        }
        else if(shpLoc.isEmpty()){
            shopLoc.setError("Please enter location");
            shopLoc.requestFocus();
            return false;
        }
        else return true;
    }
}