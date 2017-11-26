package com.lavekush.telstra.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lavekush.telstra.R;

/**
 * Application splash screen, for showoing company logo and name
 */
public class SplashScreenMainActivity extends AppCompatActivity {

    // Splash screen timeout delay
    private static int SPLASH_TIME_OUT_DELAY = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_main);

        //Handler for background task management
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer.
             * This can be replace by some configuration api call delay
             */

            @Override
            public void run() {

                // This method will be executed once the timer is over
                Intent intentHomeActivity = new Intent(SplashScreenMainActivity.this, HomeActivity.class);
                startActivity(intentHomeActivity);

                // close current this activity
                finish();
            }
        }, SPLASH_TIME_OUT_DELAY);
    }
}
