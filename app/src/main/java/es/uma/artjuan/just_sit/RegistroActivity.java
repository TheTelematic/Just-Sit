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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static es.uma.artjuan.just_sit.R.id.spinner;

public class RegistroActivity extends AppCompatActivity {
    private Spinner spinner1;
    private EditText user,pass1, pass2;

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
    }

    public void enviar(View view) {

        String selec=spinner1.getSelectedItem().toString();
        if (selec.equals("Bar")) {
            //Añade registro de bar
            Intent i = new Intent(this, EscaneoActivity.class );
            startActivity(i);
        } else
        if (selec.equals("Cliente")) {
            //Añade registro a cliente
            Intent i = new Intent(this, InicioActivity.class );
            startActivity(i);
        }

    }

}



