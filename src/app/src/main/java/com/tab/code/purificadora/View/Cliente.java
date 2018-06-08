package com.tab.code.purificadora.View;


import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.tab.code.purificadora.Controller.BDController;
import com.tab.code.purificadora.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cliente extends Fragment {

    private TextView telefono;
    private TextView nombre;
    private TextView domicilio;
    private TextView pseudo;
    private ImageView code;
    private Button btnVender;
    public final static int QRcodeWidth = 100 ;
    Bitmap bitmap ;

    private Fragment fragment;
    private FragmentTransaction transaction;

    public Cliente() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cliente, container, false);
        telefono = (TextView) root.findViewById(R.id.telefono_cliente);
        nombre = (TextView) root.findViewById(R.id.nombre_cliente);
        domicilio = (TextView) root.findViewById(R.id.domicilio_cliente);
        pseudo = (TextView) root.findViewById(R.id.pseudo);
        code = (ImageView) root.findViewById(R.id.code_cliente);
        btnVender = (Button) root.findViewById(R.id.btn_vender);

        final String[] info = getArguments().getStringArray("cliente");

        nombre.setText(info[0]);
        domicilio.setText(info[1]);
        telefono.setText(info[2]);
        pseudo.setText(info[3]);
        try {
            bitmap = TextToImageEncode(info[4]);
            code.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        transaction = getFragmentManager().beginTransaction();

        btnVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("idCliente" , info[4]);
                args.putString("nombreCliente", info[0]);
                fragment = new Vender();
                fragment.setArguments(args);
                transaction.replace(R.id.content, fragment).addToBackStack(null).commit();
            }
        });
        return root;
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
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 100, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}
