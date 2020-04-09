package com.example.shop.VistaControlador.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shop.Modelo.CategoriaProducto.CategoriaProducto;
import com.example.shop.Modelo.Producto.Producto;
import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;
import com.example.shop.VistaControlador.Producto.AdapterProduct;
import com.example.shop.VistaControlador.Producto.ProductCreate;
import com.example.shop.VistaControlador.Producto.ProductDetail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentProducto extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private FloatingActionButton btnProductoCreate;
    private ListView listProduct;
    private Spinner spinnerCategory;

    private ArrayList<Producto> arrayProduct =  new ArrayList<Producto>();
    private ArrayList<CategoriaProducto> arrayCategory;
    private ArrayAdapter<CharSequence> adapterSpinner;
    private  AdapterProduct adapterProduct;

    CategoriaProducto category = new CategoriaProducto();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto, container,false);

        SpinnerCategory();

        btnProductoCreate = (FloatingActionButton) view.findViewById(R.id.btnProductoCreate);
        listProduct = (ListView) view.findViewById(R.id.listProduct);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);


        spinnerCategory.setAdapter(adapterSpinner);

        spinnerCategory.setOnItemSelectedListener(this);
        listProduct.setOnItemClickListener(this);
        btnProductoCreate.setOnClickListener(this);

        return  view;
    }

    private void ProductListAll(){
        //Traemos los datos de la tabla porductos y los presentamos en una lista
        SqliteOpenHelper admin = new SqliteOpenHelper(getActivity(), "shop", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Producto product = null;

        listProduct.setAdapter(null);
        arrayProduct.clear();
        adapterProduct = new AdapterProduct(getActivity(),arrayProduct);

        //Filtramos segun categoria
        Cursor cursor = bd.rawQuery("SELECT * FROM " + "producto", null);

        while (cursor.moveToNext()){

            product = new Producto();
              product.setId(cursor.getInt(0));
              product.setName(cursor.getString(1));
              product.setImage(cursor.getBlob(2));
              product.setDetail(cursor.getString(3));
              product.setPrice(cursor.getDouble(4));
              product.setQuantity(cursor.getInt(5));
              product.setCategory(cursor.getString(6));

              arrayProduct.add(product);
        }
        bd.close();
        listProduct.setAdapter(adapterProduct);
    }

    private void ProductListFilter(){
        //Traemos los datos de la tabla porductos y los presentamos en una lista
        SqliteOpenHelper admin = new SqliteOpenHelper(getActivity(), "shop", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Producto product = null;

        listProduct.setAdapter(null);
        arrayProduct.clear();
        adapterProduct = new AdapterProduct(getActivity(),arrayProduct);

        //Filtramos segun categoria
        Cursor cursor = bd.rawQuery("SELECT * FROM " + "producto" + " WHERE categoria ='" + category.getName() +"'", null);

        while (cursor.moveToNext()){


            product = new Producto();
            product.setId(cursor.getInt(0));
            product.setName(cursor.getString(1));
            product.setImage(cursor.getBlob(2));
            product.setDetail(cursor.getString(3));
            product.setPrice(cursor.getDouble(4));
            product.setQuantity(cursor.getInt(5));
            product.setCategory(cursor.getString(6));

            arrayProduct.add(product);
        }
        bd.close();
        listProduct.setAdapter(adapterProduct);

    }

    private void SpinnerCategory(){
        //ComboBox para filtrar Categorias

        SqliteOpenHelper admin = new SqliteOpenHelper(getActivity(),"shop",null,1);
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
        arrayCategoryInfo.add("Todos");
        for (int i = 0; i < arrayCategory.size() ; i++) {
            arrayCategoryInfo.add(arrayCategory.get(i).getName());
        }

        adapterSpinner = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1 ,arrayCategoryInfo);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        category.setName(parent.getItemAtPosition(position).toString());
        if (category.getName() == "Todos" ){
            ProductListAll();
        }else {
            ProductListFilter();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
