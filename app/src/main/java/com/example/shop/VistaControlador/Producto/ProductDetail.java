package com.example.shop.VistaControlador.Producto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;

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
    private EditText inputPedidoCantidad;

    private EditText inputProductQuantity;
    private Button btnProductEdit;
    private Button btnOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        txtProductName = (TextView)findViewById(R.id.txtUserList);
        txtProductQuantity = (TextView)findViewById(R.id.txtProductQuantity);
        txtProductPrice = (TextView)findViewById(R.id.txtProductPrice);
        txtProductDetail = (TextView)findViewById(R.id.txtProductDetail);
        imgProductImage = (ImageView)findViewById(R.id.imgProductImage);
        inputPedidoCantidad = (EditText)findViewById(R.id.inputPedidoCantidad);

        inputProductQuantity = (EditText)findViewById(R.id.inputPedidoCantidad);
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
        btnOrder.setOnClickListener(this);

        Detail();

    }

    private void Detail() {

        txtProductName.setText(productName);
        txtProductQuantity.setText("Stock "+productQuantity);
        txtProductPrice.setText("Precio $"+productPrice);
        txtProductDetail.setText(productDetail);
        Glide.with(this).load(productImage).into(imgProductImage);

    }

    public void crearPedido(){
        SqliteOpenHelper admin = new SqliteOpenHelper(getApplicationContext(),"shop",null,1);

        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues registrar = new ContentValues();

        ContentValues editar = new ContentValues();



        int cantidadProducto = Integer.parseInt(inputPedidoCantidad.getText().toString());
        if(cantidadProducto<=0||cantidadProducto>productQuantity){
            Toast.makeText(getApplicationContext(),"Verifique la cantidad ingresada",Toast.LENGTH_LONG).show();


        }
        else{
            double totalPagar = cantidadProducto*productPrice;
            registrar.put("cantidad",cantidadProducto);
            registrar.put("total_pagar",totalPagar);
            registrar.put("producto_id",productId);
            registrar.put("usuario_id",1);
            registrar.put("latitude","51512");
            registrar.put("longitude","2313.213");
            db.insert("pedido", null,registrar);

            Toast.makeText(getApplicationContext(),"Pedido registrado",Toast.LENGTH_LONG).show();

            int restarStock=productQuantity-cantidadProducto;

            editar.put("quantity",restarStock);
            int value = db.update("producto",editar,"id="+productId,null);
            db.close();

        }
    }


    @Override
    public void onClick(View view) {
        //Para editar el producto
        switch (view.getId()){
            case R.id.btnProductEdit:

            Intent intent = new Intent(getApplicationContext(), ProductEdit.class);

            intent.putExtra("productId", productId);
            intent.putExtra("productName", productName);
            intent.putExtra("productQuantity", productQuantity);
            intent.putExtra("productPrice", productPrice);
            intent.putExtra("productImage", productImage);
            intent.putExtra("productDetail", productDetail);

            startActivity(intent);
            break;

            case R.id.btnOrder:
                crearPedido();
                break;
        }
    }

}
