package dao;

/**
 * Created by Juangra on 12/03/2017.
 */
public class clientePedidoDetalle {
    private int idClientePedido;
    private int codigoVariedad;
    private int codigoMedida;
    private int cantidadPedido;

    public clientePedidoDetalle(int idClientePedido, int codigoVariedad, int codigoMedida, int cantidadPedido) {
        this.idClientePedido = idClientePedido;
        this.codigoVariedad = codigoVariedad;
        this.codigoMedida = codigoMedida;
        this.cantidadPedido = cantidadPedido;
    }

    public clientePedidoDetalle() {
    }

    public int getIdClientePedido() {
        return idClientePedido;
    }

    public void setIdClientePedido(int idClientePedido) {
        this.idClientePedido = idClientePedido;
    }

    public int getCodigoVariedad() {
        return codigoVariedad;
    }

    public void setCodigoVariedad(int codigoVariedad) {
        this.codigoVariedad = codigoVariedad;
    }

    public int getCodigoMedida() {
        return codigoMedida;
    }

    public void setCodigoMedida(int codigoMedida) {
        this.codigoMedida = codigoMedida;
    }

    public int getCantidadPedido() {
        return cantidadPedido;
    }

    public void setCantidadPedido(int cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }
}
