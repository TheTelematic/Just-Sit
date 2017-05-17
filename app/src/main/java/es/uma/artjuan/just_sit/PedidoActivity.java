package es.uma.artjuan.just_sit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class PedidoActivity extends AppCompatActivity {

    private NestedScrollView nsv;
    private TextView cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Pedir la cuenta", Snackbar.LENGTH_LONG)
                        .setAction("PedirLaCuenta",null ).show();
            }
        });

        nsv = (NestedScrollView) findViewById(R.id.scroll_contenido_cuenta);


        cuenta = (TextView) findViewById(R.id.contenido_cuenta);

        List<Plato> platos = Menu.getSingleton().getListaMenu();

        for(Plato p : platos){

            cuenta.setText(cuenta.getText() + "\r\n" + p.getNombre() + );


        }

    }


    private void pedirCuenta(){

    }
}
