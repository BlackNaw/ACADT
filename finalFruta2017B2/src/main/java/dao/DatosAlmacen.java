package dao;

/**
 * Created by Yo on 08/03/2017.
 */
public class DatosAlmacen {

    private String fruta;
    private String variedad;
    private String almacen;
    private String medida;
    private int stock;
    private float precio;

    public DatosAlmacen(String fruta, String variedad, String almacen, String medida, int stock, float precio) {
        this.fruta = fruta;
        this.variedad = variedad;
        this.almacen = almacen;
        this.medida = medida;
        this.stock = stock;
        this.precio = precio;
    }

    public DatosAlmacen() {
    }

    public String getFruta() {
        return fruta;
    }

    public void setFruta(String fruta) {
        this.fruta = fruta;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
