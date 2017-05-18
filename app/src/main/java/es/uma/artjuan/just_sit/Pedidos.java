package es.uma.artjuan.just_sit;

import java.util.List;

/**
 * Created by artur on 18/05/2017.
 */

public class Pedidos {
    private List<Plato> platos;
    private int mesa;
    private boolean pagado;

    public Pedidos(List<Plato> platos, int mesa, boolean pagado) {
        this.platos = platos;
        this.mesa = mesa;
        this.pagado = pagado;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
}
