package es.uma.artjuan.just_sit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.R.string.ok;
import static es.uma.artjuan.just_sit.Pedidos.getMesas;

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
        public static final String RCOMPARA_NOVERIFICADO_BAR = "RCOMPARA_NOVERIFICADO_BAR";
        public static final String GETPEDIDOS = "GETPEDIDOS";
        public static final String RGETPEDIDOS = "RGETPEDIDOS";
        public static final String SETMESAS = "SETMESAS";           //TODO: Comandos pa meter las mesas
        public static final String MESAS_OK = "SETMESAS_OK";
        public static final String START_PEDIDOS = "######START########";
        public static final String END_PEDIDOS = "######END########";
        public static final String MESAS_NOOK = "MESAS_NOOK";
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
                resultado = Comandos.RCOMPARA_NOOK;
            }else if(tmp.equals("OK - " + Comandos.RCOMPARA_NOVERIFICADO_BAR)){
                System.out.println("BAR NO VERIFICADO");
                resultado = Comandos.RCOMPARA_NOVERIFICADO_BAR;
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
            m.setTam(tam);
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

    public String hacerPedido(BufferedWriter out, ArrayList<String> id_platos, int mesa, ArrayList<Integer> cantidad_platos, BufferedReader in) {

        if(id_platos.size() != cantidad_platos.size()){
            return null;
        }

        String estadoPed="";
        try {
            out.write(Comandos.PEDIDO);
            out.newLine();

            out.write(String.valueOf(id_platos.size() + cantidad_platos.size()+1));
            out.newLine();
            out.write(String.valueOf(mesa));
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

    public boolean getPedidos(BufferedWriter out, int bar_id, BufferedReader in){

        try{

            out.write(Comandos.GETPEDIDOS);
            out.newLine();
            out.write(String.valueOf(bar_id));
            out.newLine();

            String ok = in.readLine();

            if(!ok.equals("OK - "+Comandos.RGETPEDIDOS)){
                return false;
            }

            int n_mesas = Integer.parseInt(in.readLine());

            for(int k = 0; k < n_mesas; k++){

                int num_mesa = Integer.parseInt(in.readLine());
                String contenido = "";
                if(in.readLine().equals(Comandos.START_PEDIDOS)){

                    String linea = in.readLine();
                    while(!linea.equals(Comandos.END_PEDIDOS)){
                        contenido += linea;
                        linea = in.readLine();
                    }

                }else{
                    System.out.println("ERROR - 00008");
                    return false;
                }


                Mesa m = new Mesa(num_mesa);
                m.addContenidoPedido(contenido);

                Pedidos.saveMesa(m);


            }

            System.out.println("PEDIDOS DEL BAR");

            for(Mesa m: Pedidos.getMesas()){
                System.out.println("Mesa " + m.getNum_mesa());
                System.out.println("--------------------------------------------------");
                System.out.println("Contenido: " + m.getPlatos());
                System.out.println("--------------------------------------------------");

            }



            return true;
        }catch (IOException e){
            e.printStackTrace();
        }


        return false;
    }

    public String int_mesas(BufferedWriter out, int id_bar, int nmesas, BufferedReader in){     //TODO: Funcion pa meter las mesas
        String mesasint="";
        try {
            out.write(Comandos.SETMESAS);
            out.newLine();
            out.write(String.valueOf(id_bar));
            out.newLine();
            out.write(String.valueOf(nmesas));
            out.newLine();

            mesasint = in.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }
        return mesasint;
    }

}
