package com.example.shop.VistaControlador;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;

public class Login extends AppCompatActivity {

    Button botonIniSes;
    Button botonRegistro;

    SqliteOpenHelper helper = new SqliteOpenHelper(this,"shopDB",null,1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        botonRegistro = (Button) findViewById(R.id.botonRegistro);

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registro_usuario.class);
                startActivity(intent);
            }
        });
        botonIniSes = (Button) findViewById(R.id.botonIniSes);
        botonIniSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etUser = (EditText) findViewById(R.id.etUser);
                EditText etPassword = (EditText) findViewById(R.id.etPassword);

                try {
                    Cursor cursor = helper.consultarUsuarioPassword(etUser.getText().toString(),etPassword.getText().toString());

                    if (cursor.getCount() > 0) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario y/o password incorrectos",
                                Toast.LENGTH_LONG).show();
                    }
                    etUser.setText("");
                    etPassword.setText("");
                    etUser.findFocus();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
