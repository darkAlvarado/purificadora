package com.tab.code.purificadora.View;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.tab.code.purificadora.Controller.BDController;
import com.tab.code.purificadora.Model.ClientesBD;
import com.tab.code.purificadora.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoCliente extends Fragment implements View.OnClickListener {

    public final static int QRcodeWidth = 500 ;
    private static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";
    Bitmap bitmap ;

    private Button btnGuardar;
    private Button btnCancelar;
    private EditText nombre;
    private EditText domicilio;
    private EditText telefono;

    private Dialog mDialog;

    private String valNombre, valDomicilio, valTelefono;

    FirebaseDatabase database;
    private DatabaseReference mDatabase;

    public NuevoCliente() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nuevo_cliente, container, false);

        database = FirebaseDatabase.getInstance();

        btnGuardar = (Button) root.findViewById(R.id.btn_guardar);
        btnCancelar = (Button) root.findViewById(R.id.btn_cancelar);

        nombre = (EditText) root.findViewById(R.id.nombre_cliente);
        domicilio = (EditText) root.findViewById(R.id.domicilio_cliente);
        telefono = (EditText) root.findViewById(R.id.telefono_cliente);

        btnCancelar.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);

        mDialog = new Dialog(getActivity());

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_guardar: guardar(); break;
            case R.id.btn_cancelar: cancelar(); break;
        }
    }


    private void guardar() {
        valNombre = nombre.getText().toString();
        valDomicilio = domicilio.getText().toString();
        valTelefono = telefono.getText().toString();

        if(!validar(valNombre)){
            Toast.makeText(getContext(), "Ingrese el nombre del cliente", Toast.LENGTH_SHORT).show();
        }else if (!validar(valDomicilio)){
            Toast.makeText(getContext(), "Ingrese el domicilio", Toast.LENGTH_SHORT).show();
        }else if (!validar(valTelefono)){
            Toast.makeText(getContext(), "Ingrese telefono", Toast.LENGTH_SHORT).show();
        }else {
            String pseudo = valNombre.substring(0,4) + valTelefono.substring(0,3);
            ClientesBD clientesBD = new ClientesBD(valDomicilio, valNombre, valTelefono, pseudo);
            String id = new BDController().SaveCliente(clientesBD);
            try {
                bitmap = TextToImageEncode(id);
                String path = saveImage(bitmap);
                nombre.setText("");
                domicilio.setText("");
                telefono.setText("");
                ShowPopup(getView());
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validar(String dato) {
        if (dato.trim().length() == 0) return false;
        return true;
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.blueLight):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity().getApplicationContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }

    private void cancelar() {

    }

    private void ShowPopup(View v){
        TextView nombre;
        TextView domicilio;
        TextView telefono;
        Button btnContinuar;
        ImageView img;

        mDialog.setContentView(R.layout.fragment_saved);
        btnContinuar = (Button) mDialog.findViewById(R.id.btn_continuar);
        nombre = (TextView) mDialog.findViewById(R.id.nombre);
        domicilio = (TextView) mDialog.findViewById(R.id.domicilio);
        telefono = (TextView) mDialog.findViewById(R.id.telefono);
        img = (ImageView) mDialog.findViewById(R.id.qr_image);

        nombre.setText(valNombre);
        domicilio.setText(valDomicilio);
        telefono.setText(valTelefono);
        img.setImageBitmap(bitmap);
        mDialog.getWindow().setBackgroundDrawableResource(R.color.colorDark);
        mDialog.show();
    }

}
