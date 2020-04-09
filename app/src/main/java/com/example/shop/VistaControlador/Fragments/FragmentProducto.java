package com.example.shop.VistaControlador.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shop.Modelo.Producto.Producto;
import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;
import com.example.shop.VistaControlador.Producto.Adapter;
import com.example.shop.VistaControlador.Producto.ProductCreate;
import com.example.shop.VistaControlador.Producto.ProductDetail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentProducto extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private FloatingActionButton btnProductoCreate;
    private ListView listProduct;
    private ArrayList<Producto> arrayProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto, container,false);

        btnProductoCreate = (FloatingActionButton) view.findViewById(R.id.btnProductoCreate);
        listProduct = (ListView) view.findViewById(R.id.listProduct);
        listProduct.setOnItemClickListener(this);

        ProductList();
        Adapter adapter = new Adapter(getActivity(),arrayProduct);

        listProduct.setAdapter(adapter);

        btnProductoCreate.setOnClickListener(this);
        return  view;
    }

    private void ProductList(){
        //Traemos los datos de la tabla porductos y los presentamos en una lista
        SqliteOpenHelper admin = new SqliteOpenHelper(getActivity(), "shop", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Producto product = null;
        arrayProduct = new ArrayList<Producto>();

        Cursor cursor = bd.rawQuery("SELECT * FROM " + "producto", null);

        while (cursor.moveToNext()){
            product = new Producto();
            product.setId(cursor.getInt(0));
            product.setName(cursor.getString(1));
            product.setImage(cursor.getBlob(2));
            product.setDetail(cursor.getString(3));
            product.setPrice(cursor.getDouble(4));
            product.setQuantity(cursor.getInt(5));

            arrayProduct.add(product);
        }
        bd.close();
    }

    @Override
    public void onClick(View view) {
        //Cambiar a la ctividad de ProductoCreate
        Intent intent = new Intent(getActivity(), ProductCreate.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Acciones para los items de la lista, Cambia a la actividad ProductoDetail

        Producto product = arrayProduct.get(position);

        Intent intent = new Intent(getActivity(), ProductDetail.class);

        intent.putExtra("productId", product.getId());
        intent.putExtra("productName", product.getName());
        intent.putExtra("productQuantity", product.getQuantity());
        intent.putExtra("productPrice", product.getPrice());
        intent.putExtra("productImage", product.getImage());
        intent.putExtra("productDetail", product.getDetail());

        startActivity(intent);


    }
}
