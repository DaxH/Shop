package com.example.shop.VistaControlador.Fragments;

  import android.content.Intent;
  import android.database.Cursor;
  import android.database.sqlite.SQLiteDatabase;
  import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
  import android.widget.ArrayAdapter;
  import android.widget.ListView;

  import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

  import com.example.shop.Modelo.CategoriaProducto.CategoriaProducto;
  import com.example.shop.R;
  import com.example.shop.VistaControlador.BaseDatos.SqliteOpenHelper;
  import com.example.shop.VistaControlador.CategoryProduct.CategoryCreate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

  import java.util.ArrayList;

public class FragmentCategriaProducto extends Fragment implements View.OnClickListener {

    private FloatingActionButton btnCategoryCreate;
    private ListView listViewCategory;
    private ArrayList<CategoriaProducto> arrayCategory;
    private ArrayList<String> arrayCategoryInfo;
    CategoriaProducto category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoria_producto, container,false);

        btnCategoryCreate = (FloatingActionButton) view.findViewById(R.id.btnCategoryCreate);
        listViewCategory = (ListView)view.findViewById(R.id.listViewCategory);

        btnCategoryCreate.setOnClickListener(this);
        DataCategory();

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayCategoryInfo);
        listViewCategory.setAdapter(adapter);
        return  view;
    }

    private void DataCategory(){
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
        GetList();

    }

    private void GetList() {

        arrayCategoryInfo = new ArrayList<String>();
        for (int i = 0; i < arrayCategory.size() ; i++) {
            arrayCategoryInfo.add(arrayCategory.get(i).getName());
        }
    }

    @Override
    public void onClick(View view) {
        //Cambia a la actividad CategoryCreate para crear una categria

        Intent intent = new Intent(getActivity(), CategoryCreate.class);
        startActivity(intent);
    }
}
