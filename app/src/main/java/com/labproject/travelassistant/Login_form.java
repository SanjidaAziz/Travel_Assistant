package com.labproject.travelassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class Login_form extends AppCompatActivity {
    private EditText myusername,mypassword;
    private Button rlogin,rresgister;
    private TextView adminlogin;
    FirebaseAuth firebaseAuth;
    //ProgressBar progressBar;
    FirebaseStorage fstore;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        //getSupportActionBar().setTitle("Login Form");

        myusername=findViewById(R.id.myemail);
        mypassword=findViewById(R.id.password);
        rlogin=findViewById(R.id.loginn);
        rresgister=(Button)findViewById(R.id.register);
        adminlogin=findViewById(R.id.adminloginbtn);

        fStore=FirebaseFirestore.getInstance();
        rlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = myusername.getText().toString();
                String p =mypassword.getText().toString();
                firebaseAuth=FirebaseAuth.getInstance();

                if(e.isEmpty()){
                    myusername.setError("Please enter your email");
                    myusername.requestFocus();
                }
                else if(p.isEmpty()){
                    mypassword.setError("Please enter your password");
                    mypassword.requestFocus();
                }
                else if(!e.isEmpty() && !p.isEmpty()){

                    firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                                finish();
                            }
                            else{

                                Toast.makeText(Login_form.this, "Login Failed-You entered wrong details..", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        rresgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Login_form.this,Signup_form.class));
                Intent intent= new Intent(Login_form.this,Signup.class);
                startActivity(intent);

            }
        });

        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth =FirebaseAuth.getInstance();
               // fstore= FirebaseStorage.getInstance();

                String e = myusername.getText().toString();
                String p =mypassword.getText().toString();
                if(myusername.getText().toString().equals("")){
                    myusername.setError("Please enter your email");
                    myusername.requestFocus();
                }
                else if(mypassword.getText().toString().equals("")){
                    mypassword.setError("Please enter your password");
                    mypassword.requestFocus();
                }
                else if(!e.isEmpty() && !p.isEmpty()){

                    firebaseAuth.signInWithEmailAndPassword(e, p).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //Toast.makeText(getApplicationContext(), "Successfully Loged in as Admin", Toast.LENGTH_SHORT).show();
                            checkUserAccesslevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }

    private void checkUserAccesslevel(String uId){
        DocumentReference df=fStore.collection("Users").document(uId);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.getString("isAdmin").equals("1")){
                    Toast.makeText(getApplicationContext(), "Successfully Loged in as Admin", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "You are not an admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent= new Intent(getApplicationContext(),UserDashboard.class);
            startActivity(intent);
            finish();
        }

    }
}