package dao;

import bd.AccesoBd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Juangra on 06/03/2017.
 */
public class Fruta {

    private static AccesoBd acceso = new AccesoBd();

    private int codigoFruta;
    private String nombreFruta;

    public Fruta(int codigoFruta, String nombreFruta) {
        this.codigoFruta = codigoFruta;
        this.nombreFruta = nombreFruta;
    }

    public Fruta() {
    }

    public String getNombreFruta() {
        return nombreFruta;
    }

    public void setNombreFruta(String nombreFruta) {
        this.nombreFruta = nombreFruta;
    }

    public int getCodigoFruta() {
        return codigoFruta;
    }

    public void setCodigoFruta(int codigoFruta) {
        this.codigoFruta = codigoFruta;
    }

    public static ArrayList<Fruta> obtenerFrutas() throws SQLException, ClassNotFoundException {
        conectar();
        ArrayList<Fruta> frutas = new ArrayList<Fruta>();
        ResultSet rs = acceso.executeQuery("SELECT * FROM `fruta` ORDER BY `codFruta`;");
        while (rs.next()) {
            frutas.add(new Fruta(Integer.parseInt(rs.getString("codFruta")), rs.getString("nombreFruta")));
        }
        return frutas;
    }

    private static void conectar() throws SQLException, ClassNotFoundException {
        acceso.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/frutas2016", "cliente", "cliente");
    }
}
