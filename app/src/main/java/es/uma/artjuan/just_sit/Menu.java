package es.uma.artjuan.just_sit;

import java.util.ArrayList;

/**
 * Created by Juanca on 11/05/2017.
 */

public class Menu {
    private static ArrayList<Plato> listaMenu;

    private static Menu singleton = null;

    protected Menu(){
        listaMenu = new ArrayList<Plato>();
    }

    public static Menu getSingleton() {
        if(singleton == null){
            singleton = new Menu();
        }

        return singleton;
    }

    public static  void add(String id, String precio, String nombre){
        Plato plato= new Plato(id, precio, nombre, false);
        listaMenu.add(plato);

    }

    public ArrayList<Plato> getListaMenu(){
        return listaMenu;
    }
}
