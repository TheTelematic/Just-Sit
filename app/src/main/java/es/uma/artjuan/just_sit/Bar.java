package es.uma.artjuan.just_sit;

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

    public void setNmesas(int nmesas){
        if(this.nmesas<nmesas) {
            for(int i = this.nmesas; i <= nmesas; i++){
                ocupado.add(false);
            }
        }else{
            ocupado.subList(nmesas,this.nmesas).clear();
        }

        this.nmesas = nmesas;
    }

    public void setOcupado(int p, boolean estado){
        ocupado.set(p, estado);
    }

    public boolean getOcupado(int p){
        return ocupado.get(p);
    }
}
