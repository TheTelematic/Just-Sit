package es.uma.artjuan.just_sit;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by Juanca on 17/05/2017.
 */

public class Bar {

    private int nmesas;
    private ArrayList<Boolean> ocupado;
    private static Bar instance = null;


    private Bar(){
        nmesas = 10;
        ocupado =new ArrayList<Boolean>(Arrays.asList(new Boolean[nmesas]));
        Collections.fill(ocupado, Boolean.FALSE);;
    }

    public static Bar getInstance() {
        if(instance == null){
            instance = new Bar();
        }
        return instance;
    }

    public int getNmesas() {
        return nmesas;
    }

    public String setNmesas(int nmesas){
        String intmesas="";
        Mensajes m = new Mensajes();

        if(this.nmesas<nmesas) {
            for(int i = this.nmesas; i <= nmesas; i++){
                ocupado.add(false);
            }
        }else{
            ocupado.subList(nmesas,this.nmesas).clear();
        }

        try {
            ServerInfo server = ServerInfo.getInstance();;
            Socket socket = new Socket(server.getAddress(), server.getPort());

            intmesas=m.int_mesas(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                    nmesas,
                    new BufferedReader(new InputStreamReader(socket.getInputStream())));

            this.nmesas = nmesas;


        }catch (IOException ex) {
            Log.e("E/TCP Client", "" + ex.getMessage());
        }
        return intmesas;
    }

    public void setOcupado(int p, boolean estado){
        ocupado.set(p, estado);
    }

    public boolean getOcupado(int p){
        return ocupado.get(p);
    }
}
