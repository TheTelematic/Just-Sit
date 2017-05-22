package es.uma.artjuan.just_sit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artur on 22/05/2017.
 */

public class Mesa {
    private String platos;
    private int num_mesa;
    private boolean pagado;
    private boolean pendiente;



    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }



    public Mesa(int num_mesa) {
        platos = "-NO HAY NINGUN PEDIDO-";
        this.num_mesa = num_mesa;
        pagado = false;
        pendiente = false;
    }

    public void addContenidoPedido(String pedidos){

        if(!pendiente){
            platos = pedidos;

            pendiente = true;
        }else{
            platos += pedidos;
        }


    }

    public String getPlatos() {
        return platos;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public int getNum_mesa() {
        return num_mesa;
    }


    public boolean equals(Mesa m){

        return (m.getNum_mesa() == this.num_mesa);

    }
}
