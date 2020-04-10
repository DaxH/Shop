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

import com.bumptech.glide.Glide;
import com.example.shop.Modelo.Producto.Producto;
import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage;

public class ProductEdit extends AppCompatActivity implements View.OnClickListener {

    private int productId;
    private String productName;
    private int productQuantity;
    private double productPrice;
    private String productDetail;
    private byte[] productImage;

    private EditText inputProductId;
    private EditText inputProductName;
    private EditText inputProductQuantity;
    private EditText inputProductPrice;
    private ImageView imgProductImage;
    private EditText inputProductDetail;

    private Button btnProductSave;
    private Button btnSelectImage;

    private MagicalCamera magicalCamera;
    private MagicalPermissions magicalPermissions;
    private int RESIZE_PHOTO_PIXELES_PERCENTAGE = 500;

    Producto product = new Producto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);

        String[] permissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE };
        magicalPermissions = new MagicalPermissions(this,permissions);
        magicalCamera = new MagicalCamera(this, RESIZE_PHOTO_PIXELES_PERCENTAGE, magicalPermissions);

        inputProductId = (EditText)findViewById(R.id.inputPedidoCodigo);
        inputProductName = (EditText)findViewById(R.id.inputPedidoName);
        inputProductQuantity = (EditText)findViewById(R.id.inputPedidoCantidad);
        inputProductPrice = (EditText)findViewById(R.id.inputPedidoLatitud);
        inputProductDetail = (EditText)findViewById(R.id.inputPedidoDetail);
        imgProductImage = (ImageView)findViewById(R.id.pedidoImage);

        btnProductSave = (Button)findViewById(R.id.btnProductEdit);
        btnSelectImage = (Button)findViewById(R.id.btnImage);

        ProductData();

        btnProductSave.setOnClickListener(this);
        btnSelectImage.setOnClickListener(this);
    }

    public void ProductData(){
        //Detalle del Producto a Editar

        Intent intent = getIntent();
        productName = intent.getStringExtra("productName");
        productQuantity = intent.getIntExtra("productQuantity", 1);
        productPrice = intent.getDoubleExtra("productPrice" ,1);
        productImage = intent.getByteArrayExtra("productImage");
        productDetail = intent.getStringExtra("productDetail");
        productId = intent.getIntExtra("productId",1);

        inputProductId.setText(""+(Integer)productId);
        inputProductName.setText(productName);
        inputProductQuantity.setText(""+(Integer)productQuantity);
        inputProductPrice.setText(""+(Double) productPrice);
        inputProductDetail.setText(productDetail);
        Glide.with(this).load(productImage).into(imgProductImage);
    }

    public void Edit(){

        SqliteOpenHelper admin = new SqliteOpenHelper(this,"shop",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String productId = inputProductId.getText().toString();
        String productName = inputProductName.getText().toString();
        String productDetail = inputProductDetail.getText().toString();
        String productPrice = inputProductPrice.getText().toString();
        String productQuantity = inputProductQuantity.getText().toString();

        if (!productId.isEmpty() && !productName.isEmpty() && !productDetail.isEmpty() && !productPrice.isEmpty() &&  !productQuantity.isEmpty()){

            ContentValues register = new ContentValues();

            register.put("id", productId);
            register.put("name", productName);
            register.put("detail", productDetail);
            register.put("price", productPrice);
            register.put("quantity", productQuantity);
            register.put("image", product.getImage());
            register.put("categoria", "Zapato");

            int value = bd.update("producto", register,"id="+ productId,null);
            bd.close();

            if (value == 1){
                Toast.makeText(this, "Producto Editado", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "No se puedo Editar", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        magicalCamera.resultPhoto(requestCode,resultCode,data);
        imgProductImage.setImageBitmap(magicalCamera.getPhoto());
        productImage = ConvertSimpleImage.bitmapToBytes(magicalCamera.getPhoto(),MagicalCamera.PNG);
        product.setImage(productImage);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnProductEdit){
            Edit();
        }
        if (view.getId() == R.id.btnImage){
            magicalCamera.selectedPicture("Escoja una Aplicación");
        }

    }
}
