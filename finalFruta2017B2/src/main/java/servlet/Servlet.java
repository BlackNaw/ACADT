package servlet;

import bd.AccesoBd;
import controlador.ControladorCliente;
import dao.Cliente;
import dao.Fruta;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Juangra on 27/02/2017.
 */
@WebServlet(value = "/Servlet", name = "Servlet")

public class Servlet extends HttpServlet {
    private String json;
    private JSONParser parser;
    private String mensaje;
    private JSONObject jsonObject;
    private Object obj = null;
    private Cliente cliente;
    private ControladorCliente controladorCliente;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        json = new String(request.getParameter("json").getBytes(), "UTF-8");
        parser = new JSONParser();

        try {
            obj = parser.parse(json);
            jsonObject = (JSONObject) obj;
            controladorCliente = new ControladorCliente();
        } catch (ParseException e) {
            mensaje = "Error " + e.getMessage();
        } catch (SQLException e) {
            mensaje = "Error " + e.getMessage();
        } catch (ClassNotFoundException e) {
            mensaje = "Error " + e.getMessage();
        }
        String accion = (String) jsonObject.get("accion");
        // Dependiendo de la accion, desivamos a un método u otro
        if (accion.equals("login")) {
            login(request);
        } else if (accion.equals("registro")) {
            registrar();
        }
        response.setHeader("mensaje", mensaje);
    }

    private void login(HttpServletRequest request) {
        ObjectMapper mapperObj = new ObjectMapper();
        try {
            Cliente cliente = controladorCliente.loguearCliente((String) jsonObject.get("usuario"), (String) jsonObject.get("password"));
            if (cliente != null ) {
                request.getSession().setAttribute("cliente", cliente);
                request.getSession().setAttribute("pagina", "cliente");
                String jsonStr = mapperObj.writeValueAsString(cliente);
                mensaje = jsonStr;
            } else {
                mensaje = "Error, Usuario o contraseña erróneos";
            }
        } catch (IOException e) {
            mensaje = "Error " + e.getMessage();
        } catch (SQLException e) {
            mensaje = "Error " + e.getMessage();
        }
    }

    private void registrar() {
        //Hay que tener añadida la libreria de JSON simple a las librerias
        // del tomcat donde estamos ejecutandolo
        cliente = new Cliente((String) jsonObject.get("nombreCliente"), (String) jsonObject.get("apellidosCliente"),
                (String) jsonObject.get("usuarioCliente"), (String) jsonObject.get("passwordCliente"));
        try {
            mensaje = controladorCliente.insertarCliente(cliente);
        } catch (SQLException e) {
            mensaje = "Error " + e.getMessage();
        } catch (ClassNotFoundException e) {
            mensaje = "Error " + e.getMessage();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
