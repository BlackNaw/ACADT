package controlador;

import bd.AccesoBd;
import dao.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Juangra on 01/03/2017.
 */
public class ControladorCliente {
    private AccesoBd acceso = new AccesoBd();

    public ControladorCliente() throws SQLException, ClassNotFoundException {
        acceso.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pagina2017b", "cliente", "cliente");
    }

    public String insertarCliente(Cliente cliente) throws SQLException, ClassNotFoundException {
        if (acceso.executeUpdate("INSERT INTO cliente" +
                "(IdCliente, NombreCliente, ApellidosCliente, UsuarioCliente, PasswordCliente, FechaAltaCliente)" +
                " VALUES (NULL, '" + cliente.getNombre() + "', '" + cliente.getApellidos() + "', '"
                + cliente.getUsuario() + "', '" + cliente.getPassword() + "', NOW())") > 0) {
            return "Cliente insertado correctamente";
        }
        return "Error al insertar el cliente";
    }

    public Cliente loguearCliente(String usuario, String password) throws SQLException {
        ResultSet rs = acceso.executeQuery("SELECT * FROM cliente WHERE UsuarioCliente = '"+ usuario+"' AND PasswordCliente = '"+ password +"';");
        if (rs.first()) {
            return new Cliente(rs.getString("NombreCliente"), rs.getString("ApellidosCliente"), rs.getString("UsuarioCliente"));
        }
        return null;
    }
}
