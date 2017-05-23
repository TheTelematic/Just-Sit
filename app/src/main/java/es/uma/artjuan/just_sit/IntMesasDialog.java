package es.uma.artjuan.just_sit;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
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
    private EditText edit;
    private ServerInfo server;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createIntMesas();
    }

    public AlertDialog createIntMesas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_intmesas, null);
        edit =(EditText)v.findViewById(R.id.mesas_input);
        builder.setView(v);

        server = ServerInfo.getInstance();
        Button signin = (Button) v.findViewById(R.id.entrar_boton);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = edit.getText().toString();
                MyATaskIntMesas myATaskmenu = new MyATaskIntMesas();
                myATaskmenu.execute(tmp);

                dismiss();
            }
        });
        return builder.create();
    }
    private class MyATaskIntMesas extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog;
        String estadoPed="";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            /*progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("Conectando al servidor");
            progressDialog.setMessage("Espera por favor...");
            progressDialog.show();*/

        }

        @Override
        protected String doInBackground(String... values){

            try {
                Socket socket = new Socket(server.getAddress(), server.getPort());
                Mensajes m = new Mensajes();


                intmesas=m.int_mesas(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),Bar.getInstance().getId(),
                            Integer.parseInt(values[0]),
                            new BufferedReader(new InputStreamReader(socket.getInputStream())));
                System.out.println("EASFASEFSAEFASF_________________________SAFDFASDFASDFASDFASDF   " + values[0]);

                    Bar.getInstance().setNmesas(Integer.parseInt(values[0]));

                    socket.close();
                    return intmesas;

            }catch (UnknownHostException ex) {
                Log.e("E/TCP Client", "" + ex.getMessage());
                return ex.getMessage();
            } catch (IOException ex) {
                Log.e("E/TCP Client", "" + ex.getMessage());
                return ex.getMessage();
            }catch (NullPointerException e){
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String value){
            //progressDialog.dismiss();//oculta ventana emergente




        }
    }


}
