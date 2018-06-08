package com.tab.code.purificadora.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tab.code.purificadora.Model.ClientesBD;
import com.tab.code.purificadora.R;
import com.tab.code.purificadora.View.Cliente;
import com.tab.code.purificadora.View.Menu;

import java.util.ArrayList;


public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ViewHolderClientes>
        implements View.OnClickListener{

    ArrayList<ClientesBD> clientes;
    private View.OnClickListener listener;
    private FragmentTransaction transaction;
    public ClientsAdapter(ArrayList<ClientesBD> clientes, FragmentTransaction transaction){
        this.clientes = clientes;
        this.transaction = transaction;
    }


    @Override
    public ViewHolderClientes onCreateViewHolder( ViewGroup parent, int viewType) {
        int layout = R.layout.clientes_adapter;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,null,false);
//        v.setOnClickListener(this);
        return new ViewHolderClientes(v);
    }

    @Override
    public void onBindViewHolder(ClientsAdapter.ViewHolderClientes holder, final int position) {
        holder.txtNombre.setText(clientes.get(position).getNombre());
        holder.txtTelefono.setText(clientes.get(position).getTelefono());
        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                Fragment fm = new Cliente();
                String[] info = new String[5];
                info[0] = clientes.get(position).getNombre();
                info[1] = clientes.get(position).getDomicilio();
                info[2] = clientes.get(position).getTelefono();
                info[3] = clientes.get(position).getPseudo();
                info[4] = clientes.get(position).getIdCliente();

                args.putStringArray("cliente", info);

                fm.setArguments(args);

                transaction.replace(R.id.content,fm).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }

    }

    public class ViewHolderClientes extends RecyclerView.ViewHolder {

        TextView txtNombre;
        TextView txtTelefono;
        ImageView btnView;
        ImageView btnEdit;
        View circle;

        public ViewHolderClientes(View itemView) {
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.txtNombre);
            txtTelefono = (TextView) itemView.findViewById(R.id.txtTelefono);
            btnEdit = (ImageView) itemView.findViewById(R.id.btnEdit);
            btnView = (ImageView) itemView.findViewById(R.id.btnView);
            circle = (View) itemView.findViewById(R.id.view_nombre);
        }
    }
}
