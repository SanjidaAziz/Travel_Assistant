package com.labproject.travelassistant;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;



public class Profile extends AppCompatActivity {

    private ImageView profilePic;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private Button logout;
    private Button back;

    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePic =findViewById(R.id.profilePic);
        storage =FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        fStore=FirebaseFirestore.getInstance();//T

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();


            }
        });

        logout = findViewById(R.id.signOut);
        back =findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseAuth.getInstance().signOut();
               // startActivity(new Intent(Profile.this,Login_form.class));
                //t
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(Profile.this,Login_form.class);
                startActivity(intent);
                finish();

            }
        });

        user=FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID= user.getUid();

        final TextView emailTextView =(TextView) findViewById(R.id.emailAddress);
        final TextView fullNameTextView =(TextView) findViewById(R.id.fullName);
        final TextView ageTextView =(TextView) findViewById(R.id.age);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile !=null){
                    String fullName=userProfile.fullName;
                    String email=userProfile.email;
                    String age=userProfile.age;

                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    ageTextView.setText(age);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something wrong happened!",Toast.LENGTH_LONG).show();

            }
        });


    }

    private void choosePicture() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && requestCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri= data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image....");
        pd.show();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference mountainsRef = storageReference.child("image/" + randomKey);

        mountainsRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    public void onFailure( @NonNull Exception exception){
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Failed to upload",Toast.LENGTH_LONG).show();

                    }

                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent =(100.00* taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                        pd.setMessage("Progress :"+(int) progressPercent + "%");

                    }
                });


    }
}

