package es.uma.artjuan.just_sit;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static android.os.Build.VERSION_CODES.M;

public class InicioActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText pass;
    private Button button;
    private Context context = this;
    private String tipouser;
    private ServerInfo server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        usuario=(EditText)findViewById(R.id.nombre_t);
        pass=(EditText)findViewById(R.id.pass_t);

        button = ((Button) findViewById(R.id.enter_b));


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if((usuario.getText().toString().length()>0)&&(pass.getText().toString().length()>0)){
                    MyATaskCliente myATaskusuario = new MyATaskCliente();
                    myATaskusuario.execute(usuario.getText().toString(), pass.getText().toString());

                }else{
                    Toast.makeText(context, "Introduce usuario y contraseña", Toast.LENGTH_LONG).show();
                }
            }
        });

        server = ServerInfo.getInstance();
    }

    private class MyATaskCliente extends AsyncTask<String,Void,String> {

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
                Socket socket = new Socket(server.getAddress(), server.getPort());

                OutputStream sOut = socket.getOutputStream();
                OutputStream sOutp = socket.getOutputStream();

                // Usando la API que da Arturo en la clase Mensajes...

                Mensajes m = new Mensajes();

                tipouser = m.es_correcto(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                        values[0],
                        values[1],
                        new BufferedReader(new InputStreamReader(socket.getInputStream())));

                //Toast.makeText(context, "Usuario logueado tipo "+ tipouser, Toast.LENGTH_LONG).show();

                /*enviaSocket(sOut,values[0]);
                String passcod = values[1];
                codificarPass(passcod);
                enviaSocket(sOutp, passcod);

                InputStream sIn = socket.getInputStream();
                tipouser = recibeSocket(sIn);*/

                socket.close();
                return tipouser;
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
            if(tipouser.equals(Mensajes.Comandos.TIPO_BAR)) {
                //Intent i = new Intent(context, EscaneoActivity.class);
                //startActivity(i);
                Toast.makeText(context, "Usuario logueado tipo "+ tipouser, Toast.LENGTH_LONG).show();
            }else if(tipouser.equals(Mensajes.Comandos.TIPO_NORMAL)){
                Toast.makeText(context, "Usuario logueado tipo "+ tipouser, Toast.LENGTH_LONG).show();
                //Intent i = new Intent(context, RegistroActivity.class);
                //startActivity(i);
            }else{
                Toast.makeText(context, "Usuario o pass incorrecta", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String recibeSocket(InputStream sIn) throws IOException {
        String mensaje="";
        int c;
        while( ( c = sIn.read() ) != (int)'\n' ){
            mensaje+=(char)c;
        }
        return mensaje;
    }

    public void enviaSocket(OutputStream o, String mensaje) throws IOException {
        int longCad;
        longCad = mensaje.length();
        for( int i=0; i < longCad; i++ ){
            o.write( (int)mensaje.charAt( i ) );
        }
        o.write( (int)'\n' );
    }

    public String codificarPass(String pass){
        String cod=pass;

        return cod;
    }

    public void registro(View view) {
        Intent i = new Intent(this,RegistroActivity.class );
        startActivity(i);
    }
}
