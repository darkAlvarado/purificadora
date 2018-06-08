package com.tab.code.purificadora.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tab.code.purificadora.Controller.ClientsAdapter;
import com.tab.code.purificadora.Model.ClientesBD;
import com.tab.code.purificadora.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Clientes extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<ClientesBD> sessions;
    RecyclerView recyclerClientes;
    DatabaseReference myRef;

    public Clientes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_clientes, container, false);

        sessions = new ArrayList<>();
        recyclerClientes = (RecyclerView) root.findViewById(R.id.recycler_sessions);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this.getContext()));
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        final ClientsAdapter adapter = new ClientsAdapter(sessions, transaction);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String l = sessions.get(recyclerClientes.getChildAdapterPosition(view))
//                        .getTelefono();
                ImageButton v = (ImageButton) view;

                switch (v.getId()){
                    case R.id.btnView:
                        System.out.println("Ouch !");
                        Bundle args = new Bundle();
                        String[] info = new String[5];
                        info[0] = sessions.get(recyclerClientes.getChildLayoutPosition(view)).getNombre();
                        info[1] = sessions.get(recyclerClientes.getChildAdapterPosition(view)).getDomicilio();
                        info[2] = sessions.get(recyclerClientes.getChildAdapterPosition(view)).getTelefono();
                        info[3] = sessions.get(recyclerClientes.getChildAdapterPosition(view)).getPseudo();
                        info[4] = sessions.get(recyclerClientes.getChildAdapterPosition(view)).getIdCliente();
                        args.putStringArray("cliente", info);

                        System.out.println(info);

                        break;
                    case R.id.btnEdit:
                        System.out.println("Vous avez juste la moyenne.");
                        break;
                }
            }
        });


        myRef = database.getReference("Clientes");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                sessions.add(new ClientesBD(
                        dataSnapshot.child("domicilio").getValue(String.class),
                        dataSnapshot.child("nombre").getValue(String.class),
                        dataSnapshot.child("telefono").getValue(String.class),
                        dataSnapshot.child("pseudo").getValue(String.class),
                        dataSnapshot.getKey()));
                recyclerClientes.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (ClientesBD se: sessions){
//                    se.
//                    se.nombre = dataSnapshot.child("name").getValue(String.class);
//                    se.active = dataSnapshot.chi/ld("active").getValue(Boolean.class);
//                    se.id = dataSnapshot.child("id").getValue(Integer.class);
//                    se.timeS = dataSnapshot.child("time").getValue(String.class);
                    recyclerClientes.setAdapter(adapter);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (ClientesBD se: sessions){
                    sessions.remove(se);
                    recyclerClientes.setAdapter(adapter);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return root;
    }

}
