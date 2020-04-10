package com.example.shop.VistaControlador.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shop.Modelo.Pedido.Pedido;
import com.example.shop.Modelo.Producto.Producto;
import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;
import com.example.shop.VistaControlador.Pedido.AdapterPedido;

import java.util.ArrayList;

public class FragmentPedido extends Fragment {

    private ListView listViewPedido;
    private ArrayList<Pedido> arrayPedido;
    private AdapterPedido adapterPedido;

    Pedido pedido;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedido, container,false);

        dataPedido();

        listViewPedido =(ListView)view.findViewById(R.id.listPedido);
        adapterPedido=new AdapterPedido(getActivity(),arrayPedido);
        listViewPedido.setAdapter(adapterPedido);

        return  view;
    }

    public void dataPedido(){

        SqliteOpenHelper admin = new SqliteOpenHelper(getActivity(), "shop", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        arrayPedido = new ArrayList<>();
        Cursor cursor = bd.rawQuery("SELECT * FROM "+"pedido",null);
        while (cursor.moveToNext()){
            pedido = new Pedido();

            pedido.setId(cursor.getInt(0));
            pedido.setLongitude(cursor.getString(1));
            pedido.setLatitude(cursor.getString(2));
            pedido.setState(cursor.getString(3));
            pedido.setCantidad(cursor.getInt(4));
            pedido.setTotal_pagar(cursor.getInt(5));
            pedido.setProducto_id(cursor.getInt(6));
            pedido.setUsuario_id(cursor.getInt(7));

            arrayPedido.add(pedido);
            bd.close();

        }
    }
}
