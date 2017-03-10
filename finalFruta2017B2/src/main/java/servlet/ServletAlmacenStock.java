package servlet;

import dao.AlmacenStock;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.DeserializationConfig;
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
 * Created by Juangra on 08/03/2017.
 */
@WebServlet(value = "/ServletAlmacenStock", name = "ServletAlmacenStock")
public class ServletAlmacenStock extends HttpServlet {
    String mensaje = "";
    private JSONParser parser;
    private JSONObject jsonObject;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String json = request.getParameter("json");
        ObjectMapper mapperObj = new ObjectMapper();
        mapperObj.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII,true);
        parser = new JSONParser();
        try {
            Object obj = parser.parse(json);
            jsonObject = (JSONObject) obj;
            String accion = (String) jsonObject.get("codFruta");
            ArrayList<AlmacenStock> almacenStocks = AlmacenStock.obtenerAlmacenesStock(Integer.parseInt(accion));
            if (almacenStocks.size() > 0) {
                String jsonStr = mapperObj.writeValueAsString(almacenStocks);
                mensaje = jsonStr;
            }
        } catch (SQLException e) {
            mensaje = "Error " + e.getMessage();
        } catch (ClassNotFoundException e) {
            mensaje = "Error " + e.getMessage();
        } catch (JsonGenerationException e) {
            mensaje = "Error " + e.getMessage();
        } catch (JsonMappingException e) {
            mensaje = "Error " + e.getMessage();
        } catch (IOException e) {
            mensaje = "Error " + e.getMessage();
        } catch (ParseException e) {
            mensaje = "Error " + e.getMessage();
        }
        response.setCharacterEncoding( "UTF-8" );
        response.getWriter().write(mensaje);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
