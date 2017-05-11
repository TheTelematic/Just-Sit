package es.uma.artjuan.just_sit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class EscaneoActivity extends ActionBarActivity implements View.OnClickListener {
    private Button scanBtn;
    private TextView formatTxt, contentTxt;
    private Context context = this;
    private ServerInfo server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaneo);

        server=ServerInfo.getInstance();
        //Se Instancia el botón de Scan
        scanBtn = (Button)findViewById(R.id.scan_button);
        //Se Instancia el Campo de Texto para el nombre del formato de código de barra
        formatTxt = (TextView)findViewById(R.id.scan_format);
        //Se Instancia el Campo de Texto para el contenido  del código de barra
        contentTxt = (TextView)findViewById(R.id.scan_content);
        //Se agrega la clase MainActivity.java como Listener del evento click del botón de Scan
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Se responde al evento click
        if(view.getId()==R.id.scan_button){
            //Se instancia un objeto de la clase IntentIntegrator
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //Se procede con el proceso de scaneo
            scanIntegrator.initiateScan();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Se obtiene el resultado del proceso de scaneo y se parsea
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //Quiere decir que se obtuvo resultado pro lo tanto:
            //Desplegamos en pantalla el contenido del código de barra scaneado
            String scanContent = scanningResult.getContents();//ENVIAR scanContent AL SERVIDOR
            contentTxt.setText("Contenido: " + scanContent);

            MyATaskEscaneo myATaskusuario = new MyATaskEscaneo();

            myATaskusuario.execute(scanContent);


        }else{
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos del escaneo!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class MyATaskEscaneo extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog;
        private ArrayList<Plato> menu;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("Conectando al servidor");
            progressDialog.setMessage("Espera por favor...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... values) {

            try {
                Socket socket = new Socket(server.getAddress(), server.getPort());

                Mensajes m = new Mensajes();

                boolean r = m.pedirMenu(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                        values[0],
                        new BufferedReader(new InputStreamReader(socket.getInputStream())));
                if(!r){
                    System.out.println("___________________________________________ERROR____________________________");
                    //Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                }

                socket.close();
                return "ok";
            } catch (UnknownHostException ex) {
                Log.e("E/TCP Client", "" + ex.getMessage());
                return ex.getMessage();
            } catch (IOException ex) {
                Log.e("E/TCP Client", "" + ex.getMessage());
                return ex.getMessage();
            } catch (NullPointerException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String value) {
            progressDialog.dismiss();//oculta ventana emergente
            Intent intent = new Intent(context, MenuActivity.class);
            startActivity(intent);
        }
    }
}
