package com.example.shop.VistaControlador.CategoryProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shop.Modelo.CategoriaProducto.CategoriaProducto;
import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;

public class CategoryCreate extends AppCompatActivity implements View.OnClickListener {

    private EditText inputCategoryName;
    private Button btnCategorySave;

    CategoriaProducto category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        inputCategoryName = (EditText)findViewById(R.id.inputCategoryName);
        btnCategorySave = (Button)findViewById(R.id.btnCategorySave);

        btnCategorySave.setOnClickListener(this);

    }

    public void Create(){
        SqliteOpenHelper admin = new SqliteOpenHelper(this,"shop",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        category = new CategoriaProducto();

        category.setName(inputCategoryName.getText().toString());

        if (!category.getName().isEmpty()){

            ContentValues register = new ContentValues();
            register.put("name", category.getName());

            bd.insert("categoria_producto", null, register);
            bd.close();

            inputCategoryName.setText("");

            Toast.makeText(this, "Categoria Guardada", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Debes Llenar el Campo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        Create();

    }
}
