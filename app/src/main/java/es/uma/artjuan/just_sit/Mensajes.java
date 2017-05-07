package es.uma.artjuan.just_sit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by artur on 07/05/2017.
 */

public class Mensajes {
    private class Comandos {
        public static final String COMPARA = "COMPARA";
        public static final String ADDNEWUSER = "ADDNEWUSER";
        public static final String RCOMPARA_OK_USUARIO = "COMPARA_OK_USUARIO";
        public static final String RCOMPARA_OK_BAR = "COMPARA_OK_BAR";
        public static final String RCOMPARA_NOOK = "COMPARA_NOOK";
        public static final String TIPO_NORMAL = "normal";
        public static final String TIPO_BAR = "bar";
    }

    public void addnewuser(BufferedWriter out, String username, String password){

        try {
            out.write(Comandos.ADDNEWUSER + " " + username + " " + password);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String es_correcto(BufferedWriter out, String username, String password, BufferedReader in){
        String resultado = "";
        try {
            out.write(Comandos.COMPARA + " " + username + " " + password);
            out.newLine();
            out.flush();

            String tmp = in.readLine();

            if(tmp.equals("OK - " + Comandos.RCOMPARA_OK_USUARIO)){
                resultado = Comandos.TIPO_NORMAL;
            }else if(tmp.equals("OK - " + Comandos.RCOMPARA_OK_BAR)){
                resultado = Comandos.TIPO_BAR;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}
