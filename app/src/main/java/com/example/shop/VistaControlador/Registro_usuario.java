package com.example.shop.VistaControlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;

public class Registro_usuario extends AppCompatActivity {

    Button btnRegistrar;
    EditText etNombre,
             etLastNameRegistro,
             etUserRegistro,
             etPasswordRegistro;

    SqliteOpenHelper helper = new SqliteOpenHelper(this,"shop",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        btnRegistrar=(Button) findViewById(R.id.btnRegistrar);
        etNombre=(EditText) findViewById(R.id.etNombre);
        etLastNameRegistro=(EditText) findViewById(R.id.etLastNameRegistro);
        etUserRegistro=(EditText)findViewById(R.id.etUserRegistro);
        etPasswordRegistro=(EditText)findViewById(R.id.etPasswordRegistro);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.abrirBD();
                helper.insertarUsuario(String.valueOf(etNombre.getText()),
                        String.valueOf(etLastNameRegistro.getText()),
                        String.valueOf(etUserRegistro.getText()),
                        String.valueOf(etPasswordRegistro.getText()));
                helper.cerrarBD();

                Toast.makeText(getApplicationContext(),"Usuario registrado con exito"
                ,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);

            }
        });


    }
}
