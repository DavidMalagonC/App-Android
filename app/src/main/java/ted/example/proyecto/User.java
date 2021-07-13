package ted.example.proyecto;

import android.app.Application;

import java.io.Serializable;

public class User extends Application implements Serializable {

    private int rol;
    private int id;

    public User(int id, int rol) {
        this.id = id;
        this.rol = rol;
    }
    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
}
