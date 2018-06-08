package com.tab.code.purificadora.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tab.code.purificadora.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Menu extends Fragment implements View.OnClickListener {

    private CardView buscar;
    private CardView addCliente;
    private CardView clientes;
    private CardView inventario;
    private CardView confi;
    private Fragment fragment;
    private FragmentTransaction transaction;


    public Menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        buscar = (CardView) root.findViewById(R.id.buscar_cliente);
        addCliente = (CardView) root.findViewById(R.id.add_cliente);
        clientes = (CardView) root.findViewById(R.id.clientes);
        inventario = (CardView) root.findViewById(R.id.inventario);
        confi = (CardView) root.findViewById(R.id.config);

        transaction = getFragmentManager().beginTransaction();


        buscar.setOnClickListener(this);
        addCliente.setOnClickListener(this);
        clientes.setOnClickListener(this);
        inventario.setOnClickListener(this);
        confi.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        transaction = getFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.buscar_cliente:
                fragment = new Escanear();
                break;
            case R.id.add_cliente:
                fragment = new NuevoCliente();
                break;
            case R.id.clientes:
                fragment = new Clientes();
                break;
            case R.id.inventario:
                fragment = new Inventario();
                break;
            case R.id.config:
                break;
        }
        transaction.replace(R.id.content, fragment).addToBackStack(null).commit();
    }
}
