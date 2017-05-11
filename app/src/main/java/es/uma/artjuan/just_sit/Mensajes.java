package es.uma.artjuan.just_sit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by arthur on 07/05/2017.
 */

public class Mensajes {
    public class Comandos {
        public static final String COMPARA = "COMPARA";
        public static final String ADDNEWUSER = "ADDNEWUSER";
        public static final String RCOMPARA_OK_USUARIO = "COMPARA_OK_USUARIO";
        public static final String RCOMPARA_OK_BAR = "COMPARA_OK_BAR";
        public static final String RCOMPARA_NOOK = "COMPARA_NOOK";
        public static final String TIPO_NORMAL = "normal";
        public static final String TIPO_BAR = "bar";
        public static final String RADDNEWUSER_YAEXISTE = "ADDNEWUSER_YAEXISTE";
        public static final String RADDNEWUSER_OK = "ADDNEWUSER_OK";
        public static final String PEDIR_MENU="PEDIRMENU";
        public static final String HACER_PEDIDO="HACERPEDIDO";
        public static final String RPEDIRMENU="RPEDIRMENU";
        public static final String PEDIDO = "PEDIDO";
        public static final String PEDIDO_OK = "PEDIDO_OK";

    }

    public boolean addnewuser(BufferedWriter out, String username, String password, String typeuser, BufferedReader in ){
        String tmp = "";
        try {
            out.write(Comandos.ADDNEWUSER);
            out.newLine();
            out.write(username);
            out.newLine();
            out.write(password);
            out.newLine();
            out.write(typeuser);
            out.newLine();
            out.flush();

            tmp = in.readLine();




        } catch (IOException e) {
            e.printStackTrace();
        }

        if(tmp.equals("OK - " + Comandos.RADDNEWUSER_YAEXISTE)){
            return false;
        }else if(tmp.equals("OK - " + Comandos.RADDNEWUSER_OK)){
            return true;
        }else{

            System.out.println("ERROR - 0000002");

            return false;
        }
    }

    public String es_correcto(BufferedWriter out, String username, String password, BufferedReader in){
        String resultado = "";
        try {
            out.write(Comandos.COMPARA);
            out.newLine();
            out.write(username);
            out.newLine();
            out.write(password);
            out.newLine();
            out.flush();

            String tmp = in.readLine();

            if(tmp.equals("OK - " + Comandos.RCOMPARA_OK_USUARIO)){
                resultado = Comandos.TIPO_NORMAL;
            }else if(tmp.equals("OK - " + Comandos.RCOMPARA_OK_BAR)){
                resultado = Comandos.TIPO_BAR;
            }else if(tmp.equals("OK - " + Comandos.RCOMPARA_NOOK)){
                System.out.println("USUARIO o CONTRASEÑA INCORRECTA");
            }else{
                System.out.println("ERROR - 0000001");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public boolean pedirMenu(BufferedWriter out, String qr, BufferedReader in){

        String nombre="";
        String precio="";
        String id="";
        try {
            out.write(Comandos.PEDIR_MENU);
            out.newLine();
            out.write(qr);
            out.newLine();
            out.flush();
            String ok=in.readLine();
            System.out.println("_______________________ vjbsdfg__________________"+ok);
            if(!ok.equals("OK - "+Comandos.RPEDIRMENU)){
                return false;

            }

            Menu m = Menu.getSingleton();
            int tam=Integer.parseInt(in.readLine());
            for(int i=0; i<tam;i++){
                id = in.readLine();
                nombre = in.readLine();
                precio = in.readLine();
                m.add(id, precio,nombre);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return true;
    }

    public String hacerPedido(BufferedWriter out, ArrayList<String> id_platos, ArrayList<Integer> cantidad_platos, BufferedReader in) {

        if(id_platos.size() != cantidad_platos.size()){
            return null;
        }

        String estadoPed="";
        try {
            out.write(Comandos.PEDIDO);
            out.newLine();
            out.write(String.valueOf(id_platos.size() + cantidad_platos.size()));
            out.newLine();

            for (int i = 0; i < id_platos.size(); i++) {
                out.write(id_platos.get(i));
                out.newLine();
                out.write(String.valueOf(cantidad_platos.get(i)));
                out.newLine();
                out.flush();
            }
            estadoPed=in.readLine();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return estadoPed;
    }

}
