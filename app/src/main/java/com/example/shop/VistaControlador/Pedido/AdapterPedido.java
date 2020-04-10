package com.example.shop.VistaControlador.Pedido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shop.Modelo.Pedido.Pedido;
import com.example.shop.R;

import java.util.List;

public class AdapterPedido extends BaseAdapter {

    private Context context;
    private List<Pedido> pedidoList;

    public AdapterPedido(Context context, List<Pedido> pedidoList) {
        this.context = context;
        this.pedidoList = pedidoList;
    }

    @Override
    public int getCount() {

        return pedidoList.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pedidoList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.item_pedido_list, null);
        TextView txtProductID = (TextView)view.findViewById(R.id.inputProductId);
        TextView txtUsuarioID = (TextView)view.findViewById(R.id.inputUsuarioID);
        TextView txtCantidad = (TextView)view.findViewById(R.id.inputCantidad);
        TextView txtTotalAPagar = (TextView)view.findViewById(R.id.inputTotalAPagar);
        TextView txtEstado = (TextView)view.findViewById(R.id.inputEstado);

        txtProductID.setText(""+pedidoList.get(position).getProducto_id());
        txtUsuarioID.setText(""+pedidoList.get(position).getUsuario_id());
        txtCantidad.setText(""+pedidoList.get(position).getCantidad());
        txtTotalAPagar.setText(""+pedidoList.get(position).getTotal_pagar());
        txtEstado.setText(pedidoList.get(position).getState());


        return view;
    }
}
