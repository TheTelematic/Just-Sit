package es.uma.artjuan.just_sit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class BarPedidoActivity extends AppCompatActivity {

    TextView contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                System.out.println( "_afsdfASDFASDFASDF_____________________________________________Token actualizado: " + refreshedToken);

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
            }
        });

        Intent extra = getIntent();
        int num_mesa = extra.getExtras().getInt("numeroMesa");
        setTitle("Mesa: "+String.valueOf(num_mesa));


        contenido = (TextView) findViewById(R.id.contenido_pedido_mesa);

        Pedidos pedidos = Pedidos.getInstance();

        Mesa m = pedidos.getMesa(num_mesa);

        contenido.setText(m.getPlatos());
    }
}
