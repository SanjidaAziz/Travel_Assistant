package com.labproject.travelassistant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.HashMap;

public class AddRestaurant extends AppCompatActivity {

    private LinearLayout uploadImg;
    private Spinner division;
    private Button addNewRestaurant;
    private TextInputLayout restaurantName;
    private TextInputLayout restaurantLoc;
    private TextInputLayout restaurantRate;
    private FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference root= db.getReference();
    private Uri purl;
    //private static final int IMAGE_REQUEST=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        addNewRestaurant=findViewById(R.id.newRestaurantAdd);
        restaurantName= findViewById(R.id.newRestaurantName);
        restaurantLoc= findViewById(R.id.newRestaurantLoc);
        restaurantRate= findViewById(R.id.newRestaurantRate);
        division=findViewById(R.id.divisionChooseRestaurant);
        //uploadImg = findViewById(R.id.newRestaurantImage);


        String[] DistrictNames= new String[]{"Dhaka", "Barisal", "Chittagong","Khulna","Mymensingh","Rajshahi","Rangpur","Sylhet"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.division_view,R.id.textViewsampleId,DistrictNames);

        division.setAdapter(adapter);

       /* uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
                Toast.makeText(getApplicationContext(), "Image loaded successfully.", Toast.LENGTH_SHORT).show();
            }
        });*/

        addNewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String div= division.getSelectedItem().toString();
                if(valid()){
                    //String url= uploadImage();

                    HashMap<String,Object> map= new HashMap<>();
                    map.put("rname",restaurantName.getEditText().getText().toString());
                    map.put("location",restaurantLoc.getEditText().getText().toString());
                    map.put("rating",restaurantRate.getEditText().getText().toString());
                   // map.put("purl",url);

                    //Dhaka bsl ctg khulna mym raj rang syl
                    if(div.equals("Dhaka")) root.child("Restaurant").child("Dhaka").push().setValue(map);
                    else if(div.equals("Barisal")) root.child("Restaurant").child("bsl").push().setValue(map);
                    else if(div.equals("Chittagong")) root.child("Restaurant").child("ctg").push().setValue(map);
                    else if(div.equals("Khulna")) root.child("Restaurant").child("khulna").push().setValue(map);
                    else if(div.equals("Mymensingh")) root.child("Restaurant").child("mym").push().setValue(map);
                    else if(div.equals("Rajshahi")) root.child("Restaurant").child("raj").push().setValue(map);
                    else if(div.equals("Rangpur")) root.child("Restaurant").child("rang").push().setValue(map);
                    else if(div.equals("Sylhet")) root.child("Restaurant").child("syl").push().setValue(map);



                    restaurantName.getEditText().setText("");
                    restaurantLoc.getEditText().setText("");
                    restaurantRate.getEditText().setText("");
                    Toast.makeText(getApplicationContext(), "Place added successfully.", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(),"You can't leave any field empty",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean valid(){
        String htlName= restaurantName.getEditText().getText().toString();
        String htlLoc= restaurantLoc.getEditText().getText().toString();
        String htlRate= restaurantRate.getEditText().getText().toString();
        if(htlName.isEmpty()){
            restaurantName.setError("Please enter Restaurant name");
            restaurantName.requestFocus();
            return false;
        }
        else if(htlLoc.isEmpty()){
            restaurantLoc.setError("Please enter location");
            restaurantLoc.requestFocus();
            return false;
        }
        else if(htlRate.isEmpty()){
            restaurantRate.setError("Please enter rating");
            restaurantRate.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    /*private void openImage(){
        Intent intent=new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }*/

   /* @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK){
            purl= data.getData();


        }
    }*/

   /* private String uploadImage(){
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        if(purl != null){
            final StorageReference fileRef= FirebaseStorage.getInstance().getReference().child("Uploads").child(System.currentTimeMillis()+"."+getFileExtension(purl));
            fileRef.putFile(purl).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url=uri.toString();
                            Log.d("Download Url",url);

                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Image upload successfully.", Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            });


        }
        return purl.toString();
    }*/
}