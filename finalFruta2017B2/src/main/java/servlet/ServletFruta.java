package servlet;

import dao.Fruta;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Juangra on 06/03/2017.
 */
@WebServlet(value = "/ServletFruta", name = "ServletFruta")
public class ServletFruta extends HttpServlet {
    private String mensaje;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapperObj = new ObjectMapper();
        mapperObj.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII,true);
        try {
            ArrayList<Fruta> frutas = Fruta.obtenerFrutas();
            if (frutas.size() > 0){
                String jsonStr = mapperObj.writeValueAsString(frutas);
                mensaje = jsonStr;
            }
        } catch (SQLException e) {
            mensaje = "Error " +  e.getMessage();
        } catch (ClassNotFoundException e) {
            mensaje = "Error " +  e.getMessage();
        } catch (JsonGenerationException e) {
            mensaje = "Error " +  e.getMessage();
        } catch (JsonMappingException e) {
            mensaje = "Error " +  e.getMessage();
        } catch (IOException e) {
            mensaje = "Error " +  e.getMessage();
        }
        response.setCharacterEncoding( "UTF-8" );
        response.getWriter().write(mensaje);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
