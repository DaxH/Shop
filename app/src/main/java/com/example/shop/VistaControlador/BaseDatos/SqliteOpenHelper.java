package com.example.shop.VistaControlador.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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
        Shop.execSQL("create table producto(id INTEGER PRIMARY KEY, name text NOT NULL, image blob, detail text NOT NULL, " +
                "price real NOT NULL, quantity int NOT NULL , categoria text, FOREIGN KEY(categoria) REFERENCES categoria_producto(name))");


//        TABLA USUARIO
        Shop.execSQL("create table usuario(id INTEGER PRIMARY KEY AUTOINCREMENT, name text NOT NULL, last_name text NOT NULL, user_name text NOT NULL, password text NOT NULL" +
                ", rol text DEFAULT('Cliente') )");

//        TABLA PEDIDO
        Shop.execSQL("create table pedido(id INTEGER PRIMARY KEY AUTOINCREMENT, longitude text NOT NULL, latitude NOT NULL, state text DEFAULT('Pendiente'), " +
                "producto_id int, usuario_id int, FOREIGN KEY(producto_id) REFERENCES producto(id), FOREIGN KEY(usuario_id) REFERENCES usuario(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


//       METODO ABRIR BD
    public void abrirBD(){
        this.getReadableDatabase();
    }


//       METODO CERRAR BD
    public void cerrarBD(){
        this.close();
    }


//       METODO INSERTAR REGISTROS EN LA TABLA USUARIO
    public void insertarUsuario(String name,String last_name, String user_name, String password){

        ContentValues valoresUsuarios = new ContentValues();
        valoresUsuarios.put("Nombre", name);
        valoresUsuarios.put("Apellido", last_name);
        valoresUsuarios.put("Username", user_name);
        valoresUsuarios.put("Password", password);

        this.getWritableDatabase().insert("usuario",null,valoresUsuarios);
     }

//      VALIDACION DE UN USUARIO EXISTENTE
        public Cursor consultarUsuarioPassword(String usuario, String password) throws SQLException{

            Cursor cursor=null;
            cursor=this.getReadableDatabase().query("usuario",
                    new  String[]{"name","last_name","user_name","password"}, "Nombre like '"+usuario+"' and Password like '"+password+"'",
                    null ,null,null,null);
            return cursor;
        }
                }
