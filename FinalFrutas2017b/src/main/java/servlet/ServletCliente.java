package servlet;

import DAO.Cliente;
import bd.AccesoBd;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yo on 27/02/2017.
 */
@WebServlet(value = "/ServletCliente", name = "ServlevtCliente")
public class ServletCliente extends javax.servlet.http.HttpServlet {
    String json;
    JSONParser parser;
    String mensaje;
    Object obj;
    JSONObject jsonObject;
    String accion;
    AccesoBd acceso;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //Hay que tener añadida la libreria de JSON simple a las librerias
        // del tomcat donde estamos ejecutandolo
        //Pasamos la cadena de caracteres a bytes para poderla codificar en UTF8 y poder usar acentos y ñ
        json = new String(request.getParameter("json").getBytes(), "UTF-8");
        parser = new JSONParser();
        mensaje = "";
        acceso = new AccesoBd();
        try {
            obj = parser.parse(json);
        } catch (ParseException e) {
            mensaje = mensaje + "\nError Parse";
        }
        jsonObject = (JSONObject) obj;

        accion = (String) jsonObject.get("accion");

        if (accion.equals("login")) {
            login(request);
        } else if (accion.equals("registro")) {
            registrar();
        }
        response.setHeader("mensaje", mensaje);

    }

    private void login(javax.servlet.http.HttpServletRequest request) {
        try {
            String usuario = (String) jsonObject.get("usuario");
            String password = (String) jsonObject.get("password");
            acceso.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pagina2017b", "root", "");
            ResultSet rs = acceso.executeQuery("SELECT * FROM `cliente` WHERE `UsuarioCliente` ='"+usuario+"' AND `PasswordCliente`='"+password+"';");
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNombre(rs.getString(2));
                cliente.setApellidos(rs.getString(3));
                request.getSession().setAttribute("cliente",cliente);
                request.getSession().setAttribute("pagina","cliente");
                request.getSession().setAttribute("usuario","cliente");
                ObjectMapper mapperObj = new ObjectMapper();
                String jsonStr = mapperObj.writeValueAsString(cliente);
                mensaje=jsonStr;
            }else{
                mensaje=mensaje+"Error, Usuario o contraseña erróneos";
            }

        } catch (SQLException e) {
            mensaje = mensaje + "\nError en la base de datos";
        } catch (ClassNotFoundException e) {
            mensaje = mensaje + "\nError, clase no encontrada";
        } catch (JsonGenerationException e) {
            mensaje = mensaje + "\nError en el JSON";
        } catch (JsonMappingException e) {
            mensaje = mensaje + "\nError en el JSON";
        } catch (IOException e) {
            mensaje = mensaje + "\nError en el JSON";
        }
    }

    private void registrar() {
        try {
            String nombreCliente = (String) jsonObject.get("nombreCliente");
            String apellidosCliente = (String) jsonObject.get("apellidosCliente");
            String usuarioCliente = (String) jsonObject.get("usuarioCliente");
            String passwordCliente = (String) jsonObject.get("passwordCliente");
            acceso.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pagina2017b", "root", "");
            acceso.executeUpdate("INSERT INTO cliente\n" +
                    "\t(IdCliente, NombreCliente, ApellidosCliente, UsuarioCliente, PasswordCliente, FechaAltaCliente)\n" +
                    "\tVALUES (NULL, '" + nombreCliente + "', '" + apellidosCliente + "', '" + usuarioCliente + "', '" + passwordCliente
                    + "', NOW())");
        } catch (SQLException e) {
            mensaje = mensaje + "\nError en la base de datos";
        } catch (ClassNotFoundException e) {
            mensaje = mensaje + "\nError, clase no encontrada";
        } catch (NoClassDefFoundError e) {
            mensaje = mensaje + "\nError, clase def no encontrada";
        } finally {
            mensaje = mensaje.length() > 0 ? mensaje : "Todo correcto";
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
