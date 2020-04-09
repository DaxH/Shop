package com.example.shop.VistaControlador.Producto;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shop.Modelo.Producto.Producto;
import com.example.shop.R;

import java.util.List;

public class Adapter extends BaseAdapter {
    private Context context;
    private List<Producto> productList;

    public Adapter(Context context, List<Producto> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.item_list,null);

        ImageView imgProductImage = (ImageView)view.findViewById(R.id.imgProductImage);
        TextView txtProductName = (TextView)view.findViewById(R.id.txtProdcutName);
        TextView txtProductPrice = (TextView)view.findViewById(R.id.txtProductPrice);

        Glide.with(context).load(productList.get(position).getImage()).into(imgProductImage);
        txtProductName.setText(productList.get(position).getName());
        txtProductPrice.setText("$ " + (Double) productList.get(position).getPrice());

        return view;
    }
}
