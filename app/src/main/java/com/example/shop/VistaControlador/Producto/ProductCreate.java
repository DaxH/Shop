package com.example.shop.VistaControlador.Producto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shop.Modelo.Producto.Producto;
import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage;

public class ProductCreate extends AppCompatActivity{

    private EditText txtProductId;
    private EditText txtProductName;
    private EditText txtProductQuantity;
    private EditText txtProductPrice;
    private EditText txtProductDetail;
    private ImageView imgProducImage;
    private Button btnSelectPhoto;
    private byte [] bite;

    private MagicalCamera magicalCamera;
    private MagicalPermissions magicalPermissions;
    private int RESIZE_PHOTO_PIXELES_PERCENTAGE = 1000;

    Producto product = new Producto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_create);
        String[] permissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE };

        magicalPermissions = new MagicalPermissions(this,permissions);

        magicalCamera = new MagicalCamera(this, RESIZE_PHOTO_PIXELES_PERCENTAGE, magicalPermissions);

        txtProductId = (EditText)findViewById(R.id.inputProductId);
        txtProductName = (EditText)findViewById(R.id.inputProductName);
        txtProductDetail = (EditText)findViewById(R.id.inputProductDetail);
        txtProductPrice = (EditText)findViewById(R.id.inputProductPrice);
        txtProductQuantity = (EditText)findViewById(R.id.inputProductQuantity);
        imgProducImage = (ImageView)findViewById(R.id.productImage);
        btnSelectPhoto = (Button)findViewById(R.id.btnImage);

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magicalCamera.selectedPicture("Escoja una Aplicación");
            }
        });
    }

    public void Create(View view){

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
            register.put("categoria", "Zapato");

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        magicalCamera.resultPhoto(requestCode,resultCode,data);
        imgProducImage.setImageBitmap(magicalCamera.getPhoto());
        bite = ConvertSimpleImage.bitmapToBytes(magicalCamera.getPhoto(),MagicalCamera.PNG);
        product.setImage(bite);
    }
}
