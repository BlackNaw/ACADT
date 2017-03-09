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
    private String nombreFruta;
    private int id;

    public Fruta(String nombreFruta, int id) {
        this.nombreFruta = nombreFruta;
        this.id = id;
    }

    public Fruta() {
    }

    public String getNombreFruta() {
        return nombreFruta;
    }

    public void setNombreFruta(String nombreFruta) {
        this.nombreFruta = nombreFruta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList<Fruta> obtenerFrutas() throws SQLException, ClassNotFoundException {
        conectar();
        ArrayList<Fruta> frutas = new ArrayList<Fruta>();
        ResultSet rs = acceso.executeQuery("SELECT * FROM `fruta` ORDER BY `nombreFruta`;");
//        ResultSet rs = acceso.executeQuery("SELECT f.nombreFruta AS fruta, v.nombreVariedad AS variedad , " +
//                "a.nombreAlmacen AS almacen, m.nombreMedida AS medida , d.stock_distribucion AS stock , " +
//                "d.precio_distribucion AS precio FROM `distribucion` d, almacen a, variedad v, medida m, fruta f " +
//                "WHERE v.codFruta = f.codFruta AND a.codAlmacen = d.codAlmacen AND m.codMedida = d.codMedida " +
//                "AND d.codVariedad = v.codVariedad");
        while (rs.next()) {
//            frutas.add(new Fruta(rs.getString("fruta"), rs.getString("variedad"), rs.getString("almacen"),rs.getString("medida"), Integer.parseInt(rs.getString("stock")),Float.parseFloat(rs.getString("precio"))));
            frutas.add(new Fruta(rs.getString("nombreFruta"),rs.getInt("codFruta")));
        }
        return frutas;
    }

    public static void conectar() throws SQLException, ClassNotFoundException {
        acceso.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/frutas2016", "cliente", "cliente");
    }
}
