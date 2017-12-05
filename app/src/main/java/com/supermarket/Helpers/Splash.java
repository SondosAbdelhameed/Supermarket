package com.supermarket.Helpers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.supermarket.*;
import com.supermarket.R;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // final RelativeLayout ParkEasily = (RelativeLayout) findViewById(R.id.splashT);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               startActivity(new Intent(Splash.this,Home.class));
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. *//*
                Intent mainIntent = new Intent(Splash.this, Home.class);
                final RelativeLayout ParkEasily = (RelativeLayout) findViewById(R.id.splashT);/*
                Animation fade1 = AnimationUtils.loadAnimation(Splash.this, R.anim.animations);
                ParkEasily.startAnimation(fade1);

                ParkEasily.startAnimation(fade1);

                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    public void onAnimationRepeat(Animation animation){
    }
    public void onAnimationStart(Animation animation){
    }

                final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.animations);
                final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.antirotate);
                final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

                ParkEasily.startAnimation(animation_2);
                animation_2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ParkEasily.startAnimation(animation_1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                animation_1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ParkEasily.startAnimation(animation_3);
                        finish();
                        Intent i = new Intent(getBaseContext(),Home.class);
                        startActivity(i);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/
            }
        }

