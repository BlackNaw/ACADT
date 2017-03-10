package dao;

import bd.AccesoBd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Juangra on 08/03/2017.
 */
public class AlmacenStock {

    private static AccesoBd acceso = new AccesoBd();
    private int idDistribucion;
    private String nombreVariedad;
    private String nombreAlmacen;
    private String nombreMedida;
    private int stock;
    private float precio;

    public AlmacenStock(int idDistribucion, String nombreVariedad, String nombreAlmacen, String nombreMedida, int stock, float precio) {
        this.idDistribucion = idDistribucion;
        this.nombreVariedad = nombreVariedad;
        this.nombreAlmacen = nombreAlmacen;
        this.nombreMedida = nombreMedida;
        this.stock = stock;
        this.precio = precio;
    }

    public AlmacenStock(){}

    public static ArrayList<AlmacenStock> obtenerAlmacenesStock(int codFruta) throws SQLException, ClassNotFoundException {
        conectar();
        ArrayList<AlmacenStock> almacenStocks = new ArrayList<AlmacenStock>();
        ResultSet rs = acceso.executeQuery("SELECT d.`IdDistribucion` AS idDistribucion,v.nombreVariedad AS variedad , " +
                "a.nombreAlmacen AS almacen, m.nombreMedida AS medida , d.stock_distribucion AS stock , " +
                "d.precio_distribucion AS precio FROM `distribucion` d, almacen a, variedad v, medida m, fruta f " +
                "WHERE v.codFruta = f.codFruta AND a.codAlmacen = d.codAlmacen AND m.codMedida = d.codMedida " +
                "AND d.codVariedad = v.codVariedad AND f.codFruta = "+ codFruta + " ORDER BY `d`.`idDistribucion`");
        while (rs.next()) {
            almacenStocks.add(new AlmacenStock(rs.getInt("idDistribucion"),rs.getString("variedad"), rs.getString("almacen"),
                    rs.getString("medida"), rs.getInt("stock"),
                    rs.getFloat("precio")));
        }
        return almacenStocks;
    }

    private static void conectar() throws SQLException, ClassNotFoundException {
        acceso.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pagina2017b", "cliente", "cliente");
    }

    public String getNombreVariedad() {
        return nombreVariedad;
    }

    public void setNombreVariedad(String nombreVariedad) {
        this.nombreVariedad = nombreVariedad;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public String getNombreMedida() {
        return nombreMedida;
    }

    public void setNombreMedida(String nombreMedida) {
        this.nombreMedida = nombreMedida;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getIdDistribucion() {
        return idDistribucion;
    }

    public void setIdDistribucion(int idDistribucion) {
        this.idDistribucion = idDistribucion;
    }
}
