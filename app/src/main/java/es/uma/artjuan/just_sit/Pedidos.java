package es.uma.artjuan.just_sit;

import java.util.ArrayList;
import java.util.List;

import static es.uma.artjuan.just_sit.R.id.mesa;

/**
 * Created by artur on 18/05/2017.
 */

public class Pedidos {

    private List<Mesa> mesas;
    private static Pedidos singleton = null;

    protected Pedidos() {
        this.mesas = new ArrayList<>();
    }

    public static Pedidos getInstance(){
        if(singleton == null){
            singleton = new Pedidos();
        }

        return singleton;
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    public void saveMesa(Mesa m){



        int k = 0;
        while (k < mesas.size()){

            if(mesas.get(k).getNum_mesa() == m.getNum_mesa()){

                Mesa tmp = mesas.get(k);

                tmp.addContenidoPedido(m.getPlatos());

                mesas.set(k, tmp);
                break;

            }

            k++;
        }

        if(k == mesas.size()){

            mesas.add(m);

        }

    }
}
