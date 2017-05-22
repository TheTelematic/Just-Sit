package es.uma.artjuan.just_sit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import static android.R.attr.value;

public class BarActivity extends AppCompatActivity {
    private MesaAdapter dataAdapter = null;
    private Context context = this;
    private Button intMesas;
    private Bar bar;
    private ArrayList<String> mesaList = new ArrayList<>();
    private Button actualizar;


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

        actualizar = (Button) findViewById(R.id.actualizar);

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyATaskACTUALIZAR myATaskmenu = new MyATaskACTUALIZAR();
                myATaskmenu.execute();
            }
        });

        displayListView();
    }
    private void displayListView() {

        for (int i = 0; i<bar.getInstance().getNmesas();i++){
            mesaList.add("Mesa: "+String.valueOf(i+1));
        }


        dataAdapter = new MesaAdapter(this, R.layout.mesa_list, mesaList);
        ListView listView = (ListView) findViewById(R.id.listView2);

        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Llamar a la vista de esta mesa
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class MesaAdapter extends ArrayAdapter<String> {

        private ArrayList<String> mesaList;

        public MesaAdapter(Context context, int textViewResourceId, ArrayList<String> mesaList) {
            super(context, textViewResourceId, mesaList);
            this.mesaList = new ArrayList<String>();
            this.mesaList.addAll(mesaList);
        }

        private class ViewHolder {
            TextView mesa;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.mesa_list, null);
                holder = new ViewHolder();
                holder.mesa = (TextView) convertView.findViewById(R.id.mesai);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mesa.setText(mesaList.get(position));
            if(Bar.getInstance().getOcupado(position)) {
                parent.getChildAt(position).setBackgroundColor(Color.RED);
            }
            return convertView;

        }
    }

    public void actualizarPedidos(View view){

        MyATaskACTUALIZAR myATaskmenu = new MyATaskACTUALIZAR();
        myATaskmenu.execute();


    }

    private class MyATaskACTUALIZAR extends AsyncTask<ArrayList<Plato>,Void,String> {


        @Override
        protected String doInBackground(ArrayList<Plato>... params) {
            try {
                System.out.println("DEBUG 1");
                Socket socket = new Socket(ServerInfo.getInstance().getAddress(), ServerInfo.getInstance().getPort());
                System.out.println("DEBUG 2");
                Mensajes m = new Mensajes();
                System.out.println("DEBUG 2.5");

                if(m.getPedidos(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                        Bar.getInstance().getId(),
                        new BufferedReader(new InputStreamReader(socket.getInputStream())))){
                    System.out.println("DEBUG 3 TRUE");
                }else{
                    System.out.println("DEBUG 3 FALSE");
                }

                System.out.println("DEBUG 4");

                return "TRUE;";
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(String value){

        }
    }

}
