package DAO;

/**
 * Created by Yo on 07/03/2017.
 */
public class Fruta {
    String nombreFruta,nombreVariedad,nombreAlmacen,medida;
    int stock,codFruta;
    float precio;

    public Fruta(String nombreFruta, String nombreVariedad, String nombreAlmacen, String medida, int stock, float precio) {
        this.nombreFruta = nombreFruta;
        this.nombreVariedad = nombreVariedad;
        this.nombreAlmacen = nombreAlmacen;
        this.medida = medida;
        this.stock = stock;
        this.precio = precio;
    }

    public Fruta(String nombreFruta,int codFruta) {
        this.nombreFruta = nombreFruta;
        this.codFruta=codFruta;
    }

    public String getNombreFruta() {
        return nombreFruta;
    }

    public void setNombreFruta(String nombreFruta) {
        this.nombreFruta = nombreFruta;
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

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCodFruta() {
        return codFruta;
    }

    public void setCodFruta(int codFruta) {
        this.codFruta = codFruta;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
