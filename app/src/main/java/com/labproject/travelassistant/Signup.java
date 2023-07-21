package com.labproject.travelassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    private EditText musername,memail,mpassword,mage;
    private  Button msignupBtn;
    private TextView mgender;
    private RadioGroup radiogroup;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fStore=FirebaseFirestore.getInstance();

         //mFullName=findViewById(R.id.fullname);
         musername=findViewById(R.id.fullName);
         memail=findViewById(R.id.email);
         mpassword=findViewById(R.id.passwordnum);
         mage=findViewById(R.id.age);
         msignupBtn=findViewById(R.id.signup);
         radiogroup=findViewById(R.id.person);

         msignupBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String  fullName = musername.getText().toString();
                 String  email = memail.getText().toString();
                 String password =mpassword.getText().toString();
                 String age =mage.getText().toString();
                 String idAdmin ="0";
                 firebaseAuth =FirebaseAuth.getInstance();
                 if(fullName.isEmpty()){
                     musername.setError("Please enter Your fullName ");
                     musername.requestFocus();
                 }
                 else if(email.isEmpty()){
                     memail.setError("Please enter Your email ");
                     memail.requestFocus();
                 }
                 else if(password.isEmpty()){
                     mpassword.setError("Please enter your password");
                     mpassword.requestFocus();
                 }
                 else if(password.length() < 6){
                     mpassword.setError("Password is less than 6 digits");
                     mpassword.requestFocus();
                 }
                 else if(age.isEmpty()){
                     mage.setError("Please enter  your password correctly");
                     mage.requestFocus();
                 }
                 else{
                     firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                         @Override
                         public void onSuccess(AuthResult authResult) {

                             User user = new User(fullName,age,email);
                             FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                     .setValue(user);

                             //T
                             FirebaseUser fusr= firebaseAuth.getCurrentUser();

                             DocumentReference df=fStore.collection("Users").document(fusr.getUid());
                             Map<String,Object> userInfo= new HashMap<>();
                             userInfo.put("fullName",musername.getText().toString());
                             userInfo.put("email",memail.getText().toString());
                             userInfo.put("age",mage.getText().toString());
                             userInfo.put("isAdmin","0");

                             df.set(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     Toast.makeText(getApplicationContext(), "Signup Successful.", Toast.LENGTH_SHORT).show();
                                     Intent intent= new Intent(Signup.this,UserDashboard.class);
                                     startActivity(intent);
                                     finish();
                                 }
                             });

                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(getApplicationContext(), "Signup Failed.Try again! ", Toast.LENGTH_SHORT).show();
                         }
                     });

                 }

             }
         });
    }
}