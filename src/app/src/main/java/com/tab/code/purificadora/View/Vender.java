package com.tab.code.purificadora.View;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tab.code.purificadora.Controller.BDController;
import com.tab.code.purificadora.Model.VentasBD;
import com.tab.code.purificadora.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class Vender extends Fragment implements View.OnClickListener {

    private TextView[] cte = new TextView[15];
    private int[] colors = new  int[15];
    private Button btnVender;

    private TextView nombreCliente;
    private TextView precio;
    private TextView cantidad;
    private TextView total;
    private EditText edPrecio;
    private EditText edCantidad;
    private TextView subtotal;
    private EditText pagoRealizado;

    private RadioButton tipoContado;
    private RadioButton tipoCredito;

    private View.OnClickListener preferencias;

    private RadioButton rNormal;
    private RadioButton r1;
    private RadioButton r2;
    private RadioButton r3;
    private RadioButton r4;
    private RadioButton r5;
    private RadioButton[] pref = new RadioButton[6];

    private boolean tipoPago;
    private String prefPago;
    private Dialog mDialog;

    private String idCliente;
    public Vender() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_vender, container, false);

        cte[0] = root.findViewById(R.id.tx1);
        cte[1] = root.findViewById(R.id.tx2);
        cte[2] = root.findViewById(R.id.tx3);
        cte[3] = root.findViewById(R.id.tx4);
        cte[4] = root.findViewById(R.id.tx5);
        cte[5] = root.findViewById(R.id.tx8);
        cte[6] = root.findViewById(R.id.tx10);
        cte[7] = root.findViewById(R.id.tx12);
        cte[8] = root.findViewById(R.id.tx15);
        cte[9] = root.findViewById(R.id.tx16);
        cte[10] = root.findViewById(R.id.tx18);
        cte[11] = root.findViewById(R.id.tx20);
        cte[12] = root.findViewById(R.id.tx22);
        cte[13] = root.findViewById(R.id.tx24);
        cte[14] = root.findViewById(R.id.tx25);

        cte[0].setOnClickListener(this);
        cte[1].setOnClickListener(this);
        cte[2].setOnClickListener(this);
        cte[3].setOnClickListener(this);
        cte[4].setOnClickListener(this);
        cte[5].setOnClickListener(this);
        cte[6].setOnClickListener(this);
        cte[7].setOnClickListener(this);
        cte[8].setOnClickListener(this);
        cte[9].setOnClickListener(this);
        cte[10].setOnClickListener(this);
        cte[11].setOnClickListener(this);
        cte[12].setOnClickListener(this);
        cte[13].setOnClickListener(this);
        cte[14].setOnClickListener(this);

        btnVender = (Button) root.findViewById(R.id.btn_vender);
        precio = (TextView) root.findViewById(R.id.precio);
        edPrecio = (EditText) root.findViewById(R.id.txt_precio);
        cantidad = (TextView) root.findViewById(R.id.cantidad);
        edCantidad = (EditText) root.findViewById(R.id.txt_cantidad);
        total = (TextView) root.findViewById(R.id.total);

        subtotal = (TextView) root.findViewById(R.id.subtotal);
        pagoRealizado = (EditText) root.findViewById(R.id.pago);

        tipoContado = (RadioButton) root.findViewById(R.id.tipoContado);
        tipoCredito = (RadioButton) root.findViewById(R.id.tipoCredito);

        tipoCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipoContado.isChecked()) tipoContado.setChecked(false);

                if (tipoCredito.isChecked()){
                    tipoCredito.setChecked(true);
                    setTipoPago(false);
                }
            }
        });
        tipoContado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipoCredito.isChecked()) tipoCredito.setChecked(false);

                if (tipoContado.isChecked()){
                    tipoContado.setChecked(true);
                    setTipoPago(true);
                }
            }
        });


        edCantidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus){
                    int i = 0;
                    for (TextView txt : cte){
                        txt.setBackgroundColor(colors[i]);
                        i++;
                    }
                    cantidad.setText(((TextView) v).getText());
                    precio.setText(edPrecio.getText());
                    operacion();
                }
            }
        });

        r1 = (RadioButton) root.findViewById(R.id.r1);
        r2 = (RadioButton) root.findViewById(R.id.r2);
        r3 = (RadioButton) root.findViewById(R.id.r3);
        r4 = (RadioButton) root.findViewById(R.id.r4);
        r5 = (RadioButton) root.findViewById(R.id.r5);
        rNormal = (RadioButton) root.findViewById(R.id.rNormal);

        r1.setOnClickListener(new Preferencias());
        r2.setOnClickListener(new Preferencias());
        r3.setOnClickListener(new Preferencias());
        r4.setOnClickListener(new Preferencias());
        r5.setOnClickListener(new Preferencias());
        rNormal.setOnClickListener(new Preferencias());

        r1.setTag("Pago rago 1");
        r2.setTag("Pago rago 2");
        r3.setTag("Pago rago 3");
        r4.setTag("Pago rago 4");
        r5.setTag("Pago rago 5");
        rNormal.setTag("Pago normal");

        pref[0] = rNormal;
        pref[1] = r1;
        pref[2] = r2;
        pref[3] = r3;
        pref[4] = r4;
        pref[5] = r5;

        llenar();

        btnVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VentasBD venta = new VentasBD();


                venta.setFecha(fecha());
                venta.setCantidad(Integer.parseInt(cantidad.getText().toString()));
                venta.setPrecio(Double.parseDouble(precio.getText().toString()));
                venta.setTotal(Double.parseDouble(total.getText().toString()));
                venta.setTipo_pago(isTipoPago());
                venta.setPrefPago(getPrefPago());
                venta.setPagoRealizado(Double.parseDouble(pagoRealizado.getText().toString()));


                new BDController().SaveVenta(venta, idCliente);
                ShowPopup(getView());

            }
        });
        nombreCliente = (TextView) root.findViewById(R.id.nombre_cliente);

        Intent i = new Intent();

        idCliente = getArguments().getString("idCliente");
        nombreCliente.setText(getArguments().getString("nombreCliente"));
        return root;
    }

    private String fecha(){
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date d = new Date();
        return hourdateFormat.format(d);
    }

    @Override
    public void onClick(View v) {
        TextView choix = (TextView) v;
        cantidad.setText(((TextView) v).getText());
        precio.setText(edPrecio.getText());
        int i = 0;
        for (TextView txt : cte){
            if (txt.getId()!= choix.getId()){
                txt.setBackgroundColor(getResources().getColor(R.color.gris));
            }else{
                choix.setBackgroundColor(colors[i]);
            }
            i++;
        }
        edCantidad.setText("");
        operacion();
    }

    private void operacion(){
        double p = Double.parseDouble(precio.getText().toString());
        double c = Double.parseDouble(cantidad.getText().toString());
        double pt = c * p ;

        total.setText(String.valueOf(pt));
        subtotal.setText(String.valueOf(pt));
    }

    public class Preferencias implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            RadioButton v = (RadioButton) view;
            for (RadioButton r : pref){
                if (v.getId() == r.getId()){
                    r.setChecked(true);
                    setPrefPago(r.getTag().toString());
                }else{
                    r.setChecked(false);
                }
            }
        }
    }

    private void llenar(){
        colors[0] = getResources().getColor(R.color.tx1);
        colors[1] = getResources().getColor(R.color.tx2);
        colors[2] = getResources().getColor(R.color.tx3);
        colors[3] = getResources().getColor(R.color.tx4);
        colors[4] = getResources().getColor(R.color.tx5);
        colors[5] = getResources().getColor(R.color.tx8);
        colors[6] = getResources().getColor(R.color.tx10);
        colors[7] = getResources().getColor(R.color.tx12);
        colors[8] = getResources().getColor(R.color.tx15);
        colors[9] = getResources().getColor(R.color.tx16);
        colors[10] = getResources().getColor(R.color.tx18);
        colors[11] = getResources().getColor(R.color.tx20);
        colors[12] = getResources().getColor(R.color.tx22);
        colors[13] = getResources().getColor(R.color.tx24);
        colors[14] = getResources().getColor(R.color.tx25);
    }

    public boolean isTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(boolean tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getPrefPago() {
        return prefPago;
    }

    public void setPrefPago(String prefPago) {
        this.prefPago = prefPago;
    }

    private void ShowPopup(View v){
        TextView fecha;
        TextView nombreC;
        TextView mPrecio;
        TextView mCantidad;
        TextView mSubtotal;
        TextView mTotal;
        TextView mDescuento;
        TextView mInfo;
        TextView mImporte;
        TextView mCambio;
        TextView mAdeudo;

        ImageView img;
        mDialog = new Dialog(getActivity(), android.R.style.Theme_Holo_NoActionBar_Fullscreen);
        mDialog.setContentView(R.layout.recibo_pago);

        mDialog.getWindow().setBackgroundDrawableResource(R.color.colorDark);

        fecha = (TextView) mDialog.findViewById(R.id.fecha);
        nombreC = (TextView) mDialog.findViewById(R.id.nombre_cliente);
        mPrecio = (TextView) mDialog.findViewById(R.id.precio);
        mCantidad = (TextView) mDialog.findViewById(R.id.cantidad);
        mSubtotal = (TextView) mDialog.findViewById(R.id.subtotal);
        mTotal = (TextView) mDialog.findViewById(R.id.total);
        mDescuento = (TextView) mDialog.findViewById(R.id.descuento);
        mInfo = (TextView) mDialog.findViewById(R.id.info_descuento);
        mImporte = (TextView) mDialog.findViewById(R.id.importe);
        mCambio = (TextView) mDialog.findViewById(R.id.cambio);
        mAdeudo = (TextView) mDialog.findViewById(R.id.adeudo);

        fecha.setText(fecha());
        nombreC.setText(nombreCliente.getText());
        mPrecio.setText(precio.getText());
        mCantidad.setText(cantidad.getText());
        mSubtotal.setText(subtotal.getText());
        mTotal.setText(total.getText());
        mDescuento.setText("Descuento Aqu");
        mInfo.setText("Descripcion del descuento");
        mImporte.setText(pagoRealizado.getText());

        double t = Double.parseDouble(mTotal.getText().toString()) - Double.parseDouble(mImporte.getText().toString());

        mCambio.setText(String.valueOf(t));
        mAdeudo.setText("55");
        mDialog.show();
    }
}
