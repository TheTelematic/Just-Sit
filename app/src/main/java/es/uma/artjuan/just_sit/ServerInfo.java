package es.uma.artjuan.just_sit;

/**
 * Created by artur on 08/05/2017.
 */

public class ServerInfo {

    private static final String ADDRESS = "192.168.1.90";
    private static final int PORT = 5051;

    private static String address;
    private static int port;

    private static ServerInfo singleton = null;

    protected ServerInfo(){
        address = ADDRESS;
        port = PORT;
    }

    public static ServerInfo getInstance(){
        if(singleton == null){
            singleton = new ServerInfo();
        }

        return singleton;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}