package dao;

import org.json.simple.JSONArray;

import java.util.ArrayList;

/**
 * Created by Juangra on 12/03/2017.
 */
public class ClientePedidoCabecera {
    private int idCliente;
    private int codigoAlmacen;
    private String fechaPedido;
    private int finalizado;
    private int albaranGenerado;

    public ClientePedidoCabecera(int idCliente, int codigoAlmacen, String fechaPedido, int finalizado, int albaranGenerado) {
        this.idCliente = idCliente;
        this.codigoAlmacen = codigoAlmacen;
        this.fechaPedido = fechaPedido;
        this.finalizado = finalizado;
        this.albaranGenerado = albaranGenerado;
    }

    public ClientePedidoCabecera(int idCliente, int codigoAlmacen, String fechaPedido, int finalizado) {
        this.idCliente = idCliente;
        this.codigoAlmacen = codigoAlmacen;
        this.fechaPedido = fechaPedido;
        this.finalizado = finalizado;
    }

    public ClientePedidoCabecera() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(int codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(int finalizado) {
        this.finalizado = finalizado;
    }

    public int getAlbaranGenerado() {
        return albaranGenerado;
    }

    public void setAlbaranGenerado(int albaranGenerado) {
        this.albaranGenerado = albaranGenerado;
    }


    public static String generarPedido(ArrayList<Object> jsonArray) {
        for (Object obj: jsonArray) {
            //TODO: Eliminar SOUT
            System.out.println(obj.toString());
        }
        return "";
    }
}
