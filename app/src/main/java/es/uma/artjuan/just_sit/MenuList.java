package es.uma.artjuan.just_sit;

/**
 * Created by Juanca on 10/05/2017.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class MenuList extends Activity {
    private Context context=this;
    private ServerInfo server;
    MyCustomAdapter dataAdapter = null;
    ArrayList<Plato> platoList = new ArrayList<Plato>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        server = ServerInfo.getInstance();
        //Generate list View from ArrayList
        displayListView();

        checkButtonClick();

    }

    private void displayListView() {

        //Array list of countries
        platoList = (ArrayList<Plato>) getIntent().getSerializableExtra("menu");

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.plato_info, platoList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Plato plato = (Plato) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + plato.getNombre(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<Plato> {

        private ArrayList<Plato> platoList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Plato> countryList) {
            super(context, textViewResourceId, countryList);
            this.platoList = new ArrayList<Plato>();
            this.platoList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.plato_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Plato plato = (Plato) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        plato.setMarcado(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Plato plato = platoList.get(position);
            holder.code.setText(" (" +  plato.getPrecio() + "€)");
            holder.name.setText(plato.getNombre());
            holder.name.setChecked(plato.isMarcado());
            holder.name.setTag(plato);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MyATaskMenu myATaskmenu = new MyATaskMenu();
                myATaskmenu.execute(platoList);

            }
        });

    }

    private class MyATaskMenu extends AsyncTask<ArrayList<Plato>,Void,String> {

        ProgressDialog progressDialog;
        String estadoPed="";
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
        protected String doInBackground(ArrayList<Plato>... values){

            try {
                Socket socket = new Socket(server.getAddress(), server.getPort());
                Mensajes m = new Mensajes();

                estadoPed= m.hacerPedido(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                        values[0],
                        new BufferedReader(new InputStreamReader(socket.getInputStream())));

                socket.close();
                return estadoPed;
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
            Toast.makeText(context,estadoPed,Toast.LENGTH_SHORT).show();
        }
    }


}



