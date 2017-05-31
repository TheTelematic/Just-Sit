package es.uma.artjuan.just_sit;

/**
 * Created by Juanca on 31/05/2017.
 */

public class Notificaciones {

    private String username;
    private String email;

    public Notificaciones(String username, String email) {
        this.username = username;
        this.email =email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
