package es.uma.artjuan.just_sit;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by artur on 07/05/2017.
 */

public class Mensajes {
    private class Comandos {
        public static final String COMPARA = "COMPARA";
        public static final String ADDNEWUSER = "ADDNEWUSER";



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
}
