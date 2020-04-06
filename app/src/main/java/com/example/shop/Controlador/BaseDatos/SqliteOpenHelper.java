package com.example.shop.Controlador.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteOpenHelper extends SQLiteOpenHelper {


    public SqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase Shop) {
//        TABLA CATEGORIA PRODUCTO
        Shop.execSQL("create table categoria_producto(name text PRIMARY KEY)");

//        TABLA PRODUCTO
        Shop.execSQL("create table producto(id int PRIMARY KEY AUTOINCREMENT, name text NOT NULL, image blob NOT NULL, detail text NOT NULL, " +
                    "price real NOT NULL, quantity int NOT NULL , FOREIGN KEY(categoria) REFERENCES categoria_producto(name))");

//        TABLA USUARIO
        Shop.execSQL("create table usuario(id int PRIMARY KEY AUTOINCREMENT, name text NOT NULL, last_name text NOT NULL, user_name text NOT NULL" +
                ", rol text NOT NULL DEFAULT (Cliente))");

//        TABLA PEDIDO
        Shop.execSQL("create table producto(id int PRIMARY KEY AUTOINCREMENT, longitude text NOT NULL, latitude NOT NULL, state text DEFAULT(Pendiente), " +
                "FOREIGN KEY(producto_id) REFERENCES producto(id), FOREIGN KEY(usuario_id) REFERENCES usuario(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
