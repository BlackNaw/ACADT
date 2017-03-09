package dao;

/**
 * Created by Juangra on 01/03/2017.
 */
public class Cliente {
    private String nombre;
    private String apellidos;
    private String usuario;
    private String password;

    public Cliente(String nombre, String apellidos, String usuario, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
    }
    public Cliente(String nombre, String apellidos, String usuario) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
    }

    public Cliente() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
