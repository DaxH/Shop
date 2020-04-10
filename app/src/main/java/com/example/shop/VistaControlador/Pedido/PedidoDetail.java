package com.example.shop.VistaControlador.Pedido;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shop.Modelo.Producto.Producto;
import com.example.shop.Modelo.Usuario.Usuario;
import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;

public class PedidoDetail extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewClienteDet;
    private TextView txtproductQuantityPedido,txtproductPricePedido,txtClientNombre,txtClienteApellido;
    private Button btnEntregar;

    private int usuarioID,productoID,cantidadPedido;
    private double totalPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_detail);

        txtproductQuantityPedido=(TextView)findViewById(R.id.txtProductQuantity);
        txtproductPricePedido=(TextView)findViewById(R.id.txtProductPrice);
        txtClientNombre=(TextView)findViewById(R.id.txtClienteNombre);

        btnEntregar=(Button)findViewById(R.id.btnEntregar);

        Intent intent = getIntent();
        usuarioID=intent.getIntExtra("usuarioID",1);
        productoID=intent.getIntExtra("productoID",1);
        cantidadPedido=intent.getIntExtra("cantidadProducto",1);
        totalPagar=intent.getDoubleExtra("totalPagar",1);

        detailPedido();
    }

    public void detailPedido(){

        SqliteOpenHelper admin = new SqliteOpenHelper(getApplicationContext(),"shop",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor cursorProducto = db.rawQuery("SELECT image FROM "+"producto"+" WHERE id='"+productoID+"'",null);
        Cursor cursorUsuario = db.rawQuery("SELECT name,last_name FROM "+"usuario"+" WHERE id='"+usuarioID+"'",null);
        Producto producto = new Producto();
        Usuario usuario = new Usuario();

        if (cursorProducto.moveToFirst()){
            do{

                producto.setImage(cursorProducto.getBlob(0));
            }while (cursorProducto.moveToNext());
        }


        if (cursorUsuario.moveToFirst()){
            do {
                usuario.setName(cursorUsuario.getString(0));
                usuario.setLast_name(cursorUsuario.getString(1));
            }while (cursorUsuario.moveToNext());
        }

        if (producto.getImage() == null){
            Toast.makeText(this, "No hay una imagen disponible", Toast.LENGTH_SHORT).show();
        }else {
            Glide.with(this).load(producto.getImage()).into(imageViewClienteDet);
        }
        txtproductQuantityPedido.setText("Cantidad "+cantidadPedido);
        txtproductPricePedido.setText("Total a Pagar "+totalPagar);
        txtClientNombre.setText("Cliente " +usuario.getName()+ " " + usuario.getLast_name());
        db.close();
    }

    @Override
    public void onClick(View v) {

    }
}
