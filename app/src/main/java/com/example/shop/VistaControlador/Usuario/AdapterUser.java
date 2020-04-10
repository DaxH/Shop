package com.example.shop.VistaControlador.Usuario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shop.Modelo.Usuario.Usuario;
import com.example.shop.R;

import java.util.List;

public class AdapterUser extends BaseAdapter {
    private Context context;
    private List<Usuario> usertList;

    public AdapterUser(Context context, List<Usuario> usertList) {

        this.context = context;
        this.usertList = usertList;
    }

    @Override
    public int getCount() {
        return usertList.size();
    }

    @Override
    public Object getItem(int position) {
        return usertList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return usertList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.item_user_list,null);

        TextView txtUseName= (TextView)view.findViewById(R.id.inputUserName);
        TextView txtUserLastName = (TextView)view.findViewById(R.id.inputUserLastName);
        TextView txtUser = (TextView)view.findViewById(R.id.inputUser);

        txtUseName.setText(usertList.get(position).getName());
        txtUserLastName.setText(usertList.get(position).getLast_name());
        txtUser.setText(usertList.get(position).getUser_name());

        return view;
    }
}