package com.example.shop.VistaControlador.Producto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shop.Modelo.Producto.Producto;
import com.example.shop.R;

public class ProductDetail extends AppCompatActivity implements View.OnClickListener {

    private int productId;
    private String productName;
    private int productQuantity;
    private double productPrice;
    private String productDetail;
    private byte[] productImage;

    private TextView txtProductName;
    private TextView txtProductQuantity;
    private TextView txtProductPrice;
    private ImageView imgProductImage;
    private TextView txtProductDetail;

    private EditText inputProductQuantity;
    private Button btnProductEdit;
    private Button btnOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        txtProductName = (TextView)findViewById(R.id.txtProductName);
        txtProductQuantity = (TextView)findViewById(R.id.txtProductQuantity);
        txtProductPrice = (TextView)findViewById(R.id.txtProductPrice);
        txtProductDetail = (TextView)findViewById(R.id.txtProductDetail);
        imgProductImage = (ImageView)findViewById(R.id.imgProductImage);

        inputProductQuantity = (EditText)findViewById(R.id.inputProductQuantity);
        btnOrder = (Button)findViewById(R.id.btnOrder);
        btnProductEdit =(Button)findViewById(R.id.btnProductEdit);

        Intent intent = getIntent();
        productName = intent.getStringExtra("productName");
        productQuantity = intent.getIntExtra("productQuantity", 1);
        productPrice = intent.getDoubleExtra("productPrice" ,1);
        productImage = intent.getByteArrayExtra("productImage");
        productDetail = intent.getStringExtra("productDetail");
        productId = intent.getIntExtra("productId",1);

        btnProductEdit.setOnClickListener(this);

        Detail();

    }

    private void Detail() {

        txtProductName.setText(productName);
        txtProductQuantity.setText("Stock "+productQuantity);
        txtProductPrice.setText("Precio $"+productPrice);
        txtProductDetail.setText(productDetail);
        Glide.with(this).load(productImage).into(imgProductImage);

    }


    @Override
    public void onClick(View view) {
        //Para editar el producto
        Intent intent = new Intent(getApplicationContext(), ProductEdit.class);

        intent.putExtra("productId", productId);
        intent.putExtra("productName", productName);
        intent.putExtra("productQuantity", productQuantity);
        intent.putExtra("productPrice", productPrice);
        intent.putExtra("productImage", productImage);
        intent.putExtra("productDetail", productDetail);

        startActivity(intent);
    }
}
