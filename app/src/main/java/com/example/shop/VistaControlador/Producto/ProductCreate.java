package com.example.shop.VistaControlador.Producto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shop.Modelo.CategoriaProducto.CategoriaProducto;
import com.example.shop.Modelo.Producto.Producto;
import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;

import com.example.shop.VistaControlador.CategoryProduct.CategoryCreate;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage;

import java.util.ArrayList;

public class ProductCreate extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText txtProductId;
    private EditText txtProductName;
    private EditText txtProductQuantity;
    private EditText txtProductPrice;
    private EditText txtProductDetail;
    private ImageView imgProducImage;
    private Button btnSelectImage;
    private Button btnCategoryCreate;
    private Button btnProductSave;
    private byte [] bite;
    private Spinner spinnerCategory;

    private  ArrayAdapter<CharSequence> adapter;
    private ArrayList<CategoriaProducto> arrayCategory;

    private MagicalCamera magicalCamera;
    private MagicalPermissions magicalPermissions;
    private int RESIZE_PHOTO_PIXELES_PERCENTAGE = 100;

    Producto product = new Producto();
    CategoriaProducto category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_create);
        //Permisos para Seleccionar Imagenes
        String[] permissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE };
        magicalPermissions = new MagicalPermissions(this,permissions);
        magicalCamera = new MagicalCamera(this, RESIZE_PHOTO_PIXELES_PERCENTAGE, magicalPermissions);

        txtProductId = (EditText)findViewById(R.id.inputPedidoCodigo);
        txtProductName = (EditText)findViewById(R.id.inputPedidoName);
        txtProductDetail = (EditText)findViewById(R.id.inputPedidoDetail);
        txtProductPrice = (EditText)findViewById(R.id.inputPedidoLatitud);
        txtProductQuantity = (EditText)findViewById(R.id.inputPedidoCantidad);
        imgProducImage = (ImageView)findViewById(R.id.pedidoImage);
        btnSelectImage = (Button)findViewById(R.id.btnSelectImage);
        btnCategoryCreate = (Button)findViewById(R.id.btnCategoryCreate);
        btnProductSave = (Button)findViewById(R.id.btnProductSave);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);

        SpinnerCategory();

        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(this);
        btnSelectImage.setOnClickListener(this);
        btnProductSave.setOnClickListener(this);
        btnCategoryCreate.setOnClickListener(this);
    }

    public void Create(){

        SqliteOpenHelper admin = new SqliteOpenHelper(this, "shop", null , 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String productId = txtProductId.getText().toString();
        String productName = txtProductName.getText().toString();
        String productDetail = txtProductDetail.getText().toString();
        String productPrice = txtProductPrice.getText().toString();
        String productQuantity = txtProductQuantity.getText().toString();


        if (!productId.isEmpty() && !productName.isEmpty() && !productDetail.isEmpty() && !productPrice.isEmpty() &&  !productQuantity.isEmpty()){

            ContentValues register = new ContentValues();

            register.put("id", productId);
            register.put("name", productName);
            register.put("detail", productDetail);
            register.put("price", productPrice);
            register.put("quantity", productQuantity);
            register.put("image", product.getImage());
            register.put("categoria", category.getName());

            bd.insert("producto", null, register);

            bd.close();

            txtProductId.setText("");
            txtProductName.setText("");
            txtProductDetail.setText("");
            txtProductPrice.setText("");
            txtProductQuantity.setText("");
            imgProducImage.setImageResource(android.R.drawable.ic_menu_gallery);

            Toast.makeText(this, "Producto Guardado", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void SpinnerCategory(){
        SqliteOpenHelper admin = new SqliteOpenHelper(this,"shop",null,1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        arrayCategory = new ArrayList<CategoriaProducto>();
        Cursor cursor = bd.rawQuery("SELECT * FROM " + "categoria_producto", null);

        while (cursor.moveToNext()){
            category = new CategoriaProducto();
            category.setName(cursor.getString(0));

            arrayCategory.add(category);
        }
        bd.close();

        ArrayList <String>arrayCategoryInfo = new ArrayList<String>();
        for (int i = 0; i < arrayCategory.size() ; i++) {
            arrayCategoryInfo.add(arrayCategory.get(i).getName());
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 ,arrayCategoryInfo);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        magicalCamera.resultPhoto(requestCode,resultCode,data);
        imgProducImage.setImageBitmap(magicalCamera.getPhoto());
        bite = ConvertSimpleImage.bitmapToBytes(magicalCamera.getPhoto(),MagicalCamera.PNG);
        product.setImage(bite);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProductSave:
                Create();
                break;

            case R.id.btnSelectImage:
                magicalCamera.selectedPicture("Escoja una Aplicación");
                break;

            case R.id.btnCategoryCreate:
                Intent intent = new Intent(getApplicationContext(), CategoryCreate.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category.setName(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
