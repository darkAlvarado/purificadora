package com.tab.code.purificadora.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tab.code.purificadora.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstadoCuenta extends Fragment {


    public EstadoCuenta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estado_cuenta, container, false);
    }

}
