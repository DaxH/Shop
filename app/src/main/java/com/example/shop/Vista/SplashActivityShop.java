package com.example.shop.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.R;

public class SplashActivityShop extends AppCompatActivity {


     private final int DURACION_SPLASH = 3000; // 2 segundos

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_shop);

        new Handler().postDelayed(new Runnable(){
            public void run(){

                Intent intent = new Intent(SplashActivityShop.this, MainActivity.class);
                startActivity(intent);
                finish();
            };

        }, DURACION_SPLASH);
    }
}
