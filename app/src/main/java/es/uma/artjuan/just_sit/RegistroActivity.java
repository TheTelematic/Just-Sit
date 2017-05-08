package es.uma.artjuan.just_sit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static es.uma.artjuan.just_sit.R.id.spinner;

public class RegistroActivity extends AppCompatActivity {
    private Spinner spinner1;
    private EditText user,pass1, pass2;
    private Button button;
    private Context context = this;
    private int SERVERPORT = 5051;
    private  String ADDRESS = "192.168.1.90";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        user=(EditText)findViewById(R.id.nom_t);
        pass1=(EditText)findViewById(R.id.pass1_t);
        pass2=(EditText)findViewById(R.id.pass2_t);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        String[] opciones = {"Cliente","Bar"};
        ArrayAdapter <String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner1.setAdapter(adapter);
        button = (Button) findViewById(R.id.enviar_b);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if((user.getText().toString().length()>0)&&(pass1.getText().toString().length()>0)&&(pass2.getText().toString().length()>0)){
                    MyATaskRegistro myATaskusuario = new MyATaskRegistro();
                    if(pass1.getText().toString().equals(pass2.getText().toString())) {
                        myATaskusuario.execute(user.getText().toString(), pass1.getText().toString(),spinner1.getSelectedItem().toString());
                    }else{
                        Toast.makeText(context, "Las contraseñas deben coincidir", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context, "Introduce usuario y contraseña", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class MyATaskRegistro extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("Conectando al servidor");
            progressDialog.setMessage("Espera por favor...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... values){

            try {
                Socket socket = new Socket(ADDRESS, SERVERPORT);

                Mensajes m = new Mensajes();

                if(values[2].equals("Cliente")){
                    m.addnewuser(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), values[0], values[1], "0");
                }else if(values[2].equals("Bar")){
                    m.addnewuser(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), values[0], values[1], "1");
                }

                socket.close();
                return "ok";
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
            progressDialog.dismiss();//oculta ventana emergente

        }
    }


}



