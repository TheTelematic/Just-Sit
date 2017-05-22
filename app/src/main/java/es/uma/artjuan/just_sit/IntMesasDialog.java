package es.uma.artjuan.just_sit;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import es.uma.artjuan.just_sit.Bar;
import es.uma.artjuan.just_sit.R;

/**
 * Created by Juanca on 18/05/2017.
 */

public class IntMesasDialog extends DialogFragment {

    private String intmesas="";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createIntMesas();
    }

    public AlertDialog createIntMesas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_intmesas, null);

        builder.setView(v);


        Button signin = (Button) v.findViewById(R.id.entrar_boton);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit=(EditText)v.findViewById(R.id.mesas_input);
                intmesas=Bar.getInstance().setNmesas(Integer.parseInt(edit.getText().toString()));
                if(!intmesas.equals(Mensajes.Comandos.MESAS_OK)){

                    Toast.makeText(getActivity(), "Error al introducir mesas.", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });
        return builder.create();
    }



}
