package com.example.shop.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.R;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

    }

    public void VistaPrincipal(View vista){
//        Al iniciar secion cambia a la venta principal de la apliacion

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
