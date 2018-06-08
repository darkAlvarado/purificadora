package com.tab.code.purificadora;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.tab.code.purificadora.View.Clientes;
import com.tab.code.purificadora.View.Escanear;
import com.tab.code.purificadora.View.Inventario;
import com.tab.code.purificadora.View.Menu;
import com.tab.code.purificadora.View.NuevoCliente;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView buscar;
    private CardView addCliente;
    private CardView clientes;
    private CardView inventario;
    private CardView confi;
    private Fragment fragment;
    FragmentManager fm;

    private Fragment selecFragment = null;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        buscar = (CardView) findViewById(R.id.buscar_cliente);
//        addCliente = (CardView) findViewById(R.id.add_cliente);
//        clientes = (CardView) findViewById(R.id.clientes);
//        inventario = (CardView) findViewById(R.id.inventario);
//        confi = (CardView) findViewById(R.id.config);

        fm = getSupportFragmentManager();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new Menu()).addToBackStack(null).commit();

//        buscar.setOnClickListener(this);
//        addCliente.setOnClickListener(this);
//        clientes.setOnClickListener(this);
//        inventario.setOnClickListener(this);
//        confi.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        transaction = getSupportFragmentManager().beginTransaction();
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

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
