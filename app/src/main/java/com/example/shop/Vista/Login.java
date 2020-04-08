package com.example.shop.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.R;

public class Login extends AppCompatActivity {
    private EditText etUser;
    private EditText etPW;
    private Button btnLogin;

    String user ="admin";
    String pw ="12345";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etUser =( EditText) findViewById(R.id.inputUser);
        etPW = (EditText) findViewById(R.id.inputPassword);
        btnLogin=(Button)findViewById(R.id.botonRegistrar);

    }
    public void validarLogin(View view){

        if (user.equals(etUser.getText()) && (pw.equals(etPW.getText()))){
            Intent intentMenu = new Intent(Login.this,MainActivity.class);
            startActivity(intentMenu);
            finish();

        }

    }
}
