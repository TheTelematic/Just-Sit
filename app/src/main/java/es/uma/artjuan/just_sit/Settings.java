package es.uma.artjuan.just_sit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private EditText ip_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ip_address = (EditText) findViewById(R.id.ip_server);
        ip_address.setText(ServerInfo.getInstance().getAddress());

    }

    public void guardar(View view){


        ServerInfo.getInstance().setAddress(ip_address.getText().toString());
        Toast.makeText(this, "IP CAMBIADA A " + ServerInfo.getInstance().getAddress(), Toast.LENGTH_SHORT).show();

    }

}
