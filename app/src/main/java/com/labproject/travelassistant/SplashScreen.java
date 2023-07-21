package com.labproject.travelassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private static int splash_timer=5000;

    //variable
    ImageView backgroundImage;
    TextView  splashScreenTxt;

    //animation
    Animation sideAnim,bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //hook
        backgroundImage= findViewById(R.id.background_image);
        splashScreenTxt= findViewById(R.id.sstxt);

        //animation
        sideAnim= AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //set animation on element
        backgroundImage.setAnimation(sideAnim);
        splashScreenTxt.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(SplashScreen.this,Login_form.class);
                startActivity(intent);
                finish();
            }
        },splash_timer);
    }
}