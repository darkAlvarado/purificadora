package com.tab.code.purificadora.Controller;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.tab.code.purificadora.Model.ClientesBD;
import com.tab.code.purificadora.Model.CreditoBD;
import com.tab.code.purificadora.Model.Historico;
import com.tab.code.purificadora.Model.Pagos;
import com.tab.code.purificadora.Model.UsuariosBD;
import com.tab.code.purificadora.Model.VentasBD;

import java.util.Date;

public class BDController {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private boolean transacion;
    public BDController() {
        init();
    }

    private void init(){
        myRef = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
    }

    public String SaveCliente(ClientesBD cliente){
        DatabaseReference myRef = database.getReference("Clientes");

        String id = myRef.push().getKey();

        myRef.child(myRef.push().getKey())
                .setValue(cliente);
        return id;
    }

    public void SaveUsuario(String id,
                             String domicilio, boolean admin,
                             String password, Long telefono, String username){
        UsuariosBD user = new
                UsuariosBD(
                    id,
                    domicilio,
                    admin,
                    password,
                    telefono,
                    username);

        myRef.child("Usuarios").child(id).setValue(user);
    }

    public void SaveCredito(CreditoBD credito){

        myRef.child("Credito").child("dd").setValue(credito);
    }

    public void  SaveVenta(final VentasBD venta, final String idU) {

        DatabaseReference myRef = database.getReference("Ventas");
        String id = myRef.push().getKey();

        if(venta.isTipo_pago()){
            myRef.child(idU).child(id).setValue(venta);
        }else{
            myRef.child(idU).child(id).setValue(venta);

            myRef = database.getReference("Credito/"+idU);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null){
                        /**
                         * Creamos un nuevo registro a la base de datos
                         * Esta será un principio para futuros cambios
                         */
                        DatabaseReference ref = database.getReference("Credito");
                        CreditoBD c = new CreditoBD();
                        Pagos p  = new Pagos();
                        Historico h = new Historico();
                        h.setpDefault(p);

                        p.setAdeudo(venta.getTotal());
                        p.setFecha(venta.getFecha());
                        p.setPagoRealizado(venta.getPagoRealizado());

                        c.setFechaPago(venta.getFecha());
                        c.setTotalAdeudo(venta.getTotal() - venta.getPagoRealizado());
                        c.setUltimoPago(venta.getPagoRealizado());

                        c.setHistorico(h);
                        ref.child(idU).setValue(c);
                    }else{
                        /**
                         * Recogemos los datos ya guardados para ser actualizados a los nuevos
                         * valores que se traigan en cuenta
                         */
                        GenericTypeIndicator<CreditoBD> ct = new GenericTypeIndicator<CreditoBD>() {};
                        CreditoBD ct2 = dataSnapshot.getValue(ct);
                        double total = ct2.getTotalAdeudo() - venta.getPagoRealizado();
                        double ultimo = venta.getPagoRealizado();
                        DatabaseReference my = database.getReference("Credito/"+idU);

                        my.child("fechaPago").setValue(venta.getFecha());
                        my.child("totalAdeudo").setValue(total);
                        my.child("ultimoPago").setValue(ultimo);
                        String id = my.push().getKey();
                        Pagos p  = new Pagos();
                        p.setFecha(venta.getFecha());
                        p.setAdeudo(total);
                        p.setPagoRealizado(ultimo);
                        my.child("historico").child(id).setValue(p);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public boolean isTransacion() {
        return transacion;
    }

    public void setTransacion(boolean transacion) {
        this.transacion = transacion;
    }
}
