package com.article.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.article.R;


public class SplashActivity extends AppCompatActivity {
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mHandler.postAtTime(() -> {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this,
                    MainActivity.class));
            finish();
        }, 1000);
    }


}
