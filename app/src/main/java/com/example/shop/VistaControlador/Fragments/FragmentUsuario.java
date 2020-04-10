package com.example.shop.VistaControlador.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shop.Modelo.Usuario.Usuario;
import com.example.shop.R;
import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;
import com.example.shop.VistaControlador.Usuario.AdapterUser;

import java.util.ArrayList;

public class FragmentUsuario extends Fragment {

    private ListView listViewUser;
    private ArrayList<Usuario> arrayUser;
    private ArrayList<String> arrayUserInfo;
    private AdapterUser adapterUser;

    Usuario user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario, container,false);

        listViewUser = (ListView)view.findViewById(R.id.listViewUser);

        DataUser();
        adapterUser = new AdapterUser(getActivity(),arrayUser);
        listViewUser.setAdapter(adapterUser);
        return  view;
    }

    public void DataUser() {
        SqliteOpenHelper admin = new SqliteOpenHelper(getActivity(), "shop", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        arrayUser = new ArrayList<>();
        Cursor cursor = bd.rawQuery("SELECT * FROM " + "usuario", null);

        while (cursor.moveToNext()) {
            user = new Usuario();

            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setLast_name(cursor.getString(2));
            user.setUser_name(cursor.getString(3));
            user.setPassword(cursor.getString(4));

            arrayUser.add(user);
        }

        bd.close();
    }
}
