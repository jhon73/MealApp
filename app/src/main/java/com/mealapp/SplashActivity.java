package com.mealapp;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mealapp.helper.ConstantData;
import com.mealapp.utility.Utility;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.getString(ConstantData.TOKEN, "") != null && sharedPreferences.getString(ConstantData.TOKEN, "").length() != 0) {
                    Utility.navigationIntent(SplashActivity.this, MainActivity.class);
                    finish();
                }else{
                    Utility.navigationIntent(SplashActivity.this,LoginActivity.class);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
