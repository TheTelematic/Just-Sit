package es.uma.artjuan.just_sit;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import es.uma.artjuan.just_sit.Dialogos.IntMesasDialog;

public class BarActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Context context = this;
    Button intMesas;
    Bar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        intMesas = (Button)findViewById(R.id.intmesas);

        intMesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntMesasDialog().show(getFragmentManager(), "IntMesasDialog");
            }
        });
    }
}
