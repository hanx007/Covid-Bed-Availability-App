package com.example.covidbedavailability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private  static  int SPLASH_SCREEN =3500;
    TextView textView1,textView2,textView3;
    Animation top,bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
//        textView1 = findViewById(R.id.textView1);
//        textView2 = findViewById(R.id.textView2);
//        textView3 = findViewById(R.id.textView3);
//        top  = AnimationUtils.loadAnimation(this,R.anim.top);
//        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom);
//
//
//        textView1.setAnimation(top);
//        textView2.setAnimation(bottom);
//        textView3.setAnimation(bottom);



        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, SPLASH_SCREEN);

    }
}